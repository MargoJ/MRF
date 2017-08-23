package pl.margoj.mrf.map.metadata.ismain

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.metadata.prototypes.BooleanMeta

class IsMain(override val value: Boolean) : BooleanMeta()
{
    override val dataType: Class<out MapMetadataElementData<*>> = IsMainData::class.java
}