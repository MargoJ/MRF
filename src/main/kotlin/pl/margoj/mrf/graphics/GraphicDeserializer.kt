package pl.margoj.mrf.graphics

import pl.margoj.mrf.MRFIcon
import pl.margoj.mrf.MRFIconFormat
import pl.margoj.mrf.serialization.MRFDeserializer
import java.io.DataInputStream
import java.io.IOException

class GraphicDeserializer : MRFDeserializer<GraphicResource>()
{
    override fun doDeserialize(input: DataInputStream): GraphicResource
    {
        var id = input.readUTF()

        var version = 0x00

        if (id.isEmpty()) // backward compatibility
        {
            version = input.readByte().toInt()
            id = input.readUTF()
        }

        val name = input.readUTF()
        val fileName = input.readUTF()
        val category = GraphicResource.GraphicCategory.findById(input.readByte())!!
        val format = MRFIconFormat.getById(input.readByte().toInt())
        val size = input.readInt()
        val buffer = ByteArray(size)
        val read = input.read(buffer)
        var catalog = ""

        if(version >= 0x01)
        {
            catalog = input.readUTF()
        }

        if (read != size)
        {
            throw IOException("icon loading failed, read = $read, size = $size")
        }

        return GraphicResource(id, fileName, MRFIcon(buffer, format!!, null), category, catalog)
    }
}