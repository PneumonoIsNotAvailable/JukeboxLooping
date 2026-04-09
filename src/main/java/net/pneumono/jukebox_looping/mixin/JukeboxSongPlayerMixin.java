package net.pneumono.jukebox_looping.mixin;

//? if >=1.21 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.pneumono.jukebox_looping.JukeboxLooping;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JukeboxSongPlayer.class)
public abstract class JukeboxSongPlayerMixin {
    @Shadow
    @Final
    private BlockPos blockPos;
    @Shadow
    @Nullable
    private Holder<JukeboxSong> song;

    @Shadow
    public abstract void play(LevelAccessor world, Holder<JukeboxSong> song);

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/JukeboxSongPlayer;stop(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/block/state/BlockState;)V"
            )
    )
    public void stopPlayingOrLoop(JukeboxSongPlayer instance, LevelAccessor world, BlockState state, Operation<Void> original) {
        if (JukeboxLooping.shouldJukeboxLoop(world, blockPos)) {
            play(world, song);
        } else {
            original.call(instance, world, state);
        }
    }
}
*///?}
