package pl.margoj.mrf.map.metadata.welcome

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class WelcomeMessageData : MapMetadataElementData<WelcomeMessage>
{
    override val objectId: Int = 2

    override fun encode(obj: WelcomeMessage, context: MapSerializationContext)
    {
        context.output!!.writeUTF(obj.value)
    }

    override fun decode(context: MapSerializationContext): WelcomeMessage
    {
        return WelcomeMessage(context.input!!.readUTF())
    }

    override val defaultValue: WelcomeMessage = WelcomeMessage()
}