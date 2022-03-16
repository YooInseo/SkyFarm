package trade.skyfarm;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.events.ChatEvent;
import trade.skyfarm.events.InventoryClick;
import trade.skyfarm.events.InventoryClose;
import java.util.Arrays;

public final class SkyFarm extends JavaPlugin {

    public final static String prefix =  "§cSystem > §f";
    public static SkyFarm plugin;
    private Economy econ;

    public void onEnable() {
        plugin = this;
        this.getCommand("거래").setExecutor(new cmds());
        Listener[] listeners = new Listener[]{new InventoryClick(), new InventoryClose(), new ChatEvent()};
        PluginManager pm = Bukkit.getPluginManager();
        Arrays.stream(listeners).forEach(classes->{pm.registerEvents(classes,this);});
        setupEconomy();
    }
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getConsoleSender().sendMessage("§c이 플러그인은 Vault와 Economy플러그인이 필요합니다!");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public Economy getEconomy() {
        return econ;
    }
}
