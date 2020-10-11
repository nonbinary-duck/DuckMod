/* (CC BY-SA 4.0) 2020 */
package duckmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import duckmod.managers.settings.DuckSetting;
import duckmod.managers.settings.DuckSettings;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class ConfigCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        LiteralArgumentBuilder command =
                (LiteralArgumentBuilder)
                        ((LiteralArgumentBuilder)
                                CommandManager.literal("duckmod")
                                        .requires(
                                                (serverCommandSource) -> {
                                                    return serverCommandSource.hasPermissionLevel(
                                                            DuckSettings.CONFIG_COMMAND_GET_OP_LEVEL
                                                                    .getValue());
                                                }));

        DuckSetting[] settings = DuckSettings.getSettings();

        for (DuckSetting setting : settings) {
            command.then(
                    CommandManager.literal(setting.getName())
                            .executes(
                                    (context) -> {
                                        return DuckSettings.sendSettingFeedback(
                                                setting.getName(), context.getSource());
                                    }));

            command.then(
                    CommandManager.literal(setting.getName())
                            .then(
                                    CommandManager.argument("value", setting.commandArgument())
                                            .executes(
                                                    (context) -> {
                                                        if (!DuckSettings.setPermissionFeedback(context.getSource())) return 0;
                                                        
                                                        return setting.executeCommand(context);
                                                    })));
        }

        dispatcher.register(command);
    }

    protected static int m_executeCommand(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            boolean bool = BoolArgumentType.getBool(context, "enabled");

            player.sendMessage(new LiteralText(bool + ""), false);
            System.out.println(bool + "");

        } catch (Exception e) {
            if (e instanceof CommandSyntaxException) return 0;
        }

        return 1;
    }
}
