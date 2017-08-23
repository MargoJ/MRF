package pl.margoj.mrf.map.metadata.istown

import pl.margoj.mrf.map.metadata.prototypes.BooleanMetadata

class IsTownData : BooleanMetadata<IsTown>()
{
    override val objectId: Int = 6

    override fun create(boolean: Boolean): IsTown
    {
        return IsTown(boolean)
    }
}