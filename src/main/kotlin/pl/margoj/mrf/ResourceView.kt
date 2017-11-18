package pl.margoj.mrf

import com.google.gson.JsonObject

class ResourceView(id: String, name: String, var meta: JsonObject?, override val category: Category, val fileName: String) : MargoResource(id, name)
{
    override val view: ResourceView
        get() = this

    override fun equals(other: Any?): Boolean
    {
        if (other !is ResourceView)
        {
            return false
        }
        if (other === this)
        {
            return true
        }
        return other.id == this.id && other.category == this.category
    }

    override fun hashCode(): Int
    {
        return id.hashCode()
    }
}