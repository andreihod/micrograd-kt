data class MLP(val nin: Int, val nouts: List<Int>) : Module {
    private val sz: List<Int> = listOf(nin) + nouts
    private val layers: List<Layer> = List(nouts.size) { i -> Layer(sz[i], sz[i + 1], i != nouts.size - 1) }

    override fun call(x: List<Value>): List<Value> {
        var vector = x
        for (layer in layers) {
            vector = layer.call(vector)
        }

        return vector
    }

    override fun parameters() = layers.map { layer -> layer.parameters() }.flatten()

    override fun toString() = "MLP of $layers"
}