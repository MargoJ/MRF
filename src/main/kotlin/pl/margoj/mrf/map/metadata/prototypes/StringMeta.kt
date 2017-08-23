package pl.margoj.mrf.map.metadata.prototypes

import pl.margoj.mrf.map.metadata.MetadataElement

abstract class StringMeta : MetadataElement()
{
    abstract val value: String
}