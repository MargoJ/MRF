package pl.margoj.mrf.map.metadata.welcome

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.metadata.MetadataElement

class WelcomeMessage(val value: String = "") : MetadataElement()
{
    override val dataType: Class<out MapMetadataElementData<*>> = WelcomeMessageData::class.java
}