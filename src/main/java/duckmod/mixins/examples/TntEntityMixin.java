/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.examples;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntEntity.class)
public abstract class TntEntityMixin extends Entity {

    public TntEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow private LivingEntity causingEntity;

    @Inject(at = @At("HEAD"), method = "explode()V")
    private void explode(CallbackInfo ci) {
        if (causingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) causingEntity;
            player.sendMessage(new LiteralText("Hello!!"), false);
            player.sendMessage(new LiteralText(Double.toString(getPos().x)), false);
        }

        // this.world.createExplosion(
        //         null, pos.x, getBodyY(0.0625D), pos.z, f, Explosion.DestructionType.BREAK);
    }
}
