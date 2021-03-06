package net.dark_roleplay.medieval.common.blocks.templates;

import net.dark_roleplay.medieval.common.blocks.WoodHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class WoodenBlock extends Block{

	//public final PropertyEnum<WoodHelper.WoodType> VARIANT;
	
	public WoodenBlock(Material material, WoodHelper.WoodType... types) {
		super(material);
		//this.VARIANT = PropertyEnum.<WoodHelper.WoodType>create("variant", WoodHelper.WoodType.class, WoodHelper.apiaryTypes);
	}
	
	public static int getTypeAmount(){
		return 1;
	}
	
	public IBlockState getStateForVariant(WoodHelper.WoodType type){
		return this.getDefaultState();// VARIANT.getAllowedValues().contains(type) ? this.getDefaultState().withProperty(VARIANT, type) : this.getDefaultState();
	}
}

