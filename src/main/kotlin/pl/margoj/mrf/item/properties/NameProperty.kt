package pl.margoj.mrf.item.properties

class NameProperty(id: Int, name: String) : StringProperty(id, name)
{
    init
    {
        this.showWhenRestricted = true
    }
}