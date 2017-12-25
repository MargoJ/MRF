package pl.margoj.mrf.npc

import com.google.gson.JsonObject
import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class MargoNpc(val version: Byte, id: String, name: String) : MargoResource(id, name)
{
    override val category: Category = Category.NPC

    override val view: ResourceView get() = ResourceView(this.id, this.name, this.createMeta(), this.category, "$id.mjn")

    private fun createMeta(): JsonObject
    {
        val meta = JsonObject()

        meta.addProperty("l", this.level)
        meta.addProperty("p", this.profession.id)
        meta.addProperty("t", this.type.id)
        meta.addProperty("r", this.rank.id)

        return meta
    }

    var level: Int = 0

    var type: NpcType = NpcType.NPC

    var rank: NpcRank = NpcRank.NORMAL

    var gender: NpcGender = NpcGender.UNKNOWN

    var profession: NpcProfession = NpcProfession.WARRIOR

    var script: String = ""

    var graphics: String = ""

    var strength: Int = 0

    var agility: Int = 0

    var intellect: Int = 0

    var maxHp: Int = 0

    var attackSpeed: Int = 0

    var attack: IntRange = 0..0

    var armor: Int = 0

    var block: Int = 0

    var evade: Int = 0

    var spawnTime: Long = 0
}