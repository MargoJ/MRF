package pl.margoj.mrf.map.tileset

import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO

data class TilesetFile(val file: InputStream, val name: String, val auto: Boolean)
{
    val image: BufferedImage = ImageIO.read(file)
}