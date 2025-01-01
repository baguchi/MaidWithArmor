package baguchi.maid_with_armor.mixin.client;

import baguchi.maid_with_armor.forge.client.IMaidArmor;
import baguchi.maid_with_armor.forge.client.layer.CustomMaidArmorLayer;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.sistr.littlemaidmodelloader.maidmodel.ModelLittleMaidBase;
import net.sistr.littlemaidmodelloader.maidmodel.ModelLittleMaid_Aug;
import net.sistr.littlemaidmodelloader.maidmodel.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ModelLittleMaid_Aug.class)
public class ModelLittleMaid_AugMixin extends ModelLittleMaidBase implements IMaidArmor {
    public ModelLittleMaid_AugMixin() {
    }

    public ModelLittleMaid_AugMixin(float psize) {
        super(psize);
    }

    public ModelLittleMaid_AugMixin(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
        super(psize, pyoffset, pTextureWidth, pTextureHeight);
    }

    @Override
    public void translateToHead(ModelRenderer var1, PoseStack var2) {
        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedTorso, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedNeck, var2);
        CustomMaidArmorLayer.translateAndRotate(var1, var2);
    }

    @Override
    public void translateToChest(ModelRenderer var1, PoseStack var2) {
        var2.scale(0.85F, 0.85F, 0.85F);
        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedTorso, var2);
        CustomMaidArmorLayer.translateAndRotate(var1, var2);
    }

    @Override
    public void translateToLeg(ModelRenderer var1, PoseStack var2) {
        var2.translate(0, (4 / 18F), 0);
        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedTorso, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedBody, var2);
        CustomMaidArmorLayer.translateAndRotate(var1, var2);
    }

    @Override
    public void translateToChestPat(ModelRenderer var1, PoseStack var2) {
        var2.scale(0.85F, 0.85F, 0.85F);
        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedTorso, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedBody, var2);
        CustomMaidArmorLayer.translateAndRotate(var1, var2);
    }

    @Override
    public Iterable<ModelRenderer> headPartArmors() {
        return ImmutableList.of(this.bipedHead);
    }

    @Override
    public Iterable<ModelRenderer> bodyPartArmors() {
        return ImmutableList.of(this.bipedBody);
    }

    @Override
    public Iterable<ModelRenderer> leftHandArmors() {
        return ImmutableList.of(this.bipedLeftArm);
    }

    @Override
    public Iterable<ModelRenderer> rightHandArmors() {
        return ImmutableList.of(this.bipedRightArm);
    }

    @Override
    public Iterable<ModelRenderer> leftLegPartArmors() {
        return ImmutableList.of(this.bipedLeftLeg);
    }

    @Override
    public Iterable<ModelRenderer> rightLegPartArmors() {
        return ImmutableList.of(this.bipedRightLeg);
    }
}
