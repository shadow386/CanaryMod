
public class OItemMinecart extends OItem {

    public int a;

    public OItemMinecart(int var1, int var2) {
        super(var1);
        this.bQ = 1;
        this.a = var2;
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        int var8 = var3.a(var4, var5, var6);

        if (OBlockRail.d(var8)) {
            if (!var3.F) {
                // CanaryMod: placing of a mine cart
                Block block = new Block(var3.world, var8, var4, var5, var6);
                Player player = ((OEntityPlayerMP) var2).getPlayer();

                if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, player, block, block, new Item(var1))) {
                    return false;
                }
                var3.b((OEntity) (new OEntityMinecart(var3, (double) ((float) var4 + 0.5F), (double) ((float) var5 + 0.5F), (double) ((float) var6 + 0.5F), this.a)));
            }

            --var1.a;
            return true;
        } else {
            return false;
        }
    }
}
