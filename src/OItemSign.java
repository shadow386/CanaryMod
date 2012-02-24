
public class OItemSign extends OItem {

    public OItemSign(int var1) {
        super(var1);
        this.bO = 1;
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        if (var7 == 0) {
            return false;
        } else if (!var3.d(var4, var5, var6).a()) {
            return false;
        } else {
            // CanaryMod: Store block data clicked
            Block blockClicked = new Block(var3.world, var3.a(var4, var5, var6), var4, var5, var6);

            blockClicked.setFaceClicked(Block.Face.fromId(var7));

            if (var7 == 1) {
                ++var5;
            }

            if (var7 == 2) {
                --var6;
            }

            if (var7 == 3) {
                ++var6;
            }

            if (var7 == 4) {
                --var4;
            }

            if (var7 == 5) {
                ++var4;
            }

            if (!var2.d(var4, var5, var6)) {
                return false;
            } else if (!OBlock.aF.c(var3, var4, var5, var6)) {
                return false;
            } else {
                // CanaryMod: Now we can call itemUse :)
                Block blockPlaced = new Block(var3.world, (var7 == 1 ? OBlock.aF.bO : OBlock.aK.bO), var4, var5, var6);

                if (var2 instanceof OEntityPlayerMP && (Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, ((OEntityPlayerMP) var2).getPlayer(), blockPlaced, blockClicked, new Item(var1))) {
                    return false;
                }

                if (var7 == 1) {
                    int var8 = OMathHelper.b((double) ((var2.bs + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;

                    var3.b(var4, var5, var6, OBlock.aF.bO, var8);
                } else {
                    var3.b(var4, var5, var6, OBlock.aK.bO, var7);
                }

                --var1.a;
                OTileEntitySign var9 = (OTileEntitySign) var3.b(var4, var5, var6);

                if (var9 != null) {
                    var2.a(var9);
                }

                return true;
            }
        }
    }
}
