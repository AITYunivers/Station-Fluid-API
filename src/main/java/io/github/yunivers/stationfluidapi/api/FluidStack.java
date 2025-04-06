package io.github.yunivers.stationfluidapi.api;

import io.github.yunivers.stationfluidapi.api.fluid.StationBucketItem;
import io.github.yunivers.stationfluidapi.api.fluid.StationFluid;
import net.minecraft.block.Block;
import net.minecraft.block.LiquidBlock;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;

import static net.modificationstation.stationapi.api.StationAPI.LOGGER;

public class FluidStack
{
    public int amount;
    public LiquidBlock fluid;

    public FluidStack(LiquidBlock fluid, int amount)
    {
        if (fluid == null) {
            LOGGER.fatal(
                    "Null fluid supplied to fluidstack. Did you try and create a stack for an unregistered fluid?");
            throw new IllegalArgumentException("Cannot create a fluidstack from a null fluid");
        }

        this.fluid = fluid;
        this.amount = amount;
    }

    public FluidStack(ItemStack stack)
    {
        if (stack.getItem() instanceof BucketItem item)
        {
            if (item.id == Item.WATER_BUCKET.id)
            {
                this.fluid = (LiquidBlock)Block.WATER;
                this.amount = 1000;
            }
            else if (item.id == Item.LAVA_BUCKET.id)
            {
                this.fluid = (LiquidBlock)Block.LAVA;
                this.amount = 1000;
            }
            else if (item instanceof StationBucketItem stationBucketItem)
            {
                this.fluid = (LiquidBlock)Block.BLOCKS[stationBucketItem.getLiquidBlockId()];
                this.amount = 1000;
            }
        }
        else if (Block.BLOCKS[stack.getItem().id] instanceof LiquidBlock fluidBlock)
        {
            this.fluid = fluidBlock;
            this.amount = 1000;
        }
    }

    public FluidStack(NbtCompound nbt)
    {
        readNbt(nbt);
    }

    public void readNbt(NbtCompound nbt)
    {
        fluid = (LiquidBlock)Block.BLOCKS[nbt.getInt("FluidID")];
        amount = nbt.getInt("Amount");
    }

    public void writeNbt(NbtCompound nbt)
    {
        nbt.putInt("FluidID", fluid.id);
        nbt.putInt("Amount", amount);
    }

    public final LiquidBlock getFluid()
    {
        return fluid;
    }

    public final String getFluidName()
    {
        if (fluid instanceof StationFluid sFluid)
            return sFluid.getFluidName();
        else if (fluid.id == Block.WATER.id || fluid.id == Block.FLOWING_WATER.id)
            return "Water";
        else if (fluid.id == Block.LAVA.id || fluid.id == Block.FLOWING_LAVA.id)
            return "Lava";
        return fluid.getClass().getName();
    }

    /**
     * @return A copy of this FluidStack
     */
    public FluidStack copy()
    {
        return new FluidStack(getFluid(), amount);
    }

    /**
     * Determines if the FluidIDs and NBT Tags are equal. This does not check amounts.
     *
     * @param other The FluidStack for comparison
     * @return true if the Fluids (IDs and NBT Tags) are the same
     */
    public boolean isFluidEqual(FluidStack other)
    {
        return other != null && getFluid().id == other.getFluid().id;
    }

    /**
     * Determines if the Fluids are equal and this stack is larger.
     *
     * @return true if this FluidStack contains the other FluidStack (same fluid and >= amount)
     */
    public boolean containsFluid(FluidStack other)
    {
        return isFluidEqual(other) && amount >= other.amount;
    }

    /**
     * Determines if the FluidIDs, Amounts, and NBT Tags are all equal.
     *
     * @param other
     *            - the FluidStack for comparison
     * @return true if the two FluidStacks are exactly the same
     */
    public boolean isFluidStackIdentical(FluidStack other)
    {
        return isFluidEqual(other) && amount == other.amount;
    }

    /**
     * Determines if the FluidIDs and NBT Tags are equal compared to a registered container
     * ItemStack. This does not check amounts.
     *
     * @param other
     *            The ItemStack for comparison
     * @return true if the Fluids (IDs and NBT Tags) are the same
     */
    public boolean isFluidEqual(ItemStack other)
    {
        return isFluidEqual(new FluidStack(other));
    }

    public boolean isBright()
    {
        if (fluid instanceof StationFluid sFluid)
            return sFluid.isBright();
        return fluid.id == Block.LAVA.id || fluid.id == Block.FLOWING_LAVA.id;
    }

    public int getBucketId()
    {
        if (fluid instanceof StationFluid sFluid)
            return sFluid.getBucketId();
        else if (fluid.id == Block.WATER.id || fluid.id == Block.FLOWING_WATER.id)
            return Item.WATER_BUCKET.id;
        else if (fluid.id == Block.LAVA.id || fluid.id == Block.FLOWING_LAVA.id)
            return Item.LAVA_BUCKET.id;
        return Item.BUCKET.id;
    }

    public Sprite getSprite(int side, int meta)
    {
        if (fluid instanceof StationFluid sFluid)
            return sFluid.getSprite(side, meta);
        return Atlases.getTerrain().getTexture(fluid.getTexture(side, meta)).getSprite();
    }
}
