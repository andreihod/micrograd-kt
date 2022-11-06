import kotlin.random.Random

data class Neuron(val nin: Int, private val nonLinear: Boolean = true) : Module {
    private val w: List<Value> = List(nin) { Value(Random.nextDouble(-1.0, 1.0)) }
    private val b = Value(0.0)

    override fun call(x: List<Value>): List<Value> {
        val act = w.zip(x).map { (wi, xi) -> wi * xi }.sum() + b
        return listOf(if (nonLinear) act.relu() else act)
    }

    override fun parameters() = w + b

    override fun toString() = (if (nonLinear) "ReLU" else "Linear") + "Neuron(${w.size})"
}