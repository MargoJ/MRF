package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class IntProperty(
        id: Int,
        name: String,
        propertyName: String,
        val minimum: Int = 0,
        default: Int = 0
) : ItemProperty<Int>(id, name = name, propertyName = propertyName, default = default)
{
    override fun serialize(out: DataOutputStream, value: Int)
    {
        out.writeInt(value)
    }

    override fun deserialize(input: DataInputStream): Int
    {
        return input.readInt()
    }
}