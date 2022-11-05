fun main() {
    val a = Value(-4.0)
    val b = Value(2.0)
    var c = a + b
    var d = a * b + b.pow(3)
    c += c + 1
    c += 1 + c + (-a)
    d += d * 2 + (b + a).relu()
    d += 3 * d + (b - a).relu()
    val e = c - d
    val f = e.pow(2)
    var g = f / 2.0
    g += 10.0 / f
    println("${g.data}")
    g.backward()
    println("${a.grad}")
    println("${b.grad}")
}