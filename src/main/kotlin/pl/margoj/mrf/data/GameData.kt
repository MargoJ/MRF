package pl.margoj.mrf.data

data class GameData
(
    var spawns: MutableMap<Char, String> = hashMapOf(),

    var defaultBag: String = ""
)