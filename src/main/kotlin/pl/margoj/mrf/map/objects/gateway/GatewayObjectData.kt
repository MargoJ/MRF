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
        output.writeBoolean(obj.levelRestriction.enabled)

        if (obj.levelRestriction.enabled)
        {
            output.writeShort(obj.levelRestriction.minLevel)
            output.writeShort(obj.levelRestriction.maxLevel)
        }

        output.writeBoolean(obj.keyId != null) // key needed
        if (obj.keyId != null)
        {
            output.writeUTF(obj.keyId)
        }
    }

    override fun decode(context: MapSerializationContext): GatewayObject
    {
        val input = context.input!!
        val point = Point(input.readByte().toInt(), input.readByte().toInt())
        val targetMap = input.readUTF()

        // backward compatibility
        val levelRestriction: GatewayObject.LevelRestriction
        val keyId: String?

        if (context.map.version >= 0x02)
        {
            val levelRestrictionEnabled = input.readBoolean()

            levelRestriction = GatewayObject.LevelRestriction(
                    enabled = levelRestrictionEnabled,
                    minLevel = if (levelRestrictionEnabled) input.readShort().toInt() else 0,
                    maxLevel = if (levelRestrictionEnabled) input.readShort().toInt() else 0
            )

            if (input.readBoolean()) // key needed
            {
                keyId = input.readUTF()
            }
            else
            {
                keyId = null
            }
        }
        else
        {
            levelRestriction = GatewayObject.LevelRestriction(false, 0, 0)
            keyId = null
        }

        return GatewayObject(context.objectPoint, point, targetMap, levelRestriction, keyId)
    }
}
