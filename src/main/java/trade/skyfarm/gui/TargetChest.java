package trade.skyfarm.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.data.PlayerData;
import trade.skyfarm.data.readytype;

import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

public class TargetChest {

    public static String title = SkyFarm.prefix + "§6교환 메뉴 ";
    /**
     * @param player Who's gonna send request to trade to Target;
     * @param Target Who's Recived request from player;
     */
    public static Inventory TargetChest(Player player, Player Target){
        Inventory inv = Bukkit.createInventory(null, 45, title + Target.getName());
        itemStack(Material.BARRIER,"§c초기화", Arrays.asList(""),1,36,inv);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!player.equals(null)){
                    itemStack(Material.GOLD_INGOT,"§f" + SkyFarm.format(1000)  , Arrays.asList("§7(Shift + Click) 가격 * 64"),1,27,inv);
                    itemStack(Material.EMERALD,"§f" + SkyFarm.format(10000) , Arrays.asList(""),1,18,inv);
                    itemStack(Material.DIAMOND,"§f" + SkyFarm.format(100000) , Arrays.asList(""),1,9,inv);
                    itemStack(Material.DIAMOND_BLOCK,"§f" + SkyFarm.format(1000000), Arrays.asList(""),1,0,inv);

                    switch (getreceive(player).getType()) {
                        case Plus:
                            itemStack(Material.REDSTONE, "§d설정 §7(클릭해서 변경)", Arrays.asList("§a+ §7(선택됨)", "§c-", "§b*"), 1, 40, inv);
                            break;
                        case Minus:
                            itemStack(Material.REDSTONE, "§d설정 §7(클릭해서 변경)", Arrays.asList("§a+", "§c- §7(선택됨)", "§b*"), 1, 40, inv);
                            break;
                        case Multiply:
                            itemStack(Material.REDSTONE, "§d설정 §7(클릭해서 변경)", Arrays.asList("§a+", "§c-", "§b* §7(선택됨)"), 1, 40, inv);
                            break;
                    }
                    itemStack(Material.CHEST, "§f" +player.getDisplayName() , Arrays.asList("§7클릭으로 열기"),1,11,inv);
                    itemStack(Material.CHEST,"§f" + Target.getDisplayName()   , Arrays.asList("§7클릭으로 열기"),1,15,inv);
                    itemStack(getreceive(player).Change(),getreceive(player).getCoin()  + "", Arrays.asList(""),1,20,inv);
                    itemStack(getreceive(Target).Change(),getreceive(Target).getCoin()  + "", Arrays.asList(""),1,24,inv);
                    itemStack(getreceive(Target).Ready(),getreceive(player).getReady().getA(), Arrays.asList(""),1,33,inv);
                    itemStack(getreceive(player).Ready(),getreceive(player).getReady().getA(), Arrays.asList(""),1,29,inv);
                }
            }

        }.runTaskTimer(SkyFarm.plugin, 1, 1);
        Target.openInventory(inv);
        return inv;
    }




    public static void TradeMenu(Player player,Player request){
        Inventory inv = Bukkit.createInventory(null,45, player.getDisplayName() + "/" + request.getDisplayName());

        request.openInventory(inv);
        player.openInventory(inv);
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
    public static PlayerData getreceive(Player player){
        if(cmds.receive.containsKey(player.getUniqueId())){
            return cmds.receive.get(player.getUniqueId());
        } else{

            return cmds.receive.get(player.getUniqueId());
        }
    }

    public static void AcceptTrade(Player player,Player target){
        if(TargetChest.getreceive(player).getReady().equals(readytype.Accept) && TargetChest.getreceive(target).getReady().equals(readytype.Accept)  ){
            player.closeInventory();
            target.closeInventory();
        }
    }
}
