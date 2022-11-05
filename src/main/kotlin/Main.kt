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

    println("${g.data}") // prints ~24.7041, the outcome of this forward pass
    g.backward()

    println("${a.grad}") // prints ~138.8338, i.e. the numerical value of dg/da
    println("${b.grad}") // prints ~645.5773, i.e. the numerical value of dg/db
}