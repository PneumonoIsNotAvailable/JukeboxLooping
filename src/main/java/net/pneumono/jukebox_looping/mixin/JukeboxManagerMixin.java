package net.pneumono.jukebox_looping.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.jukebox.JukeboxManager;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JukeboxManager.class)
public abstract class JukeboxManagerMixin {
    @Shadow
    @Final
    private BlockPos pos;
    @Shadow
    @Nullable
    private RegistryEntry<JukeboxSong> song;

    @Shadow
    public abstract void startPlaying(WorldAccess world, RegistryEntry<JukeboxSong> song);

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/jukebox/JukeboxManager;stopPlaying(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/block/BlockState;)V"
            )
    )
    public void stopPlayingOrLoop(JukeboxManager instance, WorldAccess world, BlockState state, Operation<Void> original) {
        if (world.getBlockEntity(pos.down()) == null) {
            startPlaying(world, song);
        } else {
            original.call(instance, world, state);
        }
    }
}
