package pl.margoj.mrf.npc.serialization

import pl.margoj.mrf.npc.NpcScript
import pl.margoj.mrf.serialization.MRFSerializer
import java.io.DataOutputStream
import java.nio.charset.StandardCharsets

class NpcScriptSerializer : MRFSerializer<NpcScript>()
{
    override fun doSerialize(obj: NpcScript, out: DataOutputStream)
    {
        val bytes = obj.content.toByteArray(StandardCharsets.UTF_8)
        out.write(bytes)
    }
}