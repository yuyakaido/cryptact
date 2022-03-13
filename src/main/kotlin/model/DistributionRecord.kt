package model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class DistributionRecord(
    val distributedAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal
)
