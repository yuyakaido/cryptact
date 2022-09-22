package bybit

enum class BybitEarnProduct(val value: String) {
    FlexibleStaking("Flexible staking"),
    DeFiMining("Defi mining"),
    Launchpool("Launchpool"),
    Airdrop("Airdrop");

    companion object {
        fun from(value: String): BybitEarnProduct? {
            return values().find { it.value == value }
        }
    }
}
