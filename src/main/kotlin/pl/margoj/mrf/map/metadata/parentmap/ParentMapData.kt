package pl.margoj.mrf.map.metadata.parentmap

import pl.margoj.mrf.map.metadata.prototypes.StringMetaData

class ParentMapData : StringMetaData<ParentMap>()
{
    override val objectId: Int = 3

    override fun create(string: String): ParentMap
    {
        return ParentMap(string)
    }
}