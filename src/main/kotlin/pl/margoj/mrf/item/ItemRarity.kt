package pl.margoj.mrf.item

enum class ItemRarity(val localizedName: String, val statType: String?)
{
    NORMAL("Normalny", null),

    UNIQUE("Unikat", "unique"),

    HEROIC("Heroiczny", "heroic"),

    LEGENDARY("Legendarny", "legendary"),

    UPGRADED("Ulepszony", "upgraded")
}