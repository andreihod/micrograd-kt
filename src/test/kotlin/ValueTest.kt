import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ValueTest {

    @Test
    fun backwardTestA() {
        val x = Value(-4.0)
        val z = 2 * x + 2 + x
        val q = z.relu() + z * x
        val h = (z * z).relu()
        val y = h + q + q * x
        y.backward()

        assertEquals(-20.0, y.data)
        assertEquals(46.0, x.grad)
    }

    @Test
    fun backwardTestB() {
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
        g.backward()

        val tol = 1e-4
        assertEquals(24.7041, g.data, absoluteTolerance=tol)
        assertEquals(138.8338, a.grad, absoluteTolerance=tol)
        assertEquals(645.5773, b.grad, absoluteTolerance=tol)
    }
}