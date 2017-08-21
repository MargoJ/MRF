package pl.margoj.mrf.map.objects.mapspawn

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.objects.MapObject

data class MapSpawnObject(override var position: Point) : MapObject<MapSpawnObject>()
{
    override val dataType: Class<MapSpawnObjectData> = MapSpawnObjectData::class.java

    override fun copyObject(): MapSpawnObject
    {
        return this.copy()
    }
}