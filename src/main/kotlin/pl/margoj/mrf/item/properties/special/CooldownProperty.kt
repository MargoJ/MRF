package pl.margoj.mrf.item.properties.special

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class CooldownProperty(
        id: Int,
        name: String,
        propertyName: String,
        default: Cooldown = Cooldown(0, 0L)
) : ItemProperty<CooldownProperty.Cooldown>(id, name, propertyName, default)
{
    var minimum = 0

    override fun serialize(out: DataOutputStream, value: Cooldown)
    {
        out.writeInt(value.cooldown)
        out.writeLong(value.nextUse)
    }

    override fun deserialize(input: DataInputStream): Cooldown
    {
        return Cooldown(input.readInt(), input.readLong())
    }

    data class Cooldown(var cooldown: Int, var nextUse: Long)
}