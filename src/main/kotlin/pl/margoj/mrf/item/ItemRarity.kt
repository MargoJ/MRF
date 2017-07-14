package pl.margoj.mrf.item

enum class ItemRarity(val localizedName: String, val statType: String?)
{
    NORMAL("Normalny", null),

    UPGRADED("Ulepszony", "upgraded"),

    UNIQUE("Unikat", "unique"),

    HEROIC("Heroiczny", "heroic"),

    LEGENDARY("Legendarny", "legendary"),

    ARTIFACT("Artefakt", "artefact")
}