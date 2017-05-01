package pl.margoj.mrf.map.fragment

import org.apache.commons.lang3.Validate
import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.serialization.DataTypeProperty
import pl.margoj.mrf.map.tileset.Tileset
import java.awt.Graphics

abstract class MapFragment(open val tileset: Tileset?, val point: Point, val layer: Int): DataTypeProperty<MapFragmentData<*>>
{
    init
    {
        Validate.isTrue(layer in 0..MargoMap.LAYERS, "Invalid layer number")
    }


    override abstract val dataType: Class<out MapFragmentData<*>>

    abstract fun draw(g: Graphics)

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as MapFragment

        if (tileset != other.tileset) return false
        if (point != other.point) return false
        if (layer != other.layer) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = tileset?.hashCode() ?: 0
        result = 31 * result + point.hashCode()
        result = 31 * result + layer
        return result
    }
}