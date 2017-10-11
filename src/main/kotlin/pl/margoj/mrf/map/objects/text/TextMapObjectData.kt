package pl.margoj.mrf.map.objects.text

import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class TextMapObjectData : MapObjectData<TextMapObject>
{
    override val objectId: Int = 4

    override fun encode(obj: TextMapObject, context: MapSerializationContext)
    {
        context.output!!.writeUTF(obj.text)
    }

    override fun decode(context: MapSerializationContext): TextMapObject
    {
        return TextMapObject(context.objectPoint, context.input!!.readUTF())
    }

}