package pl.margoj.mrf.graphics

import pl.margoj.mrf.MRFIcon
import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class GraphicResource(id: String, val icon: MRFIcon) : MargoResource(id, "$id.${icon.format.extension}")
{
    override val category: Category get() = Category.GRAPHIC

    override val view: ResourceView get() = ResourceView(this.id, this.name, this.category, "$id.mjg")
}