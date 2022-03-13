package model

data class Symbol(
    val base: Asset,
    val quote: Asset
) {
    override fun toString(): String {
        return "$base/$quote"
    }
}
