package trade.skyfarm.data;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import trade.skyfarm.gui.Type;

public class PlayerData {

    private Type type = Type.Plus ;
    private int Coin;
    private Inventory inv;
    private Player player;
    private Inventory menu;

    public PlayerData(Player player){
        this.player = player;
    }
    public int getCoin() {
        return Coin;
    }

    public Type getType() {
        return type;
    }


    public void addCoin(int Coin){
        this.Coin += Coin;
    }

    public void SetCoins(int coin){
        switch (type){
            case Plus:
                Coin += coin;
                break;
            case Minus:
                if(!(Coin - coin < 0)){

                    Coin -= coin;
                }
                break;
            case Multiply:

                break;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void MinusCoin(int Coin){
        this.Coin -= Coin;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Material Change(){

        if(getCoin() < 1000){
            return Material.GOLD_INGOT;
        } else if (getCoin() < 10000) {
            return Material.EMERALD;
        } else if (getCoin() < 100000) {
            return Material.DIAMOND;
        }  else if (getCoin() < 1000000 ) {
            return Material.DIAMOND_BLOCK;
        }
        return Material.GOLD_INGOT;
    }

    public void openinv(Player player){
        inv = Bukkit.createInventory(null,45, player.getDisplayName());
        player.openInventory(inv);
    }

    public void setMenu(Inventory menu) {
        this.menu = menu;
    }

    public void openMenu(){
        player.openInventory(menu);
    }
}
