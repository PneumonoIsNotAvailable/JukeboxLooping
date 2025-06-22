package net.pneumono.jukebox_looping;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JukeboxLooping implements ModInitializer {
	public static final String MOD_ID = "jukebox_looping";
	public static final Logger LOGGER = LoggerFactory.getLogger("Jukebox Looping");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Jukebox Looping");
	}
}