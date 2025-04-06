package io.github.yunivers.stationfluidapi.api.fluid;

import net.modificationstation.stationapi.api.client.texture.Sprite;

public interface StationFluid
{
    Sprite getSprite(int side, int meta);
    String getFluidName();
    boolean isBright();
    int getBucketId();
}
