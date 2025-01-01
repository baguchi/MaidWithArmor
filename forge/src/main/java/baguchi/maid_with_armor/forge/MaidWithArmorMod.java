package baguchi.maid_with_armor.forge;

import baguchi.maid_with_armor.MaidWithArmor;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MaidWithArmor.MOD_ID)
public class MaidWithArmorMod {
    public MaidWithArmorMod() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(MaidWithArmor.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
    }
}
