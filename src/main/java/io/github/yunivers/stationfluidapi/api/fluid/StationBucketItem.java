package io.github.yunivers.stationfluidapi.api.fluid;

import net.modificationstation.stationapi.api.template.item.TemplateBucketItem;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class StationBucketItem extends TemplateBucketItem
{
    public StationBucketItem(Identifier identifier, int fluidBlockId) {
        super(identifier, fluidBlockId);
    }

    public abstract int getLiquidBlockId();
}
