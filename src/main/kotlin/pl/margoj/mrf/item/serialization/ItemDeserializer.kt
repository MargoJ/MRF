package pl.margoj.mrf.item.serialization

import pl.margoj.mrf.item.ItemProperty
import pl.margoj.mrf.item.MargoItem
import pl.margoj.mrf.serialization.MRFDeserializer
import java.io.DataInputStream

class ItemDeserializer : MRFDeserializer<MargoItem>()
{
    override fun doDeserialize(input: DataInputStream): MargoItem
    {
        val item = MargoItem(input.readUTF(), "")

        ItemDeserializer.readProperties(item.properties, input)
        return item
    }

    companion object
    {
        fun readProperties(map: MutableMap<ItemProperty<*>, Any?>, input: DataInputStream)
        {
            val propertiesLength = input.readShort()

            for (i in 0 until propertiesLength)
            {
                @Suppress("UNCHECKED_CAST")
                val property = ItemProperty[input.readShort().toInt()]!! as ItemProperty<Any>
                val value = property.deserialize(input)

                map.put(property, value)
            }
        }
    }
}