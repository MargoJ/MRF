package pl.margoj.mrf.map.fragment.standard

import pl.margoj.mrf.map.Point
import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.serialization.MapSerializationContext

class StandardMapFragmentData : MapFragmentData<StandardMapFragment>
{
    override val objectId: Int = 1

    override fun encode(obj: StandardMapFragment, context: MapSerializationContext)
    {
        val out = context.output!!

        out.writeShort(context.stringConstantPool!!.store(obj.tileset!!.name).toInt())
        out.writeByte(obj.tilesetPoint.x)
        out.writeByte(obj.tilesetPoint.y)
    }

    override fun decode(context: MapSerializationContext): StandardMapFragment
    {
        val input = context.input!!
        val tileset = context.getTileset(context.stringConstantPool!!.load(input.readShort()))
        val tilesetPoint = Point(input.readByte().toInt(), input.readByte().toInt())

        return StandardMapFragment(tileset, tilesetPoint, context.currentTile, context.currentLayer)
    }
}
