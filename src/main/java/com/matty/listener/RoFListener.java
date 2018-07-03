package com.matty.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;


public class RoFListener implements Listener {
	
	private ArrayList<Player> users;
	private Plugin plugin;
	
	public RoFListener(Plugin plugin){
		this.plugin = plugin;
		users = new ArrayList<Player>();
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent  e) {
		final Player player = e.getPlayer();
		if(player.getEquipment().getItemInMainHand().getType().equals(Material.BLAZE_POWDER)){
			final Location l = player.getLocation();
			for(int i = 1; i < 5; i++){
				final int num = i;
				new BukkitRunnable(){
					public void run() {
						ripple(l,num);
						player.setVelocity(new Vector());
					}
				}.runTaskLater(plugin, 5 * (i-1));
			}
			users.add(player);
			new BukkitRunnable(){
				public void run() {
					users.remove(player);
				}
			}.runTaskLater(plugin, 15);
		}
	}
	
	private void ripple(Location l, int i){
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		explode(l,x+i,y,z);
		explode(l,x-i,y,z);
		explode(l,x,y,z+i);
		explode(l,x,y,z-i);
		explode(l,x+i,y,z+i);
		explode(l,x-i,y,z-i);
		explode(l,x+i,y,z-i);
		explode(l,x-i,y,z+i);
		
	}
	private void explode(Location l, double x, double y, double z){
		l.getWorld().createExplosion(x,y,z, 1.0f, false, false);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		if(e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)){
			if(users.contains(e.getEntity())){
				e.setCancelled(true);
			}
		}
	}
	
}
