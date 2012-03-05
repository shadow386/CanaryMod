
final class OEnchantmentModifierDamage implements OIEnchantmentModifier {

    public int a;
    public ODamageSource b;

    private OEnchantmentModifierDamage() {
        super();
    }

    public void a(OEnchantment var1, int var2) {
        this.a += var1.a(var2, this.b);
    }

    // $FF: synthetic method
    OEnchantmentModifierDamage(OEmpty1 var1) {
        this();
    }
}
