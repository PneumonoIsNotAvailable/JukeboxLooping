package net.pneumono.jukebox_looping.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.JukeboxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin {
    @WrapOperation(
            method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;stopPlaying()V"
            )
    )
    public void stopPlayingOrLoop(JukeboxBlockEntity jukebox, Operation<Void> original) {
        if (Objects.requireNonNull(jukebox.getWorld()).getBlockEntity(jukebox.getPos().down()) == null) {
            jukebox.startPlaying();
        } else {
            original.call(jukebox);
        }
    }
}
