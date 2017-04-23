package pl.margoj.mrf.map.fragment.auto

import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.tileset.AutoTileset
import pl.margoj.mrf.map.tileset.TilesetFile
import java.awt.Graphics
import java.util.LinkedHashMap

class AutoMapFragment(override val tileset: AutoTileset, val map: MargoMap, val tilesetFile: TilesetFile, point: Point, layer: Int) : MapFragment(tileset, point, layer)
{
    override val fragmentDataType: Class<out MapFragmentData<*>> = AutoMapFragmentData::class.java

    override fun draw(g: Graphics)
    {
        val parts = this.tileset.getTilesetParts(this.tilesetFile)!!
        val mask = this.getMask()

        g.drawImage(parts[this.getTilePart(tilingMapLeftUp, mask)], 0, 0, null)
        g.drawImage(parts[this.getTilePart(tilingMapLeftDown, mask)], 0, 16, null)
        g.drawImage(parts[this.getTilePart(tilingMapRightUp, mask)], 16, 0, null)
        g.drawImage(parts[this.getTilePart(tilingMapRightDown, mask)], 16, 16, null)
    }

    private fun getRelative(relativeX: Int, relativeY: Int): AutoMapFragment?
    {
        val x = this.point.x + relativeX
        val y = this.point.y + relativeY
        val point = Point(x, y)

        if (!this.map.inBounds(point))
        {
            return null
        }

        val fragment = map.getFragment(point, layer) as? AutoMapFragment ?: return null

        return if (fragment.tileset == this.tileset && fragment.tilesetFile == this.tilesetFile) fragment else null
    }

    private fun getMask(): Int
    {
        var mask = 0

        mask = mask or if (getRelative(-1, -1) == null) 0 else DIRECTION_UP_LEFT
        mask = mask or if (getRelative(0, -1) == null) 0 else DIRECTION_UP
        mask = mask or if (getRelative(1, -1) == null) 0 else DIRECTION_UP_RIGHT
        mask = mask or if (getRelative(-1, 0) == null) 0 else DIRECTION_LEFT
        mask = mask or if (getRelative(1, 0) == null) 0 else DIRECTION_RIGHT
        mask = mask or if (getRelative(-1, 1) == null) 0 else DIRECTION_DOWN_LEFT
        mask = mask or if (getRelative(0, 1) == null) 0 else DIRECTION_DOWN
        mask = mask or if (getRelative(1, 1) == null) 0 else DIRECTION_DOWN_RIGHT

        return mask
    }

    private fun getTilePart(tilingMap: Map<Int, Int>, mask: Int): Int
    {
        var match = 0

        tilingMap.forEach {
            key, value ->
            if ((mask and key) == key)
            {
                match = value
            }
        }

        return match
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as AutoMapFragment

        if (tileset != other.tileset) return false
        if (tilesetFile != other.tilesetFile) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = super.hashCode()
        result = 31 * result + tileset.hashCode()
        result = 31 * result + tilesetFile.hashCode()
        return result
    }

    override fun toString(): String
    {
        return "AutoMapFragment(map=$map, point=$point, tileset=$tileset, tilesetFile=$tilesetFile, layer=$layer)"
    }

