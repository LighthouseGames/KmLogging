import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.lighthousegames.logging.Platform
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
            +"setRelease"
            onClickFunction = { event ->
                log.i { "setRelease.onClick($event)" }
                Platform.setRelease(true)
                log.d { "not logged" }
            }
        }
        div {
            +"setDebug"
            onClickFunction = { event ->
                log.i { "setDebug.onClick($event)" }
                Platform.setRelease(false)
                log.d { "logging started" }
            }
        }
    }
}
