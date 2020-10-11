/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.invend;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Final @Mutable @Shadow public PlayerInventory inventory;

    @Shadow
    public abstract void sendMessage(Text message, boolean actionBar);

    private final double entitySearchRadius = 18;
    private final double blockSearchRadius = 10;

    @Redirect(
            method = "dropInventory",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"))
    protected void preventInventoryDropOnDeath(PlayerInventory inv) {
        if (this.getPos().y < 1) {
            return;
        } else if (this.world
                        .getRegistryKey()
                        .getValue()
                        .compareTo(DimensionType.THE_END_REGISTRY_KEY.getValue())
                == 0) {
            return;
        }

        inv.dropAll();
    }
}
