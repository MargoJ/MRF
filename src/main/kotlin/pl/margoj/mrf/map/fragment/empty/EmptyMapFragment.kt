package pl.margoj.mrf.map.fragment.empty

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.MapFragmentData
import java.awt.Graphics

class EmptyMapFragment(point: Point, layer: Int) : MapFragment(null, point, layer)
{
    override val dataType: Class<out MapFragmentData<*>> = EmptyMapFragmentData::class.java

    override fun draw(g: Graphics)
    {
    }

    override fun toString(): String
    {
        return "EmptyMapFragment(point=$point, layer=$layer)"
    }
}