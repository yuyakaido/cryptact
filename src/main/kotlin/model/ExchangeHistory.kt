package model

import java.math.MathContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

// https://support.cryptact.com/hc/ja/articles/360002571312#menu21
data class ExchangeHistory(
    val records: List<ExchangeRecord>
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
    fun toCsv(): List<Pair<ZonedDateTime, String>> {
        return records.map {
            Pair(
                first = it.exchangedAt,
                second = buildString {
                    append(it.exchangedAt.format(formatter)) // Timestamp
                    append(",")
                    append("BUY") // Action
                    append(",")
                    append("Bybit - Exchange") // Source
                    append(",")
                    append(it.symbol.base.value) // Base
                    append(",")
                    append(it.toAmount.toString()) // Volume
                    append(",")
                    append(it.fromAmount.divide(it.toAmount, MathContext.DECIMAL32).toString()) // Price
                    append(",")
                    append(it.symbol.quote.value) // Counter
                    append(",")
                    append(it.feeAmount) // Fee
                    append(",")
                    append(it.feeAsset.value) // FeeCcy
                    append(",")
                    append("N/A")
                    append("\n")
                }
            )
        }
    }
}
