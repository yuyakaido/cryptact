package model

data class Asset(
    val value: String
) {
    override fun toString(): String {
        return value
    }
}
