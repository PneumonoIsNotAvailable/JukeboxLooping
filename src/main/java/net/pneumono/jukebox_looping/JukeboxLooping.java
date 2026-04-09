package net.pneumono.jukebox_looping;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JukeboxLooping implements ModInitializer {
	public static final String MOD_ID = "jukebox_looping";
	public static final Logger LOGGER = LoggerFactory.getLogger("Jukebox Looping");

	public static final TagKey<Block> FORCE_LOOP = TagKey.create(Registries.BLOCK, id("force_loop"));
	public static final TagKey<Block> BLOCK_LOOP = TagKey.create(Registries.BLOCK, id("block_loop"));

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Jukebox Looping");
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.tryBuild(MOD_ID, path);
	}

	public static boolean shouldJukeboxLoop(LevelAccessor world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.is(FORCE_LOOP)) {
			return true;
		} else if (state.is(BLOCK_LOOP)) {
			return false;
		} else {
			return world.getBlockEntity(pos.below()) == null;
		}
	}
}