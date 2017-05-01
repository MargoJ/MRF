package pl.margoj.mrf.map.objects

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.serialization.DataTypeProperty

abstract class MapObject<M: MapObject<M>>: DataTypeProperty<MapObjectData<*>>
{
    abstract var position: Point

    override abstract val dataType: Class<out MapObjectData<*>>

    abstract fun copyObject(): M;
}