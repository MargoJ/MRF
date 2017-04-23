package pl.margoj.mrf.map.fragment.empty

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class EmptyMapFragmentData: MapFragmentData<EmptyMapFragment>
{
    override var fragmentId: Int = 0

    override fun encode(fragment: EmptyMapFragment, context: MapSerializationContext)
    {
    }

    override fun decode(context: MapSerializationContext): EmptyMapFragment
    {
        return EmptyMapFragment(context.currentTile, context.currentLayer)
    }
}