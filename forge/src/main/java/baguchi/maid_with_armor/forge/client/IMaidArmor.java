package baguchi.maid_with_armor.forge.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.sistr.littlemaidmodelloader.maidmodel.ModelRenderer;

public interface IMaidArmor {
    void translateToHead(ModelRenderer var1, PoseStack var2);

    void translateToChest(ModelRenderer var1, PoseStack var2);

    void translateToLeg(ModelRenderer var1, PoseStack var2);

    void translateToChestPat(ModelRenderer var1, PoseStack var2);

    default Iterable<ModelRenderer> rightHandArmors() {
        return ImmutableList.of();
    }

    default Iterable<ModelRenderer> leftHandArmors() {
        return ImmutableList.of();
    }

    default Iterable<ModelRenderer> rightLegPartArmors() {
        return ImmutableList.of();
    }

    default Iterable<ModelRenderer> leftLegPartArmors() {
        return ImmutableList.of();
    }

    default Iterable<ModelRenderer> bodyPartArmors() {
        return ImmutableList.of();
    }

    default Iterable<ModelRenderer> headPartArmors() {
        return ImmutableList.of();
    }
}
