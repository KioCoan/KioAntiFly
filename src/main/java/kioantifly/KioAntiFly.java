package kioantifly;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class KioAntiFly extends JavaPlugin implements Listener {
	
	private boolean pluginEnabled;
	
	public KioAntiFly()
	{
		setPluginEnabled(true);
	}
	
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		this.getLogger().info("KioAntiFly Started");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable()
	{
		this.getLogger().info("KioAntiFly Disabled");
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e)
	{
		if (!this.isPluginEnabled())
		{
			return;
		}
		String worldName = e.getPlayer().getLocation().getWorld().getName();
		String rawMessage = e.getMessage();
		String label = rawMessage.split(" ")[0].toLowerCase();
		if ((label.equals("/fly") || label.equals("/efly") ) && canFlyIn(worldName))
		{
			e.getPlayer().sendMessage(ChatColor.RED + "Comando fly nao esta permitido neste mundo");
			e.setCancelled(true);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {
		if (!cmd.getName().equalsIgnoreCase("kaf"))
			return false;

		if (args.length == 0 || args.length == 1 && args[0].equalsIgnoreCase("help")) {
			//HelpSection
			sender.sendMessage(ChatColor.AQUA + "========== KioAntiFly Help Section ===========");
			sender.sendMessage(ChatColor.AQUA + "kioantifly/kaf - Comando principal");
			sender.sendMessage(ChatColor.AQUA + "kaf <enable/on> - Ativa o Plugin");
			sender.sendMessage(ChatColor.AQUA + "kaf <disable/off> - Desliga o Plugin");
			sender.sendMessage(ChatColor.AQUA + "kaf <add> <NomeDoMundo> - Adiciona mundo a lista de mundos bloqueados");
			sender.sendMessage(ChatColor.AQUA + "kaf <remove> <NomeDoMundo> - Remove nome do mundo da lista de bloqueados");
			sender.sendMessage(ChatColor.AQUA + "kaf <list> - Exibe listagem dos mundos com fly bloqueados");

		} else if (args[0].equalsIgnoreCase("list")) {

			//List blocked words
			sender.sendMessage(ChatColor.AQUA + "========== Mundos bloqueados ===========");
			for (String world : getConfig().getStringList("blockedWorlds"))
				sender.sendMessage(ChatColor.RED + " - " + world);

		} else if (args[0].equalsIgnoreCase("info")) {
			
			sender.sendMessage(ChatColor.AQUA + "EOQ");
			//Plugin infos
			sender.sendMessage(ChatColor.AQUA + "Plugin desenvolvido por Kio para o servidor Pokedise");
			sender.sendMessage(ChatColor.AQUA + "WWW.POKEDISE.COM.BR");
		
		} else if (args[0].equalsIgnoreCase("reload")) {
		
			if (sender.hasPermission("antiworldfly.use")) {
				try {
					this.reloadConfig();
					sender.sendMessage(ChatColor.AQUA + "KioAntiFly reloaded with sucess");
					getLogger().info("Plugin was reloaded with sucess");
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Failed to reload KioAntiFly");
					getLogger().info("Failed to reload plugin");
					ex.printStackTrace();
				}
			} else
				sender.sendMessage(ChatColor.RED + "Você não tem permissão para usar este comando");
		
		}

		else if (sender.hasPermission("antiworldfly.use")) {

			String action = args[0].toLowerCase();

			if (action.equals("disable") || action.equals("off")) {

				setPluginEnabled(false);
				sender.sendMessage(ChatColor.AQUA + "KioAntiFly foi desabilitado");
				getLogger().info("KioAntiFly was disabled");
			} else if (action.equals("enable") || action.equals("on")) {

				setPluginEnabled(true);
				sender.sendMessage(ChatColor.AQUA + "KioAntiFly foi ativado");
				getLogger().info("KioAntiFly was enabled");
			} else if (action.equals("add") && args.length == 2) {

				List<String> worlds = getConfig().getStringList("blockedWorlds");
				worlds.add(args[1]);
				this.getConfig().set("blockedWorlds", worlds);
				this.saveConfig();
				sender.sendMessage(ChatColor.YELLOW + args[1] + " foi adicionado a lista de mundos com fly bloqueados");
				getLogger().info(args[1] + " was added to blockedWorlds list");

			} else if (action.equals("remove") && args.length == 2) {
				List<String> worlds = getConfig().getStringList("blockedWorlds");
				for (int i = 0; i < worlds.size(); i++) {
					if (worlds.get(i).equals(args[1]))
						worlds.remove(i);
				}
				this.getConfig().set("blockedWorlds", worlds);
				this.saveConfig();
				sender.sendMessage(ChatColor.YELLOW + args[1] + " foi removido da lista de mundos com fly bloqueados");
				getLogger().info(args[1] + " was removed from blockedWorlds list");

			} else
				sender.sendMessage(ChatColor.RED + "Comando usado de forma errada");
		} else
			sender.sendMessage("Você não tem permissão para usar este comando");

		return true;
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

	public boolean isPluginEnabled() {
		return pluginEnabled;
	}

	public void setPluginEnabled(boolean pluginEnabled) {
		this.pluginEnabled = pluginEnabled;
	}
	
}