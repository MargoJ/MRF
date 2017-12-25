package pl.margoj.mrf.bundle.operation

import pl.margoj.mrf.map.objects.gateway.GatewayObject
import pl.margoj.mrf.map.tileset.Tileset

interface BundleOperation
{
    fun loadTilesets(): Collection<Tileset>

    fun createMatchingGateway(currentMap: String, gateway: GatewayObject)

    fun deleteMatchingGateway(gateway: GatewayObject)
}