package pl.margoj.mrf.item.serialization

import pl.margoj.mrf.item.ItemProperty
import pl.margoj.mrf.item.MargoItem
import pl.margoj.mrf.serialization.MRFSerializer
import java.io.DataOutputStream

class ItemSerializer : MRFSerializer<MargoItem>()
{
    override fun doSerialize(obj: MargoItem, out: DataOutputStream)
    {
        out.writeUTF(obj.id)

        ItemSerializer.writeProperties(obj.properties, out, false)
    }

    companion object
    {
        fun writeProperties(obj: Map<ItemProperty<*>, Any?>, out: DataOutputStream, putDefaults: Boolean)
        {
            val toWrite = hashMapOf<ItemProperty<*>, Any?>()

            for (property in ItemProperty.properties)
            {
                if (!obj.containsKey(property))
                {
                    continue
                }

                if ((putDefaults && obj.containsKey(property)) || obj[property] != property.default)
                {
                    toWrite.put(property, obj[property])
                }
            }

            out.writeShort(toWrite.size)

            for ((key, value) in toWrite)
            {
                out.writeShort(key.id)

                @Suppress("UNCHECKED_CAST")
                key as ItemProperty<Any?>

                key.serialize(out, value)
            }
        }
    }
}