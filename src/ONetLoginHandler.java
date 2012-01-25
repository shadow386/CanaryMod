import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;


public class ONetLoginHandler extends ONetHandler {

    public static Logger a = Logger.getLogger("Minecraft");
    private static Random d = new Random();
    public ONetworkManager b;
    public boolean c = false;
    private MinecraftServer e;
    private int f = 0;
    private String g = null;
    private OPacket1Login h = null;
    private String i = "";

    public ONetLoginHandler(MinecraftServer var1, Socket var2, String var3)  throws IOException {
        super();
        this.e = var1;
        this.b = new ONetworkManager(var2, var3, this);
        this.b.f = 0;
    }

    public void a() {
        if (this.h != null) {
            this.b(this.h);
            this.h = null;
        }

        if (this.f++ == 600) {
            this.a("Took too long to log in");
        } else {
            this.b.b();
        }

    }

    public void a(String var1) {
        try {
            a.info("Disconnecting " + this.b() + ": " + var1);
            this.b.a((OPacket) (new OPacket255KickDisconnect(var1)));
            this.b.d();
            this.c = true;
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void a(OPacket2Handshake var1) {
        if (this.e.n) {
            this.i = Long.toString(d.nextLong(), 16);
            this.b.a((OPacket) (new OPacket2Handshake(this.i)));
        } else {
            this.b.a((OPacket) (new OPacket2Handshake("-")));
        }

    }

    public void a(OPacket1Login var1) {
        this.g = var1.b;
        if (var1.a != 23) {
            if (var1.a > 23) {
                this.a("Outdated server!");
            } else {
                this.a("Outdated client!");
            }

        } else {
            if (!this.e.n) {
                this.b(var1);
            } else {
                (new OThreadLoginVerifier(this, var1)).start();
            }

        }
    }

    public void b(OPacket1Login var1) {
        OEntityPlayerMP var2 = this.e.h.a(this, var1.b);

        if (var2 != null) {
            this.e.h.b(var2);
            var2.a((OWorld) this.e.a(var2.w));
            var2.c.a((OWorldServer) var2.bi);
            a.info(this.b() + " logged in with entity id " + var2.bd + " at (" + var2.bm + ", " + var2.bn + ", " + var2.bo + ")");
            OWorldServer var3 = this.e.a(var2.w);
            OChunkCoordinates var4 = var3.o();

            var2.c.b(var3.r().n());
            ONetServerHandler var5 = new ONetServerHandler(this.e, this.b, var2);

            // CanaryMod - if seed is hidden send 0 instead.
            var5.b((OPacket) (new OPacket1Login("", var2.bd, etc.getInstance().getHideSeed() == true ? 0 : var3.m(), var3.r().q(), var2.c.a(), (byte) var3.y.h, (byte) var3.v, (byte) var3.c, (byte) this.e.h.k())));
            var5.b((OPacket) (new OPacket6SpawnPosition(var4.a, var4.b, var4.c)));
            this.e.h.a(var2, var3);
            // CanaryMod - onPlayerConnect Hook
            HookParametersConnect hookResult = new HookParametersConnect(String.format(Colors.Yellow + "%s joined the game.", var2.v), true);

            hookResult = (HookParametersConnect) etc.getLoader().callHook(PluginLoader.Hook.PLAYER_CONNECT, var2.getPlayer(), hookResult);
            if (!hookResult.isHidden()) { 
                this.e.h.a((OPacket) (new OPacket3Chat(hookResult.getJoinMessage())));
            }
            
            // CanaryMod - Check Creative Mode
            var2.getPlayer().refreshCreativeMode();
            
            this.e.h.c(var2);
            var5.a(var2.bm, var2.bn, var2.bo, var2.bs, var2.bt);
            this.e.c.a(var5);
            var5.b((OPacket) (new OPacket4UpdateTime(var3.n())));

            // CanaryMod - enable/disable potion effects on login
            if (hookResult.applyPotionsEffects()) {
                Iterator var6 = var2.aD().iterator();

                while (var6.hasNext()) {
                    OPotionEffect var7 = (OPotionEffect) var6.next();

                    var5.b((OPacket) (new OPacket41EntityEffect(var2.bd, var7)));
                }
            }

            var2.v();
        }

        this.c = true;
    }

    public void a(String var1, Object[] var2) {
        a.info(this.b() + " lost connection");
        this.c = true;
    }

    public void a(OPacket254ServerPing var1) {
        if (this.b.f() == null) {
            return;
        } // CanaryMod - Fix if we don't have a socket, don't do anything
        try {
            String var2 = this.e.s + "\u00a7" + this.e.h.j() + "\u00a7" + this.e.h.k();

            this.b.a((OPacket) (new OPacket255KickDisconnect(var2)));
            // CanaryMod swapped lines below. The network connection should be terminated AFTER removing the socket from the connection list.
            this.e.c.a(this.b.f());
            this.b.d();
            this.c = true;
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void a(OPacket var1) {
        this.a("Protocol error");
    }

    public String b() {
        return this.g != null ? this.g + " [" + this.b.c().toString() + "]" : this.b.c().toString();
    }

    public boolean c() {
        return true;
    }

    // $FF: synthetic method
    static String a(ONetLoginHandler var0) {
        return var0.i;
    }

    // $FF: synthetic method
    static OPacket1Login a(ONetLoginHandler var0, OPacket1Login var1) {
        return var0.h = var1;
    }

}
