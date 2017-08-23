package pl.margoj.mrf.map.metadata.parentmap

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.metadata.prototypes.StringMeta

class ParentMap(override val value: String) : StringMeta()
{
    override val dataType: Class<out MapMetadataElementData<*>> = ParentMapData::class.java
}