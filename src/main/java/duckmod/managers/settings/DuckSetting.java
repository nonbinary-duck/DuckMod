/* (CC BY-SA 4.0) 2020 */
package duckmod.managers.settings;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

public abstract class DuckSetting<T> {
    /** Use this to init the setting with a default value */
    public DuckSetting() {}

    /**
     * A message to send the user when they fail to update a setting because the value is already
     * what they requested
     */
    public Text getSoftFailChangedMessage() {
        return new TranslatableText(
                "command.villanelle-duckmod.setting.soft_fail", getMessageName(), toMessageString());
    }

    /** A message to send OPs when a setting is updated */
    public Text getChangedMessage() {
        return getChangedMessage("");
    }

    /**
     * (optional)
     * A message to send OPs and the user when a setting is updated
     *
     * @param changedBy (optional) The name of the PlayerEntity which updated the setting
     */
    public Text getChangedMessage(String changedBy) {
        if (changedBy == "") {
            return new TranslatableText(
                    "command.villanelle-duckmod.setting.op_value_changed", getMessageName(), toMessageString());
        } else {
            return new TranslatableText(
                    "command.villanelle-duckmod.setting.op_value_changed.extra",
                    getMessageName(),
                    toMessageString(),
                    "§c§l§o" + changedBy + "§r");
        }
    }

    /**
     * (optional)
     * A message to send the user when they request what the current value of a setting is
     */
    public Text getGetMessage() {
        return new TranslatableText("commands.messages.setting.getValue", getMessageName(), toMessageString());
    }

    /** The value of your setting */
    protected T value;

    /**
     * The name must always be unique otherwise it will possibly crash the deserializer. This is a
     * raw string, not translatable.
     */
    public abstract String getName();

    /**
     * (optional) 
     * A formatted version of getName (should end with §r)
     */
    public String getMessageName() {
        return "§d§l" + getName() + "§r";
    }

    /** The translatable text ID of the description */
    public abstract String descriptionID();

    @Nullable
    /** A name to credit the setting to */
    public abstract Credit credit();

    /** The tags which your setting belongs to */
    public abstract String[] tags();

    /** The argumentType to be given to the node for your command */
    public abstract ArgumentType<T> commandArgument();

    /** Called when the config command node is executed */
    public abstract int executeCommand(CommandContext<ServerCommandSource> context);

    /**
     * This is needed to make sure the value can be serialized. Must not contain § at the start of a
     * new line. Users are fed this value (by default) when they set or get the value
     */
    public abstract String toString();

    /**
     * (optional)
     * Should be toString but with fancy formatting like §l (bold). Must always end with §r
     */
    public String toMessageString() {
        return "§5§l" + toString() + "§r";
    }

    /** This is needed to make sure the value can be deserialized */
    @Nullable
    public abstract T fromString(String str);

    /** Optional */
    public T getValue() {
        return value;
    }

    /** Optional Should call DuckSettings. */
    public void setValue(T newValue) {
        value = newValue;
    }
}
