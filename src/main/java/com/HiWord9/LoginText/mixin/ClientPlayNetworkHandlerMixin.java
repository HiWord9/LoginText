package com.HiWord9.LoginText.mixin;

import com.HiWord9.LoginText.LoginText;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("TAIL"), method = "onGameJoin")
    private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo info) {
        if (client.player == null) return;
		String configPath = FabricLoader.getInstance().getConfigDir() + "/" + LoginText.MOD_ID + ".properties";
        String nickname = client.player.getGameProfile().getName();
        Text text = Text.of("i love u " + nickname + " ❤").copy().fillStyle(Style.EMPTY.withColor(Formatting.RED));
        if (new File(configPath).exists()) {
            try {
                InputStream inputStream = Files.newInputStream(Path.of(configPath));
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Properties p = new Properties();
                p.load(reader);
                if (p.containsKey("enabled")) {
                    boolean enabled = Objects.equals(p.get("enabled"), "true");
                    if (!enabled) return;
                }
                String customText = (String) p.get("customtext");
                if (customText != null && !customText.isEmpty())
                text = Text.of(customText
                        .replace("%nickname%", nickname)
                        .replace("%color%", "§")
                        .replace("%heart%", "❤"));
            } catch (IOException e) {
                e.printStackTrace();
				return;
            }
        }
        client.inGameHud.setTitle(text);
    }
}
