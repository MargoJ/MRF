package pl.margoj.mrf.map.metadata.pvp

import pl.margoj.mrf.map.metadata.MetadataElement

class MapPvP private constructor(val margonemId: Int) : MetadataElement()
{
    companion object
    {
        val NO_PVP = MapPvP(0)
        val CONDITIONAL = MapPvP(1)
        val ARENAS = MapPvP(4)
        val UNCONDITIONAL = MapPvP(2)
    }

    override val dataType: Class<MapPvPData> = MapPvPData::class.java
}