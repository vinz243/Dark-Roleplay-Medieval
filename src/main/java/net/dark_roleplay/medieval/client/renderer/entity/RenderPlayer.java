package net.dark_roleplay.medieval.client.renderer.entity;

import net.dark_roleplay.medieval.client.renderer.premium.ModelBox;
import net.dark_roleplay.medieval.common.entity.item.EntitySledge;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderPlayer extends RenderLivingBase<EntityPlayer>{

	public RenderPlayer(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelBox(), 0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPlayer entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void doRender(EntityPlayer entity, double x, double y, double z, float entityYaw, float partialTicks){
        if (!this.renderOutlines){
            this.renderName(entity, x, y, z);
        }
        
        
    }
}

