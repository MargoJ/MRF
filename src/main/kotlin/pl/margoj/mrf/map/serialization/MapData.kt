package pl.margoj.mrf.map.serialization

import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.fragment.auto.AutoMapFragmentData
import pl.margoj.mrf.map.fragment.empty.EmptyMapFragmentData
import pl.margoj.mrf.map.fragment.standard.StandardMapFragmentData
import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.metadata.pvp.MapPvPData
import pl.margoj.mrf.map.metadata.welcome.WelcomeMessageData
import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.map.objects.gateway.GatewayObjectData
import pl.margoj.mrf.map.objects.mapspawn.MapSpawnObjectData
import pl.margoj.mrf.map.objects.npc.NpcMapObjectData

class MapData<T : SerializationData<*>>
{
    private val mapFragmentData: MutableMap<Class<out T>, T> = hashMapOf()
    private val idToData: MutableMap<Int, T> = hashMapOf()

    val values: Collection<T> get() = this.idToData.values

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

    fun getByObject(dataContainer: DataTypeProperty<T>): T?
    {
        return values.firstOrNull { dataContainer.dataType == it }
    }

    companion object
    {
        val mapFragments = MapData<MapFragmentData<*>>()
        val mapObjects = MapData<MapObjectData<*>>()
        val mapMetadata = MapData<MapMetadataElementData<*>>()

        init
        {
            mapFragments.registerData(EmptyMapFragmentData())
            mapFragments.registerData(StandardMapFragmentData())
            mapFragments.registerData(AutoMapFragmentData())

            mapObjects.registerData(GatewayObjectData())
            mapObjects.registerData(MapSpawnObjectData())
            mapObjects.registerData(NpcMapObjectData())

            mapMetadata.registerData(MapPvPData())
            mapMetadata.registerData(WelcomeMessageData())
        }
    }
}