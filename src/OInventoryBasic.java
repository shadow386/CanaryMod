
import java.util.Arrays;
import java.util.List;


public class OInventoryBasic implements OIInventory, Container<OItemStack> {

    private String a;
    private int b;
    private OItemStack[] c;
    private List d;

    public OInventoryBasic(String var1, int var2) {
        super();
        this.a = var1;
        this.b = var2;
        this.c = new OItemStack[var2];
    }

    public OItemStack c_(int var1) {
        return this.c[var1];
    }

    public OItemStack a(int var1, int var2) {
        if (this.c[var1] != null) {
            OItemStack var3;

            if (this.c[var1].a <= var2) {
                var3 = this.c[var1];
                this.c[var1] = null;
                this.z_();
                return var3;
            } else {
                var3 = this.c[var1].a(var2);
                if (this.c[var1].a == 0) {
                    this.c[var1] = null;
                }

                this.z_();
                return var3;
            }
        } else {
            return null;
        }
    }

    public void a(int var1, OItemStack var2) {
        this.c[var1] = var2;
        if (var2 != null && var2.a > this.a()) {
            var2.a = this.a();
        }

        this.z_();
    }

    public int c() {
        return this.b;
    }

    public String e() {
        return this.a;
    }

    public int a() {
        return 64;
    }

    public void z_() {
        if (this.d != null) {
            for (int var1 = 0; var1 < this.d.size(); ++var1) {
                ((OIInvBasic) this.d.get(var1)).a(this);
            }
        }

    }

    public boolean a(OEntityPlayer var1) {
        return true;
    }

    public void f() {}

    public void g() {}

    @Override
    public OItemStack[] getContents() {
        return Arrays.copyOf(this.c, getContentsSize());
    }

    @Override
    public void setContents(OItemStack[] values) {
        this.c = Arrays.copyOf(values, getContentsSize());
    }

    @Override
    public OItemStack getContentsAt(int index) {
        return this.c_(index);
    }

    @Override
    public void setContentsAt(int index, OItemStack value) {
        this.a(b, value);
    }

    @Override
    public int getContentsSize() {
        return this.c();
    }

    @Override
    public String getName() {
        return this.a;
    }

    @Override
    public void setName(String value) {
        this.a = value;
    }
}
