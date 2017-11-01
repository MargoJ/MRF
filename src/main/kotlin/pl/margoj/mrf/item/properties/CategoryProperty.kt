package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemCategory
import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

class CategoryProperty(id: Int, name: String) : ItemProperty<ItemCategory>(id, name, default = ItemCategory.NONE)
{
    init
    {
        this.showWhenRestricted = true
    }

    override fun serialize(out: DataOutputStream, value: ItemCategory)
    {
        out.writeByte(value.margoId)
    }

    override fun deserialize(input: DataInputStream): ItemCategory
    {
        return ItemCategory[input.readByte().toInt()]!!
    }
}