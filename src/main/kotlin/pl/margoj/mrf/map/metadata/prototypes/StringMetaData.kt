package pl.margoj.mrf.map.metadata.prototypes

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.serialization.MapSerializationContext

abstract class StringMetaData<T : StringMeta> : MapMetadataElementData<T>
{
    override fun encode(obj: T, context: MapSerializationContext)
    {
        context.output!!.writeUTF(obj.value)
    }

    override fun decode(context: MapSerializationContext): T
    {
        return create(context.input!!.readUTF())
    }

    abstract fun create(string: String): T

    @Suppress("LeakingThis")
    override val defaultValue: T = create("")
}