package baguchi.maid_with_armor.mixin.client;

import baguchi.maid_with_armor.forge.client.IMaidArmor;
import baguchi.maid_with_armor.forge.client.layer.CustomMaidArmorLayer;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.sistr.littlemaidmodelloader.maidmodel.ModelLittleMaidBase;
import net.sistr.littlemaidmodelloader.maidmodel.ModelLittleMaid_Chloe2;
import net.sistr.littlemaidmodelloader.maidmodel.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelLittleMaid_Chloe2.class)
public class ModelLittleMaid_Chloe2Mixin extends ModelLittleMaidBase implements IMaidArmor {
    @Shadow(remap = false)
    public ModelRenderer upperRightLeg;

    @Shadow(remap = false)
    public ModelRenderer upperLeftLeg;

    @Shadow(remap = false)
    public ModelRenderer upperRightArm;

    @Shadow(remap = false)
    public ModelRenderer upperLeftArm;

    public ModelLittleMaid_Chloe2Mixin() {
    }

    public ModelLittleMaid_Chloe2Mixin(float psize) {
        super(psize);
    }

    public ModelLittleMaid_Chloe2Mixin(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
        super(psize, pyoffset, pTextureWidth, pTextureHeight);
    }

    @Override
    public void translateToHead(ModelRenderer var1, PoseStack var2) {
        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        CustomMaidArmorLayer.translateAndRotate(var1, var2);
    }

    @Override
    public void translateToChest(ModelRenderer var1, PoseStack var2) {
        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        CustomMaidArmorLayer.translateAndRotate(var1, var2);

        var2.translate(0, -(6 / 18F), 0);
        var2.scale(0.9F, 0.9F, 0.9F);
    }

    @Override
    public void translateToLeg(ModelRenderer var1, PoseStack var2) {
        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        if (var1 == this.bipedRightLeg) {
            CustomMaidArmorLayer.translateAndRotate(this.upperRightLeg, var2);
        } else {
            CustomMaidArmorLayer.translateAndRotate(this.upperLeftLeg, var2);
        }
        CustomMaidArmorLayer.translateAndRotate(var1, var2);

        var2.translate(0, -(4 / 18F), 0);
    }

    @Override
    public void translateToChestPat(ModelRenderer var1, PoseStack var2) {

        CustomMaidArmorLayer.translateAndRotate(this.mainFrame, var2);
        CustomMaidArmorLayer.translateAndRotate(this.bipedBody, var2);
        if (var1 == this.bipedRightArm) {
            CustomMaidArmorLayer.translateAndRotate(this.upperRightArm, var2);
        } else {
            CustomMaidArmorLayer.translateAndRotate(this.upperLeftArm, var2);
        }
        CustomMaidArmorLayer.translateAndRotate(var1, var2);
        var2.translate(0, -(4 / 18F), 0);
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
