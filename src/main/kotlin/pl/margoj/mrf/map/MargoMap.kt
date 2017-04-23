package pl.margoj.mrf.map

import org.apache.commons.lang3.Validate
import pl.margoj.mrf.MargoResource
import pl.margoj.mrf.ResourceView
import pl.margoj.mrf.map.fragment.MapFragment
import pl.margoj.mrf.map.fragment.empty.EmptyMapFragment

class MargoMap(id: String, name: String, width: Int, height: Int) : MargoResource(id, name), Iterable<Array<MapFragment>>
{
    lateinit var fragments: Array<Array<Array<MapFragment>>>
        private set
    lateinit var collisions: Array<BooleanArray>
        private set

    override val category: Category get() = Category.MAPS

    override val view: ResourceView get() = ResourceView(this.id, this.name, this.category, "$id.mjm")

    override var name: String
        get() = super.name
        set(value)
        {
            Validate.isTrue(value.length <= 127, "Map name must not exceed 127 characters")
            super.name = value
        }

    var width: Int = 1
        set(value)
        {
            Validate.isTrue(value in 1..128, "Width must be between 1 and 128")
            field = value
        }

    var height: Int = 1
        set(value)
        {
            Validate.isTrue(value in 1..128, "Width must be between 1 and 128")
            field = value
        }

    init
    {
        this.width = width
        this.height = height
        this.resize(width, height)
    }

    fun resize(width: Int, height: Int)
    {
        this.width = width
        this.height = height

        // trust me i'm an engineer
        var initialized: Boolean
        try
        {
            this.fragments
            initialized = true
        }
        catch(e: UninitializedPropertyAccessException)
        {
            initialized = false
        }

        if (!initialized)
        {
            this.collisions = Array(this.width, { kotlin.BooleanArray(this.height) })
            this.fragments = Array(this.width, {
                x ->
                Array(this.height, {
                    y ->
                    Array(LAYERS, {
                        layer ->
                        @Suppress("USELESS_CAST")
                        EmptyMapFragment(Point(x, y), layer) as MapFragment
                    })
                })
            })
            return
        }

        val newCollisions: Array<BooleanArray> = Array(this.width, {
            x ->
            BooleanArray(this.height, {
                y ->
                if (x < collisions.size && y < collisions[x].size) collisions[x][y] else false
            })
        })

        val newFragments: Array<Array<Array<MapFragment>>> = Array(this.width, {
            x ->
            Array(this.height, {
                y ->
                Array(LAYERS, {
                    layer ->
                    if (x < fragments.size && y < fragments[x].size) this.fragments[x][y][layer] else EmptyMapFragment(Point(x, y), layer)
                })
            })
        })

        this.collisions = newCollisions
        this.fragments = newFragments
    }

    fun inBounds(point: Point): Boolean
    {
        return point.x >= 0 && point.y >= 0 && point.x < this.fragments.size && point.y < this.fragments[point.x].size
    }

    fun getCollisionAt(point: Point): Boolean
    {
        return this.inBounds(point) && this.collisions[point.x][point.y]
    }

    fun setCollisionAt(point: Point, collision: Boolean): Boolean
    {
        if (!this.inBounds(point))
        {
            return false
        }
        this.collisions[point.x][point.y] = collision
        return true
    }

    fun setFragment(fragment: MapFragment): Boolean
    {
        if(!this.inBounds(fragment.point))
        {
            return false
        }
        this.fragments[fragment.point.x][fragment.point.y][fragment.layer] = fragment
        return true
    }

    fun getFragment(point: Point, layer: Int): MapFragment?
    {
        if(!this.inBounds(point) || layer < 0 || layer >= LAYERS)
        {
            return null
        }
        return this.fragments[point.x][point.y][layer]
    }

    override fun iterator(): Iterator<Array<MapFragment>>
    {
        return MapIterator(this.fragments)
    }

    companion object
    {
        const val LAYERS = 10
        const val COLLISION_LAYER = LAYERS
        const val OBJECT_LAYER = LAYERS + 1
    }
}