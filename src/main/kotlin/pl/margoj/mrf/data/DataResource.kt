package pl.margoj.mrf.data

import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class DataResource<T: Any>(id: String) : MargoResource(id, id)
{
    override val view: ResourceView = ResourceView(this.id, this.id, null, Category.DATA, this.id + ".json")

    override val category: Category = Category.DATA

    lateinit var content: T
}