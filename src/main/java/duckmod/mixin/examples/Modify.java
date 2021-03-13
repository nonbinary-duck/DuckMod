package duckmod.mixin.examples;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.LivingEntity;


@Mixin(LivingEntity.class)
public class Modify {

    @ModifyConstant(method = "fall", constant = @Constant(doubleValue = 150.0))
    private static double fallParticlesChangeDensity(double original) {
		System.out.println("Density " + original);

        return 40.0 * original;
	}

    @ModifyArg(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"), index = 8)
    private double fallParticlesChangeSpeed(double original) {
        System.out.println("Speed " + original);
        
		return 0;
	}
}
