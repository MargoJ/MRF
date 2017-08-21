package pl.margoj.mrf.serialization

abstract class ConstantPool<T>
{
    private var index: Short = 0
    protected var objects: MutableMap<Short, T> = hashMapOf()

    fun store(obj: T): Short
    {
        for((key, value) in objects)
        {
            if(value == obj)
            {
                return key
            }
        }

        this.objects.put(this.index, obj)
        return this.index++
    }

    fun has(index: Short): Boolean
    {
        return this.objects.containsKey(index)
    }

    fun load(index: Short): T?
    {
        return this.objects[index]
    }

    fun clear()
    {
        this.objects.clear()
        this.index = 0
    }

    abstract fun serialize(serializationContext: SerializationContext)

    abstract fun deserialize(serializationContext: SerializationContext)
}