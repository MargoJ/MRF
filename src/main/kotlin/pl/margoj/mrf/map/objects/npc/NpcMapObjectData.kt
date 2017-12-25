package pl.margoj.mrf.map.objects.npc

import pl.margoj.mrf.map.objects.MapObjectData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class NpcMapObjectData : MapObjectData<NpcMapObject>
{
    override val objectId: Int = 3

    override fun encode(obj: NpcMapObject, context: MapSerializationContext)
    {
        val output = context.output!!

        output.writeUTF(obj.id)
        output.writeByte(obj.group.toInt())
    }

    override fun decode(context: MapSerializationContext): NpcMapObject
    {
        val input = context.input!!
        val mapObject = NpcMapObject(context.objectPoint)

        mapObject.id = input.readUTF()
        mapObject.group = input.readByte()

        return mapObject
    }
}