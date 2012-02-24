
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

    public int c() {
        return this.b.c() + this.c.c();
    }

    public String e() {
        return this.a;
    }

    public OItemStack c_(int var1) {
        return var1 >= this.b.c() ? this.c.c_(var1 - this.b.c()) : this.b.c_(var1);
    }

    public OItemStack a(int var1, int var2) {
        return var1 >= this.b.c() ? this.c.a(var1 - this.b.c(), var2) : this.b.a(var1, var2);
    }

    public void a(int var1, OItemStack var2) {
        if (var1 >= this.b.c()) {
            this.c.a(var1 - this.b.c(), var2);
        } else {
            this.b.a(var1, var2);
        }

    }

    public int a() {
        return this.b.a();
    }

    public void z_() {
        this.b.z_();
        this.c.z_();
    }

    public boolean a(OEntityPlayer var1) {
        return this.b.a(var1) && this.c.a(var1);
    }

    public void f() {
        this.b.f();
        this.c.f();
    }

    public void g() {
        this.b.g();
        this.c.g();
    }
   
    public OItemStack[] getContents() {
        int size = getContentsSize();
        OItemStack[] result = new OItemStack[size];

        for (int i = 0; i < size; i++) {
            result[i] = getContentsAt(i);
        }
        return result;
    }

    public void setContents(OItemStack[] values) {
        int size = getContentsSize();

        for (int i = 0; i < size; i++) {
            setContentsAt(i, values[i]);
        }
    }

    public OItemStack getContentsAt(int index) {
        return c_(index);
    }

    public void setContentsAt(int index, OItemStack value) {
        a(index, value);
    }

    public int getContentsSize() {
        return c();
    }

    public String getName() {
        return a;
    }

    public void setName(String value) {
        a = value;
    }

    public Block getChestBlock() {
        if (b instanceof OTileEntityChest) {
            OTileEntityChest block = (OTileEntityChest) b;

            return block.k.world.getBlockAt(block.l, block.m, block.n);
        }
        if (c instanceof OTileEntityChest) {
            OTileEntityChest block = (OTileEntityChest) c;

            return block.k.world.getBlockAt(block.l, block.m, block.n);
        }
        return null;
    }
}
