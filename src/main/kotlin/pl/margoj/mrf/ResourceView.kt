package pl.margoj.mrf

class ResourceView(id: String, name: String, override val category: Category, val fileName: String) : MargoResource(id, name)
{
    override val view: ResourceView
        get() = this

    override fun equals(other: Any?): Boolean
    {
        return (other is ResourceView) && (other == this || other.id == this.id)
    }

    override fun hashCode(): Int
    {
        return id.hashCode()
    }
}