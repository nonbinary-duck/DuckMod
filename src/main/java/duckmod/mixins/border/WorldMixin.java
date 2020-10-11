/* (CC BY-SA 4.0) 2020 */
package duckmod.mixins.border;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(World.class)
public class WorldMixin {
    @Overwrite
    private static boolean isValid(BlockPos pos) {
        return true;
    }
}
