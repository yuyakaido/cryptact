package model

// https://support.cryptact.com/hc/ja/articles/360002571312
data class BybitHistory(
    val exchange: ExchangeHistory,
    val flexibleStaking: DistributionHistory,
    val deFiMining: DistributionHistory,
    val launchpool: DistributionHistory,
    val airdrop: DistributionHistory
) {
    fun toCsv(): List<String> {
        return sequenceOf("Timestamp,Action,Source,Base,Volume,Price,Counter,Fee,FeeCcy,Comment\n")
            .plus(exchange.toCsv())
            .plus(flexibleStaking.toCsv())
            .plus(deFiMining.toCsv())
            .plus(launchpool.toCsv())
            .plus(airdrop.toCsv())
            .toList()
    }
}