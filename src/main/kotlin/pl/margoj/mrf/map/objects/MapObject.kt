package pl.margoj.mrf.map.objects

import pl.margoj.mrf.map.Point

abstract class MapObject<M: MapObject<M>>
{
    abstract var position: Point

    abstract val fragmentDataType: Class<out MapObjectData<*>>

    abstract fun copyObject(): M;
}