package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class StringProperty(
        id: Int,
        name: String,
        propertyName: String? = null,
        default: String = ""
) : ItemProperty<String>(id, name, propertyName, default)
{
    var long: Boolean = false
    var regexp: Regex? = null

    override fun serialize(out: DataOutputStream, value: String)
    {
        out.writeUTF(value)
    }

    override fun deserialize(input: DataInputStream): String
    {
        return input.readUTF()
    }
}