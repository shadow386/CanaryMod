
public class OItemRecord extends OItem {

    public final String a;

    protected OItemRecord(int var1, String var2) {
        super(var1);
        this.a = var2;
        this.bQ = 1;
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE,
                ((OEntityPlayerMP) var2).getPlayer(),
                this.getBlockInfo(var3, var4, var5, var6, var7), null, new Item(var1)))
            return true;
        if (var3.a(var4, var5, var6) == OBlock.aY.bO && var3.c(var4, var5, var6) == 0) {
            if (var3.F) {
                return true;
            } else {
                ((OBlockJukeBox) OBlock.aY).f(var3, var4, var5, var6, this.bP);
                var3.a((OEntityPlayer) null, 1005, var4, var5, var6, this.bP);
                --var1.a;
                return true;
            }
        } else {
            return false;
        }
    }
}
