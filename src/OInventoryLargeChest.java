public class OInventoryLargeChest implements OIInventory, Container<OItemStack> {

    private String a;
    private OIInventory b;
    private OIInventory c;

    public OInventoryLargeChest(String var1, OIInventory var2, OIInventory var3) {
        super();
        this.a = var1;
        if (var2 == null) {
            var2 = var3;
        }

        if (var3 == null) {
            var3 = var2;
        }

        this.b = var2;
        this.c = var3;
    }

    public int a() {
        return this.b.a() + this.c.a();
    }

    public String c() {
        return this.a;
    }

    public OItemStack b_(int var1) {
        return var1 >= this.b.a() ? this.c.b_(var1 - this.b.a()) : this.b.b_(var1);
    }

    public OItemStack a(int var1, int var2) {
        return var1 >= this.b.a() ? this.c.a(var1 - this.b.a(), var2) : this.b.a(var1, var2);
    }

    public void a(int var1, OItemStack var2) {
        if (var1 >= this.b.a()) {
            this.c.a(var1 - this.b.a(), var2);
        } else {
            this.b.a(var1, var2);
        }

    }

    public int d() {
        return this.b.d();
    }

    public void k() {
        this.b.k();
        this.c.k();
    }

    public boolean a(OEntityPlayer var1) {
        return this.b.a(var1) && this.c.a(var1);
    }

    public void e() {
        this.b.e();
        this.c.e();
    }

    public void t_() {
        this.b.t_();
        this.c.t_();
    }

    @Override
    public OItemStack[] getContents() {
        int size = getContentsSize();
        OItemStack[] result = new OItemStack[size];

        for (int i = 0; i < size; i++)
            result[i] = getContentsAt(i);
        return result;
    }

    @Override
    public void setContents(OItemStack[] values) {
        int size = getContentsSize();

        for (int i = 0; i < size; i++)
            setContentsAt(i, values[i]);
    }

    @Override
    public OItemStack getContentsAt(int index) {
        return b_(index);
    }

    @Override
    public void setContentsAt(int index, OItemStack value) {
        a(index, value);
    }

    @Override
    public int getContentsSize() {
        return a();
    }

    @Override
    public String getName() {
        return a;
    }

    @Override
    public void setName(String value) {
        a = value;
    }

    public Block getChestBlock() {
        if (b instanceof OTileEntityChest) {
            OTileEntityChest block = (OTileEntityChest) b;
            return block.i.world.getBlockAt(block.j, block.k, block.l);
        }
        if (c instanceof OTileEntityChest) {
            OTileEntityChest block = (OTileEntityChest) c;
            return block.i.world.getBlockAt(block.j, block.k, block.l);
        }
        return null;
    }

}
