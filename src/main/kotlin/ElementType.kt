interface Localizable {
    fun getLocalizedName(): String
}

enum class ElementType : Localizable {
    NoteText,
    Archive,
    Note;

    override fun getLocalizedName(): String =
        when (this) {
            Archive -> "архив"
            Note -> "заметка"
            NoteText -> "текст заметки"
        }
}