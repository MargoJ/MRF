package pl.margoj.mrf.map.objects.gateway

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class GatewayObjectData : MapObjectData<GatewayObject>
{
    override val objectId: Int = 1

    override fun encode(obj: GatewayObject, context: MapSerializationContext)
    {
        val output = context.output!!
        output.writeByte(obj.target.x)
        output.writeByte(obj.target.y)
        output.writeUTF(obj.targetMap)
    }

    override fun decode(context: MapSerializationContext): GatewayObject
    {
        val input = context.input!!
        return GatewayObject(context.objectPoint, Point(input.readByte().toInt(), input.readByte().toInt()), input.readUTF())
    }
}
