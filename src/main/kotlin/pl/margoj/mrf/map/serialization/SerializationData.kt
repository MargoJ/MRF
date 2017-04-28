package pl.margoj.mrf.map.serialization

interface SerializationData<F>
{
    val objectId: Int

    fun encode(obj: F, context: MapSerializationContext)

    fun decode(context: MapSerializationContext): F
}