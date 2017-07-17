package pl.margoj.mrf.item

enum class ItemCategory(val localizedName: String, val margoId: Int)
{
    NONE("-Brak kategorii -", 0),

    ONE_HANDED_WEAPONS("Jednoręczne", 1),

    TWO_HANDED_WEAPONS("Dwuręczne", 2),

    HAND_AND_A_HALF_WEAPONS("Półtoraręczne", 3),

    RANGE_WEAPON("Dystansowe", 4),

    HELPERS("Pomocnicze", 5),

    WANDS("Różdżki", 6),

    STAFF("Laski", 7),

    ARMOR("Zbroje", 8),

    HELMET("Hełmy", 9),

    BOOTS("Buty", 10),

    GLOVES("Rękawice", 11),

    RINGS("Pierścienie", 12),

    NECKLACES("Naszyjniki", 13),

    SHIELDS("Tarcze", 14),

    NEUTRAL("Neutralne", 15),

    CONSUMABLE("Konsumpcyjne", 16),

    GOLD("Złoto", 17),

    KEYS("Klucze", 18),

    QUEST("Questowe", 19),

    RESPAWNABLE("Odnawialne", 20),

    ARROWS("Strzały", 21),

    TALISMANS("Talizmany", 22),

    BOOKS("Książki", 23),

    BAGS("Torby", 24),

    BLESSINGS("Błogosławieństwa", 25),

    UPGRADES("Ulepszenia", 26);

    companion object
    {
        operator fun get(id: Int): ItemCategory?
        {
            return ItemCategory.values().find { it.margoId == id }
        }
    }
}