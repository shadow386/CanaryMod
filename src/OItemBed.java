
public class OItemBed extends OItem {

    public OItemBed(int var1) {
        super(var1);
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        // CanaryMod: store the block that was clicked
        Block blockClicked = new Block(var3.world, var3.world.getBlockIdAt(var4, var5, var6), var4, var5, var6);

        blockClicked.setFaceClicked(Block.Face.fromId(var7));

        if (var7 != 1) {
            return false;
        } else {
            ++var5;
            OBlockBed var8 = (OBlockBed) OBlock.U;
            int var9 = OMathHelper.b((double) (var2.bs * 4.0F / 360.0F) + 0.5D) & 3;
            byte var10 = 0;
            byte var11 = 0;

            if (var9 == 0) {
                var11 = 1;
            }

            if (var9 == 1) {
                var10 = -1;
            }

            if (var9 == 2) {
                var11 = -1;
            }

            if (var9 == 3) {
                var10 = 1;
            }

            // CanaryMod: onItemUse hook
            if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) var2).getPlayer(), blockClicked, new Block(var3.world, OBlock.S.bO, var4, var5, var6), new Item(var1))) {
                return false;
            }

            if (var2.d(var4, var5, var6) && var2.d(var4 + var10, var5, var6 + var11)) {
                if (var3.f(var4, var5, var6) && var3.f(var4 + var10, var5, var6 + var11) && var3.e(var4, var5 - 1, var6) && var3.e(var4 + var10, var5 - 1, var6 + var11)) {
                    var3.b(var4, var5, var6, var8.bO, var9);
                    if (var3.a(var4, var5, var6) == var8.bO) {
                        var3.b(var4 + var10, var5, var6 + var11, var8.bO, var9 + 8);
                    }

                    --var1.a;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
