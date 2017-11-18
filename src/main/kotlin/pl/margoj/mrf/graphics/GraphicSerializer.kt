package pl.margoj.mrf.graphics

import pl.margoj.mrf.serialization.MRFSerializer
import java.io.DataOutputStream

class GraphicSerializer : MRFSerializer<GraphicResource>()
{
    override fun doSerialize(obj: GraphicResource, out: DataOutputStream)
    {
        out.writeUTF(obj.id)
        out.writeUTF(obj.name)
        out.writeUTF(obj.fileName)
        out.writeByte(obj.graphicCategory.id.toInt())
        out.writeByte(obj.icon.format.id)
        out.writeInt(obj.icon.image.size)
        out.write(obj.icon.image)
    }
}