package model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class EarnRecord(
    val earnedAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal
)
