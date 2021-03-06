package net.dark_roleplay.medieval.common.blocks.decorative.lecterns;

import java.util.List;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.common.handler.DRPMedievalCreativeTabs;
import net.dark_roleplay.medieval.common.tileentities.lectern.TileEntity_Lectern;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class LargeLectern extends Block implements ITileEntityProvider{
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public static final PropertyBool TOP = PropertyBool.create("top");
	public static final PropertyBool BOOK = PropertyBool.create("book");

	public LargeLectern(String registryName) {
		super(Material.WOOD);
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(DRPMedievalCreativeTabs.DECORATION);
		this.setHardness(2F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.getDefaultState().withProperty(LargeLectern.FACING, EnumFacing.NORTH).withProperty(LargeLectern.TOP, false).withProperty(LargeLectern.BOOK, false));
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		state = state.getActualState(worldIn, pos);
		Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, this.getBoundingBox(state,worldIn,pos));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		if(state.getValue(LargeLectern.TOP))
			return new AxisAlignedBB(0f,0f,0f,1f,0.5f,1f);
		return new AxisAlignedBB(0.4f, 0f, 0.4f, 0.6f, 1f, 0.6f);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {

		boolean type = (meta / 4F) >= 1 ? true : false;
		meta %= 4;
		
		switch (meta) {
			case 0:
				return this.getDefaultState().withProperty(LargeLectern.FACING, EnumFacing.NORTH).withProperty(LargeLectern.TOP, type).withProperty(LargeLectern.BOOK, false);
			case 1:
				return this.getDefaultState().withProperty(LargeLectern.FACING, EnumFacing.EAST).withProperty(LargeLectern.TOP, type).withProperty(LargeLectern.BOOK, false);
			case 2:
				return this.getDefaultState().withProperty(LargeLectern.FACING, EnumFacing.SOUTH).withProperty(LargeLectern.TOP, type).withProperty(LargeLectern.BOOK, false);
			case 3:
				return this.getDefaultState().withProperty(LargeLectern.FACING, EnumFacing.WEST).withProperty(LargeLectern.TOP, type).withProperty(LargeLectern.BOOK, false);
			default:
				return this.getDefaultState().withProperty(LargeLectern.FACING, EnumFacing.NORTH).withProperty(LargeLectern.TOP, type).withProperty(LargeLectern.BOOK, false);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		int meta = state.getValue(LargeLectern.TOP) ? 4 : 0;
		
		
		EnumFacing facing = state.getValue(LargeLectern.FACING);
		if(facing.equals(EnumFacing.NORTH)) return meta;
		if(facing.equals(EnumFacing.EAST)) return meta + 1;
		if(facing.equals(EnumFacing.SOUTH)) return meta + 2;
		if(facing.equals(EnumFacing.WEST)) return meta + 3;
		return 0;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos){
		if(state.getValue(LargeLectern.TOP)){
			TileEntity_Lectern te = (TileEntity_Lectern) world.getTileEntity(pos);
			if((te != null))
				//				System.out.println(((World) world).isRemote  + "  " + te.renderBook());
				return state.withProperty(LargeLectern.BOOK, te.renderBook());
		}
        return state;
    }
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {LargeLectern.FACING,LargeLectern.TOP, LargeLectern.BOOK});
	}
	
	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){	
		return this.getDefaultState().withProperty(LargeLectern.FACING, placer.getHorizontalFacing().getOpposite()).withProperty(LargeLectern.TOP, false).withProperty(LargeLectern.BOOK, false);
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
        return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && worldIn.isAirBlock(pos.up()) && worldIn.isSideSolid(pos.down(), EnumFacing.UP);
    }
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		worldIn.setBlockState(pos.add(0,1,0), state.withProperty(LargeLectern.TOP, true).withProperty(LargeLectern.BOOK, false));
    }
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state){
		if(worldIn.getBlockState(pos.add(0,1,0)).getBlock() == this){
			worldIn.setBlockToAir(pos.add(0,1,0));
		}else if(worldIn.getBlockState(pos.add(0,-1,0)).getBlock() == this){
			worldIn.setBlockToAir(pos.add(0,-1,0));
		}
    }
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_Lectern();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {

		TileEntity tileEntity = world.getTileEntity(pos);

		if(tileEntity instanceof TileEntity_Lectern){
			TileEntity_Lectern te = (TileEntity_Lectern) tileEntity;
			ItemStack stack = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}

		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(!state.getValue(LargeLectern.TOP)){
			pos = pos.up();
			state = world.getBlockState(pos);
		}
		
		if(state.getValue(LargeLectern.TOP)){
			TileEntity_Lectern te = (TileEntity_Lectern) world.getTileEntity(pos);
			if((te != null) && te.renderBook()){
				ItemStack stack = player.getHeldItem(hand).copy();

				if (!world.isRemote){
					player.setHeldItem(hand, te.getStack());

		            this.resolveContents(te.getStack(), player);
		            
		        }

		        player.openBook(te.getStack(), hand);
		        if (!world.isRemote){
		        	player.getServer().addScheduledTask(() -> player.setHeldItem(hand, stack));
		        }

			}else{
				ItemStack stack = player.getHeldItem(hand).copy();
				stack.setCount(1);
				if(stack.getItem() == Items.WRITTEN_BOOK){
					te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).insertItem(0, stack, false);
					world.notifyBlockUpdate(pos, state, state, 3);
					if(!world.isRemote){
						player.getHeldItem(hand).shrink(1);
					}	
				}
			}
		}
		
		return true;
	}
	
	
	//Written Books
	private void resolveContents(ItemStack stack, EntityPlayer player){
        if (stack.getTagCompound() != null) {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (!nbttagcompound.getBoolean("resolved")){
                nbttagcompound.setBoolean("resolved", true);

                if (ItemWrittenBook.validBookTagContents(nbttagcompound)){
                    NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);

                    for (int i = 0; i < nbttaglist.tagCount(); ++i){
                        String s = nbttaglist.getStringTagAt(i);
                        ITextComponent itextcomponent;

                        try{
                            itextcomponent = ITextComponent.Serializer.fromJsonLenient(s);
                            itextcomponent = TextComponentUtils.processComponent(player, itextcomponent, player);
                        }catch (Exception var9){
                            itextcomponent = new TextComponentString(s);
                        }
                        nbttaglist.set(i, new NBTTagString(ITextComponent.Serializer.componentToJson(itextcomponent)));
                    }

                    nbttagcompound.setTag("pages", nbttaglist);

                    if ((player instanceof EntityPlayerMP) && (player.getHeldItemMainhand() == stack)){
                        Slot slot = player.openContainer.getSlotFromInventory(player.inventory, player.inventory.currentItem);
                        ((EntityPlayerMP)player).connection.sendPacket(new SPacketSetSlot(0, slot.slotNumber, stack));
                    }
                }
            }
        }
    }

}
