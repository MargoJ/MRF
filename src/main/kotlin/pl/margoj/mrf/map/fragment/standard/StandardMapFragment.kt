package pl.margoj.mrf.map.fragment.standard

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.tileset.Tileset
import java.awt.Graphics

class StandardMapFragment(override val tileset: Tileset, val tilesetPoint: Point, mapPoint: Point, layer: Int) : MapFragment(tileset, mapPoint, layer)
{
    override val dataType: Class<out MapFragmentData<*>> = StandardMapFragmentData::class.java

    override fun draw(g: Graphics)
    {
        g.drawImage(tileset.image.getSubimage(this.tilesetPoint.x * 32, this.tilesetPoint.y * 32, 32, 32), 0, 0, null)
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as StandardMapFragment

        if (tilesetPoint != other.tilesetPoint) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = super.hashCode()
        result = 31 * result + tilesetPoint.hashCode()
        return result
    }

    override fun toString(): String
    {
        return "StandardMapFragment(point=$point, tileset=$tileset, tilesetPoint=$tilesetPoint, layer=$layer)"
    }

}