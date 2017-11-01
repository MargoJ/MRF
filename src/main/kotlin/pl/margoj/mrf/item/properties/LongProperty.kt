package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class LongProperty(
        id: Int,
        name: String,
        propertyName: String? = null,
        default: Long = 0
) : ItemProperty<Long>(id, name, propertyName, default)
{
    var minimum: Long = 0

    override fun serialize(out: DataOutputStream, value: Long)
    {
        out.writeLong(value)
    }

    override fun deserialize(input: DataInputStream): Long
    {
        return input.readLong()
    }
}