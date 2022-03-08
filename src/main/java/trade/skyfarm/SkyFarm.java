package trade.skyfarm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.events.InventoryClick;
import trade.skyfarm.events.InventoryClose;

import java.text.DecimalFormat;
import java.util.Arrays;

public final class SkyFarm extends JavaPlugin {


    public final static String prefix =  "§bSystem > §f";

    public static SkyFarm plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.getCommand("거래").setExecutor(new cmds());

        Listener[] listeners = new Listener[]{new InventoryClick(), new InventoryClose()};
        PluginManager pm = Bukkit.getPluginManager();

        Arrays.stream(listeners).forEach(classes->{pm.registerEvents(classes,this);});
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static String format(int a){
        DecimalFormat format = new DecimalFormat("###,###");

        return format.format(a);
    }
}
