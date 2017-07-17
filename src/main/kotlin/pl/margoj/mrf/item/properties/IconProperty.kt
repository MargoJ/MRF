package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import pl.margoj.mrf.item.serialization.ItemFormat
import pl.margoj.mrf.item.serialization.ItemIcon
import java.io.*

class IconProperty(id: Int, name: String) : ItemProperty<ItemIcon?>(id = id, propertyName = "__ICON", name = name, default = null)
{
    override fun serialize(out: DataOutputStream, value: ItemIcon?)
    {
        value!!

        out.writeByte(value.format.id)
        out.writeShort(value.image.size)
        out.write(value.image)
    }

    override fun deserialize(input: DataInputStream): ItemIcon?
    {
        val format = ItemFormat.getById(input.readByte().toInt())
        val size = input.readShort().toInt()
        val buffer = ByteArray(size)
        val read = input.read(buffer)

        if (read != size)
        {
            throw IOException("icon loading failed, read = $read, size = $size")
        }

        return ItemIcon(buffer, format!!, null)
    }
}
