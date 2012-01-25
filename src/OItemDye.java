
public class OItemDye extends OItem {

   public static final String[] a = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
   public static final int[] b = new int[]{1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};


   public OItemDye(int var1) {
      super(var1);
      this.a(true);
      this.f(0);
   }

   public String a(OItemStack var1) {
      int var2 = OMathHelper.a(var1.h(), 0, 15);
      return super.b() + "." + a[var2];
   }

   public boolean a(OItemStack var1, OEntityPlayer var2, OWorld var3, int var4, int var5, int var6, int var7) {
      if(!var2.d(var4, var5, var6)) {
         return false;
      } else {
         if(var1.h() == 15) {
            int var8 = var3.a(var4, var5, var6);
            if(var8 == OBlock.A.bO) {
               if(!var3.I) {
                  ((OBlockSapling)OBlock.A).b(var3, var4, var5, var6, var3.w);
                  --var1.a;
               }

               return true;
            }

            if(var8 == OBlock.ah.bO || var8 == OBlock.ai.bO) {
               if(!var3.I && ((OBlockMushroom)OBlock.m[var8]).b(var3, var4, var5, var6, var3.w)) {
                  --var1.a;
               }

               return true;
            }

            if(var8 == OBlock.bv.bO || var8 == OBlock.bu.bO) {
               if(!var3.I) {
                  ((OBlockStem)OBlock.m[var8]).g(var3, var4, var5, var6);
                  --var1.a;
               }

               return true;
            }

            if(var8 == OBlock.aB.bO) {
               if(!var3.I) {
                  ((OBlockCrops)OBlock.aB).g(var3, var4, var5, var6);
                  --var1.a;
               }

               return true;
            }

            if(var8 == OBlock.w.bO) {
               if(!var3.I) {
                  --var1.a;

                  label73:
                  for(int var9 = 0; var9 < 128; ++var9) {
                     int var10 = var4;
                     int var11 = var5 + 1;
                     int var12 = var6;

                     for(int var13 = 0; var13 < var9 / 16; ++var13) {
                        var10 += c.nextInt(3) - 1;
                        var11 += (c.nextInt(3) - 1) * c.nextInt(3) / 2;
                        var12 += c.nextInt(3) - 1;
                        if(var3.a(var10, var11 - 1, var12) != OBlock.w.bO || var3.e(var10, var11, var12)) {
                           continue label73;
                        }
                     }

                     if(var3.a(var10, var11, var12) == 0) {
                        if(c.nextInt(10) != 0) {
                           // CanaryMod disable bonemeal from generating stuff where it shouldnt (notch, y u no check)
                           if (OBlock.Z.f(var3, var10, var11, var12)) {
                              var3.b(var10, var11, var12, OBlock.Z.bO, 1);
                           }
                        } else if(c.nextInt(3) != 0) {
                            // CanaryMod disable bonemeal from generating stuff where it shouldnt (notch, y u no check)
                           if (OBlock.af.f(var3, var10, var11, var12)) { 
                              var3.e(var10, var11, var12, OBlock.af.bO);
                           }
                        } else {
                            // CanaryMod disable bonemeal from generating stuff where it shouldnt (notch, y u no check)
                           if (OBlock.ag.f(var3, var10, var11, var12)) {
                              var3.e(var10, var11, var12, OBlock.ag.bO);
                           }
                        }
                     }
                  }
               }

               return true;
            }
         }

         return false;
      }
   }

   public void a(OItemStack var1, OEntityLiving var2) {
      if(var2 instanceof OEntitySheep) {
         OEntitySheep var3 = (OEntitySheep)var2;
         int var4 = OBlockCloth.d(var1.h());
         if(!var3.p_() && var3.w() != var4) {
            var3.b(var4);
            --var1.a;
         }
      }

   }

}
