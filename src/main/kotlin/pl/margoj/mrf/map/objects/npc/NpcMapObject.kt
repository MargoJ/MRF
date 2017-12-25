package pl.margoj.mrf.map.objects.npc

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.objects.MapObject

data class NpcMapObject(
        override var position: Point,
        var id: String? = null,
        var group: Byte = 0
) : MapObject<NpcMapObject>()
{
    override val dataType: Class<NpcMapObjectData> = NpcMapObjectData::class.java

    override fun copyObject(): NpcMapObject
    {
        return this.copy()
    }
}