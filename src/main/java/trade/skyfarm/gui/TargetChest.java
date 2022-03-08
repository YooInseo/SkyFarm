package trade.skyfarm.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.data.PlayerData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TargetChest {

    public static String title = SkyFarm.prefix + "§6교환 메뉴";

    public static HashMap<UUID, PlayerData> playerdata = new HashMap<>();

    public static void TargetChest(Player player, Player Target){

        Inventory inv = Bukkit.createInventory(null, 45, title );
        PlayerData data = new PlayerData(player);
        playerdata.put(player.getUniqueId(), data);

        itemStack(Material.BARRIER,"§c초기화", Arrays.asList(""),1,36,inv);

        new BukkitRunnable() {
            @Override
            public void run() {
                itemStack(Material.GOLD_INGOT,"§f" + SkyFarm.format(1000)  , Arrays.asList("§7(Shift + Click) 가격 * 64"),1,27,inv);
                itemStack(Material.EMERALD,"§f" + SkyFarm.format(10000) , Arrays.asList(""),1,18,inv);
                itemStack(Material.DIAMOND,"§f" + SkyFarm.format(100000) , Arrays.asList(""),1,9,inv);
                itemStack(Material.DIAMOND_BLOCK,"§f" + SkyFarm.format(1000000), Arrays.asList(""),1,0,inv);

                switch (getData(player).getType()){
                    case Plus:
                        itemStack(Material.REDSTONE,"§d설정 §7(클릭해서 변경)", Arrays.asList("§a+ §7(선택됨)" ,"§c-" ,"§b*"),1,40,inv);
                        break;
                    case Minus:
                        itemStack(Material.REDSTONE,"§d설정 §7(클릭해서 변경)", Arrays.asList("§a+" ,"§c- §7(선택됨)","§b*"),1,40,inv);
                        break;
                    case Multiply:
                        itemStack(Material.REDSTONE,"§d설정 §7(클릭해서 변경)", Arrays.asList("§a+" ,"§c-","§b* §7(선택됨)"),1,40,inv);
                        break;
                }

                itemStack(Material.CHEST, "§f" +player.getDisplayName() , Arrays.asList("§7클릭으로 열기"),1,20,inv);
                itemStack(Material.CHEST,"§f" + Target.getDisplayName()   , Arrays.asList("§7클릭으로 열기"),1,24,inv);

                itemStack(getData(player).Change(),getData(player).getCoin()  + "", Arrays.asList(""),1,29,inv);
                itemStack(getData(Target).Change(),getData(Target).getCoin()  + "", Arrays.asList(""),1,33,inv);



            }
        }.runTaskTimer(SkyFarm.plugin, 1, 1);

        Target.openInventory(inv);
        data.setMenu(inv);
    }


    public static  ItemStack itemStack(Material material, String name, List<String> lore, int stack, int loc, Inventory inv){
        ItemStack item = new ItemStack(material,stack);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(loc,item);
        return item;
    }
    public static PlayerData getData(Player player){
        return playerdata.get(player.getUniqueId());
    }
}
