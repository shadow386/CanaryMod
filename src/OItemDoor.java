
public class OItemDoor extends OItem {

    private OMaterial a;

    public OItemDoor(int var1, OMaterial var2) {
        super(var1);
        this.a = var2;
        this.bQ = 1;
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        if (var7 != 1) {
            return false;
        } else {
            ++var5;
            OBlock var8;

            if (this.a == OMaterial.d) {
                var8 = OBlock.aE;
            } else {
                var8 = OBlock.aL;
            }

            if (var2.d(var4, var5, var6) && var2.d(var4, var5 + 1, var6)) {
                if (!var8.c(var3, var4, var5, var6)) {
                    return false;
                } else {
                    // CanaryMod hook: onItemUse
                    Block blockClicked = new Block(var3.world, var3.world.getBlockIdAt(var4, var5, var6), var4, var5, var6);

                    blockClicked.setFaceClicked(Block.Face.fromId(var7));
                    Block blockPlaced = new Block(var3.world, var3.world.getBlockIdAt(var4, var5 + 1, var6), var4, var5 + 1, var6);

                    // Call the hook
                    if (var2 instanceof OEntityPlayerMP) {
                        Player player = ((OEntityPlayerMP) var2).getPlayer();

                        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, player, blockPlaced, blockClicked, new Item(var1))) {
                            return false;
                        }
                    }
                    
                    int var9 = OMathHelper.b((double) ((var2.bs + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;

                    a(var3, var4, var5, var6, var9, var8);
                    --var1.a;
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public static void a(OWorld var0, int var1, int var2, int var3, int var4, OBlock var5) {
        byte var6 = 0;
        byte var7 = 0;

        if (var4 == 0) {
            var7 = 1;
        }

        if (var4 == 1) {
            var6 = -1;
        }

        if (var4 == 2) {
            var7 = -1;
        }

        if (var4 == 3) {
            var6 = 1;
        }

        int var8 = (var0.e(var1 - var6, var2, var3 - var7) ? 1 : 0) + (var0.e(var1 - var6, var2 + 1, var3 - var7) ? 1 : 0);
        int var9 = (var0.e(var1 + var6, var2, var3 + var7) ? 1 : 0) + (var0.e(var1 + var6, var2 + 1, var3 + var7) ? 1 : 0);
        boolean var10 = var0.a(var1 - var6, var2, var3 - var7) == var5.bO || var0.a(var1 - var6, var2 + 1, var3 - var7) == var5.bO;
        boolean var11 = var0.a(var1 + var6, var2, var3 + var7) == var5.bO || var0.a(var1 + var6, var2 + 1, var3 + var7) == var5.bO;
        boolean var12 = false;

        if (var10 && !var11) {
            var12 = true;
        } else if (var9 > var8) {
            var12 = true;
        }

        var0.o = true;
        var0.b(var1, var2, var3, var5.bO, var4);
        var0.b(var1, var2 + 1, var3, var5.bO, 8 | (var12 ? 1 : 0));
        var0.o = false;
        var0.h(var1, var2, var3, var5.bO);
        var0.h(var1, var2 + 1, var3, var5.bO);
    }
}
