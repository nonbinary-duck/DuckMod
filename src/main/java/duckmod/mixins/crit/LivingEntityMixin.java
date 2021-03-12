/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.crit;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract void applyDamage(DamageSource source, float amount);

    @Redirect(
            method = "damage",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"))
    protected void changeDamage(LivingEntity self, DamageSource source, float amount) {
        // Disallow direct damage such as fire etc.
        if (source.getSource() != null) {
            // Disallow arrows
            if (source.getSource() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) source.getSource();
                ItemStack stack = attacker.getMainHandStack();
                Item hand = stack.getItem();

                // Thank god that the strength effect applies to base attack damage
                float baseDamage =
                        (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                float enchantedDamage = EnchantmentHelper.getAttackDamage(stack, self.getGroup());

                float newDamage =
                        canApplyExtra(attacker, amount, hand, stack, baseDamage, enchantedDamage);

                if (newDamage > 0F) {
                    applyDamage(source, newDamage);
                    return;
                }
            }
        }

        applyDamage(source, amount);
    }

    protected float canApplyExtra(
            LivingEntity attacker,
            float damage,
            Item hand,
            ItemStack stack,
            float baseDamage,
            float enchantedDamage) {

        if (hand instanceof SwordItem || hand instanceof AxeItem || true) {
            float critDamage = ((baseDamage * 1.5F) + enchantedDamage);

            if (critDamage == damage && attacker.fallDistance >= 1F) {
                float newDamage =
                        baseDamage
                                + enchantedDamage
                                + (baseDamage
                                        * (float)
                                                Math.max(
                                                        0.5D,
                                                        Math.pow(
                                                                        Math.log(
                                                                                Math.pow(
                                                                                        attacker.fallDistance,
                                                                                        1.35D)),
                                                                        2.5D)
                                                                + 0.35D));

                return newDamage;
            }

            return -1F;
        } else {
            return -1F;
        }
    }
}
