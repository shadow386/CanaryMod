import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class OBiomeGenBase {

    public static final OBiomeGenBase a = new OBiomeGenRainforest().b(588342).a("Rainforest").a(2094168);
    public static final OBiomeGenBase b = new OBiomeGenSwamp().b(522674).a("Swampland").a(9154376);
    public static final OBiomeGenBase c = new OBiomeGenBase().b(10215459).a("Seasonal Forest");
    public static final OBiomeGenBase d = new OBiomeGenForest().b(353825).a("Forest").a(5159473);
    public static final OBiomeGenBase e = new OBiomeGenDesert().b(14278691).a("Savanna");
    public static final OBiomeGenBase f = new OBiomeGenBase().b(10595616).a("Shrubland");
    public static final OBiomeGenBase g = new OBiomeGenTaiga().b(3060051).a("Taiga").b().a(8107825);
    public static final OBiomeGenBase h = new OBiomeGenDesert().b(16421912).a("Desert").e();
    public static final OBiomeGenBase i = new OBiomeGenDesert().b(16767248).a("Plains");
    public static final OBiomeGenBase j = new OBiomeGenDesert().b(16772499).a("Ice Desert").b().e().a(12899129);
    public static final OBiomeGenBase k = new OBiomeGenBase().b(5762041).a("Tundra").b().a(12899129);
    public static final OBiomeGenBase l = new OBiomeGenHell().b(16711680).a("Hell").e();
    public String                       m;
    public int                          n;
    public byte                         o = (byte) OBlock.u.bl;
    public byte                         p = (byte) OBlock.v.bl;
    public int                          q = 5169201;

    protected List                      r = new ArrayList();
    protected List                      s = new ArrayList();
    protected List                      t = new ArrayList();
    private boolean                     u;
    private boolean                     v = true;
    private static OBiomeGenBase[]    w = new OBiomeGenBase[4096];

    private OBiomeGenBase e() {
        v = false;
        return this;
    }

    public static void a() {
        for (int i1 = 0; i1 < 64; i1++)
            for (int i2 = 0; i2 < 64; i2++)
                w[(i1 + i2 * 64)] = a(i1 / 63.0F, i2 / 63.0F);

        h.o = (h.p = (byte) OBlock.E.bl);
        j.o = (j.p = (byte) OBlock.E.bl);
    }

    public OWorldGenerator a(Random paramRandom) {
        if (paramRandom.nextInt(10) == 0)
            return new OWorldGenBigTree();
        return new OWorldGenTrees();
    }

    protected OBiomeGenBase b() {
        u = true;
        return this;
    }

    protected OBiomeGenBase a(String paramString) {
        m = paramString;
        return this;
    }

    protected OBiomeGenBase a(int paramInt) {
        q = paramInt;
        return this;
    }

    protected OBiomeGenBase b(int paramInt) {
        n = paramInt;
        return this;
    }

    public static OBiomeGenBase a(double paramDouble1, double paramDouble2) {
        int i1 = (int) (paramDouble1 * 63.0D);
        int i2 = (int) (paramDouble2 * 63.0D);
        return w[(i1 + i2 * 64)];
    }

    public static OBiomeGenBase a(float paramFloat1, float paramFloat2) {
        paramFloat2 *= paramFloat1;
        if (paramFloat1 < 0.1F)
            return k;
        if (paramFloat2 < 0.2F) {
            if (paramFloat1 < 0.5F)
                return k;
            if (paramFloat1 < 0.95F)
                return e;
            return h;
        }
        if ((paramFloat2 > 0.5F) && (paramFloat1 < 0.7F))
            return b;
        if (paramFloat1 < 0.5F)
            return g;
        if (paramFloat1 < 0.97F) {
            if (paramFloat2 < 0.35F)
                return f;
            return d;
        }

        if (paramFloat2 < 0.45F)
            return i;
        if (paramFloat2 < 0.9F)
            return c;
        return a;
    }

    // CanaryMod: Custom mob spawning
    public List a(OEnumCreatureType paramOEnumCreatureType) {
        etc config = etc.getInstance();
        if (paramOEnumCreatureType == OEnumCreatureType.a)
            return config.getMonstersClass(this);
        if (paramOEnumCreatureType == OEnumCreatureType.b)
            return config.getAnimalsClass(this);
        if (paramOEnumCreatureType == OEnumCreatureType.c)
            return config.getWaterAnimalsClass(this);
        return null;
    }

    public boolean c() {
        return u;
    }

    public boolean d() {
        return u?false:v;
    }

    static {
        a();
    }
}
