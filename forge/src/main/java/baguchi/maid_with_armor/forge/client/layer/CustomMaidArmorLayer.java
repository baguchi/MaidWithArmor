package baguchi.maid_with_armor.forge.client.layer;

import baguchi.maid_with_armor.forge.client.IMaidArmor;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.sistr.littlemaidmodelloader.entity.compound.IHasMultiModel;
import net.sistr.littlemaidmodelloader.maidmodel.ModelLittleMaidBase;
import net.sistr.littlemaidmodelloader.maidmodel.ModelRenderer;
import org.joml.Quaternionf;

import java.util.Map;


public class CustomMaidArmorLayer<M extends EntityModel<LivingEntity>> extends RenderLayer<LivingEntity, M> {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private final HumanoidModel defaultBipedModel;
    private final HumanoidModel innerModel;
    private RenderLayerParent<LivingEntity, M> renderer;
    private final TextureAtlas armorTrimAtlas;

    public CustomMaidArmorLayer(RenderLayerParent<LivingEntity, M> render, EntityRendererProvider.Context context) {
        super(render);
        defaultBipedModel = new HumanoidModel(context.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
        this.innerModel = new HumanoidModel(context.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR));
        this.renderer = render;
        this.armorTrimAtlas = context.getModelManager().getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    public CustomMaidArmorLayer(RenderLayerParent<LivingEntity, M> render, EntityModelSet modelSet, ModelManager modelManager) {
        super(render);
        defaultBipedModel = new HumanoidModel(modelSet.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
        this.innerModel = new HumanoidModel(modelSet.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR));
        this.renderer = render;
        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof IHasMultiModel hasMultiModel) {
            {
                matrixStackIn.pushPose();
                ItemStack headItem = entity.getItemBySlot(EquipmentSlot.HEAD);
                if (headItem.getItem() instanceof ArmorItem) {
                    ArmorItem armoritem = (ArmorItem) headItem.getItem();
                    if (headItem.canEquip(EquipmentSlot.HEAD, entity)) {
                        HumanoidModel a = defaultBipedModel;
                        a = getArmorModelHook(entity, headItem, EquipmentSlot.HEAD, a);
                        boolean notAVanillaModel = a != defaultBipedModel;
                        this.setModelSlotVisible(a, EquipmentSlot.HEAD);
                        boolean flag1 = headItem.hasFoil();
                        int clampedLight = packedLightIn;
                        if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) { // Allow this for anything, not only cloth
                            int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(headItem);
                            float f = (float) (i >> 16 & 255) / 255.0F;
                            float f1 = (float) (i >> 8 & 255) / 255.0F;
                            float f2 = (float) (i & 255) / 255.0F;
                            renderHelmet(headItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, f, f1, f2, getArmorResource(entity, headItem, EquipmentSlot.HEAD, null), notAVanillaModel);
                            renderHelmet(headItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, headItem, EquipmentSlot.HEAD, "overlay"), notAVanillaModel);
                        } else {
                            renderHelmet(headItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, headItem, EquipmentSlot.HEAD, null), notAVanillaModel);
                        }
                    }
                }
                matrixStackIn.popPose();
            }
            {
                matrixStackIn.pushPose();
                ItemStack chestItem = entity.getItemBySlot(EquipmentSlot.CHEST);
                if (chestItem.getItem() instanceof ArmorItem) {
                    ArmorItem armoritem = (ArmorItem) chestItem.getItem();
                    if (armoritem.getEquipmentSlot() == EquipmentSlot.CHEST) {
                        HumanoidModel a = defaultBipedModel;
                        a = getArmorModelHook(entity, chestItem, EquipmentSlot.CHEST, a);
                        boolean notAVanillaModel = a != defaultBipedModel;
                        this.setModelSlotVisible(a, EquipmentSlot.CHEST);

                        boolean flag1 = chestItem.hasFoil();
                        int clampedLight = packedLightIn;
                        if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) { // Allow this for anything, not only cloth
                            int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(chestItem);
                            float f = (float) (i >> 16 & 255) / 255.0F;
                            float f1 = (float) (i >> 8 & 255) / 255.0F;
                            float f2 = (float) (i & 255) / 255.0F;
                            renderChestplate(chestItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, f, f1, f2, getArmorResource(entity, chestItem, EquipmentSlot.CHEST, null), notAVanillaModel);
                            renderChestplate(chestItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, chestItem, EquipmentSlot.CHEST, "overlay"), notAVanillaModel);
                        } else {
                            renderChestplate(chestItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, chestItem, EquipmentSlot.CHEST, null), notAVanillaModel);
                        }

                    }
                }
                matrixStackIn.popPose();
            }
            {
                matrixStackIn.pushPose();
                ItemStack legItem = entity.getItemBySlot(EquipmentSlot.LEGS);
                if (legItem.getItem() instanceof ArmorItem) {
                    ArmorItem armoritem = (ArmorItem) legItem.getItem();
                    if (armoritem.getEquipmentSlot() == EquipmentSlot.LEGS) {
                        HumanoidModel a = this.innerModel;
                        a = getArmorModelHook(entity, legItem, EquipmentSlot.LEGS, a);
                        boolean notAVanillaModel = a != defaultBipedModel;
                        this.setModelSlotVisible(a, EquipmentSlot.LEGS);

                        boolean flag1 = legItem.hasFoil();
                        int clampedLight = packedLightIn;
                        if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) { // Allow this for anything, not only cloth
                            int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(legItem);
                            float f = (float) (i >> 16 & 255) / 255.0F;
                            float f1 = (float) (i >> 8 & 255) / 255.0F;
                            float f2 = (float) (i & 255) / 255.0F;
                            renderLeg(legItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, f, f1, f2, getArmorResource(entity, legItem, EquipmentSlot.LEGS, null), notAVanillaModel);
                            renderLeg(legItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, legItem, EquipmentSlot.LEGS, "overlay"), notAVanillaModel);
                        } else {
                            renderLeg(legItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, legItem, EquipmentSlot.LEGS, null), notAVanillaModel);
                        }

                    }
                }
                matrixStackIn.popPose();
            }
            {
                matrixStackIn.pushPose();
                ItemStack feetItem = entity.getItemBySlot(EquipmentSlot.FEET);
                if (feetItem.getItem() instanceof ArmorItem) {
                    ArmorItem armoritem = (ArmorItem) feetItem.getItem();
                    if (armoritem.getEquipmentSlot() == EquipmentSlot.FEET) {
                        HumanoidModel a = defaultBipedModel;
                        a = getArmorModelHook(entity, feetItem, EquipmentSlot.FEET, a);
                        boolean notAVanillaModel = a != defaultBipedModel;
                        this.setModelSlotVisible(a, EquipmentSlot.FEET);

                        boolean flag1 = feetItem.hasFoil();
                        int clampedLight = packedLightIn;
                        if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) { // Allow this for anything, not only cloth
                            int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(feetItem);
                            float f = (float) (i >> 16 & 255) / 255.0F;
                            float f1 = (float) (i >> 8 & 255) / 255.0F;
                            float f2 = (float) (i & 255) / 255.0F;
                            renderBoot(feetItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, f, f1, f2, getArmorResource(entity, feetItem, EquipmentSlot.FEET, null), notAVanillaModel);
                            renderBoot(feetItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, feetItem, EquipmentSlot.FEET, "overlay"), notAVanillaModel);
                        } else {
                            renderBoot(feetItem, entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, feetItem, EquipmentSlot.FEET, null), notAVanillaModel);
                        }

                    }
                }
                matrixStackIn.popPose();
            }
        }
    }

    public static ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @javax.annotation.Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }

        return resourcelocation;
    }

    private static boolean usesInnerModel(EquipmentSlot p_117129_) {
        return p_117129_ == EquipmentSlot.LEGS;
    }

    private HumanoidModel getArmorModel(EquipmentSlot p_117079_) {
        return (usesInnerModel(p_117079_) ? this.innerModel : this.defaultBipedModel);
    }

    private void renderTrim(ModelPart part, ArmorMaterial p_267946_, PoseStack p_268019_, MultiBufferSource p_268023_, int p_268190_, ArmorTrim p_267984_, boolean p_267965_, HumanoidModel p_267949_, boolean p_268259_, float p_268337_, float p_268095_, float p_268305_) {
        TextureAtlasSprite textureatlassprite = this.armorTrimAtlas.getSprite(p_268259_ ? p_267984_.innerTexture(p_267946_) : p_267984_.outerTexture(p_267946_));
        VertexConsumer vertexconsumer = textureatlassprite.wrap(ItemRenderer.getFoilBufferDirect(p_268023_, Sheets.armorTrimsSheet(), true, p_267965_));
        part.render(p_268019_, vertexconsumer, p_268190_, OverlayTexture.NO_OVERLAY, p_268337_, p_268095_, p_268305_, 1.0F);
    }

    private void renderTrim(ModelPart part, ItemStack item, LivingEntity entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, EquipmentSlot equipmentSlot, HumanoidModel modelIn) {
        if (item.getItem() instanceof ArmorItem armorItem) {
            ArmorTrim.getTrim(entity.level().registryAccess(), item).ifPresent((p_267897_) -> {
                this.renderTrim(part, armorItem.getMaterial(), matrixStackIn, bufferIn, packedLightIn, p_267897_, glintIn, modelIn, this.usesInnerModel(equipmentSlot), 1.0F, 1.0F, 1.0F);
            });
        }
    }

    private void renderLeg(ItemStack legItem, LivingEntity entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, HumanoidModel modelIn, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityCutoutNoCull(armorResource), false, glintIn);
        renderer.getModel().copyPropertiesTo(modelIn);
        modelIn.body.xRot = 0F;
        modelIn.body.yRot = 0;
        modelIn.body.zRot = 0;
        modelIn.body.x = 0;
        modelIn.body.y = 0F;
        modelIn.body.z = 0F;
        modelIn.rightLeg.x = 0F;
        modelIn.rightLeg.xRot = 0F;
        modelIn.rightLeg.yRot = 0F;
        modelIn.rightLeg.zRot = 0F;
        modelIn.leftLeg.x = 0F;
        modelIn.leftLeg.xRot = 0F;
        modelIn.leftLeg.yRot = 0F;
        modelIn.leftLeg.zRot = 0F;
        modelIn.leftLeg.y = 0F;
        modelIn.rightLeg.y = 0F;
        modelIn.leftLeg.z = 0F;
        modelIn.rightLeg.z = 0F;
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.LEGS).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.rightLegPartArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToLeg(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.rightLeg.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.rightLeg, legItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.LEGS, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.LEGS).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.leftLegPartArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToLeg(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.leftLeg.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.leftLeg, legItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.LEGS, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.LEGS).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.bodyPartArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToChest(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.body.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.body, legItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.LEGS, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });
    }

    private void renderBoot(ItemStack feetItem, LivingEntity entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, HumanoidModel modelIn, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityCutoutNoCull(armorResource), false, glintIn);
        renderer.getModel().copyPropertiesTo(modelIn);
        modelIn.rightLeg.x = 0F;
        modelIn.rightLeg.xRot = 0F;
        modelIn.rightLeg.yRot = 0F;
        modelIn.rightLeg.zRot = 0F;
        modelIn.leftLeg.x = 0F;
        modelIn.leftLeg.xRot = 0F;
        modelIn.leftLeg.yRot = 0F;
        modelIn.leftLeg.zRot = 0F;
        modelIn.leftLeg.y = 0F;
        modelIn.rightLeg.y = 0F;
        modelIn.leftLeg.z = 0F;
        modelIn.rightLeg.z = 0F;
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.LEGS).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.rightLegPartArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToLeg(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.rightLeg.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.rightLeg, feetItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.FEET, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.LEGS).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.leftLegPartArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToLeg(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.leftLeg.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.leftLeg, feetItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.FEET, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });

    }


    private void renderChestplate(ItemStack chestItem, LivingEntity entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, HumanoidModel modelIn, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityCutoutNoCull(armorResource), false, glintIn);
        renderer.getModel().copyPropertiesTo(modelIn);
        modelIn.body.xRot = 0F;
        modelIn.body.yRot = 0;
        modelIn.body.zRot = 0;
        modelIn.body.x = 0;
        modelIn.body.y = 0F;
        modelIn.body.z = 0F;
        modelIn.rightArm.x = 0F;
        modelIn.rightArm.y = 0F;
        modelIn.rightArm.z = 0F;
        modelIn.rightArm.xRot = 0F;
        modelIn.rightArm.yRot = 0F;
        modelIn.rightArm.zRot = 0F;
        modelIn.leftArm.x = 0F;
        modelIn.leftArm.y = 0F;
        modelIn.leftArm.z = 0F;
        modelIn.leftArm.xRot = 0F;
        modelIn.leftArm.yRot = 0F;
        modelIn.leftArm.zRot = 0F;
        modelIn.leftArm.y = 0F;
        modelIn.rightArm.y = 0F;
        modelIn.leftArm.z = 0F;
        modelIn.rightArm.z = 0F;
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.BODY).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.rightHandArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToChestPat(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.rightArm.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.rightArm, chestItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.CHEST, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.BODY).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.leftHandArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToChestPat(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.leftArm.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.leftArm, chestItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.CHEST, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.BODY).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.bodyPartArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToChest(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.body.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.body, chestItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.CHEST, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });

    }

    private void renderHelmet(ItemStack headItem, LivingEntity entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, HumanoidModel modelIn, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityCutoutNoCull(armorResource), false, glintIn);
        renderer.getModel().copyPropertiesTo(modelIn);
        modelIn.head.xRot = 0F;
        modelIn.head.yRot = 0F;
        modelIn.head.zRot = 0F;
        modelIn.hat.xRot = 0F;
        modelIn.hat.yRot = 0F;
        modelIn.hat.zRot = 0F;
        modelIn.head.x = 0F;
        modelIn.head.y = 0F;
        modelIn.head.z = 0F;
        modelIn.hat.x = 0F;
        modelIn.hat.y = 0F;
        modelIn.hat.z = 0F;
        ((IHasMultiModel) entity).getModel(IHasMultiModel.Layer.SKIN, IHasMultiModel.Part.HEAD).ifPresent((model) -> {
            if (model instanceof ModelLittleMaidBase base && base instanceof IMaidArmor armor) {
                armor.headPartArmors().forEach(modelRenderer -> {
                    matrixStackIn.pushPose();
                    armor.translateToHead(modelRenderer, matrixStackIn);
                    //translateAndRotate(base.bipedRightLeg, matrixStackIn);
                    modelIn.head.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                    renderTrim(modelIn.head, headItem, entity, matrixStackIn, bufferIn, packedLightIn, glintIn, EquipmentSlot.HEAD, modelIn);
                    matrixStackIn.popPose();
                });
            }
        });
    }

    public static void translateAndRotate(ModelRenderer modelRenderer, PoseStack arg) {
        if (modelRenderer != null) {
            arg.translate(modelRenderer.rotationPointX / 16.0F, modelRenderer.rotationPointY / 16.0F, modelRenderer.rotationPointZ / 16.0F);
            if (modelRenderer.rotateAngleX != 0.0F || modelRenderer.rotateAngleY != 0.0F || modelRenderer.rotateAngleZ != 0.0F) {
                arg.mulPose((new Quaternionf()).rotationZYX(modelRenderer.rotateAngleZ, modelRenderer.rotateAngleY, modelRenderer.rotateAngleX));
            }

            if (modelRenderer.scaleX != 1.0F || modelRenderer.scaleY != 1.0F || modelRenderer.scaleZ != 1.0F) {
                arg.scale(modelRenderer.scaleX, modelRenderer.scaleY, modelRenderer.scaleZ);
            }
        }
    }


    protected void setModelSlotVisible(HumanoidModel p_188359_1_, EquipmentSlot slotIn) {
        this.setModelVisible(p_188359_1_);
        switch (slotIn) {
            case HEAD:
                p_188359_1_.head.visible = true;
                p_188359_1_.hat.visible = true;
                break;
            case CHEST:
                p_188359_1_.body.visible = true;
                p_188359_1_.rightArm.visible = true;
                p_188359_1_.leftArm.visible = true;
                break;
            case LEGS:
                p_188359_1_.body.visible = true;
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
                break;
            case FEET:
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
        }
    }

    protected void setModelVisible(HumanoidModel model) {
        model.setAllVisible(false);

    }


    protected HumanoidModel<?> getArmorModelHook(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
        Model basicModel = net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
        return basicModel instanceof HumanoidModel ? (HumanoidModel<?>) basicModel : model;
    }
}