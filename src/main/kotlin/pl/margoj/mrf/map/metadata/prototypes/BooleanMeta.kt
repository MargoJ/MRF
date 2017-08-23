package pl.margoj.mrf.map.metadata.prototypes

import pl.margoj.mrf.map.metadata.MetadataElement

abstract class BooleanMeta : MetadataElement()
{
    abstract val value: Boolean
}