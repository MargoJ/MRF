package pl.margoj.mrf.map.serialization

import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.fragment.auto.AutoMapFragmentData
import pl.margoj.mrf.map.fragment.empty.EmptyMapFragmentData
import pl.margoj.mrf.map.fragment.standard.StandardMapFragmentData

object MapData
{
    private val mapFragmentData: MutableMap<Class<out MapFragmentData<*>>, MapFragmentData<*>> = hashMapOf()
    private val idToData: MutableMap<Int, MapFragmentData<*>> = hashMapOf()

    fun registerMapFragmentData(data: MapFragmentData<*>)
    {
        mapFragmentData.put(data::class.java, data)
        idToData.put(data.fragmentId, data)
    }

    fun getByClass(clazz: Class<out MapFragmentData<*>>): MapFragmentData<*>?
    {
        return mapFragmentData[clazz]
    }


    fun getById(id: Int): MapFragmentData<*>?
    {
        return idToData[id]
    }

    init
    {
        registerMapFragmentData(EmptyMapFragmentData())
        registerMapFragmentData(StandardMapFragmentData())
        registerMapFragmentData(AutoMapFragmentData())
    }
}