package pl.margoj.mrf.map.objects.gateway

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.objects.MapObject

data class GatewayObject(override var position: Point, var target: Point, var targetMap: String, val levelRestriction: LevelRestriction, val keyId: String?) : MapObject<GatewayObject>()
{
    override val dataType: Class<GatewayObjectData> = GatewayObjectData::class.java

    override fun copyObject(): GatewayObject
    {
        return this.copy()
    }

    data class LevelRestriction
    (
            var enabled: Boolean,
            var minLevel: Int,
            var maxLevel: Int
    )
}