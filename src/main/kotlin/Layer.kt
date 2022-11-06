data class Layer(val nin: Int, val nout: Int, val nonLinear: Boolean = true): Module {
    private val neurons: List<Neuron> = List(nout) { Neuron(nin, nonLinear) }

    override fun call(x: List<Value>) = neurons.map { it.call(x) }.flatten()

    override fun parameters(): List<Value>  = neurons.map { n -> n.parameters() }.flatten()

    override fun toString() = "Layer of $neurons"
}
