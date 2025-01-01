package baguchi.maid_with_armor.forge.client;

import baguchi.maid_with_armor.MaidWithArmor;
import baguchi.maid_with_armor.forge.client.layer.CustomMaidArmorLayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sistr.littlemaidrebirth.client.MaidModelRenderer;
import net.sistr.littlemaidrebirth.setup.Registration;

@Mod.EventBusSubscriber(modid = MaidWithArmor.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerEntityLayer(EntityRenderersEvent.AddLayers event) {
        if (event.getRenderer(Registration.LITTLE_MAID_MOB.get()) instanceof MaidModelRenderer renderer) {
            renderer.addLayer(new CustomMaidArmorLayer(renderer, event.getEntityModels(), Minecraft.getInstance().getModelManager()));
        }
    }
}
