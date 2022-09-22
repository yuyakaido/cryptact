package model

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

// Staking: https://support.cryptact.com/hc/ja/articles/360002571312#menu210
// Mining: https://support.cryptact.com/hc/ja/articles/360002571312#menu24
// Launchpool/Airdrop: https://support.cryptact.com/hc/ja/articles/360002571312#menu28
data class EarnHistory(
    val action: String,
    val source: String,
    val records: List<EarnRecord>
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
    fun toCsv(): List<Pair<ZonedDateTime, String>> {
        return records.map {
            Pair(
                first = it.earnedAt,
                second = buildString {
                    append(it.earnedAt.format(formatter)) // Timestamp
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
            )
        }
    }
}
