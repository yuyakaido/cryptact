package model

// https://support.cryptact.com/hc/ja/articles/360002571312
data class BybitHistory(
    val exchange: ExchangeHistory,
    val flexibleStaking: EarnHistory,
    val deFiMining: EarnHistory,
    val launchpool: EarnHistory,
    val airdrop: EarnHistory
) {
    fun toCsv(): List<String> {
        return sequenceOf("Timestamp,Action,Source,Base,Volume,Price,Counter,Fee,FeeCcy,Comment\n")
            .plus(
                sequence {
                    yieldAll(exchange.toCsv())
                    yieldAll(flexibleStaking.toCsv())
                    yieldAll(deFiMining.toCsv())
                    yieldAll(launchpool.toCsv())
                    yieldAll(airdrop.toCsv())
                }.sortedByDescending { it.first }.map { it.second }
            )
            .toList()
    }
}
