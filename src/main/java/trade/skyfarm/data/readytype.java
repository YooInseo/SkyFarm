package trade.skyfarm.data;

public enum readytype {
    Ready("§a⇒준비됨"),NotReady("§c⇒준비안됨"), Accept("§b§l⇒수락");

    String a;
    readytype(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }
}
