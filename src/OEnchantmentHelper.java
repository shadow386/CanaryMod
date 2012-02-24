import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class OEnchantmentHelper {

    private static final Random a = new Random();
    private static final OEnchantmentModifierDamage b = new OEnchantmentModifierDamage((OEmpty1) null);
    private static final OEnchantmentModifierLiving c = new OEnchantmentModifierLiving((OEmpty1) null);

    public OEnchantmentHelper() {
        super();
    }

    public static int a(int var0, OItemStack var1) {
        if (var1 == null) {
            return 0;
        } else {
            ONBTTagList var2 = var1.p();

            if (var2 == null) {
                return 0;
            } else {
                for (int var3 = 0; var3 < var2.d(); ++var3) {
                    short var4 = ((ONBTTagCompound) var2.a(var3)).e("id");
                    short var5 = ((ONBTTagCompound) var2.a(var3)).e("lvl");

                    if (var4 == var0) {
                        return var5;
                    }
                }

                return 0;
            }
        }
    }

    private static int a(int var0, OItemStack[] var1) {
        int var2 = 0;
        OItemStack[] var3 = var1;
        int var4 = var1.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            OItemStack var6 = var3[var5];
            int var7 = a(var0, var6);

            if (var7 > var2) {
                var2 = var7;
            }
        }

        return var2;
    }

    private static void a(OIEnchantmentModifier var0, OItemStack var1) {
        if (var1 != null) {
            ONBTTagList var2 = var1.p();

            if (var2 != null) {
                for (int var3 = 0; var3 < var2.d(); ++var3) {
                    short var4 = ((ONBTTagCompound) var2.a(var3)).e("id");
                    short var5 = ((ONBTTagCompound) var2.a(var3)).e("lvl");

                    if (OEnchantment.b[var4] != null) {
                        var0.a(OEnchantment.b[var4], var5);
                    }
                }

            }
        }
    }

    private static void a(OIEnchantmentModifier var0, OItemStack[] var1) {
        OItemStack[] var2 = var1;
        int var3 = var1.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            OItemStack var5 = var2[var4];

            a(var0, var5);
        }

    }

    public static int a(OInventoryPlayer var0, ODamageSource var1) {
        b.a = 0;
        b.b = var1;
        a((OIEnchantmentModifier) b, var0.b);
        if (b.a > 25) {
            b.a = 25;
        }

        return (b.a + 1 >> 1) + a.nextInt((b.a >> 1) + 1);
    }

    public static int a(OInventoryPlayer var0, OEntityLiving var1) {
        c.a = 0;
        c.b = var1;
        a((OIEnchantmentModifier) c, var0.d());
        return c.a > 0 ? 1 + a.nextInt(c.a) : 0;
    }

    public static int b(OInventoryPlayer var0, OEntityLiving var1) {
        return a(OEnchantment.m.x, var0.d());
    }

    public static int c(OInventoryPlayer var0, OEntityLiving var1) {
        return a(OEnchantment.n.x, var0.d());
    }

    public static int a(OInventoryPlayer var0) {
        return a(OEnchantment.h.x, var0.b);
    }

    public static int b(OInventoryPlayer var0) {
        return a(OEnchantment.p.x, var0.d());
    }

    public static int c(OInventoryPlayer var0) {
        return a(OEnchantment.r.x, var0.d());
    }

    public static boolean d(OInventoryPlayer var0) {
        return a(OEnchantment.q.x, var0.d()) > 0;
    }

    public static int e(OInventoryPlayer var0) {
        return a(OEnchantment.s.x, var0.d());
    }

    public static int f(OInventoryPlayer var0) {
        return a(OEnchantment.o.x, var0.d());
    }

    public static boolean g(OInventoryPlayer var0) {
        return a(OEnchantment.i.x, var0.b) > 0;
    }

    public static int a(Random var0, int var1, int var2, OItemStack var3) {
        OItem var4 = var3.a();
        int var5 = var4.c();

        if (var5 <= 0) {
            return 0;
        } else {
            if (var2 > 30) {
                var2 = 30;
            }

            var2 = 1 + (var2 >> 1) + var0.nextInt(var2 + 1);
            int var6 = var0.nextInt(5) + var2;

            return var1 == 0 ? (var6 >> 1) + 1 : (var1 == 1 ? var6 * 2 / 3 + 1 : var6);
        }
    }

    public static List a(Random var0, OItemStack var1, int var2) {
        OItem var3 = var1.a();
        int var4 = var3.c();

        if (var4 <= 0) {
            return null;
        } else {
            var4 = 1 + var0.nextInt((var4 >> 1) + 1) + var0.nextInt((var4 >> 1) + 1);
            int var5 = var4 + var2;
            float var6 = (var0.nextFloat() + var0.nextFloat() - 1.0F) * 0.25F;
            int var7 = (int) ((float) var5 * (1.0F + var6) + 0.5F);
            ArrayList var8 = null;
            Map var9 = b(var7, var1);

            if (var9 != null && !var9.isEmpty()) {
                OEnchantmentData var10 = (OEnchantmentData) OWeightedRandom.a(var0, var9.values());

                if (var10 != null) {
                    var8 = new ArrayList();
                    var8.add(var10);

                    for (int var11 = var7 >> 1; var0.nextInt(50) <= var11; var11 >>= 1) {
                        Iterator var12 = var9.keySet().iterator();

                        while (var12.hasNext()) {
                            Integer var13 = (Integer) var12.next();
                            boolean var14 = true;
                            Iterator var15 = var8.iterator();

                            while (true) {
                                if (var15.hasNext()) {
                                    OEnchantmentData var16 = (OEnchantmentData) var15.next();

                                    if (var16.a.a(OEnchantment.b[var13.intValue()])) {
                                        continue;
                                    }

                                    var14 = false;
                                }

                                if (!var14) {
                                    var12.remove();
                                }
                                break;
                            }
                        }

                        if (!var9.isEmpty()) {
                            OEnchantmentData var17 = (OEnchantmentData) OWeightedRandom.a(var0, var9.values());

                            var8.add(var17);
                        }
                    }
                }
            }

            return var8;
        }
    }

    public static Map b(int var0, OItemStack var1) {
        OItem var2 = var1.a();
        HashMap var3 = null;
        OEnchantment[] var4 = OEnchantment.b;
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            OEnchantment var7 = var4[var6];

            if (var7 != null && var7.y.a(var2)) {
                for (int var8 = var7.c(); var8 <= var7.a(); ++var8) {
                    if (var0 >= var7.a(var8) && var0 <= var7.b(var8)) {
                        if (var3 == null) {
                            var3 = new HashMap();
                        }

                        var3.put(Integer.valueOf(var7.x), new OEnchantmentData(var7, var8));
                    }
                }
            }
        }

        return var3;
    }

}
