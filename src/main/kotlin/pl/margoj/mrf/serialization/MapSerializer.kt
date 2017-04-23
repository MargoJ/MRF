package pl.margoj.mrf.serialization

import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.serialization.MapData
import pl.margoj.mrf.map.serialization.MapSerializationContext
import java.io.DataOutputStream
import java.util.zip.GZIPOutputStream

class MapSerializer : MRFSerializer<MargoMap>()
{
    var compress = true

    override fun doSerialize(obj: MargoMap, out: DataOutputStream)
    {
        val context = MapSerializationContext(obj)

        context.use {
            val fragments = obj.fragments

            for (x in 0..(obj.width - 1))
            {
                for (y in 0..(obj.height - 1))
                {
                    context.currentTile = Point(x, y)
                    for (layer in 0..(MargoMap.LAYERS - 1))
                    {
                        context.currentLayer = layer

                        val fragment = fragments[x][y][layer]

                        @Suppress("UNCHECKED_CAST")
                        val data: MapFragmentData<MapFragment> = MapData.getByClass(fragment.fragmentDataType) as? MapFragmentData<MapFragment> ?: throw SerializationException("Unknown data type: ${fragment.fragmentDataType}")

                        context.output!!.writeByte(data.fragmentId)
                        data.encode(fragment, context)
                    }
                }
            }
        }

        // for compatibility
        out.writeByte(CURRENT_VERSION)

        // gzip compressed ?
        out.writeBoolean(this.compress)

        val gzip = if (this.compress) GZIPOutputStream(out) else null
        val currentOut = if (this.compress) DataOutputStream(gzip) else out

        // base info
        currentOut.writeUTF(obj.id)
        currentOut.writeUTF(obj.name)
        currentOut.writeShort(obj.width)
        currentOut.writeShort(obj.height)

        // for compability in future
        currentOut.writeByte(MargoMap.LAYERS)

        // string constant pool
        SerializationContext().use {
            stringConstantPoolContext ->
            context.stringConstantPool!!.serialize(stringConstantPoolContext)
            out.write(stringConstantPoolContext.bytes)
        }

        // reserved ; metadata elements count
        out.writeByte(0)

        // actual map
        out.write(context.bytes)

        // collisions
        for (x in 0..(obj.width - 1))
        {
            for (y in 0..(obj.height - 1))
            {
                out.writeBoolean(obj.getCollisionAt(Point(x, y)))
            }
        }

        // reserved ; objects count
        out.writeShort(0)

        gzip?.close()
    }

    companion object
    {
        const val CURRENT_VERSION = 0x01
    }
}