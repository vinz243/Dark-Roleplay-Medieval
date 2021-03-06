package net.dark_roleplay.medieval.client.model_baking;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.dark_roleplay.medieval.common.DRPMedievalInfo;
import net.dark_roleplay.medieval.common.blocks.decorative.hangingBridges.HangingBridge;
import net.dark_roleplay.medieval.common.blocks.helper.EnumAxis;
import net.dark_roleplay.medieval.common.handler.DRPMedievalBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public class DelayedBaker_HangingBridge extends DelayedBaker implements ICustomModelLoader{

	protected static final Map<IBlockState, List<BakedQuad>> CACHE = Maps.newHashMap();
	
	protected static ImmutableList<ResourceLocation> textures;
	
	protected static IModel[] planks;
	protected static IModel[] post_left;
	protected static IModel[] post_right;
	protected static IModel[] rope_side;
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		side = null;
		
		if(DelayedBaker_HangingBridge.CACHE.containsKey(state))
			return DelayedBaker_HangingBridge.CACHE.get(state);
		
		List<BakedQuad> result = Lists.newArrayList();
		
		int yRot = state.getValue(HangingBridge.AXIS) == EnumAxis.Z ? 90 : 0;
		int height = state.getValue(HangingBridge.HEIGHT) + (state.getBlock().getRegistryName().getResourcePath().contains("top") ? 8 : 0);
		
		if(state instanceof IExtendedBlockState){
			IExtendedBlockState ext = (IExtendedBlockState) state;
			
			boolean north = ext.getValue(HangingBridge.NORTH); 
			boolean east = ext.getValue(HangingBridge.EAST); 
			boolean south = ext.getValue(HangingBridge.SOUTH); 
			boolean west = ext.getValue(HangingBridge.WEST);
			
			this.addQuads(result, DelayedBaker_HangingBridge.planks[height], yRot, 0, state, side, rand);
			
			if(east){
				this.addQuads(result, DelayedBaker_HangingBridge.rope_side[height], yRot + 180, 0, state, side, rand);
				if(north){
					this.addQuads(result, DelayedBaker_HangingBridge.post_right[height], yRot, 0, state, side, rand);
				}
				if(south){
					this.addQuads(result, DelayedBaker_HangingBridge.post_left[height], yRot + 180, 0, state, side, rand);
				}
			}
			if(west){
				this.addQuads(result, DelayedBaker_HangingBridge.rope_side[height], yRot, 0, state, side, rand);
				if(north){
					this.addQuads(result, DelayedBaker_HangingBridge.post_left[height], yRot, 0, state, side, rand);
				}
				if(south){
					this.addQuads(result, DelayedBaker_HangingBridge.post_right[height], yRot + 180, 0, state, side, rand);
				}
			}
		}
		
		DelayedBaker_HangingBridge.CACHE.put(state, result);
		return result;
	}

	@Override
	public Collection<ResourceLocation> getTextures() { return DelayedBaker_HangingBridge.textures; }

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, java.util.function.Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		this.bakeInfo(format, bakedTextureGetter, new ResourceLocation(DRPMedievalInfo.MODID, "blocks/clean_plank_spruce"));
		return this;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) { DelayedBaker_HangingBridge.CACHE.clear(); }

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return ((modelLocation instanceof ModelResourceLocation) && DRPMedievalBlocks.HANGING_BRIDGE_BOTTOM.getRegistryName().equals(modelLocation)) || DRPMedievalBlocks.HANGING_BRIDGE_TOP.getRegistryName().equals(modelLocation);
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		if(DelayedBaker_HangingBridge.planks == null){
			DelayedBaker_HangingBridge.planks = new IModel[16];
			for(int i = 0; i < 16; i++){
				DelayedBaker_HangingBridge.planks[i] = DelayedBaker_HangingBridge.getModel("planks_" + i);
			}
		}
		if(DelayedBaker_HangingBridge.post_left == null){
			DelayedBaker_HangingBridge.post_left = new IModel[16];
			for(int i = 0; i < 16; i++){
				DelayedBaker_HangingBridge.post_left[i] = DelayedBaker_HangingBridge.getModel("post_left_" + i);
			}
		}
		if(DelayedBaker_HangingBridge.post_right == null){
			DelayedBaker_HangingBridge.post_right = new IModel[16];
			for(int i = 0; i < 16; i++){
				DelayedBaker_HangingBridge.post_right[i] = DelayedBaker_HangingBridge.getModel("post_right_" + i);
			}
		}
		if(DelayedBaker_HangingBridge.rope_side == null){
			DelayedBaker_HangingBridge.rope_side = new IModel[16];
			for(int i = 0; i < 16; i++){
				DelayedBaker_HangingBridge.rope_side[i] = DelayedBaker_HangingBridge.getModel("rope_side_" + i);
			}
		}
		
		if(DelayedBaker_HangingBridge.textures == null){
			ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
			
			for(int i = 0; i < 16; i++){
				builder.addAll(DelayedBaker_HangingBridge.planks[i].getTextures());
				builder.addAll(DelayedBaker_HangingBridge.post_left[i].getTextures());
				builder.addAll(DelayedBaker_HangingBridge.post_right[i].getTextures());
				builder.addAll(DelayedBaker_HangingBridge.rope_side[i].getTextures());

			}
			DelayedBaker_HangingBridge.textures = builder.build();
		}
		
		return this;
	}

	protected static IModel getModel(String modelName){
		return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(DRPMedievalInfo.MODID, "block/hanging_bridge/" + modelName), "A problem occured while trying to load: " + DRPMedievalInfo.MODID + ":block/hanging_bridge/" + modelName);
	}
}
