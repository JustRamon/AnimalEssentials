package bl4ckscor3.plugin.animalessentials.core;

import org.bukkit.plugin.Plugin;

import bl4ckscor3.plugin.animalessentials.util.Utilities;

public class Config
{
	/**
	 * Sets up the config when the server is started
	 */
	public static void createConfig(Plugin pl)
	{
		pl.reloadConfig();
		pl.getConfig().addDefault("shouldNamingUseNametag", true);
		pl.getConfig().addDefault("allowMultipleCommands", false);
		pl.getConfig().addDefault("update.force", true);
		pl.getConfig().addDefault("update.check", true);
		pl.getConfig().addDefault("find.onlyOwnAnimals", false);
		pl.getConfig().addDefault("find.allowUsersToTp", true);
		pl.getConfig().addDefault("find.keyword", "comesFromLink");
//		pl.getConfig().addDefault("xp.useForTp", true);
//		pl.getConfig().addDefault("xp.amount", 20);
		pl.getConfig().options().copyDefaults(true);
		pl.saveConfig();
		Utilities.sendConsoleMessage("Config successfully created/enabled!");
	}
}
