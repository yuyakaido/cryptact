package model

import java.math.MathContext
import java.time.format.DateTimeFormatter

data class ExchangeHistory(
    val fileName: String,
    val records: List<ExchangeRecord>
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
    fun toCsv(): List<String> {
        return listOf(
            "Timestamp,Action,Source,Base,Volume,Price,Counter,Fee,FeeCcy,Comment\n"
        ).plus(
            records.map {
                buildString {
                    append(it.exchangedAt.format(formatter)) // Timestamp
                    append(",")
                    append("BUY") // Action
                    append(",")
                    append("Bybit Exchange") // Source
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
            }
        )
    }
}
