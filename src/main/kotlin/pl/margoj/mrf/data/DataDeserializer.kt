package pl.margoj.mrf.data

import org.apache.commons.io.IOUtils
import pl.margoj.mrf.serialization.MRFDeserializer
import java.io.DataInputStream
import java.nio.charset.StandardCharsets

class DataDeserializer<T : Any>(val id: String, val type: Class<T>) : MRFDeserializer<DataResource<T>>()
{
    override fun doDeserialize(input: DataInputStream): DataResource<T>
    {
        val resource = DataResource<T>(this.id)
        resource.content = DataSerializer.gson.fromJson(String(IOUtils.toByteArray(input), StandardCharsets.UTF_8), this.type)
        return resource
    }
}