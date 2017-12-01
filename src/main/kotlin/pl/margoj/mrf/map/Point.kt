package pl.margoj.mrf.map

data class Point(val x: Int, val y: Int)
{
    fun getRelative(relativeX: Int, relativeY: Int): Point
    {
        return Point(this.x + relativeX, this.y + relativeY)
    }

    fun getNeighborhood(diagonals: Boolean = false): List<Point>
    {
        val out = ArrayList<Point>(if (diagonals) 8 else 4)

        out.add(getRelative(0, -1))
        out.add(getRelative(0, 1))
        out.add(getRelative(-1, 0))
        out.add(getRelative(1, 0))

        if (diagonals)
        {
            out.add(getRelative(-1, -1))
            out.add(getRelative(-1, 1))
            out.add(getRelative(1, -1))
            out.add(getRelative(1, 1))
        }

        return out
    }

    operator fun plus(point: Point): Point
    {
        return this.getRelative(point.x, point.y)
    }

    operator fun minus(point: Point): Point
    {
        return this.getRelative(-point.x, -point.y)
    }

    operator fun div(number: Int): Point
    {
        return Point(this.x / number, this.y / number)
    }

    operator fun times(number: Int): Point
    {
        return Point(this.x * number, this.y * number)
    }
}