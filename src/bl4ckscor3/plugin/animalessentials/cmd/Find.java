package bl4ckscor3.plugin.animalessentials.cmd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import bl4ckscor3.plugin.animalessentials.AnimalEssentials;
import bl4ckscor3.plugin.animalessentials.util.Utilities;
import mkremins.fanciful.FancyMessage;

public class Find implements IAECommand
{
	@Override
	public void exe(Plugin pl, CommandSender sender, Command cmd, String[] args) throws IOException
	{
		HashMap<World,List<Entity>> entityWorlds = new HashMap<>();
		List<Entity> foundAnimals = new ArrayList<>();
		String name = putNameTogether(args);
		
		for(World w : pl.getServer().getWorlds()) //looping through all the worlds on the server
		{
			entityWorlds.put(w, w.getEntities()); //putting the worlds in the hashmap above with their entities
		}
		
		for(World w : entityWorlds.keySet()) //looping through the worlds
		{
			for(Entity e : entityWorlds.get(w)) //looping through all the entities of the given world
			{
				if(!Utilities.isAnimal(e)) //just continuing with the next entity if it's not an animal
					continue;
				
				if(((LivingEntity)e).getCustomName() == null) //just continuing with the next entity if it doesn't have a custom name
					continue;
				
				if(!((LivingEntity)e).getCustomName().equalsIgnoreCase(name)) //just continuing with the next entity if it's not having the name we're searching for
					continue;
				
				if(pl.getConfig().getBoolean("find.onlyOwnAnimals") && sender instanceof Player)
				{
					if(Utilities.isOwnedBy((Player)sender, e, false))
						foundAnimals.add(e);
					else
						continue;
				}
				else
					foundAnimals.add(e); //adding the animal which was found to the list
			}
		}
		
		if(foundAnimals.isEmpty())
			Utilities.sendMessage(sender, "No animal was found with the name /()" + name + "()/.");
		else
		{
			for(Entity e : foundAnimals)
			{
				int x = (int)e.getLocation().getX();
				int y = (int)e.getLocation().getY();
				int z = (int)e.getLocation().getZ();
				String worldName = e.getWorld().getName();
				
				FancyMessage msg = new FancyMessage(Utilities.getPrefix())
						.then(Utilities.aN(e.getType().name(), true) + " ")
						.then(Utilities.capitalizeFirstLetter(e.getType().name())).color(ChatColor.BLUE)
						.then(" with the name ")
						.then(((LivingEntity)e).getCustomName()).color(ChatColor.BLUE)
						.then(" was spotted in world ")
						.then(worldName).color(ChatColor.BLUE)
						.then(" at the following coordinates: ")
						.then(Utilities.parseColorCodes(Utilities.getFormattedCoordinates(x, y, z)));
				
				if(sender.hasPermission("aess.aetp"))
					msg.tooltip("Teleport to the animal.").command("/aetp " + worldName + " " + x + " " + y + " " + z + " " + AnimalEssentials.instance.getAetpString());
				
				msg.send(sender);
			}
		}
	}

	private String putNameTogether(String[] args)
	{
		String s = "";
		
		for(int i = 1; i < args.length; i++)
		{
			s += args[i] + " ";
		}
		
		return s.trim();
	}
	
	@Override
	public String getAlias()
	{
		return "find";
	}

	@Override
	public boolean isConsoleCommand()
	{
		return true;
	}

	@Override
	public String[] getHelp()
	{
		return new String[]{
				"Finds an animal with the given name.",
				"You can only find a named animal, unnamed animals won't work!"
		};
	}

	@Override
	public String getPermission()
	{
		return "aess.find";
	}

	@Override
	public List<Integer> allowedArgLengths()
	{
		return Arrays.asList(new Integer[]{2}); // /ae find <name>
	}

	@Override
	public String getSyntax()
	{
		return "<name>";
	}
}
