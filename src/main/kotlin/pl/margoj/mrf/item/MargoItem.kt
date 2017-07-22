package pl.margoj.mrf.item

import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class MargoItem(id: String, name: String) : MargoResource(id, name)
{
    private val properties = hashMapOf<ItemProperty<*>, Any?>()

    override val category: Category get() = Category.ITEMS
    override val view: ResourceView get() = ResourceView(this.id, this.name, this.category, "$id.mji")

    override var name: String
        get() = this[ItemProperties.NAME]
        set(value)
        {
            this[ItemProperties.NAME] = value
        }

    init
    {
        this.name = name
    }

    var itemCategory: ItemCategory
        get() = this[ItemProperties.CATEGORY]
        set(value)
        {
            this[ItemProperties.CATEGORY] = value
        }

    var itemRarity: ItemRarity
        get() = this[ItemProperties.RARITY]
        set(value)
        {
            this[ItemProperties.RARITY] = value
        }

    operator fun <T> set(property: ItemProperty<T>, value: T)
    {
        this.properties.put(property, value)
    }

    operator fun <T> get(property: ItemProperty<T>): T
    {
        val returnValue: Any? = this.properties[property]

        @Suppress("UNCHECKED_CAST")
        if (returnValue != null)
        {
            return returnValue as T
        }

        return property.default
    }

    override fun toString(): String
    {
        val builder = StringBuilder("MargoItem(\n")
        builder.append("\tid = ").append(id).append(", \n")

        for ((key, value) in this.properties)
        {
            if (key.default == value)
            {
                continue
            }

            builder.append("\t").append(key.name).append(" = ").append(value).append(", \n")
        }

        builder.append(")")
        return builder.toString()
    }
}