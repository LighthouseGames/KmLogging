import SwiftUI
import shared

let log = KmLog(tag: "ContentView")

func greet() -> String {
    log.debug { "greet()" }
    return Greeting().greeting()
}

struct ContentView: View {
    var body: some View {
        Text(greet())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
