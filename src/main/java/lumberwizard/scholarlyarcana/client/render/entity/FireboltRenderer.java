package lumberwizard.scholarlyarcana.client.render.entity;

import lumberwizard.scholarlyarcana.world.entity.spell.FireboltEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireboltRenderer extends EntityRenderer<FireboltEntity> {
    public FireboltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FireboltEntity p_114482_) {
        return null;
    }
}
