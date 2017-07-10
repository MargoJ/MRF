package pl.margoj.mrf.item

import pl.margoj.mrf.serialization.MRFSerializer
import java.io.DataOutputStream

class ItemSerializer : MRFSerializer<MargoItem>()
{
    override fun doSerialize(obj: MargoItem, out: DataOutputStream)
    {

    }
}