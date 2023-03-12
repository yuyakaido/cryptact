package model

data class Asset(
    val value: String
) {
    companion object {
        fun from(value: String): Asset {
            return Asset(
                value = when (value) {
                    "ELT" -> "ELTBLACK"
                    "IZI" -> "IZUMI"
                    else -> value
                }
            )
        }
    }
    override fun toString(): String {
        return value
    }
}
