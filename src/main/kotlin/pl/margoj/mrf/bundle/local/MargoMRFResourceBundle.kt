package pl.margoj.mrf.bundle.local

import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.SerializationException
import pl.margoj.mrf.Paths
import pl.margoj.mrf.ResourceView
import pl.margoj.mrf.bundle.MountResourceBundle
import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.LinkedList
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class MargoMRFResourceBundle(val mrfFile: File, mount: File) : MountResourceBundle(mount), Closeable
{
    private var zip: ZipFile?

    init
    {
        try
        {
            this.zip = if (mrfFile.exists()) ZipFile(mrfFile) else null
        }
        catch(e: IOException)
        {
            throw SerializationException(e)
        }
    }

    override val remote: Boolean = false

    override fun saveBundle()
    {
        try
        {
            val filesToPack = HashMap<String, ByteArray>()
            val allFiles = ArrayList<String>()

            for (view in this.resources)
            {
                val path = "${view.category.id}/${view.fileName}"
                allFiles.add(path)

                val localFile = this.getLocalFile(view)

                if (localFile.exists())
                {
                    val entry = this.zip?.getEntry(path)
                    val bytes = Files.readAllBytes(localFile.toPath())

                    if (entry != null)
                    {
                        val checksum = CRC32()
                        checksum.update(bytes, 0, bytes.size)

                        if (checksum.value == entry.crc)
                        {
                            continue
                        }
                    }

                    filesToPack[path] = bytes
                }
            }

            val tempMrf = File(Paths.TEMP_DIR, System.currentTimeMillis().toString() + ".mrf")
            tempMrf.deleteOnExit()

            ZipOutputStream(FileOutputStream(tempMrf)).use {
                zipOut ->
                if (this.zip != null)
                {
                    // copy unchanged files from old to new
                    val iter = this.zip!!.entries()

                    while (iter.hasMoreElements())
                    {
                        val entry = iter.nextElement()

                        if ("index.json" == entry.name || !allFiles.contains(entry.name))
                        {
                            continue
                        }

                        val bytes = filesToPack[entry.name]

                        zipOut.putNextEntry(ZipEntry(entry.name))

                        if (bytes != null)
                        {
                            IOUtils.write(bytes, zipOut)
                            filesToPack.remove(entry.name)
                        }
                        else
                        {
                            IOUtils.copy(zip!!.getInputStream(entry), zipOut)
                        }

                        zipOut.closeEntry()
                    }
                }

                // put the new files
                filesToPack.forEach{
                    key, value ->
                    zipOut.putNextEntry(ZipEntry(key))
                    IOUtils.write(value, zipOut)
                    zipOut.closeEntry()
                }

                // put the index.json file
                zipOut.putNextEntry(ZipEntry("index.json"))
                IOUtils.copy(this.createIndex(this.resources), zipOut)
                zipOut.closeEntry()
            }

            this.zip?.close()

            Files.copy(tempMrf.toPath(), this.mrfFile.toPath(), StandardCopyOption.REPLACE_EXISTING)

            this.zip = ZipFile(this.mrfFile)
            this.touched = false
        }
        catch (e: IOException)
        {
            throw SerializationException(e)
        }
    }

    override fun fetchResources(): Collection<ResourceView>
    {
        if (this.zip == null)
        {
            return LinkedList()
        }

        val indexEntry = this.zip?.getEntry("index.json") ?: throw SerializationException("MRF file doesn't contain an index")

        try
        {
            return this.getResourcesFromIndex(zip!!.getInputStream(indexEntry))
        }
        catch(e: IOException)
        {
            throw SerializationException(e)
        }
    }

    override fun provideInput(view: ResourceView): InputStream?
    {
        val entry = this.zip?.getEntry("${view.category.id}/${view.fileName}")
        return if (entry == null) null else this.zip!!.getInputStream(entry)
    }

    override fun close()
    {
        this.zip?.close()
    }
}