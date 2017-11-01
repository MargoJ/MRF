package pl.margoj.mrf.item.properties

import pl.margoj.mrf.MRFIcon
import pl.margoj.mrf.MRFIconFormat
import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

class IconProperty(id: Int, name: String) : ItemProperty<MRFIcon?>(id, name, default = null)
{
    init
    {
        this.showWhenRestricted = true
    }

    override fun serialize(out: DataOutputStream, value: MRFIcon?)
    {
        value!!

        out.writeByte(value.format.id)
        out.writeShort(value.image.size)
        out.write(value.image)
    }

    override fun deserialize(input: DataInputStream): MRFIcon?
    {
        val format = MRFIconFormat.getById(input.readByte().toInt())
        val size = input.readShort().toInt()
        val buffer = ByteArray(size)
        val read = input.read(buffer)

        if (read != size)
        {
            throw IOException("icon loading failed, read = $read, size = $size")
        }

        return MRFIcon(buffer, format!!, null)
    }
}
