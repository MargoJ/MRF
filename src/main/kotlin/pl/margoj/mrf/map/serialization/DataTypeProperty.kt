package pl.margoj.mrf.map.serialization

interface DataTypeProperty<T>
{
    val dataType: Class<out T>
}