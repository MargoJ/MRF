package pl.margoj.mrf.map.metadata.welcome

import pl.margoj.mrf.map.metadata.prototypes.StringMetaData

class WelcomeMessageData : StringMetaData<WelcomeMessage>()
{
    override val objectId: Int = 2

    override fun create(string: String): WelcomeMessage
    {
        return WelcomeMessage(string)
    }
}