package com.blakebr0.morebuckets.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

// TODO: Cucumber
public class FluidHolderItemWrapper implements ICapabilityProvider {
	
	private ItemStack stack;
	private IFluidHolder holder;
	private boolean canFill, canDrain;
	
	public FluidHolderItemWrapper(ItemStack stack, IFluidHolder holder, boolean canFill, boolean canDrain) {
		this.stack = stack;
		this.holder = holder;
		this.canFill = canFill;
		this.canDrain = canDrain;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (!hasCapability(capability, facing)) 
			return null;
		
		return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(new IFluidHandlerItem() {
			
			@Override
			public IFluidTankProperties[] getTankProperties() {
				return new IFluidTankProperties[] { new FluidTankProperties(holder.getFluid(stack), holder.getCapacity(stack), canFill, canDrain) };
			}
			
			@Override
			public int fill(FluidStack fluid, boolean canFill) {
				return holder.fill(stack, fluid, canFill);
			}
			
			@Override
			public FluidStack drain(int amount, boolean canDrain) {
				return holder.drain(stack, amount, canDrain);
			}
			
			@Override
			public FluidStack drain(FluidStack fluid, boolean canDrain) {
				if (fluid.isFluidEqual(holder.getFluid(stack)))
					return holder.drain(stack, fluid.amount, canDrain);
				
				return null;
			}
			
			@Override
			public ItemStack getContainer() {
				return stack;
			}
		});
	}
}
