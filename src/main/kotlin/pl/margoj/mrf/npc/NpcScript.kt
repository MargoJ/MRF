package pl.margoj.mrf.npc

import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class NpcScript(id: String) : MargoResource(id, "")
{
    override val category: Category = Category.NPC_SCRIPTS
    override val view: ResourceView = ResourceView(this.id, this.name, this.category, "$id.mjn")

    var content: String = ""
}