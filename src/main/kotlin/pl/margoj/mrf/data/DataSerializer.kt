package pl.margoj.mrf.data

import com.google.gson.Gson
import pl.margoj.mrf.serialization.MRFSerializer
import java.io.DataOutputStream
import java.nio.charset.StandardCharsets

class DataSerializer<T : Any> : MRFSerializer<DataResource<T>>()
{
    companion object
    {
        val gson = Gson()
    }

    override fun doSerialize(obj: DataResource<T>, out: DataOutputStream)
    {
        out.write(gson.toJson(obj.content).toByteArray(StandardCharsets.UTF_8))
    }
}