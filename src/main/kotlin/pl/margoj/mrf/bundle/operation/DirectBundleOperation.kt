package pl.margoj.mrf.bundle.operation

import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.bundle.MargoResourceBundle
import pl.margoj.mrf.map.MargoMap
import pl.margoj.mrf.map.objects.gateway.GatewayObject
import pl.margoj.mrf.map.serialization.MapDeserializer
import pl.margoj.mrf.map.serialization.MapSerializer
import pl.margoj.mrf.map.tileset.AutoTileset
import pl.margoj.mrf.map.tileset.Tileset
import pl.margoj.mrf.map.tileset.TilesetFile
import java.io.ByteArrayInputStream
import java.util.*

open class DirectBundleOperation(val bundle: MargoResourceBundle) : BundleOperation
{
    override fun loadTilesets(): Collection<Tileset>
    {
        val resources = this.bundle.getResourcesByCategory(MargoResource.Category.TILESETS)
        val tilesetFiles = ArrayList<TilesetFile>(resources.size)
        val tilesets = ArrayList<Tileset>(resources.size)

        val tilesetsInputs = this.bundle.loadAll(resources)

        resources.forEach { resource ->
            tilesetFiles.add(TilesetFile(tilesetsInputs[resource]!!, resource.id, resource.id.startsWith("auto-")))
        }

        val autoTileset = AutoTileset(AutoTileset.AUTO, tilesetFiles.filter(TilesetFile::auto))
        tilesets.add(autoTileset)
        tilesets.addAll(tilesetFiles.filter { !it.auto }.map { Tileset(it.name, it.image, Collections.singletonList(it)) })
        return tilesets
    }

    override fun createMatchingGateway(currentMap: String, gateway: GatewayObject)
    {
        this.operateOnMap(gateway.targetMap) { map ->
            val matchingGateway = GatewayObject(gateway.target, gateway.position, currentMap, GatewayObject.LevelRestriction(false, 0, 0), null)
            map.addObject(matchingGateway)
        }
    }

    override fun deleteMatchingGateway(gateway: GatewayObject)
    {
        this.operateOnMap(gateway.targetMap) { map ->
            if (map.getObject(gateway.position) is GatewayObject)
            {
                map.deleteObject(gateway.position)
            }
        }
    }

    private fun operateOnMap(id: String, callback: (MargoMap) -> Unit)
    {
        val resource = this.bundle.getResource(MargoResource.Category.MAPS, id) ?: return
        val input = this.bundle.loadResource(resource) ?: return

        val deserializer = MapDeserializer(this.loadTilesets())
        val serializer = MapSerializer()

        val map = deserializer.deserialize(input)

        callback(map)

        val output = serializer.serialize(map)
        this.bundle.saveResource(resource, ByteArrayInputStream(output))
    }
}