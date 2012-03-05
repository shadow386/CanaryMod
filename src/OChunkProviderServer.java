import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class OChunkProviderServer implements OIChunkProvider {

    private Set b = new HashSet();
    private OChunk c;
    private OIChunkProvider d;
    private OIChunkLoader e;
    public boolean a = false;
    private OLongHashMap f = new OLongHashMap();
    private List g = new ArrayList();
    private OWorldServer h;
	// CanaryMod: load status
    boolean loaded = false;
    boolean loadedpreload = false;

    public OChunkProviderServer(OWorldServer var1, OIChunkLoader var2, OIChunkProvider var3) {
        super();
        this.c = new OEmptyChunk(var1, 0, 0);
        this.h = var1;
        this.e = var2;
        this.d = var3;
    }

    public boolean a(int var1, int var2) {
        return this.f.b(OChunkCoordIntPair.a(var1, var2));
    }

    public void d(int var1, int var2) {
        if (this.h.t.c()) {
            OChunkCoordinates var3 = this.h.p();
            int var4 = var1 * 16 + 8 - var3.a;
            int var5 = var2 * 16 + 8 - var3.c;
            short var6 = 128;

            if (var4 < -var6 || var4 > var6 || var5 < -var6 || var5 > var6) {
                this.b.add(Long.valueOf(OChunkCoordIntPair.a(var1, var2)));
            }
        } else {
            this.b.add(Long.valueOf(OChunkCoordIntPair.a(var1, var2)));
        }

    }

    public void c() {
        Iterator var1 = this.g.iterator();

        while (var1.hasNext()) {
            OChunk var2 = (OChunk) var1.next();

            this.d(var2.g, var2.h);
        }

    }

    public OChunk c(int var1, int var2) {
        long var3 = OChunkCoordIntPair.a(var1, var2);

        this.b.remove(Long.valueOf(var3));
        OChunk var5 = (OChunk) this.f.a(var3);

        if (var5 == null) {
			// CanaryMod: load preload plugins once!
            if (!loadedpreload) {
                etc.getLoader().loadPreloadPlugins();
                loadedpreload = true;
            }
            var5 = this.e(var1, var2);
            if (var5 == null) {
				// Canary onChunkCreate hook
                byte[] blocks = (byte[]) etc.getLoader().callHook(PluginLoader.Hook.CHUNK_CREATE, var1, var2, h.world);

                if (blocks != null) {
					var5 = Chunk.getNewChunk(h, blocks, var1, var2).chunk;
                    var5.k = true; // is populated = true
                    var5.b(); // lightning update
				} else if (this.d == null) {
                    var5 = this.c;
                } else {
                    var5 = this.d.b(var1, var2);
                }
            }

            this.f.a(var3, var5);
            this.g.add(var5);
            if (var5 != null) {
                var5.b();
                var5.c();
                // Canary onChunkLoaded hook
                etc.getLoader().callHook(PluginLoader.Hook.CHUNK_LOADED, var5.chunk);
                if (!var5.k && this.a(var1 + 1, var2 + 1) && this.a(var1, var2 + 1) && this.a(var1 + 1, var2)) {
                    this.a(this, var1, var2);
                }
            }// To prevent NullPointerExceptions
            if (this.a(var1 - 1, var2) && !this.b(var1 - 1, var2).k && this.a(var1 - 1, var2 + 1) && this.a(var1, var2 + 1) && this.a(var1 - 1, var2)) {
                this.a(this, var1 - 1, var2);
            }

            var5.a(this, this, var1, var2);
        }

        return var5;
    }

    public OChunk b(int var1, int var2) {
        OChunk var3 = (OChunk) this.f.a(OChunkCoordIntPair.a(var1, var2));

        return var3 == null ? (!this.h.y && !this.a ? this.c : this.c(var1, var2)) : var3;
    }

    private OChunk e(int var1, int var2) {
        if (this.e == null) {
            return null;
        } else {
            try {
                OChunk var3 = this.e.a(this.h, var1, var2);

                if (var3 != null) {
                    var3.n = this.h.o();
                }

                return var3;
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    private void a(OChunk var1) {
        if (this.e != null) {
            try {
                this.e.b(this.h, var1);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    private void b(OChunk var1) {
        if (this.e != null) {
            var1.n = this.h.o();
			this.e.a(this.h, var1);
        }
    }

    public void a(OIChunkProvider var1, int var2, int var3) {
        OChunk var4 = this.b(var2, var3);

        if (!var4.k) {
            var4.k = true;
            if (this.d != null) {
                this.d.a(var1, var2, var3);
                var4.e();
            }
        }

    }

    public boolean a(boolean var1, OIProgressUpdate var2) {
		// CanaryMod: load once!
        if (!loaded) {
            etc.getLoader().loadPlugins();
            loaded = true;
        }
		
        int var3 = 0;

        for (int var4 = 0; var4 < this.g.size(); ++var4) {
            OChunk var5 = (OChunk) this.g.get(var4);

            if (var1) {
                this.a(var5);
            }

            if (var5.a(var1)) {
                this.b(var5);
                var5.l = false;
                ++var3;
                if (var3 == 24 && !var1) {
                    return false;
                }
            }
        }

        if (var1) {
            if (this.e == null) {
                return true;
            }

            this.e.b();
        }

        return true;
    }

    public boolean a() {
        if (!this.h.I) {
            for (int var1 = 0; var1 < 100; ++var1) {
                if (!this.b.isEmpty()) {
                    Long var2 = (Long) this.b.iterator().next();
                    OChunk var3 = (OChunk) this.f.a(var2.longValue());

					// Canary onChunkUnload hook
                    etc.getLoader().callHook(PluginLoader.Hook.CHUNK_UNLOAD, var3.chunk);
                    var3.d();
                    this.b(var3);
                    this.a(var3);
                    this.b.remove(var2);
                    this.f.d(var2.longValue());
                    this.g.remove(var3);
                }
            }

            if (this.e != null) {
                this.e.a();
            }
        }

        return this.d.a();
    }
	
	public OChunk regenerateChunk(int chunkX, int chunkZ)
    {
        Long chunkCoordIntPair = OChunkCoordIntPair.a(chunkX, chunkZ);
        
        // Unloading the chunk
        OChunk unloadedChunk = (OChunk)f.a(chunkCoordIntPair.longValue());
        if (unloadedChunk != null)
        {
            unloadedChunk.e();
            b(unloadedChunk);
            a(unloadedChunk);
            b.remove(chunkCoordIntPair);
            f.d(chunkCoordIntPair.longValue());
            g.remove(unloadedChunk);
        }
        
        // Generating the new chunk
        OChunk newChunk = d.b(chunkX, chunkZ);
        f.a(chunkCoordIntPair, newChunk);
        g.add(newChunk);
        if(newChunk != null)
        {
            newChunk.c();
            newChunk.d();
        }
        newChunk.a(this, this, chunkX, chunkZ);
        
        // Save the new chunk, overriding the old one
        a(newChunk);
        b(newChunk);
        newChunk.k = false;
        if(e != null)
        {
            e.b();
        }
        
        return newChunk;
    }

    public boolean b() {
        return !this.h.I;
    }

    public String d() {
        return "ServerChunkCache: " + this.f.a() + " Drop: " + this.b.size();
    }

    public List a(OEnumCreatureType var1, int var2, int var3, int var4) {
        return this.d.a(var1, var2, var3, var4);
    }

    public OChunkPosition a(OWorld var1, String var2, int var3, int var4, int var5) {
        return this.d.a(var1, var2, var3, var4, var5);
    }
}
