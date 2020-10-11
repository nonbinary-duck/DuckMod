# A guide to forking and contributing to DuckMod

Hai!

## Forking

If you want to have a setting registered in the `/duckmod` command, you need to create a class that extends DuckSetting (for example `duckmod.managers.settings.rules.fun.ExtraDamageCritsEnabledSetting`). You can then create an instance of that setting in `duckmod.managers.settings.DuckSettings` by declaring something like this:

```java
public static YourAmazingSetting AMAZING_SETTING =
        new YourAmazingSetting();
```

Then by registering it with the manager `settings`:

```java
public static void initSettings() {
    [...]
    // Contributor's settings go after this please!
    settings.put(AMAZING_SETTING.getName(), AMAZING_SETTING);
    [...]
}
```

My mod doesn't use FabricAPI!! Everything is done through Mixins, directly altering Minecraft's code. This can be dangerous if you have lots of straight-up overwrites of minecraft functions. Obviously injections are harmless (usually!), so feel free to go mad with them!

You'll need to register any Mixins you make in [`/src/main/resources/villanelle-duckmod.mixins.json`](src/main/resources/villanelle-duckmod.mixins.json). All Mixins will need to be in the [mixins folder](src/main/java/duckmod/mixins/).

Check out my example mixins for help! Other helpful locations to check for info on writing fabric mods is [The Fabric Wiki](https://fabricmc.net/wiki/doku.php) and the [Sponge Mixin Wiki](https://github.com/SpongePowered/Mixin/wiki). If you didn't know, fabric uses the Sponge Mixin framework.

You absolutley need a copy of the minecraft source code to write mixins. You can get it by running `gradle genSources` and you'll find a copy of Minecraft's entire source code under the [gradle cache directory](https://docs.gradle.org/current/userguide/directory_layout.html).

For example, on linux, the source code was found under `~/.gradle/caches/fabric-loom/minecraft-1.16.2-mapped-net.fabricmc.yarn-1.16.2+build.47-v2-sources.jar`. You can then use any archive manager (for example 7zip) to extract the source from the jar!

You'll find Minecraft's asset folder in the jar that isn't the sources.

If you were wondering, I use VSCode.

## Contributing

It will make my day if someone creates a pull request! But please don't give me a headache after making my day so good!

### Styling Guidelines

All of your new Mixins should be stored in your own folder (named as your GitHub username) in the same folder where I store my Mixins (named `mixins/`).

Try your best to keep your code clean and organised please!

### Rules

- Your contribution cannot in any way resemble or be bigoted. If it is, your pull request will be denied and I will personally block you.
- I have soul discretion in deciding what pull requests to accept and reserve the right to revert the pull request at any time.
- Your contribution must be made in any licence equal to or more permissive than [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/). If your pull request doesn't specify a licence, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/) will be assumed.
- Your contribution must have a Credit object attached to any DuckSetting class.
- Your pull request should adhere to rough styling guidelines.
- You must run `gradle spotlessApply` before submitting a pull request.
- Your contribution cannot breach copyright law.
- Your contribution cannot be in breach of any UK law.
- Your contribution cannot breach GitHub TOS.