fun String.capitalize(): String {
    val array = toCharArray()
    array[0] = array[0].uppercaseChar()
    return array.concatToString(0, array.size)
}