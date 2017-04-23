package pl.margoj.mrf.map.fragment

import pl.margoj.mrf.map.serialization.MapSerializationContext

interface MapFragmentData<F : MapFragment>
{
    val fragmentId: Int

    fun encode(fragment: F, context: MapSerializationContext)

    fun decode(context: MapSerializationContext): F
}