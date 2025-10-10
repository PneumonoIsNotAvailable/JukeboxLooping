package net.pneumono.jukebox_looping;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JukeboxLooping implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Jukebox Looping");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Jukebox Looping");
	}

	public static boolean shouldJukeboxLoop(WorldAccess world, BlockPos pos) {
		return world.getBlockEntity(pos.down()) == null;
	}
}