package pl.margoj.mrf.map.objects

import pl.margoj.mrf.map.serialization.SerializationData

interface MapObjectData<F : MapObject<*>> : SerializationData<F>
