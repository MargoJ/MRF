package pl.margoj.mrf.map.metadata.ismain

import pl.margoj.mrf.map.metadata.prototypes.BooleanMetadata

class IsMainData : BooleanMetadata<IsMain>()
{
    override val objectId: Int = 5

    override fun create(boolean: Boolean): IsMain
    {
        return IsMain(boolean)
    }
}