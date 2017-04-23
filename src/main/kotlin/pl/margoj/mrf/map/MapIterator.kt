package pl.margoj.mrf.map

import org.apache.commons.lang3.Validate
import pl.margoj.mrf.map.fragment.MapFragment

class MapIterator internal constructor(private val fragments: Array<Array<Array<MapFragment>>>) : Iterator<Array<MapFragment>>
{
    private var x = 0
    private var y = 0
    private var hasNext = false

    init
    {
        this.hasNext = fragments.isNotEmpty() && fragments[0].isNotEmpty()
    }

    override fun hasNext(): Boolean = this.hasNext

    override fun next(): Array<MapFragment>
    {
        Validate.isTrue(this.hasNext, "There's no more elements")

        val current = fragments[x][y]

        if (++x >= fragments.size)
        {
            x = 0
            if (++y >= fragments[x].size)
            {
                hasNext = false
            }
        }

        return current
    }
}