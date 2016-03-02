package kioantifly;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class KioAntiFly extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e)
	{
		String worldName = e.getPlayer().getLocation().getWorld().getName();
		String rawMessage = e.getMessage();
		String label = rawMessage.split(" ")[0].toLowerCase();
		if (label.equals("/fly") && canFlyIn(worldName))
		{
			e.getPlayer().sendMessage("Comando fly nao esta permitido neste mundo");
			e.setCancelled(true);
		}else{		
			e.getPlayer().sendMessage("Comando fly permitido neste mundo, bom proveito");
		}
	}
	
	private boolean canFlyIn(String worldName)
	{
		for(String w : getConfig().getStringList("blockedWorlds"))
		{
			if (worldName.equals(w))
			{
				return true;
			}
		}
		return false;
	}
	
}