package pl.margoj.mrf.item

import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class MargoItem(id: String, name: String) : MargoResource(id, name)
{
    override val category: Category get() = Category.ITEMS
    override val view: ResourceView get() = ResourceView(this.id, this.name, this.category, "$id.mji")
}