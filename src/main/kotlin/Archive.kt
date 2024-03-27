data class Archive(val name: String)  {
    val notes = mutableSetOf<Note>()
    private val typeName: String = "архив"
    fun getType(): String {
        return typeName
    }
}