package trade.skyfarm.data;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.text.DecimalFormat;
import java.util.List;

public class PlayerData {

    private int Coin;
    private Player player;
    private Player request;
    private readytype ready = readytype.NotReady;
    private Inventory inv = null;
    private Inventory menu = null;
    private boolean isSetCoin = false;
    private boolean isOpen = false;

    public PlayerData(Player player){
        this.player = player;
    }

    public int getCoin() {
        return Coin;
    }

    public void SetCoins(int coin){
            this.Coin = coin;
    }

    public Player getPlayer() {
        return player;
    }

    public readytype getReady() {
        return ready;
    }

    public void setReady(readytype ready) {
            this.ready = ready;
    }

    public Inventory getInv() {
        return inv;
    }

    public Inventory createInv(Player player){
        if(this.inv == null){
            Inventory inv = Bukkit.createInventory(null,54, player.getDisplayName());
            this.inv = inv;
            return inv;
        }
        return null;
    }

    public Inventory OpenMenu(Player player){
        player.openInventory(menu);
        return menu;
    }

    public void setMenu(Inventory menu) {
        this.menu = menu;
    }

    public boolean isSetCoin() {
        return isSetCoin;
    }

    public void setisSetCoin(boolean setCoin) {
        isSetCoin = setCoin;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setRequest(Player request) {
        this.request = request;
    }

    public Player getRequest() {
        return request;
    }

    public ItemStack addContent(Player player){

        return null;
    }

    public ItemStack giveItemToOther(){
        ItemStack item = null;
        for(ItemStack items : getInv().getContents().clone()){
            if(items != null){
                item = items;
                player.getInventory().addItem(items);
            }
        }
        return item;
    }

    public ItemStack giveSelf(){
        ItemStack item = null;
        for(ItemStack items : getInv().getContents().clone()){
            if(items != null){
                item = items;
                request.getInventory().addItem(items);
            }
        }
        return item;
    }

    public ItemStack playerhead(Inventory inv, List<String> lore  , int Loc){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD); // Create a new ItemStack of the Player Head type.
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties
        skullMeta.setDisplayName( player.getName());
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getName())); // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).
        skullMeta.setLore(lore);
        skull.setItemMeta(skullMeta); // Apply the modified meta to the initial created item
        inv.setItem(Loc,skull);
        return skull;
    }
    public String Format(){
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(getCoin());
    }

}

