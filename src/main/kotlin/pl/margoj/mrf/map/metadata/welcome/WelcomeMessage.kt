package pl.margoj.mrf.map.metadata.welcome

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.metadata.prototypes.StringMeta

class WelcomeMessage(override val value: String) : StringMeta()
{
    override val dataType: Class<out MapMetadataElementData<*>> = WelcomeMessageData::class.java
}