package pl.margoj.mrf.map.metadata

import pl.margoj.mrf.map.serialization.DataTypeProperty

abstract class MetadataElement: DataTypeProperty<MapMetadataElementData<*>>
{
    override abstract val dataType: Class<out MapMetadataElementData<*>>
}