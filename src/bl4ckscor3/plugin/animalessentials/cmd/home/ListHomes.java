package bl4ckscor3.plugin.animalessentials.cmd.home;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import bl4ckscor3.plugin.animalessentials.cmd.IAECommand;
import bl4ckscor3.plugin.animalessentials.util.Utilities;

public class ListHomes implements IAECommand
{
	@Override
	public void exe(Plugin pl, CommandSender sender, Command cmd, String[] args) throws IOException
	{
		Player p = (Player)sender;
		File f = new File(pl.getDataFolder(), "playerStorage/" + p.getUniqueId() +".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
		List<String> homes = yaml.getStringList("homes");
		String msg = "";

		for(String s : homes)
		{
			msg += s + ", ";
		}

		Utilities.sendChatMessage(p, "These are the homes you have set:");

		if(msg != "")
			Utilities.sendChatMessage(p, "/()" + msg.substring(0, msg.lastIndexOf(','))); //removing the comma at the end
		else
			Utilities.sendChatMessage(p, "None. Use /()/ae sethome()/ to set a home.");
	}

	@Override
	public String getAlias()
	{
		return "listhomes";
	}

	@Override
	public boolean isConsoleCommand()
	{
		return false;
	}

	@Override
	public String[] getHelp()
	{
		return new String[]{
				"Lists all the homes you set."
		};
	}

	@Override
	public String getPermission()
	{
		return "aess.home.list";
	}
	
	@Override
	public List<Integer> allowedArgLengths()
	{
		return Arrays.asList(new Integer[]{1}); // /ae listhomes
	}
	
	@Override
	public String getSyntax()
	{
		return "";
	}
}
