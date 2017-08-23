package pl.margoj.mrf.map.metadata.respawnmap

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.metadata.prototypes.StringMeta

class RespawnMap(override val value: String) : StringMeta()
{
    override val dataType: Class<out MapMetadataElementData<*>> = RespawnMapData::class.java
}