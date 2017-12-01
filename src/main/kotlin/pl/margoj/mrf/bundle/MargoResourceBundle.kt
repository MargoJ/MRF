package pl.margoj.mrf.bundle

import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView
import java.io.InputStream

interface MargoResourceBundle
{
    val resources: Collection<ResourceView>

    val remote: Boolean

    var touched: Boolean

    fun getResource(category: MargoResource.Category, id: String): ResourceView?

    fun loadResource(view: ResourceView): InputStream?

    fun saveResource(resource: MargoResource, stream: InputStream)

    fun deleteResource(view: ResourceView)

    fun saveBundle()

    fun getResourcesByCategory(category: MargoResource.Category) = this.resources.filter { it.category == category }
}