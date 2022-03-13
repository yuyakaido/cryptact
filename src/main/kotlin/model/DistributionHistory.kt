package model

import java.time.format.DateTimeFormatter

// Mining: https://support.cryptact.com/hc/ja/articles/360002571312#menu24
// Launchpool/Airdrop: https://support.cryptact.com/hc/ja/articles/360002571312#menu28
// Staking: https://support.cryptact.com/hc/ja/articles/360002571312#menu210
data class DistributionHistory(
    val action: String,
    val source: String,
    val records: List<DistributionRecord>
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
    fun toCsv(): List<String> {
        return records.map {
            buildString {
                append(it.distributedAt.format(formatter)) // Timestamp
                append(",")
                append(action) // Action
                append(",")
                append(source) // Source
                append(",")
                append(it.asset.value) // Base
                append(",")
                append(it.amount.toString()) // Volume
                append(",")
                append("") // Price
                append(",")
                append("JPY") // Counter
                append(",")
                append(0) // Fee
                append(",")
                append("JPY") // FeeCcy
                append(",")
                append("N/A")
                append("\n")
            }
        }
    }
}
