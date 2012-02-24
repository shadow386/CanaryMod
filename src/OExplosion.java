import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class OExplosion {

    public boolean a = false;
    private Random h = new Random();
    private OWorld i;
    public double b;
    public double c;
    public double d;
    public OEntity e;
    public float f;
    public Set g;
    protected boolean toRet;

    public OExplosion(OWorld var1, OEntity var2, double var3, double var5, double var7, float var9) {
        super();
        this.i = var1;
        this.e = var2;
        this.f = var9;
        this.b = var3;
        this.c = var5;
        this.d = var7;
        this.g = new HashSet();
        this.toRet = false;
    }

    public void a() {
         // CanaryMod: allow explosion
        Block block = new Block(i.world, i.a((int) Math.floor(b), (int) Math.floor(c), (int) Math.floor(d)), (int) Math.floor(b), (int) Math.floor(c), (int) Math.floor(d));
        if(this.e == null){
            block.setStatus(1);
        }
        else if(this.e instanceof OEntityCreeper){
            block.setStatus(2);
        }
        else if(this.e instanceof OEntityFireball){
            block.setStatus(3);
        }
        float var1 = this.f;
        byte var2 = 16;

        int var3;
        int var4;
        int var5;
        double var15;
        double var17;
        double var19;

        for (var3 = 0; var3 < var2; ++var3) {
            for (var4 = 0; var4 < var2; ++var4) {
                for (var5 = 0; var5 < var2; ++var5) {
                    if (var3 == 0 || var3 == var2 - 1 || var4 == 0 || var4 == var2 - 1 || var5 == 0 || var5 == var2 - 1) {
                        double var6 = (double) ((float) var3 / ((float) var2 - 1.0F) * 2.0F - 1.0F);
                        double var8 = (double) ((float) var4 / ((float) var2 - 1.0F) * 2.0F - 1.0F);
                        double var10 = (double) ((float) var5 / ((float) var2 - 1.0F) * 2.0F - 1.0F);
                        double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);

                        var6 /= var12;
                        var8 /= var12;
                        var10 /= var12;
                        float var14 = this.f * (0.7F + this.i.w.nextFloat() * 0.6F);

                        var15 = this.b;
                        var17 = this.c;
                        var19 = this.d;

                        for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F) {
                            int var22 = OMathHelper.b(var15);
                            int var23 = OMathHelper.b(var17);
                            int var24 = OMathHelper.b(var19);
                            int var25 = this.i.a(var22, var23, var24);

                            if (var25 > 0) {
                                var14 -= (OBlock.m[var25].a(this.e) + 0.3F) * var21;
                            }

                            if (var14 > 0.0F) {
                                this.g.add(new OChunkPosition(var22, var23, var24));
                            }

                            var15 += var6 * (double) var21;
                            var17 += var8 * (double) var21;
                            var19 += var10 * (double) var21;
                        }
                    }
                }
            }
        }
        
        this.toRet = (Boolean) etc.getLoader().callHook(PluginLoader.Hook.EXPLODE, block, e, g);
        
        this.f *= 2.0F;
        var3 = OMathHelper.b(this.b - (double) this.f - 1.0D);
        var4 = OMathHelper.b(this.b + (double) this.f + 1.0D);
        var5 = OMathHelper.b(this.c - (double) this.f - 1.0D);
        int var26 = OMathHelper.b(this.c + (double) this.f + 1.0D);
        int var27 = OMathHelper.b(this.d - (double) this.f - 1.0D);
        int var28 = OMathHelper.b(this.d + (double) this.f + 1.0D);
        List var29 = this.i.b(this.e, OAxisAlignedBB.b((double) var3, (double) var5, (double) var27, (double) var4, (double) var26, (double) var28));
        OVec3D var30 = OVec3D.b(this.b, this.c, this.d);

        for (int var31 = 0; var31 < var29.size(); ++var31) {
            OEntity var32 = (OEntity) var29.get(var31);
            double var33 = var32.f(this.b, this.c, this.d) / (double) this.f;

            if (var33 <= 1.0D) {
                var15 = var32.bm - this.b;
                var17 = var32.bn - this.c;
                var19 = var32.bo - this.d;
                double var35 = (double) OMathHelper.a(var15 * var15 + var17 * var17 + var19 * var19);

                var15 /= var35;
                var17 /= var35;
                var19 /= var35;
                double var37 = (double) this.i.a(var30, var32.bw);
                double var39 = (1.0D - var33) * var37;
                
                // CanaryMod Damage hook: Explosions
                int damage = (int) ((var39 * var39 + var39) / 2.0D * 8.0D * f + 1.0D);
                PluginLoader.DamageType dmgType = (e instanceof OEntityCreeper) ? PluginLoader.DamageType.CREEPER_EXPLOSION : PluginLoader.DamageType.EXPLOSION;

                if (!(Boolean) etc.getLoader().callHook(PluginLoader.Hook.DAMAGE, dmgType, null, var32.entity, damage) && !toRet) {
                    var32.a(ODamageSource.l, (int) ((var39 * var39 + var39) / 2.0D * 8.0D * (double) this.f + 1.0D));
                }

                var32.bp += var15 * var39;
                var32.bq += var17 * var39;
                var32.br += var19 * var39;
            }
        }

        this.f = var1;
        ArrayList var43 = new ArrayList();

        var43.addAll(this.g);
    }

    public void a(boolean var1) {
        this.i.a(this.b, this.c, this.d, "random.explode", 4.0F, (1.0F + (this.i.w.nextFloat() - this.i.w.nextFloat()) * 0.2F) * 0.7F);
        this.i.a("hugeexplosion", this.b, this.c, this.d, 0.0D, 0.0D, 0.0D);
        ArrayList var2 = new ArrayList();

        var2.addAll(this.g);
        if(this.toRet){
            this.g = new HashSet();
            return;
        }
        int var3;
        OChunkPosition var4;
        int var5;
        int var6;
        int var7;
        int var8;

        for (var3 = var2.size() - 1; var3 >= 0; --var3) {
            var4 = (OChunkPosition) var2.get(var3);
            var5 = var4.a;
            var6 = var4.b;
            var7 = var4.c;
            var8 = this.i.a(var5, var6, var7);
            if (var1) {
                double var9 = (double) ((float) var5 + this.i.w.nextFloat());
                double var11 = (double) ((float) var6 + this.i.w.nextFloat());
                double var13 = (double) ((float) var7 + this.i.w.nextFloat());
                double var15 = var9 - this.b;
                double var17 = var11 - this.c;
                double var19 = var13 - this.d;
                double var21 = (double) OMathHelper.a(var15 * var15 + var17 * var17 + var19 * var19);

                var15 /= var21;
                var17 /= var21;
                var19 /= var21;
                double var23 = 0.5D / (var21 / (double) this.f + 0.1D);

                var23 *= (double) (this.i.w.nextFloat() * this.i.w.nextFloat() + 0.3F);
                var15 *= var23;
                var17 *= var23;
                var19 *= var23;
                this.i.a("explode", (var9 + this.b * 1.0D) / 2.0D, (var11 + this.c * 1.0D) / 2.0D, (var13 + this.d * 1.0D) / 2.0D, var15, var17, var19);
                this.i.a("smoke", var9, var11, var13, var15, var17, var19);
            }

            if (var8 > 0) {
                OBlock.m[var8].a(this.i, var5, var6, var7, this.i.c(var5, var6, var7), 0.3F, 0);
                this.i.e(var5, var6, var7, 0);
                OBlock.m[var8].a_(this.i, var5, var6, var7);
            }
        }

        if (this.a) {
            for (var3 = var2.size() - 1; var3 >= 0; --var3) {
                var4 = (OChunkPosition) var2.get(var3);
                var5 = var4.a;
                var6 = var4.b;
                var7 = var4.c;
                var8 = this.i.a(var5, var6, var7);
                int var25 = this.i.a(var5, var6 - 1, var7);

                if (var8 == 0 && OBlock.o[var25] && this.h.nextInt(3) == 0) {
                    this.i.e(var5, var6, var7, OBlock.at.bO);
                }
            }
        }

    }
    
    public boolean getRet(){
        return this.toRet;
    }
}
