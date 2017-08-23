package pl.margoj.mrf.map.metadata.istown

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.metadata.prototypes.BooleanMeta

class IsTown(override val value: Boolean) : BooleanMeta()
{
    override val dataType: Class<out MapMetadataElementData<*>> = IsTownData::class.java
}