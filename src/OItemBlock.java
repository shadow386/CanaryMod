
public class OItemBlock extends OItem {

    private int a;

    public OItemBlock(int var1) {
        super(var1);
        this.a = var1 + 256;
        this.d(OBlock.m[var1 + 256].a(2));
    }

    public int a() {
        return this.a;
    }

    public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
        // CanaryMod: Bail if we have nothing of the items in hand
        if (var1.a == 0) {
            return false;
        }
        // CanaryMod: Store blockInfo of the one we clicked
        int blockClickedId = var3.a(var4, var5, var6);
        Block blockClicked = new Block(var3.world, blockClickedId, var4, var5, var6);
        
        int var8 = var3.a(var4, var5, var6);

        if (var8 == OBlock.aS.bO) {
            var7 = 1;
        } else if (var8 != OBlock.bu.bO && var8 != OBlock.X.bO && var8 != OBlock.Y.bO) {
            if (var7 == 0) {
                --var5;
            }

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
        }
        
        // CanaryMod: Store faceClicked (must be here to have the 'snow' special case).
        blockClicked.setFaceClicked(Block.Face.fromId(var7));
        // CanaryMod: And the block we're about to place
        Block blockPlaced = new Block(var3.world, a, var4, var5, var6);

        if (var1.a == 0) {
            return false;
        } else if (!var2.d(var4, var5, var6)) {
            return false;
        } else if (var5 == 255 && OBlock.m[this.a].cd.a()) {
            return false;
        } else if (var3.a(this.a, var4, var5, var6, false, var7)
                // CanaryMod: prevent unwanted blocks from getting placed.
                && !(Boolean) etc.getLoader().callHook(PluginLoader.Hook.BLOCK_PLACE, ((OEntityPlayerMP) var2).getPlayer(), blockPlaced, blockClicked, new Item(var1))) {
            OBlock var9 = OBlock.m[this.a];

            if (var3.b(var4, var5, var6, this.a, this.a(var1.h()))) {
                if (var3.a(var4, var5, var6) == this.a) {
                    OBlock.m[this.a].e(var3, var4, var5, var6, var7);
                    OBlock.m[this.a].a(var3, var4, var5, var6, (OEntityLiving) var2);
                }

                var3.a((double) ((float) var4 + 0.5F), (double) ((float) var5 + 0.5F), (double) ((float) var6 + 0.5F), var9.cb.c(), (var9.cb.a() + 1.0F) / 2.0F, var9.cb.b() * 0.8F);
                --var1.a;
            }

            return true;
        } else {
            return false;
        }
    }

    public String a(OItemStack var1) {
        return OBlock.m[this.a].p();
    }

    public String b() {
        return OBlock.m[this.a].p();
    }
}
