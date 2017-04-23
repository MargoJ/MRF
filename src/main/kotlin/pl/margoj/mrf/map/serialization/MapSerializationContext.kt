package pl.margoj.mrf.map.serialization

import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.tileset.Tileset
import pl.margoj.mrf.serialization.SerializationContext
import pl.margoj.mrf.serialization.StringConstantPool
import java.io.InputStream
import java.util.stream.Collectors

class MapSerializationContext : SerializationContext
{
    private var tilesets: Map<String, Tileset>? = null
    var map: MargoMap
        private set
    var stringConstantPool: StringConstantPool? = null
    var currentTile: Point = Point(0, 0)
    var currentLayer: Int = 0

    /**
     * write-only mode
     */
    constructor(map: MargoMap) : super()
    {
        this.map = map
    }

    /**
     * read-only mode
     */
    constructor(input: InputStream, tilesets: Collection<Tileset>, map: MargoMap) : super(input)
    {
        this.tilesets = this.makeTilesetMap(tilesets)
        this.map = map
    }

    /**
     * read-only mode
     */
    constructor(input: ByteArray, tilesets: Collection<Tileset>, map: MargoMap) : super(input)
    {
        this.tilesets = this.makeTilesetMap(tilesets)
        this.map = map
    }

    fun getTileset(name: String?): Tileset?
    {
        return if(name == null) null else this.tilesets?.get(name)
    }

    private fun makeTilesetMap(tilesets: Collection<Tileset>): Map<String, Tileset>
    {
        return tilesets.stream().collect(Collectors.toMap(Tileset::name, { tileset -> tileset }))
    }
}