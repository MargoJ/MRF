package pl.margoj.mrf.item

import pl.margoj.mrf.item.properties.*
import java.io.DataInputStream
import java.io.DataOutputStream

object ItemProperties
{
    // basic properties
    val NAME = NameProperty(id = 0, name = "Nazwa przedmiotu")
    val CATEGORY = CategoryProperty(id = 1, name = "Kategoria przedmiotu")
    val RARITY = RarityProperty(id = 2, name = "Klasa przedmiotu")
    val ICON = IconProperty(id = 3, name = "Ikonka przedmiotu")
    val PRICE = PriceProperty(id = 4, name = "Cena przedmiotu")

    // info
    val DESCRIPTION = StringProperty(id = 10, name = "Opis", propertyName = "opis").also { it.showWhenRestricted = true; it.long = true }

    // statistics
    val ALL_ATTRIBUTES = IntProperty(id = 20, name = "Wszystkie cechy", propertyName = "da")
    val STRENGTH = IntProperty(id = 21, name = "Siła", propertyName = "ds")
    val AGILITY = IntProperty(id = 22, name = "Zręczność", propertyName = "dz")
    val INTELLECT = IntProperty(id = 23, name = "Inteligencja", propertyName = "di")
    val HEALTH = IntProperty(id = 24, name = "Życie", propertyName = "hp")
    val ATTACK_SPEED = IntProperty(id = 25, name = "SA (58 SA == 0.58 SA)", propertyName = "sa")
    val HEALTH_FOR_STRENGTH = DoubleProperty(id = 26, name = "X życia za 1 punkt siły", propertyName = "hpbon", default = 0.0).also { it.minimum = 0.0 }

    // batttle-only
    val DAMAGE = IntRangeProperty(id = 30, name = "Atak fizyczny", propertyName = "dmg", default = 0..0).also { it.minimum = 0 }
    val ARMOR = IntProperty(id = 32, name = "Pancerz", propertyName = "ac", default = 0).also { it.minimum = 0 }
    val BLOCK = IntProperty(id = 33, name = "Blok", propertyName = "blok", default = 0).also { it.minimum = 0 }
    val EVADE = IntProperty(id = 34, name = "Unik", propertyName = "evade", default = 0).also { it.minimum = 0 }

    // special
    val SIZE = IntProperty(id = 50, name = "Pojemnosc (tylko torby)", propertyName = "bag")

    // loots
    val LOOT = StringProperty(id = 70, name = "", propertyName = "loot").also { it.editable = false }

    // requirements
    val LEVEL_REQUIREMENT = IntProperty(id = 80, name = "Wymagany poziom", propertyName = "lvl")
    val PROFESSION_REQUIREMENT = ProfessionRequirementProperty(id = 81, name = "Wymagana profesja: ", propertyName = "reqp")

    // bounds
    val SOUL_BOUND = BooleanProperty(id = 90, name  = "Związany z właścicielem", propertyName = "soulbound")
    val PERM_BOUND = BooleanProperty(id = 91, name  = "Związany z właścicielem na stałe", propertyName = "permbound")
    val BINDS = BooleanProperty(id = 92, name  = "Wiąże po założeniu", propertyName = "binds")

    // others
    val NO_DESCRIPTION = BooleanProperty(id = 31, name = "Niezidentyfikowany", propertyName = "nodesc").also { it.showWhenRestricted = true }
}

abstract class ItemProperty<T>(
        val id: Int,
        val name: String,
        val propertyName: String? = null,
        val default: T
) : Comparable<ItemProperty<*>>
{
    var showWhenRestricted: Boolean = false
    var editable: Boolean = true

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
        _properties.put(this.id, this)
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