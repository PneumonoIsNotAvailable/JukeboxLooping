package net.pneumono.jukebox_looping;

import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JukeboxLooping implements ModInitializer {
	public static final String MOD_ID = "jukebox_looping";
	public static final Logger LOGGER = LoggerFactory.getLogger("Jukebox Looping");

	public static final TagKey<Block> FORCE_LOOP = TagKey.of(RegistryKeys.BLOCK, id("force_loop"));
	public static final TagKey<Block> BLOCK_LOOP = TagKey.of(RegistryKeys.BLOCK, id("block_loop"));

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Jukebox Looping");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static boolean shouldJukeboxLoop(WorldAccess world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.isIn(FORCE_LOOP)) {
			return true;
		} else if (state.isIn(BLOCK_LOOP)) {
			return false;
		} else {
			return world.getBlockEntity(pos.down()) == null;
		}
	}
}