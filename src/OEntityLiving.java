import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public abstract class OEntityLiving extends OEntity {

    public int S = 20;
    public float T;
    public float U;
    public float V = 0.0F;
    public float W = 0.0F;
    public float X = 0.0F;
    public float Y = 0.0F;
    protected float Z;
    protected float aa;
    protected float ab;
    protected float ac;
    protected boolean ad = true;
    protected String ae = "/mob/char.png";
    protected boolean af = true;
    protected float ag = 0.0F;
    protected String ah = null;
    protected float ai = 1.0F;
    protected int aj = 0;
    protected float ak = 0.0F;
    public float al = 0.1F;
    public float am = 0.02F;
    public float an;
    public float ao;
    protected int ap = this.c();
    public int aq;
    protected int ar;
    private int a;
    public int as;
    public int at;
    public float au = 0.0F;
    public int av = 0;
    public int aw = 0;
    public float ax;
    public float ay;
    protected boolean az = false;
    protected int aA;
    public int aB = -1;
    public float aC = (float) (Math.random() * 0.8999999761581421D + 0.10000000149011612D);
    public float aD;
    public float aE;
    public float aF;
    protected OEntityPlayer aG = null;
    protected int aH = 0;
    protected OEntityLiving aI = null;
    public int aJ = 0;
    public int aK = 0;
    protected HashMap aL = new HashMap();
    private boolean b = true;
    private int c;
    private OEntityLookHelper d;
    private OEntityMoveHelper e;
    private OEntityJumpHelper f;
    private OIPathHandler g;
    protected OEntityAITasks aM = new OEntityAITasks();
    protected int aN;
    protected double aO;
    protected double aP;
    protected double aQ;
    protected double aR;
    protected double aS;
    float aT = 0.0F;
    protected int aU = 0;
    protected int aV = 0;
    protected float aW;
    protected float aX;
    protected float aY;
    protected boolean aZ = false;
    protected float ba = 0.0F;
    protected float bb = 0.7F;
    private int h = 0;
    private OEntity i;
    protected int bc = 0;
    
    // CanaryMod Start
    LivingEntity entity = new LivingEntity(this);
    protected MobSpawner spawner = null;
    // CanaryMod end

    public OEntityLiving(OWorld var1) {
        super(var1);
        this.bf = true;
        this.d = new OEntityLookHelper(this);
        this.e = new OEntityMoveHelper(this, this.bb);
        this.f = new OEntityJumpHelper(this);
        this.g = new OPathNavigate(this, var1);
        this.U = (float) (Math.random() + 1.0D) * 0.01F;
        this.c(this.bm, this.bn, this.bo);
        this.T = (float) Math.random() * 12398.0F;
        this.bs = (float) (Math.random() * 3.1415927410125732D * 2.0D);
        this.X = this.bs;
        this.bP = 0.5F;
    }

    public OEntityLookHelper ae() {
        return this.d;
    }

    public OEntityMoveHelper af() {
        return this.e;
    }

    public OEntityJumpHelper ag() {
        return this.f;
    }

    public OIPathHandler ah() {
        return this.g;
    }

    public Random ai() {
        return this.bS;
    }

    public OEntityLiving aj() {
        return this.aI;
    }

    public int ak() {
        return this.aV;
    }

    protected void b() {
        this.bY.a(8, Integer.valueOf(this.c));
    }

    public boolean g(OEntity var1) {
        return this.bi.a(OVec3D.b(this.bm, this.bn + (double) this.y(), this.bo), OVec3D.b(var1.bm, var1.bn + (double) var1.y(), var1.bo)) == null;
    }

    public boolean e_() {
        return !this.bE;
    }

    public boolean f_() {
        return !this.bE;
    }

    public float y() {
        return this.bH * 0.85F;
    }

    public int h() {
        return 80;
    }

    public void al() {
        String var1 = this.c_();

        if (var1 != null) {
            this.bi.a(this, var1, this.o(), this.v());
        }

    }

    public void am() {
        this.an = this.ao;
        super.am();
        OProfiler.a("mobBaseTick");
        if (this.bS.nextInt(1000) < this.a++) {
            this.a = -this.h();
            this.al();
        }

        if (this.aq() && this.U()) {
            // CanaryMod Damage hook: Suffocation
            if (!(Boolean) manager.callHook(PluginLoader.Hook.DAMAGE, PluginLoader.DamageType.SUFFOCATION, null, entity, 1)) {
                this.a(ODamageSource.e, 1);
            }
        }

        if (this.aI() || this.bi.I) {
            this.aH();
        }

        if (this.aq() && this.a(OMaterial.g) && !this.f() && !this.aL.containsKey(Integer.valueOf(OPotion.o.H))) {
            this.l(this.f(this.aQ()));
            if (this.aQ() == -20) {
                this.l(0);
                // CanaryMod Damage hook: Drowning
                if (!(Boolean) manager.callHook(PluginLoader.Hook.DAMAGE, PluginLoader.DamageType.WATER, null, entity, 2)) {
                    for (int var1 = 0; var1 < 8; ++var1) {
                        float var2 = this.bS.nextFloat() - this.bS.nextFloat();
                        float var3 = this.bS.nextFloat() - this.bS.nextFloat();
                        float var4 = this.bS.nextFloat() - this.bS.nextFloat();
	
                        this.bi.a("bubble", this.bm + (double) var2, this.bn + (double) var3, this.bo + (double) var4, this.bp, this.bq, this.br);
                    }
                
                    this.a(ODamageSource.f, 2);
                }

            }

            this.aH();
        } else {
            this.l(300);
        }

        this.ax = this.ay;
        if (this.aw > 0) {
            --this.aw;
        }

        if (this.as > 0) {
            --this.as;
        }

        if (this.bW > 0) {
            --this.bW;
        }

        if (this.ap <= 0) {
            this.an();
        }

        if (this.aH > 0) {
            --this.aH;
        } else {
            this.aG = null;
        }

        this.aB();
        this.ac = this.ab;
        this.W = this.V;
        this.Y = this.X;
        this.bu = this.bs;
        this.bv = this.bt;
        OProfiler.a();
    }

    protected void an() {
        ++this.av;
        if (this.av == 20) {
            int var1;

            if (!this.bi.I && (this.aH > 0 || this.ac()) && !this.l()) {
                var1 = this.a(this.aG);

                while (var1 > 0) {
                    int var2 = OEntityXPOrb.b(var1);

                    var1 -= var2;
                    this.bi.b((OEntity) (new OEntityXPOrb(this.bi, this.bm, this.bn, this.bo, var2)));
                }
            }

            this.ay();
            this.T();

            for (var1 = 0; var1 < 20; ++var1) {
                double var3 = this.bS.nextGaussian() * 0.02D;
                double var5 = this.bS.nextGaussian() * 0.02D;
                double var7 = this.bS.nextGaussian() * 0.02D;

                this.bi.a("explode", this.bm + (double) (this.bS.nextFloat() * this.bG * 2.0F) - (double) this.bG, this.bn + (double) (this.bS.nextFloat() * this.bH), this.bo + (double) (this.bS.nextFloat() * this.bG * 2.0F) - (double) this.bG, var3, var5, var7);
            }
        }

    }

    protected int f(int var1) {
        return var1 - 1;
    }

    protected int a(OEntityPlayer var1) {
        return this.aA;
    }

    protected boolean ac() {
        return false;
    }

    public void ao() {
        for (int var1 = 0; var1 < 20; ++var1) {
            double var2 = this.bS.nextGaussian() * 0.02D;
            double var4 = this.bS.nextGaussian() * 0.02D;
            double var6 = this.bS.nextGaussian() * 0.02D;
            double var8 = 10.0D;

            this.bi.a("explode", this.bm + (double) (this.bS.nextFloat() * this.bG * 2.0F) - (double) this.bG - var2 * var8, this.bn + (double) (this.bS.nextFloat() * this.bH) - var4 * var8, this.bo + (double) (this.bS.nextFloat() * this.bG * 2.0F) - (double) this.bG - var6 * var8, var2, var4, var6);
        }

    }

    public void N() {
        super.N();
        this.Z = this.aa;
        this.aa = 0.0F;
        this.bK = 0.0F;
    }

    public void y_() {
        super.y_();
        if (this.aJ > 0) {
            if (this.aK <= 0) {
                this.aK = 60;
            }

            --this.aK;
            if (this.aK <= 0) {
                --this.aJ;
            }
        }

        this.d();
        double var1 = this.bm - this.bj;
        double var3 = this.bo - this.bl;
        float var5 = OMathHelper.a(var1 * var1 + var3 * var3);
        float var6 = this.V;
        float var7 = 0.0F;

        this.Z = this.aa;
        float var8 = 0.0F;

        if (var5 > 0.05F) {
            var8 = 1.0F;
            var7 = var5 * 3.0F;
            var6 = (float) Math.atan2(var3, var1) * 180.0F / 3.1415927F - 90.0F;
        }

        if (this.ao > 0.0F) {
            var6 = this.bs;
        }

        if (!this.bx) {
            var8 = 0.0F;
        }

        this.aa += (var8 - this.aa) * 0.3F;

        float var9;

        for (var9 = var6 - this.V; var9 < -180.0F; var9 += 360.0F) {
            ;
        }

        while (var9 >= 180.0F) {
            var9 -= 360.0F;
        }

        this.V += var9 * 0.3F;

        float var10;

        for (var10 = this.bs - this.V; var10 < -180.0F; var10 += 360.0F) {
            ;
        }

        while (var10 >= 180.0F) {
            var10 -= 360.0F;
        }

        boolean var11 = var10 < -90.0F || var10 >= 90.0F;

        if (var10 < -75.0F) {
            var10 = -75.0F;
        }

        if (var10 >= 75.0F) {
            var10 = 75.0F;
        }

        this.V = this.bs - var10;
        if (var10 * var10 > 2500.0F) {
            this.V += var10 * 0.2F;
        }

        if (var11) {
            var7 *= -1.0F;
        }

        while (this.bs - this.bu < -180.0F) {
            this.bu -= 360.0F;
        }

        while (this.bs - this.bu >= 180.0F) {
            this.bu += 360.0F;
        }

        while (this.V - this.W < -180.0F) {
            this.W -= 360.0F;
        }

        while (this.V - this.W >= 180.0F) {
            this.W += 360.0F;
        }

        while (this.bt - this.bv < -180.0F) {
            this.bv -= 360.0F;
        }

        while (this.bt - this.bv >= 180.0F) {
            this.bv += 360.0F;
        }

        this.ab += var7;
    }

    protected void b(float var1, float var2) {
        super.b(var1, var2);
    }

    public void d(int var1) {
        if (this.ap > 0) {
            this.ap += var1;
            if (this.ap > this.c()) {
                this.ap = this.c();
            }

            this.bW = this.S / 2;
        }
    }

    public abstract int c();

    public int ap() {
        return this.ap;
    }

    public void i(int var1) {
        this.ap = var1;
        if (var1 > this.c()) {
            var1 = this.c();
        }

    }

    public boolean a(ODamageSource var1, int var2) {
        if (this.bi.I) {
            return false;
        } else {
            this.aV = 0;
            if (this.ap <= 0) {
                return false;
            } else if (var1.k() && this.a(OPotion.n)) {
                return false;
            } else {
                this.aE = 1.5F;
            
                // CanaryMod damage entities.
                LivingEntity attacker = null;

                if (var1 != null && var1 instanceof OEntityDamageSource && ((OEntityDamageSource) var1).a() instanceof OEntityLiving) {
                    OEntity o = ((OEntityDamageSource) var1).a();

                    attacker = new LivingEntity((OEntityLiving) o);
                }

                // CanaryMod attack by entity, but it might not do damage!
                if (attacker != null && (Boolean) manager.callHook(PluginLoader.Hook.ATTACK, attacker, entity, var2)) {
                    if (this instanceof OEntityCreature) {
                        ((OEntityCreature) this).f = 0;
                    }
                    return false;
                }
                boolean var3 = true;

                if ((float) this.bW > (float) this.S / 2.0F) {
                    if (var2 <= this.aU) {
                        return false;
                    }
               
                    // CanaryMod: partial damage
                    if (attacker != null && (Boolean) manager.callHook(PluginLoader.Hook.DAMAGE, PluginLoader.DamageType.ENTITY, attacker, entity, var2 - bW)) {
                        return false;
                    }

                    this.c(var1, var2 - this.aU);
                    this.aU = var2;
                    var3 = false;
                } else {
                    // CanaryMod: full damage
                    if (attacker != null && (Boolean) manager.callHook(PluginLoader.Hook.DAMAGE, PluginLoader.DamageType.ENTITY, attacker, entity, var2)) {
                        return false;
                    }

                    this.aU = var2;
                    this.aq = this.ap;
                    this.bW = this.S;
                    this.c(var1, var2);
                    this.as = this.at = 10;
                }

                this.au = 0.0F;
                OEntity var4 = var1.a();

                if (var4 != null) {
                    if (var4 instanceof OEntityPlayer) {
                        this.aH = 60;
                        this.aG = (OEntityPlayer) var4;
                    } else if (var4 instanceof OEntityWolf) {
                        OEntityWolf var5 = (OEntityWolf) var4;

                        if (var5.C()) {
                            this.aH = 60;
                            this.aG = null;
                        }
                    }
                }

                if (var3) {
                    this.bi.a(this, (byte) 2);
                    this.aM();
                    if (var4 != null) {
                        double var6 = var4.bm - this.bm;

                        double var8;

                        for (var8 = var4.bo - this.bo; var6 * var6 + var8 * var8 < 1.0E-4D; var8 = (Math.random() - Math.random()) * 0.01D) {
                            var6 = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.au = (float) (Math.atan2(var8, var6) * 180.0D / 3.1415927410125732D) - this.bs;
                        this.a(var4, var2, var6, var8);
                    } else {
                        this.au = (float) ((int) (Math.random() * 2.0D) * 180);
                    }
                }

                if (this.ap <= 0) {
                    if (var3) {
                        this.bi.a(this, this.n(), this.o(), this.v());
                    }

                    this.a(var1);
                } else if (var3) {
                    this.bi.a(this, this.m(), this.o(), this.v());
                }

                return true;
            }
        }
    }

    private float v() {
        return this.l() ? (this.bS.nextFloat() - this.bS.nextFloat()) * 0.2F + 1.5F : (this.bS.nextFloat() - this.bS.nextFloat()) * 0.2F + 1.0F;
    }

    public int P() {
        return 0;
    }

    protected void g(int var1) {}

    protected int d(ODamageSource var1, int var2) {
        if (!var1.e()) {
            int var3 = 25 - this.P();
            int var4 = var2 * var3 + this.ar;

            this.g(var2);
            var2 = var4 / 25;
            this.ar = var4 % 25;
        }

        return var2;
    }

    protected int b(ODamageSource var1, int var2) {
        if (this.a(OPotion.m)) {
            int var3 = (this.b(OPotion.m).c() + 1) * 5;
            int var4 = 25 - var3;
            int var5 = var2 * var4 + this.ar;

            var2 = var5 / 25;
            this.ar = var5 % 25;
        }

        return var2;
    }

    protected void c(ODamageSource var1, int var2) {
        var2 = this.d(var1, var2);
        var2 = this.b(var1, var2);
        this.ap -= var2;
    }

    protected float o() {
        return 1.0F;
    }

    protected String c_() {
        return null;
    }

    protected String m() {
        return "damage.hurtflesh";
    }

    protected String n() {
        return "damage.hurtflesh";
    }

    public void a(OEntity var1, int var2, double var3, double var5) {
        this.ce = true;
        float var7 = OMathHelper.a(var3 * var3 + var5 * var5);
        float var8 = 0.4F;

        this.bp /= 2.0D;
        this.bq /= 2.0D;
        this.br /= 2.0D;
        this.bp -= var3 / (double) var7 * (double) var8;
        this.bq += (double) var8;
        this.br -= var5 / (double) var7 * (double) var8;
        if (this.bq > 0.4000000059604645D) {
            this.bq = 0.4000000059604645D;
        }

    }

    public void a(ODamageSource var1) {
        OEntity var2 = var1.a();

        if (this.aj >= 0 && var2 != null) {
            var2.b(this, this.aj);
        }

        if (var2 != null) {
            var2.a(this);
        }

        this.az = true;
        if (!this.bi.I) {
            int var3 = 0;

            if (var2 instanceof OEntityPlayer) {
                var3 = OEnchantmentHelper.f(((OEntityPlayer) var2).k);
            }

            if (!this.l()) {
                this.a(this.aH > 0, var3);
            }
        }

        this.bi.a(this, (byte) 3);
    }

    protected void a(boolean var1, int var2) {
        int var3 = this.e();

        if (var3 > 0) {
            int var4 = this.bS.nextInt(3);

            if (var2 > 0) {
                var4 += this.bS.nextInt(var2 + 1);
            }

            for (int var5 = 0; var5 < var4; ++var5) {
                this.b(var3, 1);
            }
        }

    }

    protected int e() {
        return 0;
    }

    protected void b(float var1) {
        super.b(var1);
        int var2 = (int) Math.ceil((double) (var1 - 3.0F));

        if (var2 > 0) {
            // CanaryMod Damage hook: Falling
            if (!(Boolean) manager.callHook(PluginLoader.Hook.DAMAGE, PluginLoader.DamageType.FALL, null, entity, var2)) {
                if (var2 > 4) {
                    this.bi.a(this, "damage.fallbig", 1.0F, 1.0F);
                } else {
                    this.bi.a(this, "damage.fallsmall", 1.0F, 1.0F);
                }
                this.a(ODamageSource.i, var2);
            }
            int var3 = this.bi.a(OMathHelper.b(this.bm), OMathHelper.b(this.bn - 0.20000000298023224D - (double) this.bF), OMathHelper.b(this.bo));

            if (var3 > 0) {
                OStepSound var4 = OBlock.m[var3].bZ;

                this.bi.a(this, var4.c(), var4.a() * 0.5F, var4.b() * 0.75F);
            }
        }

    }

    public void a(float var1, float var2) {
        double var3;

        if (this.aK()) {
            var3 = this.bn;
            this.a(var1, var2, 0.02F);
            this.a(this.bp, this.bq, this.br);
            this.bp *= 0.800000011920929D;
            this.bq *= 0.800000011920929D;
            this.br *= 0.800000011920929D;
            this.bq -= 0.02D;
            if (this.by && this.d(this.bp, this.bq + 0.6000000238418579D - this.bn + var3, this.br)) {
                this.bq = 0.30000001192092896D;
            }
        } else if (this.aL()) {
            var3 = this.bn;
            this.a(var1, var2, 0.02F);
            this.a(this.bp, this.bq, this.br);
            this.bp *= 0.5D;
            this.bq *= 0.5D;
            this.br *= 0.5D;
            this.bq -= 0.02D;
            if (this.by && this.d(this.bp, this.bq + 0.6000000238418579D - this.bn + var3, this.br)) {
                this.bq = 0.30000001192092896D;
            }
        } else {
            float var5 = 0.91F;

            if (this.bx) {
                var5 = 0.54600006F;
                int var6 = this.bi.a(OMathHelper.b(this.bm), OMathHelper.b(this.bw.b) - 1, OMathHelper.b(this.bo));

                if (var6 > 0) {
                    var5 = OBlock.m[var6].cc * 0.91F;
                }
            }

            float var12 = 0.16277136F / (var5 * var5 * var5);
            float var7 = this.bx ? this.al * var12 : this.am;

            this.a(var1, var2, var7);
            var5 = 0.91F;
            if (this.bx) {
                var5 = 0.54600006F;
                int var8 = this.bi.a(OMathHelper.b(this.bm), OMathHelper.b(this.bw.b) - 1, OMathHelper.b(this.bo));

                if (var8 > 0) {
                    var5 = OBlock.m[var8].cc * 0.91F;
                }
            }

            if (this.r()) {
                float var13 = 0.15F;

                if (this.bp < (double) (-var13)) {
                    this.bp = (double) (-var13);
                }

                if (this.bp > (double) var13) {
                    this.bp = (double) var13;
                }

                if (this.br < (double) (-var13)) {
                    this.br = (double) (-var13);
                }

                if (this.br > (double) var13) {
                    this.br = (double) var13;
                }

                this.bK = 0.0F;
                if (this.bq < -0.15D) {
                    this.bq = -0.15D;
                }

                if (this.aO() && this.bq < 0.0D) {
                    this.bq = 0.0D;
                }
            }

            this.a(this.bp, this.bq, this.br);
            if (this.by && this.r()) {
                this.bq = 0.2D;
            }

            this.bq -= 0.08D;
            this.bq *= 0.9800000190734863D;
            this.bp *= (double) var5;
            this.br *= (double) var5;
        }

        this.aD = this.aE;
        var3 = this.bm - this.bj;
        double var9 = this.bo - this.bl;
        float var11 = OMathHelper.a(var3 * var3 + var9 * var9) * 4.0F;

        if (var11 > 1.0F) {
            var11 = 1.0F;
        }

        this.aE += (var11 - this.aE) * 0.4F;
        this.aF += this.aE;
    }

    public boolean r() {
        int var1 = OMathHelper.b(this.bm);
        int var2 = OMathHelper.b(this.bw.b);
        int var3 = OMathHelper.b(this.bo);

        return this.bi.a(var1, var2, var3) == OBlock.aH.bO;
    }

    public void b(ONBTTagCompound var1) {
        var1.a("Health", (short) this.ap);
        var1.a("HurtTime", (short) this.as);
        var1.a("DeathTime", (short) this.av);
        var1.a("AttackTime", (short) this.aw);
        if (!this.aL.isEmpty()) {
            ONBTTagList var2 = new ONBTTagList();
            Iterator var3 = this.aL.values().iterator();

            while (var3.hasNext()) {
                OPotionEffect var4 = (OPotionEffect) var3.next();
                ONBTTagCompound var5 = new ONBTTagCompound();

                var5.a("Id", (byte) var4.a());
                var5.a("Amplifier", (byte) var4.c());
                var5.a("Duration", var4.b());
                var2.a((ONBTBase) var5);
            }

            var1.a("ActiveEffects", (ONBTBase) var2);
        }

    }

    public void a(ONBTTagCompound var1) {
        this.ap = var1.e("Health");
        if (!var1.c("Health")) {
            this.ap = this.c();
        }

        this.as = var1.e("HurtTime");
        this.av = var1.e("DeathTime");
        this.aw = var1.e("AttackTime");
        if (var1.c("ActiveEffects")) {
            ONBTTagList var2 = var1.m("ActiveEffects");

            for (int var3 = 0; var3 < var2.d(); ++var3) {
                ONBTTagCompound var4 = (ONBTTagCompound) var2.a(var3);
                byte var5 = var4.d("Id");
                byte var6 = var4.d("Amplifier");
                int var7 = var4.f("Duration");

                this.aL.put(Integer.valueOf(var5), new OPotionEffect(var5, var7, var6));
            }
        }

    }

    public boolean aq() {
        return !this.bE && this.ap > 0;
    }

    public boolean f() {
        return false;
    }

    public void d(float var1) {
        this.aX = var1;
    }

    public void e(boolean var1) {
        this.aZ = var1;
    }

    public float ar() {
        return this.bb;
    }

    public void d() {
        if (this.h > 0) {
            --this.h;
        }

        if (this.aN > 0) {
            double var1 = this.bm + (this.aO - this.bm) / (double) this.aN;
            double var3 = this.bn + (this.aP - this.bn) / (double) this.aN;
            double var5 = this.bo + (this.aQ - this.bo) / (double) this.aN;

            double var7;

            for (var7 = this.aR - (double) this.bs; var7 < -180.0D; var7 += 360.0D) {
                ;
            }

            while (var7 >= 180.0D) {
                var7 -= 360.0D;
            }

            this.bs = (float) ((double) this.bs + var7 / (double) this.aN);
            this.bt = (float) ((double) this.bt + (this.aS - (double) this.bt) / (double) this.aN);
            --this.aN;
            this.c(var1, var3, var5);
            this.c(this.bs, this.bt);
            List var9 = this.bi.a((OEntity) this, this.bw.e(0.03125D, 0.0D, 0.03125D));

            if (var9.size() > 0) {
                double var10 = 0.0D;

                for (int var12 = 0; var12 < var9.size(); ++var12) {
                    OAxisAlignedBB var13 = (OAxisAlignedBB) var9.get(var12);

                    if (var13.e > var10) {
                        var10 = var13.e;
                    }
                }

                var3 += var10 - this.bw.b;
                this.c(var1, var3, var5);
            }
        }

        OProfiler.a("OPacket11PlayerPosition");
        if (this.M()) {
            this.aZ = false;
            this.aW = 0.0F;
            this.aX = 0.0F;
            this.aY = 0.0F;
        } else if (this.at()) {
            if (this.as()) {
                this.av();
            } else {
            this.m_();
                this.X = this.bs;
            }
        }

        OProfiler.a();
        boolean var14 = this.aK();
        boolean var15 = this.aL();

        if (this.aZ) {
            if (var14) {
                this.bq += 0.03999999910593033D;
            } else if (var15) {
                this.bq += 0.03999999910593033D;
            } else if (this.bx && this.h == 0) {
                this.o_();
                this.h = 10;
            }
        } else {
            this.h = 0;
        }

        this.aW *= 0.98F;
        this.aX *= 0.98F;
        this.aY *= 0.9F;
        float var16 = this.al;

        this.al *= this.G();
        this.a(this.aW, this.aX);
        this.al = var16;
        OProfiler.a("push");
        List var17 = this.bi.b((OEntity) this, this.bw.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (var17 != null && var17.size() > 0) {
            for (int var18 = 0; var18 < var17.size(); ++var18) {
                OEntity var19 = (OEntity) var17.get(var18);

                if (var19.f_()) {
                    var19.j(this);
                }
            }
        }

        OProfiler.a();
    }

    protected boolean as() {
        return false;
    }

    protected boolean at() {
        return !this.bi.I;
    }

    protected boolean M() {
        return this.ap <= 0;
    }

    public boolean L() {
        return false;
    }

    protected void o_() {
        this.bq = 0.41999998688697815D;
        if (this.a(OPotion.j)) {
            this.bq += (double) ((float) (this.b(OPotion.j).c() + 1) * 0.1F);
        }

        if (this.aP()) {
            float var1 = this.bs * 0.017453292F;

            this.bp -= (double) (OMathHelper.a(var1) * 0.2F);
            this.br += (double) (OMathHelper.b(var1) * 0.2F);
        }

        this.ce = true;
    }

    protected boolean d_() {
        return true;
    }

    protected void au() {
        OEntityPlayer var1 = this.bi.a(this, -1.0D);

        if (var1 != null) {
            double var2 = var1.bm - this.bm;
            double var4 = var1.bn - this.bn;
            double var6 = var1.bo - this.bo;
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;

            if (this.d_() && var8 > 16384.0D) {
                // CanaryMod hook onEntityDespawn
                if (!(Boolean) OEntityLiving.manager.callHook(PluginLoader.Hook.ENTITY_DESPAWN, this.entity)) {
                    this.T();
                }
            }

            if (this.aV > 600 && this.bS.nextInt(800) == 0 && this.d_()) {
                if (var8 < 1024.0D) {
                    this.aS = 0;
                } else {
                    // CanaryMod hook onEntityDespawn
                    if (!(Boolean) OEntityLiving.manager.callHook(PluginLoader.Hook.ENTITY_DESPAWN, this.entity)) {
                        this.T();
                    } else {
                        this.aS = 0;
                    }
                }
            }

        }
    }

    protected void av() {
        ++this.aV;
        this.au();
        if (this.aI != null && !this.aI.aq()) {
            this.aI = null;
        }

        this.aM.a();
        this.g.a();
        this.e.a();
        this.d.a();
        this.f.b();
    }

    protected void m_() {
        ++this.aV;
        OEntityPlayer var1 = this.bi.a(this, -1.0D);

        this.au();
        this.aW = 0.0F;
        this.aX = 0.0F;
        float var2 = 8.0F;

        if (this.bS.nextFloat() < 0.02F) {
            var1 = this.bi.a(this, (double) var2);
            if (var1 != null) {
                this.i = var1;
                this.bc = 10 + this.bS.nextInt(20);
            } else {
                this.aY = (this.bS.nextFloat() - 0.5F) * 20.0F;
            }
        }

        if (this.i != null) {
            this.a(this.i, 10.0F, (float) this.x());
            if (this.bc-- <= 0 || this.i.bE || this.i.i(this) > (double) (var2 * var2)) {
                this.i = null;
            }
        } else {
            if (this.bS.nextFloat() < 0.05F) {
                this.aY = (this.bS.nextFloat() - 0.5F) * 20.0F;
            }

            this.bs += this.aY;
            this.bt = this.ba;
        }

        boolean var3 = this.aK();
        boolean var4 = this.aL();

        if (var3 || var4) {
            this.aZ = this.bS.nextFloat() < 0.8F;
        }

    }

    public int x() {
        return 40;
    }

    public void a(OEntity var1, float var2, float var3) {
        double var4 = var1.bm - this.bm;
        double var6 = var1.bo - this.bo;
        double var9;

        if (var1 instanceof OEntityLiving) {
            OEntityLiving var8 = (OEntityLiving) var1;

            var9 = this.bn + (double) this.y() - (var8.bn + (double) var8.y());
        } else {
            var9 = (var1.bw.b + var1.bw.e) / 2.0D - (this.bn + (double) this.y());
        }

        double var11 = (double) OMathHelper.a(var4 * var4 + var6 * var6);
        float var13 = (float) (Math.atan2(var6, var4) * 180.0D / 3.1415927410125732D) - 90.0F;
        float var14 = (float) (-(Math.atan2(var9, var11) * 180.0D / 3.1415927410125732D));

        this.bt = -this.b(this.bt, var14, var3);
        this.bs = this.b(this.bs, var13, var2);
    }

    public boolean aw() {
        return this.i != null;
    }

    public OEntity ax() {
        return this.i;
    }

    private float b(float var1, float var2, float var3) {
        float var4;

        for (var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F) {
            ;
        }

        while (var4 >= 180.0F) {
            var4 -= 360.0F;
        }

        if (var4 > var3) {
            var4 = var3;
        }

        if (var4 < -var3) {
            var4 = -var3;
        }

        return var1 + var4;
    }

    public void ay() {}

    public boolean g() {
        return this.bi.a(this.bw) && this.bi.a((OEntity) this, this.bw).size() == 0 && !this.bi.c(this.bw);
    }

    protected void az() {
        this.a(ODamageSource.j, 4);
    }

    public OVec3D aA() {
        return this.e(1.0F);
    }

    public OVec3D e(float var1) {
        float var2;
        float var3;
        float var4;
        float var5;

        if (var1 == 1.0F) {
            var2 = OMathHelper.b(-this.bs * 0.017453292F - 3.1415927F);
            var3 = OMathHelper.a(-this.bs * 0.017453292F - 3.1415927F);
            var4 = -OMathHelper.b(-this.bt * 0.017453292F);
            var5 = OMathHelper.a(-this.bt * 0.017453292F);
            return OVec3D.b((double) (var3 * var4), (double) var5, (double) (var2 * var4));
        } else {
            var2 = this.bv + (this.bt - this.bv) * var1;
            var3 = this.bu + (this.bs - this.bu) * var1;
            var4 = OMathHelper.b(-var3 * 0.017453292F - 3.1415927F);
            var5 = OMathHelper.a(-var3 * 0.017453292F - 3.1415927F);
            float var6 = -OMathHelper.b(-var2 * 0.017453292F);
            float var7 = OMathHelper.a(-var2 * 0.017453292F);

            return OVec3D.b((double) (var5 * var6), (double) var7, (double) (var4 * var6));
        }
    }

    public int p() {
        return 4;
    }

    public boolean V() {
        return false;
    }

    protected void aB() {
        Iterator var1 = this.aL.keySet().iterator();

        while (var1.hasNext()) {
            Integer var2 = (Integer) var1.next();
            OPotionEffect var3 = (OPotionEffect) this.aL.get(var2);

            if (!var3.a(this) && !this.bi.I) {
                var1.remove();
                this.d(var3);
            }
        }

        int var10;

        if (this.b) {
            if (!this.bi.I) {
                if (!this.aL.isEmpty()) {
                    var10 = OPotionHelper.a(this.aL.values());
                    this.bY.b(8, Integer.valueOf(var10));
                } else {
                    this.bY.b(8, Integer.valueOf(0));
                }
            }

            this.b = false;
        }

        if (this.bS.nextBoolean()) {
            var10 = this.bY.c(8);
            if (var10 > 0) {
                double var4 = (double) (var10 >> 16 & 255) / 255.0D;
                double var6 = (double) (var10 >> 8 & 255) / 255.0D;
                double var8 = (double) (var10 >> 0 & 255) / 255.0D;

                this.bi.a("mobSpell", this.bm + (this.bS.nextDouble() - 0.5D) * (double) this.bG, this.bn + this.bS.nextDouble() * (double) this.bH - (double) this.bF, this.bo + (this.bS.nextDouble() - 0.5D) * (double) this.bG, var4, var6, var8);
            }
        }

    }

    public void aC() {
        Iterator var1 = this.aL.keySet().iterator();

        while (var1.hasNext()) {
            Integer var2 = (Integer) var1.next();
            OPotionEffect var3 = (OPotionEffect) this.aL.get(var2);

            if (!this.bi.I) {
                var1.remove();
                this.d(var3);
            }
        }

    }

    public Collection aD() {
        return this.aL.values();
    }

    public boolean a(OPotion var1) {
        return this.aL.containsKey(Integer.valueOf(var1.H));
    }

    public OPotionEffect b(OPotion var1) {
        return (OPotionEffect) this.aL.get(Integer.valueOf(var1.H));
    }

    public void e(OPotionEffect var1) {
        // CanaryMod - POTION_EFFECT HOOK
        PotionEffect pe = (PotionEffect) etc.getLoader().callHook(PluginLoader.Hook.POTION_EFFECT, this.entity, var1.potionEffect);

        if (pe == null) {
            return;
        }
        var1 = pe.potionEffect;
        if (this.a(var1)) {
            if (this.aL.containsKey(Integer.valueOf(var1.a()))) {
                ((OPotionEffect) this.aL.get(Integer.valueOf(var1.a()))).a(var1);
                this.c((OPotionEffect) this.aL.get(Integer.valueOf(var1.a())));
            } else {
                this.aL.put(Integer.valueOf(var1.a()), var1);
                this.b(var1);
            }
        }
    }

    public boolean a(OPotionEffect var1) {
        if (this.t() == OEnumCreatureAttribute.b) {
            int var2 = var1.a();

            if (var2 == OPotion.l.H || var2 == OPotion.u.H) {
                return false;
            }
        }

        return true;
    }

    public boolean aE() {
        return this.t() == OEnumCreatureAttribute.b;
    }

    protected void b(OPotionEffect var1) {
        this.b = true;
    }

    protected void c(OPotionEffect var1) {
        this.b = true;
    }

    protected void d(OPotionEffect var1) {
        this.b = true;
    }

    protected float G() {
        float var1 = 1.0F;

        if (this.a(OPotion.c)) {
            var1 *= 1.0F + 0.2F * (float) (this.b(OPotion.c).c() + 1);
        }

        if (this.a(OPotion.d)) {
            var1 *= 1.0F - 0.15F * (float) (this.b(OPotion.d).c() + 1);
        }

        return var1;
    }

    public void a_(double var1, double var3, double var5) {
        this.c(var1, var3, var5, this.bs, this.bt);
    }

    public boolean l() {
        return false;
    }

    public OEnumCreatureAttribute t() {
        return OEnumCreatureAttribute.a;
    }

    public void c(OItemStack var1) {
        this.bi.a(this, "random.break", 0.8F, 0.8F + this.bi.w.nextFloat() * 0.4F);

        for (int var2 = 0; var2 < 5; ++var2) {
            OVec3D var3 = OVec3D.b(((double) this.bS.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);

            var3.a(-this.bt * 3.1415927F / 180.0F);
            var3.b(-this.bs * 3.1415927F / 180.0F);
            OVec3D var4 = OVec3D.b(((double) this.bS.nextFloat() - 0.5D) * 0.3D, (double) (-this.bS.nextFloat()) * 0.6D - 0.3D, 0.6D);

            var4.a(-this.bt * 3.1415927F / 180.0F);
            var4.b(-this.bs * 3.1415927F / 180.0F);
            var4 = var4.c(this.bm, this.bn + (double) this.y(), this.bo);
            this.bi.a("iconcrack_" + var1.a().bN, var4.a, var4.b, var4.c, var3.a, var3.b + 0.05D, var3.c);
        }

    }
}
