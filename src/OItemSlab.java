
public class OItemSlab extends OItemBlock {

    public OItemSlab(int var1) {
        super(var1);
        this.f(0);
        this.a(true);
    }

    public int a(int var1) {
        return var1;
    }

    public String a(OItemStack var1) {
        int var2 = var1.h();

        if (var2 < 0 || var2 >= OBlockStep.a.length) {
            var2 = 0;
        }

        return super.b() + "." + OBlockStep.a[var2];
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE,
                ((OEntityPlayerMP) var2).getPlayer(), this.getBlockInfo(var3, var4, var5, var6, var7),
                null, new Item(var1)))
            return true;
            

        if (var1.a == 0) {
            return false;
        } else if (!var2.d(var4, var5, var6)) {
            return false;
        } else {
            int var8 = var3.a(var4, var5, var6);
            int var9 = var3.c(var4, var5, var6);

            if (var7 == 1 && var8 == OBlock.am.bO && var9 == var1.h()) {
                if (var3.b(var4, var5, var6, OBlock.al.bO, var9)) {
                    var3.a((double) ((float) var4 + 0.5F), (double) ((float) var5 + 0.5F), (double) ((float) var6 + 0.5F), OBlock.al.bZ.c(), (OBlock.al.bZ.a() + 1.0F) / 2.0F, OBlock.al.bZ.b() * 0.8F);
                    --var1.a;
                }

                return true;
            } else {
                return super.a(var1, var2, var3, var4, var5, var6, var7);
            }
        }
    }
}
