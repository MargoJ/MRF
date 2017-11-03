package pl.margoj.mrf.item.properties.special

import pl.margoj.mrf.item.properties.StringProperty

class NameProperty(id: Int, name: String) : StringProperty(id, name)
{
    init
    {
        this.showWhenRestricted = true
    }
}