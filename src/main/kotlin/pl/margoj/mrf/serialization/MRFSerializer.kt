package pl.margoj.mrf.serialization

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

abstract class MRFSerializer<in T>
{
    private val baos = ByteArrayOutputStream()

    protected abstract fun doSerialize(obj: T, out: DataOutputStream)

    fun serialize(obj: T): ByteArray
    {
        try
        {
            this.baos.reset()

            DataOutputStream(this.baos).use { this.doSerialize(obj, it) }

            return this.baos.toByteArray()
        }
        catch (e: IOException)
        {
            throw SerializationException(e)
        }
    }

}