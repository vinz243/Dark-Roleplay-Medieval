package net.dark_roleplay.medieval.common.items.blocks;

import java.util.List;

import net.dark_roleplay.medieval.common.blocks.templates.WoodenBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WoodTypeItemBlock extends ItemBlock {

	public WoodTypeItemBlock(WoodenBlock block) {
		super(block);

		this.setRegistryName(block.getRegistryName());
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
//		int size = ((WoodenBlock) this.getBlock()).VARIANT.getAllowedValues().size();
//		for(int i = 0; i < size; i++)
//			subItems.add(new ItemStack(itemIn, 1, i));
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

}
