package pl.margoj.mrf.map.fragment.auto

import pl.margoj.mrf.map.fragment.MapFragmentData
import pl.margoj.mrf.map.serialization.MapSerializationContext
import pl.margoj.mrf.map.tileset.AutoTileset

class AutoMapFragmentData: MapFragmentData<AutoMapFragment>
{
    override val fragmentId: Int = 2

    override fun encode(fragment: AutoMapFragment, context: MapSerializationContext)
    {
        context.output!!.writeShort(context.stringConstantPool!!.store(fragment.tilesetFile.name).toInt())
    }

    override fun decode(context: MapSerializationContext): AutoMapFragment
    {
        val input = context.input!!
        val tileset = context.getTileset(AutoTileset.AUTO) as AutoTileset
        val tilesetName = context.stringConstantPool!!.load(input.readShort())
        val tilesetFile = tileset.files.stream().filter({ tilesetName == it.name }).findAny().get()

        return AutoMapFragment(tileset, context.map, tilesetFile, context.currentTile, context.currentLayer)
    }
}