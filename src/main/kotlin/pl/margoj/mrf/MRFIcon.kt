package pl.margoj.mrf

import java.awt.image.BufferedImage

data class MRFIcon(
        val image: ByteArray,
        val format: MRFIconFormat,
        val cachedImage: BufferedImage?
)

enum class MRFIconFormat(val id: Int, val format: String, val extension: String)
{
    GIF(1, "gif", "gif"),
    PNG(2, "png", "png");

    companion object
    {
        fun getById(id: Int): MRFIconFormat?
        {
            return MRFIconFormat.values().find { it.id == id }
        }

        fun getByFormat(format: String): MRFIconFormat?
        {
            return MRFIconFormat.values().find { it.format == format }
        }
    }
}