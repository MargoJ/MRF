package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class DoubleProperty(
        id: Int,
        name: String,
        propertyName: String,
        val minimum: Double = 0.0,
        default: Double = 0.0
) : ItemProperty<Double>(id, name = name, propertyName = propertyName, default = default)
{
    override fun serialize(out: DataOutputStream, value: Double)
    {
        out.writeDouble(value)
    }

    override fun deserialize(input: DataInputStream): Double
    {
        return input.readDouble()
    }
}