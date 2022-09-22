package bybit

enum class BybitEarnOperation(val value: String) {
    Yield("Yield");

    companion object {
        fun from(value: String): BybitEarnOperation? {
            return values().find { it.value == value }
        }
    }
}
