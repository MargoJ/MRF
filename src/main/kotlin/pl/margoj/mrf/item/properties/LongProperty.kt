package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class LongProperty(
        id: Int,
        name: String,
        propertyName: String? = null,
        val minimum: Long = 0,
        default: Long = 0,
        showWhenRestricted: Boolean = false
) : ItemProperty<Long>(id, name = name, propertyName = propertyName, default = default, showWhenRestricted = showWhenRestricted)
{
    override fun serialize(out: DataOutputStream, value: Long)
    {
        out.writeLong(value)
    }

    override fun deserialize(input: DataInputStream): Long
    {
        return input.readLong()
    }
}