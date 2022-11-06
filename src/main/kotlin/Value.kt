import kotlin.math.pow

data class Value(
    val data: Double,
    private val children: List<Value>? = null,
    private val op: String? = null
) {
    var grad: Double = 0.0
    private var _backward: () -> Unit = {}

    operator fun plus(other: Value): Value {
        val out = Value(data + other.data, listOf(this, other), "+")

        out._backward = {
            grad += out.grad
            other.grad += out.grad
        }

        return out
    }

    operator fun times(other: Value): Value {
        val out = Value(data * other.data, listOf(this, other), "*")

        out._backward = {
            grad += other.data * out.grad
            other.grad += data * out.grad
        }

        return out
    }

    private fun pow(other: Double): Value {
        val out = Value(data.pow(other), listOf(this), "**${other}")
        out._backward = { grad += (other * this.data.pow(other - 1)) * out.grad }

        return out
    }

    fun relu(): Value {
        val out = Value(data.coerceAtLeast(0.0), listOf(this), "ReLU")
        out._backward = { if (out.data > 0.0) grad += out.grad }

        return out
    }

    fun backward() {
        val topo = mutableListOf<Value>()
        val visited = mutableListOf<Value>()

        fun buildTopo(value: Value) {
            if (visited.none { it === value }) {
                visited.add(value)

                value.children?.let {
                    for (child in it) {
                        buildTopo(child)
                    }
                }

                topo.add(value)
            }
        }

        buildTopo(this)
        grad = 1.0
        topo.reversed().map { it._backward() }
    }

    fun pow(other: Int) = pow(other.toDouble())
    operator fun plus(other: Double) = this + Value(other)
    operator fun plus(other: Int) = this + other.toDouble()
    operator fun times(other: Double) = this * Value(other)
    operator fun times(other: Int) = this * Value(other.toDouble())
    operator fun minus(other: Value) = this + (-other)
    operator fun minus(other: Double) = this - Value(other)
    operator fun minus(other: Int) = this - other.toDouble()
    operator fun div(other: Value) = this * other.pow(-1.0)
    operator fun div(other: Double) = this / Value(other)
    operator fun div(other: Int) = this / other.toDouble()
    operator fun unaryMinus() = this * -1.0
    operator fun inc() = this + 1.0
    operator fun dec() = this - 1.0
}

operator fun Double.plus(other: Value) = Value(this) + other
operator fun Double.times(other: Value) = Value(this) * other
operator fun Double.minus(other: Value) = Value(this) - other
operator fun Double.div(other: Value) = Value(this) / other

operator fun Int.plus(other: Value) = this.toDouble() + other
operator fun Int.times(other: Value) = this.toDouble() * other
operator fun Int.minus(other: Value) = this.toDouble() - other
operator fun Int.div(other: Value) = this.toDouble() / other

fun List<Value>.sum() = reduce { acc, value -> acc + value }