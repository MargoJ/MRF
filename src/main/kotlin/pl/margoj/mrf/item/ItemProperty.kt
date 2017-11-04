package pl.margoj.mrf.item

import pl.margoj.mrf.item.properties.*
import pl.margoj.mrf.item.properties.special.*
import java.io.DataInputStream
import java.io.DataOutputStream

object Category
{
    val BASIC = "Podstawowe statystyki"
    val INFO = "Informacje o przedmiocie"
    val STATISTICS = "Statystyki przedmiotu"
    val BATTLE_STATISTICS = "Statystyki walki przedmiotu"
    val SPECIAL = "Specjalne"
    val REQUIREMENTS = "Wymagania"
    val BOUNDS = "Wiązania"
    val USES = "Konsumpcyjne"
}

object ItemProperties
{
    // basic properties
    val NAME = NameProperty(id = 0, name = "Nazwa przedmiotu").also { it.category = Category.BASIC }
    val CATEGORY = CategoryProperty(id = 1, name = "Kategoria przedmiotu").also { it.category = Category.BASIC }
    val RARITY = RarityProperty(id = 2, name = "Klasa przedmiotu").also { it.category = Category.BASIC }
    val ICON = IconProperty(id = 3, name = "Ikonka przedmiotu").also { it.category = Category.BASIC }
    val PRICE = PriceProperty(id = 4, name = "Cena przedmiotu").also { it.category = Category.BASIC }

    // info
    val DESCRIPTION = StringProperty(id = 10, name = "Opis", propertyName = "opis").also { it.category = Category.INFO; it.showWhenRestricted = true; it.long = true }

    // statistics
    val ALL_ATTRIBUTES = IntProperty(id = 20, name = "Wszystkie cechy", propertyName = "da").also { it.category = Category.STATISTICS }
    val STRENGTH = IntProperty(id = 21, name = "Siła", propertyName = "ds").also { it.category = Category.STATISTICS }
    val AGILITY = IntProperty(id = 22, name = "Zręczność", propertyName = "dz").also { it.category = Category.STATISTICS }
    val INTELLECT = IntProperty(id = 23, name = "Inteligencja", propertyName = "di").also { it.category = Category.STATISTICS }
    val HEALTH = IntProperty(id = 24, name = "Życie", propertyName = "hp").also { it.category = Category.STATISTICS }
    val ATTACK_SPEED = IntProperty(id = 25, name = "SA (58 SA == 0.58 SA)", propertyName = "sa").also { it.category = Category.STATISTICS }
    val HEALTH_FOR_STRENGTH = DoubleProperty(id = 26, name = "X życia za 1 punkt siły", propertyName = "hpbon", default = 0.0).also { it.category = Category.STATISTICS; it.minimum = 0.0 }

    // batttle-only
    val DAMAGE = IntRangeProperty(id = 30, name = "Atak fizyczny", propertyName = "dmg", default = 0..0).also { it.category = Category.BATTLE_STATISTICS; it.minimum = 0 }
    val ARMOR = IntProperty(id = 32, name = "Pancerz", propertyName = "ac", default = 0).also { it.category = Category.BATTLE_STATISTICS; it.minimum = 0 }
    val BLOCK = IntProperty(id = 33, name = "Blok", propertyName = "blok", default = 0).also { it.category = Category.BATTLE_STATISTICS; it.minimum = 0 }
    val EVADE = IntProperty(id = 34, name = "Unik", propertyName = "evade", default = 0).also { it.category = Category.BATTLE_STATISTICS; it.minimum = 0 }

    // special
    val SIZE = IntProperty(id = 50, name = "Pojemnosc (tylko torby)", propertyName = "bag").also { it.category = Category.SPECIAL; it.showWhenRestricted = true }

    // loots
    val LOOT = StringProperty(id = 70, name = "", propertyName = "loot").also { it.editable = false; it.showWhenRestricted = true }

    // requirements
    val LEVEL_REQUIREMENT = IntProperty(id = 80, name = "Wymagany poziom", propertyName = "lvl").also { it.category = Category.REQUIREMENTS; it.showWhenRestricted = true }
    val PROFESSION_REQUIREMENT = ProfessionRequirementProperty(id = 81, name = "Wymagana profesja: ", propertyName = "reqp").also { it.category = Category.REQUIREMENTS; it.showWhenRestricted = true }

    // bounds
    val SOUL_BOUND = BooleanProperty(id = 90, name = "Związany z właścicielem", propertyName = "soulbound").also { it.category = Category.BOUNDS; it.showWhenRestricted = true }
    val PERM_BOUND = BooleanProperty(id = 91, name = "Związany z właścicielem na stałe", propertyName = "permbound").also { it.category = Category.BOUNDS; it.showWhenRestricted = true }
    val BINDS = BooleanProperty(id = 92, name = "Wiąże po założeniu", propertyName = "binds").also { it.category = Category.BOUNDS; it.showWhenRestricted = true }

    // uses
    val AMOUNT = IntProperty(id = 101, name = "Ilośc", propertyName = "amount").also { it.category = Category.USES }
    val MAX_AMOUNT = IntProperty(id = 102, name = "Max. Ilość", propertyName = "capacity").also { it.category = Category.USES }
    val COOLDOWN = CooldownProperty(id = 103, name="Można używać co X min", propertyName = "timelimit").also { it.category = Category.USES; it.showWhenRestricted = true }

    val HEAL = IntProperty(id = 110, name = "Leczy", propertyName = "leczy").also { it.minimum = Int.MIN_VALUE; it.category = Category.USES }
    val FULL_HEAL = IntProperty(id = 111, name = "Pełne leczenie", propertyName = "fullheal").also { it.minimum = Int.MIN_VALUE; it.category = Category.USES }
    val HEAL_PERCENT = IntProperty(id = 112, name = "% Leczenia", propertyName = "perheal").also { it.minimum = Int.MIN_VALUE; it.category = Category.USES }

    val RUN_SCRIPT = StringProperty(id = 113, name = "Uruchom skrypt", propertyName = "").also { it.category = Category.USES; it.renderable = false }

    val TELEPORT = TeleportProperty(id = 114, name="Teleportuj", propertyName = "teleport").also { it.category = Category.USES }

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
    var category: String? = null
    var showWhenRestricted: Boolean = false
    var editable: Boolean = true
    var renderable: Boolean = true

    companion object
    {
        private val _properties = hashMapOf<Int, ItemProperty<*>>()
        val properties: Collection<ItemProperty<*>> get() = this._properties.values

        operator fun get(key: Int): ItemProperty<*>?
        {
            return this._properties[key]
        }

        fun unregister(key: Int)
        {
            this._properties.remove(key)
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