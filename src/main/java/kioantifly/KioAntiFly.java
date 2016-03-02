package kioantifly;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class KioAntiFly extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable()
	{
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
		System.out.println("dsadasfsa");
		String rawMessage = e.getMessage();
		String label = rawMessage.split(" ")[0].toLowerCase();
		if (label.equals("/fly") && canFlyIn(worldName))
		{
			System.out.println("Comando fly nao esta permitido");
			e.setCancelled(true);
		}else{		
			System.out.print("Comando fly permitido neste mundo");
		}
		
	}
	
	private boolean canFlyIn(String worldName)
	{
		if (worldName.equals("flatroom"))
		{
			return true;
		}else{
			return false;
		}
	}
	
}