package pl.margoj.mrf.item

enum class ItemCategory(val localizedName: String, val margoId: Int, val usable: Boolean = false)
{
    NONE("-Brak kategorii -", 0),

    ONE_HANDED_WEAPONS("Jednoręczne", 1, true),

    TWO_HANDED_WEAPONS("Dwuręczne", 2, true),

    HAND_AND_A_HALF_WEAPONS("Półtoraręczne", 3, true),

    RANGE_WEAPON("Dystansowe", 4, true),

    HELPERS("Pomocnicze", 5, true),

    WANDS("Różdżki", 6, true),

    STAFF("Laski", 7, true),

    ARMOR("Zbroje", 8, true),

    HELMET("Hełmy", 9, true),

    BOOTS("Buty", 10, true),

    GLOVES("Rękawice", 11, true),

    RINGS("Pierścienie", 12, true),

    NECKLACES("Naszyjniki", 13, true),

    SHIELDS("Tarcze", 14, true),

    NEUTRAL("Neutralne", 15),

    CONSUMABLE("Konsumpcyjne", 16, true),

    GOLD("Złoto", 17, true),

    KEYS("Klucze", 18),

    QUEST("Questowe", 19),

    RESPAWNABLE("Odnawialne", 20),

    ARROWS("Strzały", 21, true),

    TALISMANS("Talizmany", 22, true),

    BOOKS("Książki", 23, true),

    BAGS("Torby", 24),

    BLESSINGS("Błogosławieństwa", 25, true),

    UPGRADES("Ulepszenia", 26);

    companion object
    {
        operator fun get(id: Int): ItemCategory?
        {
            return ItemCategory.values().find { it.margoId == id }
        }
    }
}