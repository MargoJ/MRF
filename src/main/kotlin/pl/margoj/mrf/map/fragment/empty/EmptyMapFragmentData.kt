package pl.margoj.mrf.map.fragment.empty

import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class EmptyMapFragmentData : MapFragmentData<EmptyMapFragment>
{
    override var objectId: Int = 0

    override fun encode(obj: EmptyMapFragment, context: MapSerializationContext)
    {
    }

    override fun decode(context: MapSerializationContext): EmptyMapFragment
    {
        return EmptyMapFragment(context.currentTile, context.currentLayer)
    }
}