package pl.margoj.mrf.map.objects.mapspawn

import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class MapSpawnObjectData : MapObjectData<MapSpawnObject>
{
    override val objectId: Int = 2

    override fun encode(obj: MapSpawnObject, context: MapSerializationContext)
    {
    }

    override fun decode(context: MapSerializationContext): MapSpawnObject
    {
        return MapSpawnObject(context.objectPoint)
    }
}