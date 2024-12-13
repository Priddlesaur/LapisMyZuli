package nl.priddle.lapismyzuli;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class LapisMyZuli implements ClientModInitializer {
	public static final String MOD_ID = "lapismyzuli";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initialized LapisMyZuli");
	}
}