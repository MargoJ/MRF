package pl.margoj.mrf.map.tileset

import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.standard.StandardMapFragment
import java.awt.image.BufferedImage

open class Tileset(val name: String, val image: BufferedImage, open var files: Collection<TilesetFile>)
{
    open val auto: Boolean = false
    val wholeTilesetWidth: Int get() = image.width
    val wholeTilesetHeight: Int get() = image.height

    open fun getFragmentAt(map: MargoMap, tilesetPoint: Point, mapPoint: Point, layer: Int): MapFragment
    {
        return StandardMapFragment(this, tilesetPoint, mapPoint, layer)
    }
}