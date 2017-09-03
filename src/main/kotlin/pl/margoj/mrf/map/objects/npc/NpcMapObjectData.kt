package pl.margoj.mrf.map.objects.npc

import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.map.serialization.MapSerializationContext
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

class NpcMapObjectData : MapObjectData<NpcMapObject>
{
    private companion object
    {
        val MASK_CUSTOM_NAME = 1 shl 1
        val MASK_CUSTOM_LEVEL = 1 shl 2
        val MASK_CUSTOM_GRAPHICS = 1 shl 3
        val MASK_GROUP = 1 shl 4
    }

    override val objectId: Int = 3

    override fun encode(obj: NpcMapObject, context: MapSerializationContext)
    {
        val output = context.output!!
        val byteArrayOutputStream = ByteArrayOutputStream()
        val data = DataOutputStream(byteArrayOutputStream)
        var mask = 0

        mask = this.encodeIfNotNull(mask, MASK_CUSTOM_NAME, obj.name, { data.writeUTF(it) })
        mask = this.encodeIfNotNull(mask, MASK_CUSTOM_LEVEL, obj.level, { data.writeInt(it) })
        mask = this.encodeIfNotNull(mask, MASK_CUSTOM_GRAPHICS, obj.graphics, { data.writeUTF(it) })
        mask = this.encodeIfNotNull(mask, MASK_GROUP, obj.group, { data.writeInt(it) })

        output.writeUTF(obj.script)
        output.writeByte(mask)
        output.write(byteArrayOutputStream.toByteArray())
    }

    private fun <T> encodeIfNotNull(mask: Int, dataMask: Int, data: T?, adder: (T) -> Unit): Int
    {
        if (data == null)
        {
            return mask
        }

        adder(data)
        return mask or dataMask
    }

    override fun decode(context: MapSerializationContext): NpcMapObject
    {
        val input = context.input!!
        val mapObject = NpcMapObject(context.objectPoint)

        mapObject.script = input.readUTF()

        val mask = input.readByte().toInt()

        mapObject.name = this.decodeNullable(mask, MASK_CUSTOM_NAME, { input.readUTF() })
        mapObject.level = this.decodeNullable(mask, MASK_CUSTOM_LEVEL, { input.readInt() })
        mapObject.graphics = this.decodeNullable(mask, MASK_CUSTOM_GRAPHICS, { input.readUTF() })
        mapObject.group = this.decodeNullable(mask, MASK_GROUP, { input.readInt() })

        return mapObject
    }

    private fun <T> decodeNullable(mask: Int, dataMask: Int, reader: () -> T): T?
    {
        if ((mask and dataMask) == 0)
        {
            return null
        }

        return reader()
    }
}