package pl.margoj.mrf.item.serialization

import java.awt.image.BufferedImage

data class ItemIcon(
        val image: ByteArray,
        val format: ItemFormat,
        val cachedImage: BufferedImage?
)

enum class ItemFormat(val id: Int, val format: String, val extension: String)
{
    GIF(1, "gif", "gif"),
    PNG(2, "png", "png");

    companion object
    {
        fun getById(id: Int): ItemFormat?
        {
            return ItemFormat.values().find { it.id == id }
        }

        fun getByFormat(format: String): ItemFormat?
        {
            return ItemFormat.values().find { it.format == format }
        }
    }
}