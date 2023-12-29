package com.HiWord9.LoginText;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginText implements ClientModInitializer {
	public static String MOD_ID = "logintext";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("logintext author likes coca-cola zero btw");
	}
}
