package pl.margoj.mrf.map.serialization

import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.tileset.Tileset
import pl.margoj.mrf.serialization.MRFDeserializer
import pl.margoj.mrf.serialization.SerializationContext
import pl.margoj.mrf.serialization.SerializationException
import pl.margoj.mrf.serialization.StringConstantPool
import java.io.DataInputStream
import java.util.zip.GZIPInputStream

class MapDeserializer(private val tilesets: Collection<Tileset>) : MRFDeserializer<MargoMap>()
{
    override fun doDeserialize(input: DataInputStream): MargoMap
    {
        val mapVersion = input.readByte()

        if (mapVersion != MapSerializer.CURRENT_VERSION.toByte())
        {
            throw SerializationException("Version is invalid, map=$mapVersion, current=${MapSerializer.CURRENT_VERSION}")
        }

        val compressed = input.readBoolean()

        val gzip = if (compressed) GZIPInputStream(input) else null
        val currentInput = if (compressed) DataInputStream(gzip) else input

        // create base object
        val map = MargoMap(currentInput.readUTF(), currentInput.readUTF(), currentInput.readShort().toInt(), currentInput.readShort().toInt())

        currentInput.readByte() // layers count, we can ignore it for now

        val stringConstantPool = StringConstantPool()

        stringConstantPool.deserialize(SerializationContext(currentInput))

        val context = MapSerializationContext(currentInput, this.tilesets, map)
        context.stringConstantPool = stringConstantPool

        for (i in 1..currentInput.readByte()) // metadata elements count
        {
            val id = currentInput.readByte().toInt()

            val data = MapData.mapMetadata.getById(id) ?: throw SerializationException("Unknown object type: $id")
            map.setMetadata(data.decode(context))
        }

        // actual map
        val fragments = map.fragments

        for (x in 0..(map.width - 1))
        {
            for (y in 0..(map.height - 1))
            {
                context.currentTile = Point(x, y)
                for (layer in 0..(MargoMap.LAYERS - 1))
                {
                    context.currentLayer = layer

                    val dataId = currentInput.readByte()
                    val data = MapData.mapFragments.getById(dataId.toInt()) ?: throw SerializationException("Unknown data id $dataId")
                    val fragment = data.decode(context)
                    fragments[x][y][layer] = fragment
                }
            }
        }
        // do not close the context

        // collisions
        for (x in 0..(map.width - 1))
        {
            for (y in 0..(map.height - 1))
            {
                map.setCollisionAt(Point(x, y), currentInput.readBoolean())
            }
        }


        val objects = currentInput.readShort() // objects count
        for (i in 1..objects)
        {
            val id = currentInput.readByte().toInt()

            // read positions
            context.objectPoint = Point(currentInput.readByte().toInt(), currentInput.readByte().toInt())

            val data = MapData.mapObjects.getById(id) ?: throw SerializationException("Unknown object type: $id")
            map.addObject(data.decode(context))
        }

        context.close()
        gzip?.close()

        return map
    }
}
