package pl.margoj.mrf.map.objects.text

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.objects.MapObject
import pl.margoj.mrf.map.objects.MapObjectData

data class TextMapObject(override var position: Point, var text: String) : MapObject<TextMapObject>()
{
    override val dataType: Class<out MapObjectData<*>> = TextMapObjectData::class.java

    override fun copyObject(): TextMapObject
    {
        return this.copy()
    }
}