    companion object
    {
        private const val DIRECTION_UP_LEFT = 0x01
        private const val DIRECTION_UP = 0x02
        private const val DIRECTION_UP_RIGHT = 0x04
        private const val DIRECTION_LEFT = 0x08
        private const val DIRECTION_RIGHT = 0x10
        private const val DIRECTION_DOWN_LEFT = 0x20
        private const val DIRECTION_DOWN = 0x40
        private const val DIRECTION_DOWN_RIGHT = 0x80

        private val tilingMapLeftUp = LinkedHashMap<Int, Int>()
        private val tilingMapLeftDown = LinkedHashMap<Int, Int>()
        private val tilingMapRightUp = LinkedHashMap<Int, Int>()
        private val tilingMapRightDown = LinkedHashMap<Int, Int>()

        init
        {

            tilingMapLeftUp.put(0, 12) // default
            tilingMapLeftUp.put(DIRECTION_UP, 36)
            tilingMapLeftUp.put(DIRECTION_LEFT, 16)
            tilingMapLeftUp.put(DIRECTION_UP or DIRECTION_DOWN, 24)
            tilingMapLeftUp.put(DIRECTION_RIGHT or DIRECTION_LEFT, 14)
            tilingMapLeftUp.put(DIRECTION_UP or DIRECTION_LEFT, 4)
            tilingMapLeftUp.put(DIRECTION_UP or DIRECTION_LEFT or DIRECTION_UP_LEFT, 40)
            tilingMapLeftUp.put(DIRECTION_UP or DIRECTION_LEFT or DIRECTION_UP_LEFT or DIRECTION_RIGHT, 26)
            tilingMapLeftUp.put(DIRECTION_UP or DIRECTION_LEFT or DIRECTION_RIGHT or DIRECTION_UP_RIGHT or DIRECTION_UP_LEFT, 38)
            tilingMapLeftUp.put(DIRECTION_UP or DIRECTION_LEFT or DIRECTION_UP_LEFT or DIRECTION_DOWN, 26)

            tilingMapLeftDown.put(0, 42) // default
            tilingMapLeftDown.put(DIRECTION_DOWN, 18)
            tilingMapLeftDown.put(DIRECTION_LEFT, 46)
            tilingMapLeftDown.put(DIRECTION_LEFT or DIRECTION_RIGHT, 44)
            tilingMapLeftDown.put(DIRECTION_UP or DIRECTION_DOWN, 30)
            tilingMapLeftDown.put(DIRECTION_DOWN or DIRECTION_LEFT, 10)
            tilingMapLeftDown.put(DIRECTION_DOWN or DIRECTION_LEFT or DIRECTION_DOWN_LEFT, 22)
            tilingMapLeftDown.put(DIRECTION_DOWN or DIRECTION_LEFT or DIRECTION_DOWN_LEFT or DIRECTION_RIGHT, 32)
            tilingMapLeftDown.put(DIRECTION_DOWN or DIRECTION_LEFT or DIRECTION_RIGHT or DIRECTION_DOWN_RIGHT or DIRECTION_DOWN_LEFT, 20)
            tilingMapLeftDown.put(DIRECTION_DOWN or DIRECTION_LEFT or DIRECTION_DOWN_LEFT or DIRECTION_UP, 32)

            tilingMapRightUp.put(0, 17) // default
            tilingMapRightUp.put(DIRECTION_UP, 41)
            tilingMapRightUp.put(DIRECTION_RIGHT, 13)
            tilingMapRightUp.put(DIRECTION_UP or DIRECTION_DOWN, 29)
            tilingMapRightUp.put(DIRECTION_LEFT or DIRECTION_RIGHT, 15)
            tilingMapRightUp.put(DIRECTION_UP or DIRECTION_RIGHT, 5)
            tilingMapRightUp.put(DIRECTION_UP or DIRECTION_RIGHT or DIRECTION_UP_RIGHT, 37)
            tilingMapRightUp.put(DIRECTION_UP or DIRECTION_RIGHT or DIRECTION_UP_RIGHT or DIRECTION_LEFT, 27)
            tilingMapRightUp.put(DIRECTION_UP or DIRECTION_LEFT or DIRECTION_RIGHT or DIRECTION_UP_RIGHT or DIRECTION_UP_LEFT, 39)
            tilingMapRightUp.put(DIRECTION_UP or DIRECTION_RIGHT or DIRECTION_UP_RIGHT or DIRECTION_DOWN, 27)

            tilingMapRightDown.put(0, 47) // default
            tilingMapRightDown.put(DIRECTION_DOWN, 23)
            tilingMapRightDown.put(DIRECTION_RIGHT, 43)
            tilingMapRightDown.put(DIRECTION_RIGHT or DIRECTION_LEFT, 45)
            tilingMapRightDown.put(DIRECTION_UP or DIRECTION_DOWN, 35)
            tilingMapRightDown.put(DIRECTION_DOWN or DIRECTION_RIGHT, 11)
            tilingMapRightDown.put(DIRECTION_DOWN or DIRECTION_RIGHT or DIRECTION_DOWN_RIGHT, 19)
            tilingMapRightDown.put(DIRECTION_DOWN or DIRECTION_RIGHT or DIRECTION_DOWN_RIGHT or DIRECTION_LEFT, 33)
            tilingMapRightDown.put(DIRECTION_DOWN or DIRECTION_LEFT or DIRECTION_RIGHT or DIRECTION_DOWN_RIGHT or DIRECTION_DOWN_LEFT, 21)
            tilingMapRightDown.put(DIRECTION_DOWN or DIRECTION_RIGHT or DIRECTION_DOWN_RIGHT or DIRECTION_UP, 33)
        }
    }
}