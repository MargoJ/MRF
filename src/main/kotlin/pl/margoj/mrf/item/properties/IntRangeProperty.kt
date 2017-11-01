package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class IntRangeProperty(
        id: Int,
        name: String,
        propertyName: String,
        default: IntRange = 0..0
) : ItemProperty<IntRange>(id, name, propertyName, default)
{
    var minimum: Int = 0

    override fun serialize(out: DataOutputStream, value: IntRange)
    {
        out.writeInt(value.first)
        out.writeInt(value.endInclusive)
    }

    override fun deserialize(input: DataInputStream): IntRange
    {
        val first = input.readInt()
        val last = input.readInt()
        return first..last
    }
}