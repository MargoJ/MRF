package pl.margoj.mrf.script.serialization

import org.apache.commons.io.IOUtils
import pl.margoj.mrf.script.NpcScript
import pl.margoj.mrf.serialization.MRFDeserializer
import java.io.DataInputStream
import java.nio.charset.StandardCharsets

class NpcScriptDeserializer : MRFDeserializer<NpcScript>()
{
    lateinit var fileName: String

    override fun doDeserialize(input: DataInputStream): NpcScript
    {
        var id = fileName
        if (id.endsWith(".mjn"))
        {
            id = id.substring(0, id.length - 4)
        }

        val script = NpcScript(id)
        script.content = String(IOUtils.toByteArray(input), StandardCharsets.UTF_8)
        return script
    }
}