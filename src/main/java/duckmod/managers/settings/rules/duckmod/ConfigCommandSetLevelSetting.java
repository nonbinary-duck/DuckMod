/* (CC BY-SA 4.0) 2020 */
package duckmod.managers.settings.rules.duckmod;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import duckmod.managers.settings.Credit;
import duckmod.managers.settings.DuckSetting;
import duckmod.managers.settings.DuckSettings;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class ConfigCommandSetLevelSetting extends DuckSetting<Integer> {
    public ConfigCommandSetLevelSetting() {
        this.value = 0;
    }

    protected final String[] TAGS = {"core config"};

    public String getName() {
        return "duckmod.core.config.setLevel";
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
    public ArgumentType<Integer> commandArgument() {
        return IntegerArgumentType.integer(0, 4);
    }

    @Override
    public int executeCommand(CommandContext<ServerCommandSource> context) {
        try {
            int newValue = IntegerArgumentType.getInteger(context, "value");
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
    public Integer fromString(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public @Nullable Credit credit() {
        return null;
    }
}
