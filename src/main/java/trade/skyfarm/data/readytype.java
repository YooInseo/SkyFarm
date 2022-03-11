package trade.skyfarm.data;

public enum readytype {
    Ready("§a준비됨"),NotReady("§c준비안됨"), Accept("§b§l수락");

    String a;
    readytype(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }
}
