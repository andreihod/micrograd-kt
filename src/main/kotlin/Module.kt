interface Module {
    fun call(x: List<Value>): Value
    fun zeroGrad() = parameters().map { it.grad = 0.0 }
    fun parameters(): List<Value>
}