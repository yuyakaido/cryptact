package bybit

import model.Asset
import model.DistributionRecord
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object BybitImporter {

    private val inputDirectory = File("${System.getProperty("user.dir")}/inputs")

    fun importFlexibleStakingRecords(): List<DistributionRecord> {
        val file = File("${inputDirectory.path}/bybit_flexible_staking_history.csv")
        val lines = file.readLines()
        val header = lines.first()
        val headers = header.split(",")
        val records = lines.subList(1, lines.size)

        val assetEarnedIndex = headers.indexOf("Asset Earned")
        val yieldIndex = headers.indexOf("Yield")
        val distributionTimeIndex = headers.indexOf("Distribution Time (UTC)")

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val regex = Regex("\".+\"")

        return records.map { record ->
            val sanitizedRecords = record.replace(regex) {
                it.value.replace(",", "")
                    .replace("\"", "")
            }
            val columns = sanitizedRecords.split(",")

            val assetEarnedValue = columns[assetEarnedIndex]
            val yieldValue = columns[yieldIndex]
            val distributionTimeValue = columns[distributionTimeIndex]

            val distributedAt = LocalDateTime.parse(distributionTimeValue, formatter)
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault())
            val asset = Asset(assetEarnedValue)
            val amount = BigDecimal(yieldValue)

            DistributionRecord(
                distributedAt = distributedAt,
                asset = asset,
                amount = amount
            )
        }
    }

    fun importDeFiMiningRecords(): List<DistributionRecord> {
        val file = File("${inputDirectory.path}/bybit_defi_mining_history.csv")
        val lines = file.readLines()
        val header = lines.first()
        val headers = header.split(",")
        val records = lines.subList(1, lines.size)

        val effectiveUntilIndex = headers.indexOf("Effective Until (UTC)")
        val yieldIndex = headers.indexOf("Yield")

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val regex = Regex("\".+\"")

        return records.map { record ->
            val sanitizedRecords = record.replace(regex) {
                it.value.replace(",", "")
                    .replace("\"", "")
            }
            val columns = sanitizedRecords.split(",")

            val effectiveUntilValue = columns[effectiveUntilIndex]
            val yieldValue = columns[yieldIndex]

            val distributedAt = LocalDateTime.parse(effectiveUntilValue, formatter)
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault())
            val asset = Asset(yieldValue.split(" ").last())
            val amount = BigDecimal(yieldValue.split(" ").first())

            DistributionRecord(
                distributedAt = distributedAt,
                asset = asset,
                amount = amount
            )
        }
    }

    fun importLaunchpoolRecords(): List<DistributionRecord> {
        val file = File("${inputDirectory.path}/bybit_launchpool_history.csv")
        val lines = file.readLines()
        val header = lines.first()
        val headers = header.split(",")
        val records = lines.subList(1, lines.size)

        val assetEarnedIndex = headers.indexOf("Asset Earned")
        val yieldIndex = headers.indexOf("Yield")
        val distributionTimeIndex = headers.indexOf("Distribution Time (UTC)")

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val regex = Regex("\".+\"")

        return records.map { record ->
            val sanitizedRecords = record.replace(regex) {
                it.value.replace(",", "")
                    .replace("\"", "")
            }
            val columns = sanitizedRecords.split(",")

            val assetEarnedValue = columns[assetEarnedIndex]
            val yieldValue = columns[yieldIndex]
            val distributionTimeValue = columns[distributionTimeIndex]

            val distributedAt = LocalDateTime.parse(distributionTimeValue, formatter)
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault())
            val asset = Asset(assetEarnedValue)
            val amount = BigDecimal(yieldValue)

            DistributionRecord(
                distributedAt = distributedAt,
                asset = asset,
                amount = amount
            )
        }
    }

    fun importAirdropRecords(): List<DistributionRecord> {
        val file = File("${inputDirectory.path}/bybit_airdrop_history.csv")
        val lines = file.readLines()
        val header = lines.first()
        val headers = header.split(",")
        val records = lines.subList(1, lines.size)

        val coinIndex = headers.indexOf("Coin")
        val quantityIndex = headers.indexOf("Quantity")
        val dateAndTimeIndex = headers.indexOf("Date & Time")

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val regex = Regex("\".+\"")

        return records.map { record ->
            val sanitizedRecords = record.replace(regex) {
                it.value.replace(",", "")
                    .replace("\"", "")
            }
            val columns = sanitizedRecords.split(",")

            val coinValue = columns[coinIndex]
            val quantityValue = columns[quantityIndex]
            val dateAndTimeValue = columns[dateAndTimeIndex]

            val distributedAt = LocalDateTime.parse(dateAndTimeValue, formatter)
                .atZone(ZoneId.systemDefault())
            val asset = Asset(coinValue)
            val amount = BigDecimal(quantityValue)

            DistributionRecord(
                distributedAt = distributedAt,
                asset = asset,
                amount = amount
            )
        }
    }

}
