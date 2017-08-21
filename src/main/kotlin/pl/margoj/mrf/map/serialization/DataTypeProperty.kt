package pl.margoj.mrf.map.serialization

interface DataTypeProperty<out T>
{
    val dataType: Class<out T>
}