import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.lighthousegames.logging.logging
import org.lighthousegames.sample.shared.Greeting
import org.w3c.dom.Node

val log = logging()

fun main() {
    log.i { "main()" }
    window.onload = { document.body?.sayHello() }
    log.i { "end main() " }
}

fun Node.sayHello() {
    append {
        div {
            +Greeting().greeting()
        }
        div {
            +"clickable"
            onClickFunction = { event ->
                log.i { "clickable.onClick($event)" }
                log.d("sayHello") { "onClick($event)" }
            }
        }
        div {
            +"verbose"
            onClickFunction = { event ->
                log.v { "verbose.onClick($event)" }
            }
        }
        div {
            +"warn"
            onClickFunction = { event ->
                log.w { "warn.onClick($event)" }
            }
        }
    }
}
