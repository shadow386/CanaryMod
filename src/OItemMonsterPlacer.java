
public class OItemMonsterPlacer extends OItem {

    public OItemMonsterPlacer(int var1) {
        super(var1);
        this.e(1);
        this.a(true);
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        if (var3.I || var1.h() < 50 || (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE,
                ((OEntityPlayerMP) var2).getPlayer(),
                this.getBlockInfo(var3, var4, var5, var6, var7), null, new Item(var1))) {
            return true;
        } else {
            var4 += OFacing.b[var7];
            var5 += OFacing.c[var7];
            var6 += OFacing.d[var7];
            OEntity var8 = OEntityList.a(var1.h(), var3);

            if (var8 != null) {
                if (!var2.L.d) {
                    --var1.a;
                }

                var8.c((double) var4 + 0.5D, (double) var5, (double) var6 + 0.5D, 0.0F, 0.0F);
                var3.b(var8);
            }

            return true;
        }
    }
}
