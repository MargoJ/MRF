package pl.margoj.mrf.map.objects.npc

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.objects.MapObject

data class NpcMapObject(
        override var position: Point,
        var script: String? = null,
        var graphics: String? = null,
        var name: String? = null,
        var level: Int? = null,
        var group: Int? = null
) : MapObject<NpcMapObject>()
{
    override val dataType: Class<NpcMapObjectData> = NpcMapObjectData::class.java

    override fun copyObject(): NpcMapObject
    {
        return this.copy()
    }
}