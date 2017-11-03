package pl.margoj.mrf.item.properties.special

import pl.margoj.mrf.item.properties.LongProperty

class PriceProperty(id: Int, name: String) : LongProperty(id = id, name = name, default = 0)
{
    init
    {
        this.showWhenRestricted = true
    }
}