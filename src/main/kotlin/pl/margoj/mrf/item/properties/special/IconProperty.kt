package pl.margoj.mrf.item.properties.special

import pl.margoj.mrf.item.properties.StringProperty

class IconProperty(id: Int, name: String) : StringProperty(id, name, default = "")
{
    init
    {
        this.showWhenRestricted = true
    }
}
