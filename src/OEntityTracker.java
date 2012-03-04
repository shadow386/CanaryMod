import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.logging.Level;

import net.minecraft.server.MinecraftServer;


public class OEntityTracker {
    // CanaryMod: New fields to store the runnables in.
    private static final DelayQueue<DelayedTask> delayQueue = new DelayQueue<DelayedTask>();

    private Set a = new HashSet();
    private OIntHashMap b = new OIntHashMap();
    private MinecraftServer c;
    private int d;
    private int e;

    public OEntityTracker(MinecraftServer var1, int var2) {
        super();
        this.c = var1;
        this.e = var2;
        this.d = var1.h.a();
    }

    public void a(OEntity var1) {
        if (var1 instanceof OEntityPlayerMP) {
            this.a(var1, 512, 2);
            OEntityPlayerMP var2 = (OEntityPlayerMP) var1;
            Iterator var3 = this.a.iterator();

            while (var3.hasNext()) {
                OEntityTrackerEntry var4 = (OEntityTrackerEntry) var3.next();

                if (var4.a != var2) {
                    var4.b(var2);
                }
            }
        } else if (var1 instanceof OEntityFishHook) {
            this.a(var1, 64, 5, true);
        } else if (var1 instanceof OEntityArrow) {
            this.a(var1, 64, 20, false);
        } else if (var1 instanceof OEntitySmallFireball) {
            this.a(var1, 64, 10, false);
        } else if (var1 instanceof OEntityFireball) {
            this.a(var1, 64, 10, false);
        } else if (var1 instanceof OEntitySnowball) {
            this.a(var1, 64, 10, true);
        } else if (var1 instanceof OEntityThrownEnderpearl) {
            this.a(var1, 64, 10, true);
        } else if (var1 instanceof OEntityEnderEye) {
            this.a(var1, 64, 10, true);
        } else if (var1 instanceof OEntityEgg) {
            this.a(var1, 64, 10, true);
        } else if (var1 instanceof OEntityPotion) {
            this.a(var1, 64, 10, true);
        } else if (var1 instanceof OEntityExpBottle) {
            this.a(var1, 64, 10, true);
        } else if (var1 instanceof OEntityItem) {
            this.a(var1, 64, 20, true);
        } else if (var1 instanceof OEntityMinecart) {
            this.a(var1, 80, 3, true);
        } else if (var1 instanceof OEntityBoat) {
            this.a(var1, 80, 3, true);
        } else if (var1 instanceof OEntitySquid) {
            this.a(var1, 64, 3, true);
        } else if (var1 instanceof OIAnimals) {
            this.a(var1, 80, 3, true);
        } else if (var1 instanceof OEntityEnderDragon) {
            this.a(var1, 160, 3, true);
        } else if (var1 instanceof OEntityTNTPrimed) {
            this.a(var1, 160, 10, true);
        } else if (var1 instanceof OEntityFallingSand) {
            this.a(var1, 160, 20, true);
        } else if (var1 instanceof OEntityPainting) {
            this.a(var1, 160, Integer.MAX_VALUE, false);
        } else if (var1 instanceof OEntityXPOrb) {
            this.a(var1, 160, 20, true);
        } else if (var1 instanceof OEntityEnderCrystal) {
            this.a(var1, 256, Integer.MAX_VALUE, false);
        }

    }

    public void a(OEntity var1, int var2, int var3) {
        this.a(var1, var2, var3, false);
    }

    public void a(OEntity var1, int var2, int var3, boolean var4) {
        if (var2 > this.d) {
            var2 = this.d;
        }

        if (this.b.b(var1.bd)) {
            throw new IllegalStateException("Entity is already tracked!");
        } else {
            OEntityTrackerEntry var5 = new OEntityTrackerEntry(var1, var2, var3, var4);

            this.a.add(var5);
            this.b.a(var1.bd, var5);
            var5.b(this.c.a(this.e).d);
        }
    }

    public void b(OEntity var1) {
        if (var1 instanceof OEntityPlayerMP) {
            OEntityPlayerMP var2 = (OEntityPlayerMP) var1;
            Iterator var3 = this.a.iterator();

            while (var3.hasNext()) {
                OEntityTrackerEntry var4 = (OEntityTrackerEntry) var3.next();

                var4.a(var2);
            }
        }

        OEntityTrackerEntry var5 = (OEntityTrackerEntry) this.b.d(var1.bd);

        if (var5 != null) {
            this.a.remove(var5);
            var5.a();
        }

    }

    public void a() {
		try {
			ArrayList var1 = new ArrayList();
			Iterator var2 = this.a.iterator();

			while (var2.hasNext()) {
				OEntityTrackerEntry var3 = (OEntityTrackerEntry) var2.next();

				var3.a(this.c.a(this.e).d);
				if (var3.n && var3.a instanceof OEntityPlayerMP) {
					var1.add((OEntityPlayerMP) var3.a);
				}
			}

			for (int var6 = 0; var6 < var1.size(); ++var6) {
				OEntityPlayerMP var7 = (OEntityPlayerMP) var1.get(var6);
				Iterator var4 = this.a.iterator();

				while (var4.hasNext()) {
					OEntityTrackerEntry var5 = (OEntityTrackerEntry) var4.next();

					if (var5.a != var7) {
						var5.b(var7);
					}
				}
			}
		} catch (ConcurrentModificationException ex) {
            // people seem to get this exception often, lets just catch so it doesn't crash the server.
            MinecraftServer.a.log(Level.WARNING, "CanaryMod WARNING: ConcurrentModificationException in OEntityTracker:", ex);   
        }
        // CanaryMod: Execute runnables contained in eventQueue.
        for (DelayedTask task = delayQueue.poll(); task != null; task = delayQueue.poll()) {
            // should we catch exceptions here?
            task.run();
        }
    }
	
	// CanaryMod: Allow adding of tasks to the queue

    public static void add(Runnable task, long delayMillis) {
        // j.u.concurent.* classes are thread safe
        delayQueue.add(new DelayedTask(task, delayMillis));
    }

    // CanaryMod: deprecated. Use server.addToServerQueue().
    @Deprecated
    public synchronized static void add(Runnable r) {
        add(r, 0L);
    }

    public void a(OEntity var1, OPacket var2) {
        OEntityTrackerEntry var3 = (OEntityTrackerEntry) this.b.a(var1.bd);

        if (var3 != null) {
            var3.a(var2);
        }

    }

    public void b(OEntity var1, OPacket var2) {
        OEntityTrackerEntry var3 = (OEntityTrackerEntry) this.b.a(var1.bd);

        if (var3 != null) {
            var3.b(var2);
        }

    }

    public void a(OEntityPlayerMP var1) {
        Iterator var2 = this.a.iterator();

        while (var2.hasNext()) {
            OEntityTrackerEntry var3 = (OEntityTrackerEntry) var2.next();

            var3.c(var1);
        }

    }
}
