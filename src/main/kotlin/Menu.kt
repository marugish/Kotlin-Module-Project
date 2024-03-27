import java.util.Scanner
import kotlin.system.exitProcess

class Menu {
    data class MenuItem(val name: Int, val type: ElementType, val action: () -> Unit)

    var elemType = ElementType.Archive
    var type = ElementType.Archive.getLocalizedName()
    val setOfArchive = mutableSetOf<Archive>()
    var archiveIndex = -1
    var noteIndex = -1

    private val menuItems: MutableList<MenuItem> = mutableListOf()

    fun addItem(name: Int, type: ElementType, action: () -> Unit) {
        menuItems.add(MenuItem(name, type, action))
    }

    fun displayMenu(type: String) {
        println("Выберите действие:")
        if (type != ElementType.NoteText.getLocalizedName()) {
            println("0. Выход в предыдущий пункт меню")
            println("1. Вывод списка $type")
            println("2. Добавить $type")
            println("3. Выбрать $type из списка")
        }
        else {
            println("0. Выход в предыдущий пункт меню")
            println("1. Вывод $type")
        }
    }

    fun getUserInt(): Int? {
        return Scanner(System.`in`).nextLine().toIntOrNull()
    }

    fun getUserNameOfElement(): String {
        return Scanner(System.`in`).nextLine().trim()
    }

    fun getUserTextOfNote(): String {
        println("Введите текст заметки:")
        return Scanner(System.`in`).nextLine() ?: ""
    }

    fun performActionByInput(n: Int?, elemType: ElementType) {
        val menuItem = menuItems.find { it.name == n && it.type == elemType }
        menuItem?.action?.invoke() ?: println("Некорректная команда. Попробуйте ещё раз...")
    }

    fun uniqueArchive(name:String): Boolean {
        return setOfArchive.any { it.name == name }
    }

    fun uniqueNote(name:String): Boolean {
        val foundArchive = setOfArchive.elementAt(archiveIndex)
        return foundArchive.notes.any { it.name == name }
    }

}

fun configureMenu(menu: Menu) {
    menu.addItem(0, ElementType.Archive) {
        println("Выход...")
        exitProcess(0)
    }

    menu.addItem(0, ElementType.Note) {
        menu.elemType = ElementType.Archive
        menu.type = ElementType.Archive.getLocalizedName()
    }

    menu.addItem(0, ElementType.NoteText) {
        menu.elemType = ElementType.Note
        menu.type = ElementType.Note.getLocalizedName()
    }

    menu.addItem(1, ElementType.Archive) {
        println("Вывожу список ${menu.type}:")
        if (menu.setOfArchive.isEmpty()) { println("Cписок пуст :( Вы ещё ничего не добавили...") }
        else menu.setOfArchive.forEach { e -> println(e.name) }
    }

    menu.addItem(1, ElementType.Note) {
        println("Вывожу список ${menu.type}:")
        val foundArchive = menu.setOfArchive.elementAt(menu.archiveIndex)
        if (foundArchive.notes.isEmpty()) {
            println("Cписок пуст :( Вы ещё ничего не добавили...")
        } else foundArchive.notes.forEach { e -> println(e.name) }
    }

    menu.addItem(1, ElementType.NoteText) {
        println("Вывожу список ${ElementType.NoteText}:")
        val foundArchive = menu.setOfArchive.elementAt(menu.archiveIndex)
        val foundNote = foundArchive.notes.elementAt(menu.noteIndex)
        println("${menu.type.capitalize()} с названием '${foundNote.name}' найден(а).")
        println(foundNote.text)
    }

    menu.addItem(2, ElementType.Archive) {
        println("Введите уникальное название ${menu.type}:")
        val name = menu.getUserNameOfElement()
        if (name.isNotEmpty() && !menu.uniqueArchive(name)) {
            menu.setOfArchive.add(Archive(name))
            println("${menu.type.capitalize()} '$name' добавлен.")
        }
        else { println("${menu.type.capitalize()} с названием '$name' не был добавлен.")}
    }

    menu.addItem(2, ElementType.Note) {
        println("Введите уникальное название ${menu.type}:")
        val name = menu.getUserNameOfElement()
        if (name.isNotEmpty() && !menu.uniqueNote(name)) {
            val noteText = menu.getUserTextOfNote()
            if (noteText.isNotEmpty()) {
                val foundArchive = menu.setOfArchive.elementAt(menu.archiveIndex)
                val note = Note(name)
                note.text = noteText
                foundArchive.notes.add(note)
                println("${menu.type.capitalize()} '$name' добавлена.")
            }
            else {
                println("Не могу создать ${menu.type}, поскольку содержание пустое")
            }
        }
        else {println("${menu.type.capitalize()} с названием '$name' не был добавлена.")}
    }

    menu.addItem(3, ElementType.Archive) {
        println("Введите уникальное название ${menu.type}:")
        val name = menu.getUserNameOfElement()
        if (name.isNotEmpty()) {
            val foundArchive = menu.setOfArchive.find { it.name == name }
            if (foundArchive != null) {
                menu.archiveIndex = menu.setOfArchive.indexOf(foundArchive)
                println("${menu.type.capitalize()} с названием '${foundArchive.name}' найден(а).")
                println(menu.archiveIndex)
                menu.elemType = ElementType.Note
                menu.type = ElementType.Note.getLocalizedName()
                println("Теперь вы можете посмотреть ${menu.type}")
            }
            else {
                menu.archiveIndex = -1
                println("${menu.type.capitalize()} с названием '$name' не найден(а).")
            }
        }
    }

    menu.addItem(3, ElementType.Note) {
        println("Введите уникальное название ${menu.type}:")
        val name = menu.getUserNameOfElement()
        if (name.isNotEmpty()) {
            val foundArchive = menu.setOfArchive.elementAt(menu.archiveIndex)
            val foundNote = foundArchive.notes.elementAt(menu.noteIndex)
            println("${menu.type.capitalize()} с названием '${foundNote.name}' найден(а).")
            println(menu.noteIndex)
            menu.elemType = ElementType.NoteText
            menu.type = ElementType.NoteText.getLocalizedName()
            println("Теперь вы можете посмотреть ${menu.type}")
        }
    }

}