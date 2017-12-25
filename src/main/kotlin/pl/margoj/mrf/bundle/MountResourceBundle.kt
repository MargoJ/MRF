package pl.margoj.mrf.bundle

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.Validate
import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView
import pl.margoj.mrf.serialization.SerializationException
import java.io.*

abstract class MountResourceBundle(val mount: File) : MargoResourceBundle
{
    override var touched: Boolean = false
    private var resources_: MutableList<ResourceView>? = null

    init
    {
        if (this.mount.exists())
        {
            Validate.isTrue(this.mount.isDirectory, "Mount point is a file")
        }
        else
        {
            Validate.isTrue(mount.mkdirs(), "Failed to create a mount point")
        }
    }

    override val resources: MutableList<ResourceView>
        get()
        {
            if (this.resources_ == null)
            {
                this.resources_ = ArrayList(this.fetchResources())
            }

            return this.resources_!!
        }

    protected open fun getResourcesFromIndex(input: InputStream): Collection<ResourceView>
    {
        val root = parser.parse(InputStreamReader(input)).asJsonObject
        val resources = ArrayList<ResourceView>(root.size())

        root.entrySet().forEach { (categoryName, entries) ->
            entries.asJsonObject.entrySet().forEach { (key, value) ->

                val json = value.asJsonObject

                resources.add(ResourceView(
                        id = json["id"].asString,
                        name = json["name"].asString,
                        meta = json["meta"]?.asJsonObject,
                        category = MargoResource.getCategoryById(categoryName)!!,
                        fileName = key
                ))
            }
        }

        return resources
    }

    open fun createIndex(views: Collection<ResourceView>): InputStream
    {
        val root = JsonObject()

        for (view in views)
        {
            var categoryElement = root[view.category.id]

            if (categoryElement == null || !categoryElement.isJsonObject)
            {
                categoryElement = JsonObject()
                root.add(view.category.id, categoryElement)
            }

            val json = JsonObject()
            json.addProperty("id", view.id)
            json.addProperty("name", view.name)
            if (view.meta != null)
            {
                json.add("meta", view.meta)
            }
            categoryElement.asJsonObject.add(view.fileName, json)
        }

        try
        {
            return IOUtils.toInputStream(root.toString(), "UTF-8")
        }
        catch (e: IOException)
        {
            throw SerializationException(e)
        }
    }

    open fun getLocalFile(view: ResourceView): File
    {
        val categoryDirectory = File(this.mount, view.category.id)

        if (!categoryDirectory.exists())
        {
            categoryDirectory.mkdirs()
        }

        return File(categoryDirectory, view.fileName)
    }

    protected open fun unpack(view: ResourceView): File?
    {
        val file = this.getLocalFile(view)

        this.provideInput(view).use { inputStream ->

            if (inputStream == null)
            {
                return null
            }

            FileOutputStream(file).use { IOUtils.copy(inputStream, it) }
        }

        return file
    }

    protected open fun loadLocally(view: ResourceView): InputStream?
    {
        val file = this.getLocalFile(view)
        return if (file.exists()) FileInputStream(file) else null
    }

    override fun getResource(category: MargoResource.Category, id: String): ResourceView?
    {
        return this.resources.stream().filter { it.id == id && it.category == category }.findAny().orElse(null)
    }

    override fun saveResource(resource: MargoResource, stream: InputStream)
    {
        val existing = this.getResource(resource.category, resource.id)
        if (existing != null)
        {
            this.deleteResource(existing)
        }

        FileOutputStream(this.getLocalFile(resource.view)).use { output ->
            IOUtils.copy(stream, output)
            this.resources.add(resource.view)
            this.updateIndex(this.resources)
        }
        this.touched = true
    }

    override fun deleteResource(view: ResourceView)
    {
        val file = this.getLocalFile(view)

        if (file.exists())
        {
            file.delete()
        }

        this.resources.remove(view)
        this.updateIndex(this.resources)
        this.touched = true
    }

    override fun loadAll(collection: Collection<ResourceView>): Map<ResourceView, InputStream?>
    {
        val out = HashMap<ResourceView, InputStream?>(collection.size)

        for (resourceView in collection)
        {
            out.put(resourceView, this.loadResource(resourceView))
        }

        return out
    }

    override fun loadResource(view: ResourceView): InputStream?
    {
        try
        {
            val local = this.loadLocally(view)
            if (local != null)
            {
                return local
            }

            return if (this.unpack(view) != null) this.loadLocally(view) else null
        }
        catch (e: IOException)
        {
            throw SerializationException(e)
        }
    }

    protected open fun updateIndex(resources: Collection<ResourceView>)
    {
        try
        {
            FileOutputStream(File(this.mount, "index.json")).use({ output ->
                this.createIndex(resources).use { input -> IOUtils.copy(input, output) }
            })
        }
        catch (e: IOException)
        {
            throw SerializationException(e)
        }
    }

    protected abstract fun fetchResources(): Collection<ResourceView>

    protected abstract fun provideInput(view: ResourceView): InputStream?

    companion object
    {
        private val gson = Gson()
        private val parser = JsonParser()
    }
}