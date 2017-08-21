package pl.margoj.mrf.bundle.remote

import pl.margoj.mrf.ResourceView
import pl.margoj.mrf.bundle.MountResourceBundle
import java.io.File
import java.io.InputStream

class MargoRemoteResourceBundle(mountPoint: File) : MountResourceBundle(mountPoint)
{
    override val remote: Boolean = true

    override fun saveBundle()
    {
        TODO("not implemented")
    }

    override fun fetchResources(): Collection<ResourceView>
    {
        TODO("not implemented")
    }

    override fun provideInput(view: ResourceView): InputStream?
    {
        TODO("not implemented")
    }
}