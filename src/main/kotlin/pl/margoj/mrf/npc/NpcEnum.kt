package pl.margoj.mrf.npc

interface NpcEnum
{
    val id: Byte

    companion object
    {
        fun <T : NpcEnum> byId(array: Array<T>, id: Byte): T? = array.find { it.id == id }
    }
}

enum class NpcType(override val id: Byte) : NpcEnum
{
    NPC(0),
    MONSTER(1)
}

enum class NpcRank(override val id: Byte) : NpcEnum
{
    NORMAL(0),
    ELITE(1),
    ELITE_II(2),
    ELITE_III(3),
    HERO(4),
    TITAN(5)
}

enum class NpcGender(override val id: Byte) : NpcEnum
{
    UNKNOWN(0),
    MALE(1),
    FEMALE(2)
}

enum class NpcProfession(override val id: Byte) : NpcEnum
{
    WARRIOR(0),
    PALADIN(1),
    MAGE(2),
    HUNTER(3),
    TRACKER(4),
    BLADE_DANCER(5)
}