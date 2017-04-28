package pl.margoj.mrf.map.serialization

import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.fragment.auto.AutoMapFragmentData
import pl.margoj.mrf.map.fragment.empty.EmptyMapFragmentData
import pl.margoj.mrf.map.fragment.standard.StandardMapFragmentData
import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.map.objects.gateway.GatewayObjectData

class MapData<T : SerializationData<*>>
{
    private val mapFragmentData: MutableMap<Class<out T>, T> = hashMapOf()
    private val idToData: MutableMap<Int, T> = hashMapOf()

    fun registerData(data: T)
    {
        mapFragmentData.put(data::class.java, data)
        idToData.put(data.objectId, data)
    }

    fun getByClass(clazz: Class<out T>): T?
    {
        return mapFragmentData[clazz]
    }

    fun getById(id: Int): T?
    {
        return idToData[id]
    }

    companion object
    {
        val mapFragments = MapData<MapFragmentData<*>>()
        val mapObjects = MapData<MapObjectData<*>>()

        init
        {
            mapFragments.registerData(EmptyMapFragmentData())
            mapFragments.registerData(StandardMapFragmentData())
            mapFragments.registerData(AutoMapFragmentData())

            mapObjects.registerData(GatewayObjectData())
        }
    }
}