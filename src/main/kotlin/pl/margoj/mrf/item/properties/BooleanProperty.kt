package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

class BooleanProperty(
        id: Int,
        name: String,
        default: Boolean = false,
        propertyName: String,
        showWhenRestricted: Boolean = false
) : ItemProperty<Boolean>(id = id, name = name, default = default, propertyName = propertyName, showWhenRestricted = showWhenRestricted)
{
    override fun serialize(out: DataOutputStream, value: Boolean)
    {
        out.writeBoolean(value)
    }

    override fun deserialize(input: DataInputStream): Boolean
    {
        return input.readBoolean()
    }

}