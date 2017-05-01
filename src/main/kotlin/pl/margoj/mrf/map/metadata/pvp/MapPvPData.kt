package pl.margoj.mrf.map.metadata.pvp

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class MapPvPData : MapMetadataElementData<MapPvP>
{
    override val objectId: Int = 1

    override val defaultValue: MapPvP = MapPvP.CONDITIONAL

    override fun encode(obj: MapPvP, context: MapSerializationContext)
    {
        context.output!!.writeByte(when (obj)
        {
            MapPvP.NO_PVP -> 0
            MapPvP.CONDITIONAL -> 1
            MapPvP.ARENAS -> 2
            MapPvP.UNCONDITIONAL -> 3
            else -> 1
        })
    }

    override fun decode(context: MapSerializationContext): MapPvP
    {
        return when (context.input!!.readByte().toInt())
        {
            0 -> MapPvP.NO_PVP
            1 -> MapPvP.CONDITIONAL
            2 -> MapPvP.ARENAS
            3 -> MapPvP.UNCONDITIONAL
            else -> MapPvP.CONDITIONAL // the safest one when something fails
        }
    }
}