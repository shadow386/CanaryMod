import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class OItemPotion extends OItem {

    private HashMap a = new HashMap();

    public OItemPotion(int var1) {
        super(var1);
        this.e(1);
        this.a(true);
        this.f(0);
    }

    public List b(OItemStack var1) {
        return this.b(var1.h());
    }

    public List b(int var1) {
        List var2 = (List) this.a.get(Integer.valueOf(var1));

        if (var2 == null) {
            var2 = OPotionHelper.a(var1, false);
            this.a.put(Integer.valueOf(var1), var2);
        }

        return var2;
    }

    public OItemStack b(OItemStack var1, OWorld var2, OEntityPlayer var3) {
        --var1.a;
        if (!var2.I) {
            List var4 = this.b(var1);

            if (var4 != null) {
                Iterator var5 = var4.iterator();

                while (var5.hasNext()) {
                    OPotionEffect var6 = (OPotionEffect) var5.next();

                    var3.e(new OPotionEffect(var6));
                }
            }
        }

        if (var1.a <= 0) {
            return new OItemStack(OItem.bs);
        } else {
            var3.k.a(new OItemStack(OItem.bs));
            return var1;
        }
    }

    public int c(OItemStack var1) {
        return 32;
    }

    public OEnumAction d(OItemStack var1) {
        return OEnumAction.c;
    }

    public OItemStack a(OItemStack var1, OWorld var2, OEntityPlayer var3) {
        if (c(var1.h())) {
            --var1.a;
            var2.a(var3, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
            if (!var2.I) {
                var2.b((OEntity) (new OEntityPotion(var2, var3, var1.h())));
            }

            return var1;
        } else {
            var3.a(var1, this.c(var1));
            return var1;
        }
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        return (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE,
                ((OEntityPlayerMP) var2).getPlayer(),
                this.getBlockInfo(var3, var4, var5, var6, var7), null, new Item(var1));
    }

    public static boolean c(int var0) {
        return (var0 & 16384) != 0;
    }
}
