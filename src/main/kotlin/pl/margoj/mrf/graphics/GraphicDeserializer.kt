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
        val id = input.readUTF()
        val name = input.readUTF()
        val fileName = input.readUTF()
        val category = GraphicResource.GraphicCategory.findById(input.readByte())!!
        val format = MRFIconFormat.getById(input.readByte().toInt())
        val size = input.readInt()
        val buffer = ByteArray(size)
        val read = input.read(buffer)

        if (read != size)
        {
            throw IOException("icon loading failed, read = $read, size = $size")
        }

        return GraphicResource(id, fileName, MRFIcon(buffer, format!!, null), category)
    }
}