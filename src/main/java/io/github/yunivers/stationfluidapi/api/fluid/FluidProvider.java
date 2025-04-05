package io.github.yunivers.stationfluidapi.api.fluid;

import net.minecraft.block.LiquidBlock;

import java.util.Collection;

public interface FluidProvider
{
    /**
     * Fluid provider method that returns {@link LiquidBlock} based on coordinate
     *
     * @param x           block X position
     * @param y           block Y position
     * @param z           block Z position
     * @return {@link LiquidBlock}
     */
    LiquidBlock getLiquid(int x, int y, int z);

    Collection<LiquidBlock> getLiquids();
}
