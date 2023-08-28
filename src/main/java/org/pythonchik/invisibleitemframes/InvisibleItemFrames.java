package org.pythonchik.invisibleitemframes;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
public final class InvisibleItemFrames extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {getServer().getPluginManager().registerEvents(this, this);}
    @Override
    public void onDisable() {}
    @EventHandler
    public void PlayerShearEntityEvent(PlayerInteractEntityEvent event){
        if ((event.getRightClicked().getType().equals(EntityType.ITEM_FRAME) || event.getRightClicked().getType().equals(EntityType.GLOW_ITEM_FRAME)) && event.getPlayer().getInventory().getItem(event.getHand()).getType().equals(Material.SHEARS) && !event.getPlayer().isSneaking()){
            if (event.getRightClicked().getPersistentDataContainer().has(new NamespacedKey(this,"Invisible"),PersistentDataType.BOOLEAN)) return;
            event.getRightClicked().getPersistentDataContainer().set(new NamespacedKey(this,"Invisible"),PersistentDataType.BOOLEAN,Boolean.TRUE);
            if (event.getPlayer().getWorld().getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK)) {
                event.getPlayer().getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK,false);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute as @e[nbt={BukkitValues:{\"invisibleitemframes:invisible\":1b},Invisible:0b},limit=1] run data modify entity @s Invisible set value 1");
                event.getPlayer().getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK,true);
            } else{
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute as @e[nbt={BukkitValues:{\"invisibleitemframes:invisible\":1b},Invisible:0b},limit=1] run data modify entity @s Invisible set value 1");
            }
            event.setCancelled(true);
        }
    }
}