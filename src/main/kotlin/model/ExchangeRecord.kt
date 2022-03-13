package model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class ExchangeRecord(
    val exchangedAt: ZonedDateTime,
    val symbol: Symbol,
    val fromAmount: BigDecimal,
    val toAmount: BigDecimal,
    val feeAmount: BigDecimal,
    val feeAsset: Asset
)
