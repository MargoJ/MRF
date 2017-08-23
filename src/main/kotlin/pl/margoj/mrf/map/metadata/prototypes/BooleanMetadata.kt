package pl.margoj.mrf.map.metadata.prototypes

import pl.margoj.mrf.map.metadata.MapMetadataElementData
import pl.margoj.mrf.map.serialization.MapSerializationContext

abstract class BooleanMetadata<T : BooleanMeta> : MapMetadataElementData<T>
{
    override fun encode(obj: T, context: MapSerializationContext)
    {
        context.output!!.writeBoolean(obj.value)
    }

    override fun decode(context: MapSerializationContext): T
    {
        return create(context.input!!.readBoolean())
    }

    abstract fun create(boolean: Boolean): T

    @Suppress("LeakingThis")
    override val defaultValue: T = create(false)
}