
public class OInventoryCraftResult implements OIInventory, Container<OItemStack> {

    private OItemStack[] a = new OItemStack[1];
    // CanaryMod
    private String name = "Result";

    public OInventoryCraftResult() {
        super();
    }

    public int c() {
        return 1;
    }

    public OItemStack g_(int var1) {
        return this.a[var1];
    }

    public String e() {
        return name;
    }

    public OItemStack a(int var1, int var2) {
        if (this.a[var1] != null) {
            OItemStack var3 = this.a[var1];

            this.a[var1] = null;
            return var3;
        } else {
            return null;
        }
    }

    public OItemStack b(int var1) {
        if (this.a[var1] != null) {
            OItemStack var2 = this.a[var1];

            this.a[var1] = null;
            return var2;
        } else {
            return null;
        }
    }

    public void a(int var1, OItemStack var2) {
        this.a[var1] = var2;
    }

    public int a() {
        return 64;
    }

    public void H_() {}

    public boolean a(OEntityPlayer var1) {
        return true;
    }

    public void f() {}

    public void g() {}
    
    @Override
    public OItemStack[] getContents() {
        int size = getContentsSize();
        OItemStack[] result = new OItemStack[size];

        for (int i = 0; i < size; i++) {
            result[i] = getContentsAt(i);
        }
        return result;
    }

    @Override
    public void setContents(OItemStack[] values) {
        int size = getContentsSize();

        for (int i = 0; i < size; i++) {
            setContentsAt(i, values[i]);
        }
    }

    @Override
    public OItemStack getContentsAt(int index) {
        return this.g_(index);
    }

    @Override
    public void setContentsAt(int index, OItemStack value) {
        this.a(index, value);
    }

    @Override
    public int getContentsSize() {
        return this.c();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String value) {
        this.name = value;
    }
}
