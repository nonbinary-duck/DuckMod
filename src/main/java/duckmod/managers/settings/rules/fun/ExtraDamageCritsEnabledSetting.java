/* (CC BY-SA 4.0) 2020 */
package duckmod.managers.settings.rules.fun;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import duckmod.managers.settings.Credit;
import duckmod.managers.settings.DuckSetting;
import duckmod.managers.settings.DuckSettings;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class ExtraDamageCritsEnabledSetting extends DuckSetting<Boolean> {
    public ExtraDamageCritsEnabledSetting() {
        this.value = true;
    }

    protected final String[] TAGS = {"fun", "feature"};

    public String getName() {
        return "fun.extraDamageCrits.enabled";
    }

    @Override
    public String descriptionID() {
        return "command.villanelle-duckmod.settings.descriptions.fun.extra_damage_crits";
    }

    @Override
    public String[] tags() {
        return this.TAGS;
    }

    @Override
    public ArgumentType<Boolean> commandArgument() {
        return BoolArgumentType.bool();
    }

    @Override
    public int executeCommand(CommandContext<ServerCommandSource> context) {
        try {
            boolean newValue = BoolArgumentType.getBool(context, "value");
            ServerCommandSource source = context.getSource();

            if (newValue == this.getValue()) {
                source.sendFeedback(this.getSoftFailChangedMessage(), false);
                return 1;
            }

            this.setValue(newValue);
            DuckSettings.saveSettings();

            try {
                ServerPlayerEntity player = source.getPlayer();
                source.sendFeedback(this.getChangedMessage(player.getName().asString()), true);
            } catch (CommandSyntaxException e) {
                source.sendFeedback(this.getChangedMessage(), true);
            }

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    public Boolean fromString(String str) {
        return Boolean.parseBoolean(str);
    }

    @Override
    public @Nullable Credit credit() {
        return null;
    }
}
