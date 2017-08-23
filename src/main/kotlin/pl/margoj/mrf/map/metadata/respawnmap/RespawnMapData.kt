package pl.margoj.mrf.map.metadata.respawnmap

import pl.margoj.mrf.map.metadata.prototypes.StringMetaData

class RespawnMapData : StringMetaData<RespawnMap>()
{
    override val objectId: Int = 4

    override fun create(string: String): RespawnMap
    {
        return RespawnMap(string)
    }
}