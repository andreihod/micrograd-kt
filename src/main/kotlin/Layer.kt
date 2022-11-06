
data class Layer(val nin: Int, val nout: Int, val nonLinear: Boolean = true): Module {
    private val neurons: List<Neuron> = List(nout) { Neuron(nin, nonLinear) }

    override fun call(x: List<Value>): Value {
        val out = neurons.map { it.call(x) }
        return if (out.size == 1) out[0] else out
    }

    override fun parameters(): List<Value>  = neurons.map { n -> n.parameters() }.flatten()
}
