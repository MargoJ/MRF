package pl.margoj.mrf.serialization

import java.io.*

open class SerializationContext : Closeable
{
    var input: DataInputStream? = null
        private set
    var output: DataOutputStream? = null
        private set
    private var baos: ByteArrayOutputStream? = null

    val bytes: ByteArray
        get() = this.baos!!.toByteArray()

    /**
     * write-only serializer
     */
    constructor()
    {
        this.baos = ByteArrayOutputStream()
        this.output = DataOutputStream(this.baos)
    }

    /**
     * read-only serializer
     */
    constructor(input: DataInputStream)
    {
        this.input = input
    }

    /**
     * read-only serializer
     */
    constructor(input: InputStream) : this(DataInputStream(input))

    /**
     * read-only serialzier
     */
    constructor(bytes: ByteArray) : this(ByteArrayInputStream(bytes))

    override fun close()
    {
        this.input?.close()
        this.output?.close()
    }
}