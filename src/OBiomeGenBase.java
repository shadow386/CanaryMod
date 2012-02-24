import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class OBiomeGenBase {

    public static final OBiomeGenBase[] a = new OBiomeGenBase[256];
    public static final OBiomeGenBase b = (new OBiomeGenOcean(0)).b(112).a("Ocean").b(-1.0F, 0.4F);
    public static final OBiomeGenBase c = (new OBiomeGenPlains(1)).b(9286496).a("Plains").a(0.8F, 0.4F);
    public static final OBiomeGenBase d = (new OBiomeGenDesert(2)).b(16421912).a("Desert").g().a(2.0F, 0.0F).b(0.1F, 0.2F);
    public static final OBiomeGenBase e = (new OBiomeGenHills(3)).b(6316128).a("Extreme Hills").b(0.2F, 1.3F).a(0.2F, 0.3F);
    public static final OBiomeGenBase f = (new OBiomeGenForest(4)).b(353825).a("Forest").a(5159473).a(0.7F, 0.8F);
    public static final OBiomeGenBase g = (new OBiomeGenTaiga(5)).b(747097).a("Taiga").a(5159473).a(0.05F, 0.8F).b(0.1F, 0.4F);
    public static final OBiomeGenBase h = (new OBiomeGenSwamp(6)).b(522674).a("Swampland").a(9154376).b(-0.2F, 0.1F).a(0.8F, 0.9F);
    public static final OBiomeGenBase i = (new OBiomeGenRiver(7)).b(255).a("River").b(-0.5F, 0.0F);
    public static final OBiomeGenBase j = (new OBiomeGenHell(8)).b(16711680).a("Hell").g().a(2.0F, 0.0F);
    public static final OBiomeGenBase k = (new OBiomeGenSky(9)).b(8421631).a("Sky").g();
    public static final OBiomeGenBase l = (new OBiomeGenOcean(10)).b(9474208).a("FrozenOcean").b(-1.0F, 0.5F).a(0.0F, 0.5F);
    public static final OBiomeGenBase m = (new OBiomeGenRiver(11)).b(10526975).a("FrozenRiver").b(-0.5F, 0.0F).a(0.0F, 0.5F);
    public static final OBiomeGenBase n = (new OBiomeGenSnow(12)).b(16777215).a("Ice Plains").a(0.0F, 0.5F);
    public static final OBiomeGenBase o = (new OBiomeGenSnow(13)).b(10526880).a("Ice Mountains").b(0.2F, 1.2F).a(0.0F, 0.5F);
    public static final OBiomeGenBase p = (new OBiomeGenMushroomIsland(14)).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).b(0.2F, 1.0F);
    public static final OBiomeGenBase q = (new OBiomeGenMushroomIsland(15)).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).b(-1.0F, 0.1F);
    public static final OBiomeGenBase r = (new OBiomeGenBeach(16)).b(16440917).a("Beach").a(0.8F, 0.4F).b(0.0F, 0.1F);
    public static final OBiomeGenBase s = (new OBiomeGenDesert(17)).b(13786898).a("DesertHills").g().a(2.0F, 0.0F).b(0.2F, 0.7F);
    public static final OBiomeGenBase t = (new OBiomeGenForest(18)).b(2250012).a("ForestHills").a(5159473).a(0.7F, 0.8F).b(0.2F, 0.6F);
    public static final OBiomeGenBase u = (new OBiomeGenTaiga(19)).b(1456435).a("TaigaHills").a(5159473).a(0.05F, 0.8F).b(0.2F, 0.7F);
    public static final OBiomeGenBase v = (new OBiomeGenHills(20)).b(7501978).a("Extreme Hills Edge").b(0.2F, 0.8F).a(0.2F, 0.3F);
    public String w;
    public int x;
    public byte y;
    public byte z;
    public int A;
    public float B;
    public float C;
    public float D;
    public float E;
    public int F;
    public OBiomeDecorator G;
    protected List H;
    protected List I;
    protected List J;
    private boolean P;
    private boolean Q;
    public final int K;
    protected OWorldGenTrees L;
    protected OWorldGenBigTree M;
    protected OWorldGenForest N;
    protected OWorldGenSwamp O;

    protected OBiomeGenBase(int var1) {
        super();
        this.y = (byte) OBlock.w.bO;
        this.z = (byte) OBlock.x.bO;
        this.A = 5169201;
        this.B = 0.1F;
        this.C = 0.3F;
        this.D = 0.5F;
        this.E = 0.5F;
        this.F = 16777215;
        this.H = new ArrayList();
        this.I = new ArrayList();
        this.J = new ArrayList();
        this.Q = true;
        this.L = new OWorldGenTrees(false);
        this.M = new OWorldGenBigTree(false);
        this.N = new OWorldGenForest(false);
        this.O = new OWorldGenSwamp();
        this.K = var1;
        a[var1] = this;
        this.G = this.a();
        this.I.add(new OSpawnListEntry(OEntitySheep.class, 12, 4, 4));
        this.I.add(new OSpawnListEntry(OEntityPig.class, 10, 4, 4));
        this.I.add(new OSpawnListEntry(OEntityChicken.class, 10, 4, 4));
        this.I.add(new OSpawnListEntry(OEntityCow.class, 8, 4, 4));
        this.H.add(new OSpawnListEntry(OEntitySpider.class, 10, 4, 4));
        this.H.add(new OSpawnListEntry(OEntityZombie.class, 10, 4, 4));
        this.H.add(new OSpawnListEntry(OEntitySkeleton.class, 10, 4, 4));
        this.H.add(new OSpawnListEntry(OEntityCreeper.class, 10, 4, 4));
        this.H.add(new OSpawnListEntry(OEntitySlime.class, 10, 4, 4));
        this.H.add(new OSpawnListEntry(OEntityEnderman.class, 1, 1, 4));
        this.J.add(new OSpawnListEntry(OEntitySquid.class, 10, 4, 4));
    }

    protected OBiomeDecorator a() {
        return new OBiomeDecorator(this);
    }

    private OBiomeGenBase a(float var1, float var2) {
        if (var1 > 0.1F && var1 < 0.2F) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        } else {
            this.D = var1;
            this.E = var2;
            return this;
        }
    }

    private OBiomeGenBase b(float var1, float var2) {
        this.B = var1;
        this.C = var2;
        return this;
    }

    private OBiomeGenBase g() {
        this.Q = false;
        return this;
    }

    public OWorldGenerator a(Random var1) {
        return (OWorldGenerator) (var1.nextInt(10) == 0 ? this.M : this.L);
    }

    protected OBiomeGenBase a(String var1) {
        this.w = var1;
        return this;
    }

    protected OBiomeGenBase a(int var1) {
        this.A = var1;
        return this;
    }

    protected OBiomeGenBase b(int var1) {
        this.x = var1;
        return this;
    }
   
    // CanaryMod start - responsible for adding spawned monsters to the monster list.
    public List a(OEnumCreatureType var1) {
        etc config = etc.getInstance();

        if (var1 == OEnumCreatureType.a) {
            return config.getMonstersClass(this);
        }
        if (var1 == OEnumCreatureType.b) {
            return config.getAnimalsClass(this);
        }
        if (var1 == OEnumCreatureType.c) {
            return config.getWaterAnimalsClass(this);
        }
        return null;
    }
    // CanaryMod end

    public boolean b() {
        return this.P;
    }

    public boolean c() {
        return this.P ? false : this.Q;
    }

    public float d() {
        return 0.1F;
    }

    public final int e() {
        return (int) (this.E * 65536.0F);
    }

    public final int f() {
        return (int) (this.D * 65536.0F);
    }

    public void a(OWorld var1, Random var2, int var3, int var4) {
        this.G.a(var1, var2, var3, var4);
    }

}
