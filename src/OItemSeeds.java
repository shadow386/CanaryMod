
public class OItemSeeds extends OItem {

    private int a;
    private int b;

    public OItemSeeds(int var1, int var2, int var3) {
        super(var1);
        this.a = var2;
        this.b = var3;
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        if (var7 != 1) {
            return false;
        } else if (var2.d(var4, var5, var6) && var2.d(var4, var5 + 1, var6)) {
            int var8 = var3.a(var4, var5, var6);

            if (var8 == this.b && var3.f(var4, var5 + 1, var6)) {
                // CanaryMod: Seeds
                Block blockClicked = new Block(var3.world, var8, var4, var5, var6);

                blockClicked.setFaceClicked(Block.Face.fromId(var7));
                Block blockPlaced = new Block(var3.world, var3.a(var4, var5 + 1, var6), var4, var5 + 1, var6);

                // Call the hook
                Player player = ((OEntityPlayerMP) var2).getPlayer();

                if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.ITEM_USE, player, blockPlaced, blockClicked, new Item(var1))) {
                    return false;
                }

                var3.e(var4, var5 + 1, var6, this.a);
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
