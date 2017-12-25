package pl.margoj.mrf.graphics

import com.google.gson.JsonObject
import pl.margoj.mrf.MRFIcon
import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView

class GraphicResource(id: String, val fileName: String, val icon: MRFIcon, val graphicCategory: GraphicCategory, var catalog: String? = null) : MargoResource(id, fileName)
{
    override val category: Category get() = Category.GRAPHIC

    override val view: ResourceView get() = ResourceView(this.id, this.name, this.createMeta(), this.category, "$id.mjg")

    private fun createMeta(): JsonObject
    {
        val out = JsonObject()
        out.addProperty("cat", this.graphicCategory.id)
        if (this.catalog != null)
        {
            out.addProperty("catalog", this.catalog)
        }
        return out
    }

    enum class GraphicCategory(val id: Byte, val displayName: String)
    {
        ITEM(0, "przedmioty"),
        NPC(1, "npc");

        companion object
        {
            fun findById(id: Byte) = GraphicCategory.values().firstOrNull { it.id == id }
        }
    }
}