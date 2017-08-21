package pl.margoj.mrf.serialization

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStream

abstract class MRFDeserializer<out T>
{
    protected abstract fun doDeserialize(input: DataInputStream): T

    fun deserialize(input: ByteArray): T
    {
        return this.deserialize(ByteArrayInputStream(input))
    }

    fun deserialize(input: InputStream): T
    {
        try
        {
            DataInputStream(input).use {
                return this.doDeserialize(it)
            }
        }
        catch (e: IOException)
        {
            throw SerializationException(e)
        }
    }

}