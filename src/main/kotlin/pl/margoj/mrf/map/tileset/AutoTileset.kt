package pl.margoj.mrf.map.tileset

import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.auto.AutoMapFragment
import java.awt.image.BufferedImage

class AutoTileset private constructor(name: String, image: BufferedImage) : Tileset(name, image)
{
    // kids, don't try that at home
    private lateinit var parts: Collection<TilesetFile>
    private lateinit var tilesetFiles: Map<Point, TilesetFile>
    private lateinit var tilesetParts: Map<TilesetFile, Array<BufferedImage>>

    private constructor(name: String, files: Collection<TilesetFile>, tilesetNames: MutableMap<Point, TilesetFile>, tilesetParts: MutableMap<TilesetFile, Array<BufferedImage>>) : this(name, createAutotilesetImage(files, tilesetNames, tilesetParts))
    {
        this.parts = files
        this.tilesetFiles = tilesetNames
        this.tilesetParts = tilesetParts
    }

    constructor(name: String, parts: Collection<TilesetFile>) : this(name, parts, hashMapOf(), hashMapOf())

    val files: Collection<TilesetFile> get() = this.parts

    fun getTilesetParts(file: TilesetFile): Array<BufferedImage>?
    {
        return this.tilesetParts[file]
    }

    fun getTilesetFile(point: Point): TilesetFile?
    {
        return this.tilesetFiles[point]
    }

    override fun getFragmentAt(map: MargoMap, tilesetPoint: Point, mapPoint: Point, layer: Int): MapFragment
    {
        return AutoMapFragment(this, map, this.tilesetFiles.get(tilesetPoint)!!, mapPoint, layer)
    }

    override val auto: Boolean = true

    companion object
    {
        val AUTO = "auto"
        val IMAGES_PER_ROW = 8

        fun createAutotilesetImage(files: Collection<TilesetFile>, tilesetNames: MutableMap<Point, TilesetFile>, tilesetParts: MutableMap<TilesetFile, Array<BufferedImage>>): BufferedImage
        {
            val rows = maxOf((files.size - 1) / IMAGES_PER_ROW + 1, 0)
            val out = BufferedImage(IMAGES_PER_ROW * 32, rows * 32, BufferedImage.TYPE_INT_ARGB)

            var x = 0
            var y = 0

            for (file in files)
            {
                if (!file.auto)
                {
                    throw IllegalArgumentException("Trying to create auto tileset from non-auto files")
                }
                if (file.image.width != 96 || file.image.height != 128)
                {
                    throw IllegalArgumentException("Tileset's bounds are invalid, not 96x128")
                }

                out.graphics.drawImage(file.image.getSubimage(0, 0, 32, 32), x * 32, y * 32, 32, 32, null)

                tilesetNames[Point(x, y)] = file

                // split tileset into parts
                val parts: Array<BufferedImage?> = arrayOfNulls(48) // 8x6 grid, each tile is 16x16

                for(partX in 0..5)
                {
                    for(partY in 0..7)
                    {
                        parts[partY * 6 + (partX % 6)] = file.image.getSubimage(partX * 16, partY * 16, 16, 16)
                    }
                }

                @Suppress("UNCHECKED_CAST")
                tilesetParts[file] = parts as Array<BufferedImage>

                if(++x >= IMAGES_PER_ROW)
                {
                    x = 0
                    y++
                }
            }

            return out
        }
    }
}