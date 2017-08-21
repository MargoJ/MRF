package pl.margoj.mrf.map.metadata

import pl.margoj.mrf.map.serialization.SerializationData

interface MapMetadataElementData<T : MetadataElement> : SerializationData<T>
{
    val defaultValue: T
}