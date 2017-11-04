package pl.margoj.mrf.item.properties.special

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class TeleportProperty(
        id: Int,
        name: String,
        propertyName: String,
        default: Teleport = Teleport("", false, 0, 0)
) : ItemProperty<TeleportProperty.Teleport>(id, name, propertyName, default)
{
    override fun serialize(out: DataOutputStream, value: Teleport)
    {
        out.writeUTF(value.map)
        out.writeBoolean(value.customCoords)
        if (value.customCoords)
        {
            out.writeInt(value.x)
            out.writeInt(value.y)
        }
    }

    override fun deserialize(input: DataInputStream): Teleport
    {
        val map = input.readUTF()
        val customCoords = input.readBoolean()
        val x = if(customCoords) input.readInt() else 0
        val y = if(customCoords) input.readInt() else 0

        return Teleport(map, customCoords, x, y)
    }

    data class Teleport(val map: String, val customCoords: Boolean, val x: Int, val y: Int)
}