package pl.margoj.mrf.item.properties

class PriceProperty(id: Int, name: String) : LongProperty(id = id, name = name, default = 0)
{
    init
    {
        this.showWhenRestricted = true
    }
}