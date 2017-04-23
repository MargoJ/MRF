package pl.margoj.mrf

import org.apache.commons.lang3.Validate
import java.util.regex.Pattern

abstract class MargoResource(id: String, open var name: String)
{
    var id: String = "notset"
        set(value)
        {
            Validate.isTrue(ID_PATTERN.matcher(value).matches(), "ID format is invalid")
            field = value
        }

    init
    {
        this.id = id
    }

    abstract val category: Category
    abstract val view: ResourceView

    val resourceReadableName: String get() = "${category.id}/$id"

    enum class Category(val readableName: String, val id: String)
    {
        MAPS("Mapy", "maps"),
        ITEMS("Przedmioty", "items");
    }

    companion object
    {
        val ID_PATTERN = Pattern.compile("[A-Za-z0-9_]{1,127}")

        fun getCategoryById(id: String): Category?
        {
            return Category.values().firstOrNull { it.id == id }
        }
    }
}