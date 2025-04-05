package io.github.yunivers.stationfluidapi.api.fluid;

import net.modificationstation.stationapi.api.client.texture.Sprite;

public interface StationFluid
{
    Sprite getSprite();
    String getFluidName();
    boolean isBright();
    int getBucketId();
}
