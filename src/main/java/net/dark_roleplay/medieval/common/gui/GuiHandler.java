package net.dark_roleplay.medieval.common.gui;

import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.medieval.client.gui.GuiSledgeInventory;
import net.dark_roleplay.medieval.client.gui.MusikMinigameGui;
import net.dark_roleplay.medieval.client.gui.Note;
import net.dark_roleplay.medieval.client.gui.storage.Gui_SimpleStorage;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityCrate;
import net.dark_roleplay.medieval.common.blocks.tileentitys.TileEntityDungeonChest;
import net.dark_roleplay.medieval.common.entity.item.EntitySledge;
import net.dark_roleplay.medieval.common.gui.container.ContainerCrate;
import net.dark_roleplay.medieval.common.gui.container.ContainerDungeonChest;
import net.dark_roleplay.medieval.common.gui.container.Container_SimpleStorage;
import net.dark_roleplay.medieval.common.inventory.ContainerSledgeInventory;
import net.dark_roleplay.medieval.common.tileentities.storage.TileEntity_SimpleStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int GUI_DUNGEONCHEST = 0;
	public static final int GUI_CRATE = 1;
	public static final int GUI_MINIGAME_MUSIK = 2;
	public static final int GUI_SLEDGE = 3;
	public static final int GUI_SIMPLE_STORAGE = 4;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		switch (ID) {
			case GUI_DUNGEONCHEST:
				return new ContainerDungeonChest(player.inventory, (TileEntityDungeonChest) world.getTileEntity(new BlockPos(x, y, z)));
			case GUI_CRATE:
				return new ContainerCrate(player.inventory, (TileEntityCrate) world.getTileEntity(new BlockPos(x, y, z)));
			case GUI_SLEDGE:
				List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player ,new AxisAlignedBB(x,y,z,x + 1,y + 1,z + 1));
				for(Entity entity : entities){
					if(entity instanceof EntitySledge){
						ContainerSledgeInventory con =  new ContainerSledgeInventory(player.inventory,((EntitySledge)entity).getChest(),(EntitySledge)entity,player);
						((EntityPlayerMP) player).openContainer = con;
						((EntityPlayerMP) player).openContainer.addListener((EntityPlayerMP)player);
						System.out.println("Server");
						System.out.println(((EntitySledge)entity).getChest());
						return con;
					}
				}
				return null;
			case GUI_SIMPLE_STORAGE:
				BlockPos pos = new BlockPos(x, y, z);
		        TileEntity te = world.getTileEntity(pos);
		        if (te instanceof TileEntity_SimpleStorage)
					return new Container_SimpleStorage(player.inventory, (TileEntity_SimpleStorage) te);
				return null;
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		switch (ID) {
			case GUI_DUNGEONCHEST:
				return new GuiDungeonChest(new ContainerDungeonChest(player.inventory, (TileEntityDungeonChest) world.getTileEntity(new BlockPos(x, y, z))));
			case GUI_CRATE:
				return new GuiCrate(new ContainerCrate(player.inventory, (TileEntityCrate) world.getTileEntity(new BlockPos(x, y, z))));
			case GUI_MINIGAME_MUSIK:
				ArrayList<Note> test = new ArrayList<Note>(){{
					
					this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_E);this.add(Note.HALF_1_E);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_E);this.add(Note.HALF_1_E);this.add(Note.QUARTER_1_E);
					this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_C);this.add(Note.QUARTER_1_D);this.add(Note.HALF_1_E);this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_F);
					this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_E);this.add(Note.EIGHT_1_E);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_D);
					this.add(Note.QUARTER_1_D);this.add(Note.QUARTER_1_E);this.add(Note.HALF_1_D);this.add(Note.HALF_2_G);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_E);this.add(Note.HALF_1_E);this.add(Note.QUARTER_1_E);
					this.add(Note.QUARTER_1_E);this.add(Note.HALF_1_E);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_2_G);this.add(Note.QUARTER_1_C);this.add(Note.QUARTER_1_D);this.add(Note.FULL_1_E);this.add(Note.QUARTER_1_F);
					this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_2_G);
					this.add(Note.QUARTER_2_G);this.add(Note.QUARTER_1_F);this.add(Note.QUARTER_1_D);this.add(Note.HALF_1_C);this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_D);this.add(Note.QUARTER_1_C);this.add(Note.FULL_NONE);
					this.add(Note.QUARTER_1_E);this.add(Note.QUARTER_1_D);this.add(Note.QUARTER_1_C);this.add(Note.FULL_NONE);
					
					}};
				return new MusikMinigameGui(test);
			case GUI_SLEDGE:
				List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player ,new AxisAlignedBB(x,y,z,x + 1,y + 1,z + 1));
				for(Entity entity : entities){
					if(entity instanceof EntitySledge){
						System.out.println("Client");
						System.out.println(((EntitySledge)entity).getChest());
						return new GuiSledgeInventory(new ContainerSledgeInventory(player.inventory,((EntitySledge)entity).getChest(),(EntitySledge)entity,player));
					}
				}
				return null;
			case GUI_SIMPLE_STORAGE:
				BlockPos pos = new BlockPos(x, y, z);
		        TileEntity te = world.getTileEntity(pos);
		        if (te instanceof TileEntity_SimpleStorage) {
		        	TileEntity_SimpleStorage containerTileEntity = (TileEntity_SimpleStorage) te;
		            return new Gui_SimpleStorage(containerTileEntity, new Container_SimpleStorage(player.inventory, containerTileEntity));
		        }
		        return null;
			default:
				return null;
		}
	}

}
