import java.util.ArrayList;
import java.util.Random;


public class OBlockStairs extends OBlock {

    private OBlock a;

    protected OBlockStairs(int var1, OBlock var2) {
        super(var1, var2.bz, var2.bN);
        this.a = var2;
        this.c(var2.bB);
        this.b(var2.bC / 3.0F);
        this.a(var2.bL);
        this.f(255);
    }

    public void a(OIBlockAccess var1, int var2, int var3, int var4) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public OAxisAlignedBB e(OWorld var1, int var2, int var3, int var4) {
        return super.e(var1, var2, var3, var4);
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public void a(OWorld var1, int var2, int var3, int var4, OAxisAlignedBB var5, ArrayList var6) {
        int var7 = var1.c(var2, var3, var4);

        if (var7 == 0) {
            this.a(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            super.a(var1, var2, var3, var4, var5, var6);
            this.a(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            super.a(var1, var2, var3, var4, var5, var6);
        } else if (var7 == 1) {
            this.a(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            super.a(var1, var2, var3, var4, var5, var6);
            this.a(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            super.a(var1, var2, var3, var4, var5, var6);
        } else if (var7 == 2) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
            super.a(var1, var2, var3, var4, var5, var6);
            this.a(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
            super.a(var1, var2, var3, var4, var5, var6);
        } else if (var7 == 3) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            super.a(var1, var2, var3, var4, var5, var6);
            this.a(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
            super.a(var1, var2, var3, var4, var5, var6);
        }

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void b(OWorld var1, int var2, int var3, int var4, OEntityPlayer var5) {
        this.a.b(var1, var2, var3, var4, var5);
    }

    public void c(OWorld var1, int var2, int var3, int var4, int var5) {
        this.a.c(var1, var2, var3, var4, var5);
    }

    public float a(OEntity var1) {
        return this.a.a(var1);
    }

    public int a(int var1, Random var2) {
        return this.a.a(var1, var2);
    }

    public int a(Random var1) {
        return this.a.a(var1);
    }

    public int a(int var1, int var2) {
        return this.a.a(var1, 0);
    }

    public int a(int var1) {
        return this.a.a(var1, 0);
    }

    public int c() {
        return this.a.c();
    }

    public void a(OWorld var1, int var2, int var3, int var4, OEntity var5, OVec3D var6) {
        this.a.a(var1, var2, var3, var4, var5, var6);
    }

    public boolean q_() {
        return this.a.q_();
    }

    public boolean a(int var1, boolean var2) {
        return this.a.a(var1, var2);
    }

    public boolean c(OWorld var1, int var2, int var3, int var4) {
        return this.a.c(var1, var2, var3, var4);
    }

    public void a(OWorld var1, int var2, int var3, int var4) {
        this.a(var1, var2, var3, var4, 0);
        this.a.a(var1, var2, var3, var4);
    }

//    CanaryMod: commented out as workaround for stone brick stair damage fail
//    public void d(OWorld var1, int var2, int var3, int var4) {
//        this.a.d(var1, var2, var3, var4);
//    }

    public void a(OWorld var1, int var2, int var3, int var4, int var5, float var6) {
        this.a.a(var1, var2, var3, var4, var5, var6);
    }

    public void b(OWorld var1, int var2, int var3, int var4, OEntity var5) {
        this.a.b(var1, var2, var3, var4, var5);
    }

    public void a(OWorld var1, int var2, int var3, int var4, Random var5) {
        this.a.a(var1, var2, var3, var4, var5);
    }

    public boolean a(OWorld var1, int var2, int var3, int var4, OEntityPlayer var5) {
        return this.a.a(var1, var2, var3, var4, var5);
    }

    public void a_(OWorld var1, int var2, int var3, int var4) {
        this.a.a_(var1, var2, var3, var4);
    }

    public void a(OWorld var1, int var2, int var3, int var4, OEntityLiving var5) {
        int var6 = OMathHelper.b((double) (var5.bl * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0) {
            var1.c(var2, var3, var4, 2);
        }

        if (var6 == 1) {
            var1.c(var2, var3, var4, 1);
        }

        if (var6 == 2) {
            var1.c(var2, var3, var4, 3);
        }

        if (var6 == 3) {
            var1.c(var2, var3, var4, 0);
        }

    }
}