package pl.margoj.mrf.item

import pl.margoj.mrf.item.properties.*
import java.io.DataInputStream
import java.io.DataOutputStream

object ItemProperties
{
    val NAME = NameProperty(id = 0, name = "Nazwa przedmiotu");
    val CATEGORY = CategoryProperty(id = 1, name = "Kategoria przedmiotu")
    val RARITY = RarityProperty(id = 2, name = "Klasa przedmiotu")
    val ICON = IconProperty(id = 3, name = "Ikonka przedmiotu")
    val PRICE = PriceProperty(id = 4, name = "Cena przedmiotu")

    val DESCRIPTION = StringProperty(id = 10, name = "Opis", propertyName = "opis", long = true)

    val STRENGTH = IntProperty(id = 21, name = "Siła", propertyName = "ds")
    val DEXTERITY = IntProperty(id = 22, name = "Zręczność", propertyName = "dz")
    val INTELLECT = IntProperty(id = 23, name = "Inteligencja", propertyName = "di")

    val SIZE = IntProperty(id = 50, name = "Pojemnosc (tylko torby)", propertyName = "bag")

    val NO_DESCRIPTION = BooleanProperty(id = 31, name = "Niezidentyfikowany", propertyName = "nodesc")
}

abstract class ItemProperty<T>(
        val id: Int,
        val name: String,
        val propertyName: String,
        val default: T
) : Comparable<ItemProperty<*>>
{
    companion object
    {
        private val _properties = hashMapOf<Int, ItemProperty<*>>()
        val properties: Collection<ItemProperty<*>> get() = this._properties.values

        operator fun get(key: Int): ItemProperty<*>?
        {
            return this._properties[key]
        }

        init
        {
            // to init the ItemProperties Object
            ItemProperties.NAME
        }
    }

    init
    {
        _properties.put(this.id, this);
    }

    abstract fun serialize(out: DataOutputStream, value: T)

    abstract fun deserialize(input: DataInputStream): T

    override fun compareTo(other: ItemProperty<*>): Int
    {
        return this.id - other.id
    }

    override fun toString(): String
    {
        return "${this.javaClass.name}(id = $id)"
    }
}