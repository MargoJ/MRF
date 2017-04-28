package pl.margoj.mrf.map.serialization

import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.objects.MapObject
import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.serialization.MRFSerializer
import pl.margoj.mrf.serialization.SerializationContext
import pl.margoj.mrf.serialization.SerializationException
import pl.margoj.mrf.serialization.StringConstantPool
import java.io.DataOutputStream
import java.util.zip.GZIPOutputStream

class MapSerializer : MRFSerializer<MargoMap>()
{
    var compress = true

    override fun doSerialize(obj: MargoMap, out: DataOutputStream)
    {
        val context = MapSerializationContext(obj)
        context.stringConstantPool = StringConstantPool()

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
                        val data: MapFragmentData<MapFragment> = MapData.mapFragments.getByClass(fragment.fragmentDataType) as? MapFragmentData<MapFragment> ?: throw SerializationException("Unknown data type: ${fragment.fragmentDataType}")

                        context.output!!.writeByte(data.objectId)
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
            currentOut.write(stringConstantPoolContext.bytes)
        }

        // reserved ; metadata elements count
        currentOut.writeByte(0)

        // actual map
        currentOut.write(context.bytes)

        // collisions
        for (x in 0..(obj.width - 1))
        {
            for (y in 0..(obj.height - 1))
            {
                currentOut.writeBoolean(obj.getCollisionAt(Point(x, y)))
            }
        }

        // objects count
        currentOut.writeShort(obj.objects.size)

        for (mapObject in obj.objects)
        {
            @Suppress("UNCHECKED_CAST")
            val data: MapObjectData<MapObject<*>> = MapData.mapObjects.getByClass(mapObject.fragmentDataType) as? MapObjectData<MapObject<*>> ?: throw SerializationException("Unknown type: ${mapObject.fragmentDataType}")

            currentOut.writeByte(data.objectId)
            currentOut.writeByte(mapObject.position.x)
            currentOut.writeByte(mapObject.position.y)

            MapSerializationContext(obj).use {
                objectContext ->
                data.encode(mapObject, objectContext)

                currentOut.write(objectContext.bytes)
            }
        }

        gzip?.close()
    }

    companion object
    {
        const val CURRENT_VERSION = 0x01
    }
}