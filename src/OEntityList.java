import java.util.HashMap;
import java.util.Map;


public class OEntityList {

    private static Map b = new HashMap();
    private static Map c = new HashMap();
    private static Map d = new HashMap();
    private static Map e = new HashMap();
    private static Map f = new HashMap();
    public static HashMap a = new HashMap();

    public OEntityList() {
        super();
    }

    private static void a(Class var0, String var1, int var2) {
        b.put(var1, var0);
        c.put(var0, var1);
        d.put(Integer.valueOf(var2), var0);
        e.put(var0, Integer.valueOf(var2));
        f.put(var1, Integer.valueOf(var2));
    }

    private static void a(Class var0, String var1, int var2, int var3, int var4) {
        a(var0, var1, var2);
        a.put(Integer.valueOf(var2), new OEntityEggInfo(var2, var3, var4));
    }

    public static OEntity a(String var0, OWorld var1) {
        OEntity var2 = null;

        try {
            Class var3 = (Class) b.get(var0);

            if (var3 != null) {
                var2 = (OEntity) var3.getConstructor(new Class[] { OWorld.class}).newInstance(new Object[] { var1});
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return var2;
    }

    public static OEntity a(ONBTTagCompound var0, OWorld var1) {
        OEntity var2 = null;

        try {
            Class var3 = (Class) b.get(var0.j("id"));

            if (var3 != null) {
                var2 = (OEntity) var3.getConstructor(new Class[] { OWorld.class}).newInstance(new Object[] { var1});
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        if (var2 != null) {
            var2.e(var0);
        } else {
            System.out.println("Skipping Entity with id " + var0.j("id"));
        }

        return var2;
    }

    public static OEntity a(int var0, OWorld var1) {
        OEntity var2 = null;

        try {
            Class var3 = (Class) d.get(Integer.valueOf(var0));

            if (var3 != null) {
                var2 = (OEntity) var3.getConstructor(new Class[] { OWorld.class}).newInstance(new Object[] { var1});
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        if (var2 == null) {
            System.out.println("Skipping Entity with id " + var0);
        }

        return var2;
    }

    public static int a(OEntity var0) {
        return ((Integer) e.get(var0.getClass())).intValue();
    }

    public static String b(OEntity var0) {
        return (String) c.get(var0.getClass());
    }

    public static int a(String var0) {
        Integer var1 = (Integer) f.get(var0);

        return var1 == null ? 90 : var1.intValue();
    }
    
    // CanaryMod: Let us do a name->class lookup for mob spawning
    public static Class<?> getEntity(String name) {
        return (Class<?>) b.get(name);
    }
    
    // CanaryMod: Let us do a class->name lookup for mob spawning
    public static String getName(Class<? extends OEntity> clazz) {
        return (String) c.get(clazz);
    }

    static {
        a(OEntityItem.class, "Item", 1);
        a(OEntityXPOrb.class, "XPOrb", 2);
        a(OEntityPainting.class, "Painting", 9);
        a(OEntityArrow.class, "Arrow", 10);
        a(OEntitySnowball.class, "Snowball", 11);
        a(OEntityFireball.class, "Fireball", 12);
        a(OEntitySmallFireball.class, "SmallFireball", 13);
        a(OEntityThrownEnderpearl.class, "ThrownEnderpearl", 14);
        a(OEntityEnderEye.class, "EyeOfEnderSignal", 15);
        a(OEntityPotion.class, "ThrownPotion", 16);
        a(OEntityExpBottle.class, "ThrownExpBottle", 17);
        a(OEntityTNTPrimed.class, "PrimedTnt", 20);
        a(OEntityFallingSand.class, "FallingSand", 21);
        a(OEntityMinecart.class, "Minecart", 40);
        a(OEntityBoat.class, "Boat", 41);
        a(OEntityLiving.class, "Mob", 48);
        a(OEntityMob.class, "Monster", 49);
        a(OEntityCreeper.class, "Creeper", 50, 894731, 0);
        a(OEntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        a(OEntitySpider.class, "Spider", 52, 3419431, 11013646);
        a(OEntityGiantZombie.class, "Giant", 53);
        a(OEntityZombie.class, "Zombie", 54, '\uafaf', 7969893);
        a(OEntitySlime.class, "Slime", 55, 5349438, 8306542);
        a(OEntityGhast.class, "Ghast", 56, 16382457, 12369084);
        a(OEntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        a(OEntityEnderman.class, "Enderman", 58, 1447446, 0);
        a(OEntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        a(OEntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        a(OEntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        a(OEntityLavaSlime.class, "LavaSlime", 62, 3407872, 16579584);
        a(OEntityEnderDragon.class, "EnderDragon", 63);
        a(OEntityPig.class, "Pig", 90, 15771042, 14377823);
        a(OEntitySheep.class, "Sheep", 91, 15198183, 16758197);
        a(OEntityCow.class, "Cow", 92, 4470310, 10592673);
        a(OEntityChicken.class, "Chicken", 93, 10592673, 16711680);
        a(OEntitySquid.class, "Squid", 94, 2243405, 7375001);
        a(OEntityWolf.class, "Wolf", 95, 14144467, 13545366);
        a(OEntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
        a(OEntitySnowMan.class, "SnowMan", 97);
        a(OEntityOzelot.class, "Ozelot", 98, 15720061, 5653556);
        a(OEntityIronGolem.class, "VillagerGolem", 99);
        a(OEntityVillager.class, "Villager", 120, 5651507, 12422002);
        a(OEntityEnderCrystal.class, "EnderCrystal", 200);
    }
}
