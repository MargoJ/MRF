package pl.margoj.mrf.bundle.local

import org.apache.commons.lang3.SerializationException
import pl.margoj.mrf.ResourceView
import pl.margoj.mrf.bundle.MountResourceBundle
import pl.margoj.mrf.bundle.operation.BundleOperation
import pl.margoj.mrf.bundle.operation.DirectBundleOperation
import java.io.*

class MountedResourceBundle(mountPoint: File) : MountResourceBundle(mountPoint)
{
    override val remote: Boolean = false

    override val bundleOperation: BundleOperation = DirectBundleOperation(this)

    override fun saveBundle()
    {
    }

    override fun fetchResources(): Collection<ResourceView>
    {
        try
        {
            FileInputStream(File(this.mount, "index.json")).use {
                return super.getResourcesFromIndex(it)
            }
        }
        catch (e: IOException)
        {
            throw SerializationException(e)
        }
    }

    override fun provideInput(view: ResourceView): InputStream?
    {
        val file = this.getLocalFile(view)
        return if (!file.exists()) null else FileInputStream(file)
    }

    override fun unpack(view: ResourceView): File?
    {
        return this.getLocalFile(view)
    }

    init
    {
        val indexFile = File(this.mount, "index.json")
        if (!indexFile.exists())
        {
            try
            {
                FileOutputStream(indexFile).use { it.write("{}".toByteArray()) }
            }
            catch (e: IOException)
            {
                throw SerializationException(e)
            }
        }
    }


}