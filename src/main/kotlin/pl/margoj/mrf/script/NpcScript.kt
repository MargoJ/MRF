package pl.margoj.mrf.script

import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class NpcScript(id: String) : MargoResource(id, "")
{
    override val category: Category = Category.SCRIPTS
    override val view: ResourceView = ResourceView(this.id, this.name, null, this.category, "$id.mjn")

    var content: String = ""
}