package com.matty.main;
import com.matty.listener.RoFListener;
import org.bukkit.plugin.java.JavaPlugin;

public class RingOfFirePlugin extends JavaPlugin{
	private RoFListener listener = new RoFListener(this);
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(listener, this);
		getLogger().info("RoF enabled and events registered");
	}
}
