import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.lighthousegames.logging.logging
import org.w3c.dom.Node

val log = logging()

fun main() {
    log.i { "main()" }
    window.onload = { document.body?.sayHello() }
}

fun Node.sayHello() {
    append {
        div {
            +"Hello from JS"
        }
        div {
            +"clickable"
            onClickFunction = { event ->
                log.i { "onClick($event)" }
                log.d("sayHello") { "onClick($event)" }
                try {
                    testFun()
                } catch (err: Error) {
                    log.e(err) { "testFun" }
                }
            }
        }
    }
}

fun foo() {
    throw Error("foo")
}

fun testFun() {
    foo()
}
