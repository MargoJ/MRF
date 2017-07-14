package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import pl.margoj.mrf.item.ItemRarity
import java.io.DataInputStream
import java.io.DataOutputStream

class RarityProperty(id: Int, name: String) : ItemProperty<ItemRarity>(id = id, name = name, propertyName = "__RARITY", default = ItemRarity.NORMAL)
{
    override fun serialize(out: DataOutputStream, value: ItemRarity)
    {
        out.writeByte(value.ordinal)
    }

    override fun deserialize(input: DataInputStream): ItemRarity
    {
        return ItemRarity.values()[input.readByte().toInt()]
    }

}