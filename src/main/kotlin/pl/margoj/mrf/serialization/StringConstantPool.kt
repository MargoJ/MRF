package pl.margoj.mrf.serialization

class StringConstantPool : ConstantPool<String>()
{
    override fun serialize(serializationContext: SerializationContext)
    {
        val out = serializationContext.output!!

        out.writeShort(this.objects.size)

        this.objects.forEach { key, value ->
            out.writeShort(key.toInt())
            out.writeUTF(value)
        }
    }

    override fun deserialize(serializationContext: SerializationContext)
    {
        this.clear()
        val input = serializationContext.input!!

        val length = input.readShort()

        for(i in 1..length)
        {
            val index = input.readShort()

            if(this.objects.put(index, input.readUTF()) != null)
            {
                throw SerializationException("Invalid constant pool format; index=$index has more than one mapping")
            }
        }
    }

}