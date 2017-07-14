package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class StringProperty(
        id: Int,
        name: String,
        propertyName: String,
        default: String = "",
        val long: Boolean = false,
        val regexp: Regex? = null
) : ItemProperty<String>(id = id, name = name, propertyName = propertyName, default = default)
{
    override fun serialize(out: DataOutputStream, value: String)
    {
        out.writeUTF(value)
    }

    override fun deserialize(input: DataInputStream): String
    {
        return input.readUTF()
    }
}