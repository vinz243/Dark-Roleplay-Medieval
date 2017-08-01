package net.dark_roleplay.medieval.client;

import java.rmi.registry.Registry;
import java.util.ArrayList;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.api.items.ItemApi;
import net.dark_roleplay.medieval.client.entities.fox.Render_Fox;
import net.dark_roleplay.medieval.client.events.Event_CameraUpdate;
import net.dark_roleplay.medieval.client.model_baking.DelayedBaker_FlowerPot;
import net.dark_roleplay.medieval.client.model_baking.DelayedBaker_HangingBridge;
import net.dark_roleplay.medieval.client.model_baking.DelayedBaker_RopeFence;
import net.dark_roleplay.medieval.client.renderer.entity.RenderEntityRopedArrow;
import net.dark_roleplay.medieval.client.renderer.entity.RenderEntitySledge;
import net.dark_roleplay.medieval.common.DRPMedievalInfo;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderAnvil;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderBookOne;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderCauldron;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderChain;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderCrate;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderDungeonChest;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderFirepit;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderGrindstone;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderHangingCauldron;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderHook;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderKeyHanging;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderMortar;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderRopeAnchor;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderShipsWheel;
import net.dark_roleplay.medieval.common.blocks.specialrenderer.SpecialRenderTarget;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityAnvil;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityBookOne;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityCauldron;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityChain;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityCrate;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityDungeonChest;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityFirepit;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityGrindstone;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityHangingCauldron;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityHook;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityKeyHanging;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityMortar;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityRopeAnchor;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityShipsWheel;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityTarget;
import net.dark_roleplay.medieval.common.entities.fox.Entity_Fox;
import net.dark_roleplay.medieval.common.entity.item.EntitySledge;
import net.dark_roleplay.medieval.common.entity.projectile.EntityRopedArrow;
import net.dark_roleplay.medieval.common.handler.DRPMedievalBlocks;
import net.dark_roleplay.medieval.common.handler.DRPMedievalItems;
import net.dark_roleplay.medieval.common.proxy.CommonProxy;
import net.dark_roleplay.medieval.common.testing.RecipeFromJSON;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {
	
	static ArrayList<Item> toRegisterMeshes = new ArrayList<Item>();
	
	public static int telescopeLevel = 0;
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event){
		ItemApi.registerItemMeshs();

		for(Item item : toRegisterMeshes){
			registerItemMesh(item);
		}
		toRegisterMeshes = null;
	}
	
	@Override
	public void init(FMLPreInitializationEvent event) {
		
		//ModelLoaderRegistry.registerLoader(new DelayedBakedModel());
		ModelLoaderRegistry.registerLoader(new DelayedBaker_HangingBridge());
		ModelLoaderRegistry.registerLoader(new DelayedBaker_RopeFence());
		ModelLoaderRegistry.registerLoader(new DelayedBaker_FlowerPot());
		
		RenderingRegistry.registerEntityRenderingHandler(EntitySledge.class, RenderEntitySledge.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityRopedArrow.class, RenderEntityRopedArrow.FACTORY);

		RenderingRegistry.registerEntityRenderingHandler(Entity_Fox.class, Render_Fox.FACTORY);
        		
		this.registerRenders();	
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
		if(manager instanceof IReloadableResourceManager) {
		    ((IReloadableResourceManager)manager).registerReloadListener(new RecipeFromJSON());
		}
		MinecraftForge.EVENT_BUS.register(new Event_CameraUpdate());
		//Minecraft.getMinecraft().renderEngine.loadTickableTexture(textureLocation, textureObj);
		
		this.forceAdditionalModels();
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(DRPMedievalBlocks.CLEAN_PLANKS), 0, new ModelResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_oak", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(DRPMedievalBlocks.CLEAN_PLANKS), 1, new ModelResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_spruce", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(DRPMedievalBlocks.CLEAN_PLANKS), 2, new ModelResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_birch", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(DRPMedievalBlocks.CLEAN_PLANKS), 3, new ModelResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_jungle", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(DRPMedievalBlocks.CLEAN_PLANKS), 4, new ModelResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_dark_oak", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(DRPMedievalBlocks.CLEAN_PLANKS), 5, new ModelResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_acacia", "inventory"));
	}

	@Override
	public void init(FMLPostInitializationEvent event) {}


	public void registerRenders() {		
		//Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemColorHandler(), DRPMedievalItems.StringCoil);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvil.class, new SpecialRenderAnvil());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMortar.class, new SpecialRenderMortar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrindstone.class, new SpecialRenderGrindstone());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHangingCauldron.class, new SpecialRenderHangingCauldron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBookOne.class, new SpecialRenderBookOne());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCauldron.class, new SpecialRenderCauldron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChain.class, new SpecialRenderChain());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrate.class, new SpecialRenderCrate());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDungeonChest.class, new SpecialRenderDungeonChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHook.class, new SpecialRenderHook());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKeyHanging.class, new SpecialRenderKeyHanging());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShipsWheel.class, new SpecialRenderShipsWheel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTarget.class, new SpecialRenderTarget());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRopeAnchor.class, new SpecialRenderRopeAnchor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFirepit.class, new SpecialRenderFirepit());
		
		// Old Blocks
		this.registerItemMesh(DRPMedievalBlocks.bookOne);
		this.registerItemMesh(DRPMedievalBlocks.ANVIL);
		this.registerItemMesh(DRPMedievalBlocks.GRINDSTONE);
		this.registerItemMesh(DRPMedievalBlocks.HANGING_CAULDRON);
		this.registerItemMesh(DRPMedievalBlocks.MORTAR);
		this.registerItemMesh(DRPMedievalBlocks.CAULDRON);
		this.registerItemMesh(DRPMedievalBlocks.ROPE_ANCHOR);
		this.registerItemMesh(DRPMedievalBlocks.FIREPIT);
//		
//		
//		
		this.registerItemMesh("SimpleChairs",DRPMedievalBlocks.SIMPLE_CHAIR_OAK);
		this.registerItemMesh("SimpleChairs",DRPMedievalBlocks.SIMPLE_CHAIR_BIRCH);
		this.registerItemMesh("SimpleChairs",DRPMedievalBlocks.SIMPLE_CHAIR_SPRUCE);
		this.registerItemMesh("SimpleChairs",DRPMedievalBlocks.SIMPLE_CHAIR_JUNGLE);
		this.registerItemMesh("SimpleChairs",DRPMedievalBlocks.SIMPLE_CHAIR_ACACIA);
		this.registerItemMesh("SimpleChairs",DRPMedievalBlocks.SIMPLE_CHAIR_DARK_OAK);
//		
//		this.registerItemMesh("SimpleTables",DRPMedievalBlocks.SIMPLE_TABLE_OAK);
//		this.registerItemMesh("SimpleTables",DRPMedievalBlocks.SIMPLE_TABLE_BIRCH);
//		this.registerItemMesh("SimpleTables",DRPMedievalBlocks.SIMPLE_TABLE_SPRUCE);
//		this.registerItemMesh("SimpleTables",DRPMedievalBlocks.SIMPLE_TABLE_JUNGLE);
//		this.registerItemMesh("SimpleTables",DRPMedievalBlocks.SIMPLE_TABLE_ACACIA);
//		this.registerItemMesh("SimpleTables",DRPMedievalBlocks.SIMPLE_TABLE_DARK_OAK);
//		
//		registerItemMesh("Apiaries",DRPMBlocks.APIARY_OAK);
//		registerItemMesh("Apiaries",DRPMBlocks.APIARY_BIRCH);
//		registerItemMesh("Apiaries",DRPMBlocks.APIARY_SPRUCE);
//		registerItemMesh("Apiaries",DRPMBlocks.APIARY_JUNGLE);
//		registerItemMesh("Apiaries",DRPMBlocks.APIARY_ACACIA);
//		registerItemMesh("Apiaries",DRPMBlocks.APIARY_DARK_OAK);
//		
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_EMPTY_OAK);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_EMPTY_BIRCH);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_EMPTY_SPRUCE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_EMPTY_JUNGLE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_EMPTY_ACACIA);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_EMPTY_DARK_OAK);
//		
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_CLOSED_OAK);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_CLOSED_BIRCH);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_CLOSED_SPRUCE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_CLOSED_JUNGLE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_CLOSED_ACACIA);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_CLOSED_DARK_OAK);
//		
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_WATER_OAK);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_WATER_BIRCH);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_WATER_SPRUCE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_WATER_JUNGLE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_WATER_ACACIA);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_WATER_DARK_OAK);
//		
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_GUNPOWDER_OAK);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_GUNPOWDER_BIRCH);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_GUNPOWDER_SPRUCE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_GUNPOWDER_JUNGLE);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_GUNPOWDER_ACACIA);
//		this.registerItemMesh("Barrels",DRPMedievalBlocks.BARREL_GUNPOWDER_DARK_OAK);
//		
//		this.registerItemMesh("BarrelChairs", DRPMedievalBlocks.BARREL_CHAIR_OAK);
//		this.registerItemMesh("BarrelChairs", DRPMedievalBlocks.BARREL_CHAIR_BIRCH);
//		this.registerItemMesh("BarrelChairs", DRPMedievalBlocks.BARREL_CHAIR_SPRUCE);
//		this.registerItemMesh("BarrelChairs", DRPMedievalBlocks.BARREL_CHAIR_JUNGLE);
//		this.registerItemMesh("BarrelChairs", DRPMedievalBlocks.BARREL_CHAIR_ACACIA);
//		this.registerItemMesh("BarrelChairs", DRPMedievalBlocks.BARREL_CHAIR_DARK_OAK);
//		
//		this.registerItemMesh("LogChairs", DRPMedievalBlocks.LOG_CHAIR_OAK);
//		this.registerItemMesh("LogChairs", DRPMedievalBlocks.LOG_CHAIR_BIRCH);
//		this.registerItemMesh("LogChairs", DRPMedievalBlocks.LOG_CHAIR_SPRUCE);
//		this.registerItemMesh("LogChairs", DRPMedievalBlocks.LOG_CHAIR_JUNGLE);
//		this.registerItemMesh("LogChairs", DRPMedievalBlocks.LOG_CHAIR_ACACIA);
//		this.registerItemMesh("LogChairs", DRPMedievalBlocks.LOG_CHAIR_DARK_OAK);
//		
//		this.registerItemMesh("Buckets",DRPMedievalBlocks.BUCKET_EMPTY);
//		this.registerItemMesh("Buckets",DRPMedievalBlocks.BUCKET_WATER);
//		this.registerItemMesh("Buckets",DRPMedievalBlocks.BUCKET_DIRT);

		
	}


	@Override
	public void addItemToRegisterMesh(Item item) {
		this.toRegisterMeshes.add(item);
	}
	
	public void forceAdditionalModels() {
		ModelBakery.registerItemVariants(Item.getItemFromBlock(DRPMedievalBlocks.CLEAN_PLANKS), new ResourceLocation[] {new ResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_oak"), new ResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_spruce"), new ResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_birch"), new ResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_jungle"), new ResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_dark_oak"), new ResourceLocation(DRPMedievalInfo.MODID + ":" + "clean_planks/clean_plank_acacia")});
	}

	public void registerItemMesh(Block block) {
		this.registerItemMesh(null, Item.getItemFromBlock(block));
	}
	
	public void registerItemMesh(String folder, Block block) {
		this.registerItemMesh(folder, Item.getItemFromBlock(block));
	}
	
	public static void registerItemMesh(Item item){
		if(item instanceof DRPItem){
			
		}else{
			registerItemMesh(null, item);
		}
	}
	
	public static void registerItemMesh(String folder, Item item) {
	    String path = stringParseName(item.getUnlocalizedName().toString().substring(item.getUnlocalizedName().toString().indexOf(".") + 1, item.getUnlocalizedName().toString().length()));
		if(folder != null){
			path = stringParseName(folder) + "/" + path;
			ModelBakery.registerItemVariants(item,new ResourceLocation(DRPMedievalInfo.MODID + ":" + path));
		}
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(DRPMedievalInfo.MODID + ":" + path, "inventory"));
	}
	
	private static String stringParseName(String name){
		char[] nameArray = name.toCharArray();
		ArrayList<Character> nameList = new ArrayList();
		for(int i = 0; i < nameArray.length; i++){
			if(Character.isUpperCase(nameArray[i])){
				if(i > 0) {
					nameList.add('_');
				}
				nameList.add(Character.toLowerCase(nameArray[i]));
			}else{
				nameList.add(nameArray[i]);
			}
		}
		
		StringBuilder builder = new StringBuilder(nameList.size());
	    for(Character ch: nameList){
	    	builder.append(ch);
	    	}
	    return builder.toString();
	}
}
