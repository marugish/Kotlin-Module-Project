data class Note(val name: String) {
    var text = ""
    private val typeName: String = "заметка"
    fun getType(): String {
        return typeName
    }
}