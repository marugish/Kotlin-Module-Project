fun main() {
    println("Начало программы!)")

    val menu = Menu()
    configureMenu(menu)
    while (true) {
        menu.displayMenu(menu.type)
        val userInput = menu.getUserInt()
        userInput.let { menu.performActionByInput(it, menu.elemType) }
    }

}