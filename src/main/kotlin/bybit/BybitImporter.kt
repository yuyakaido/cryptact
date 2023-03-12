package bybit

import model.Asset
import model.EarnRecord
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object BybitImporter {

    private val inputDirectory = File("${System.getProperty("user.dir")}/inputs/bybit")
    private val earnDirectory = File("${inputDirectory.path}/earn")
    private val airdropDirectory = File("${inputDirectory}/airdrop")
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private fun importEarnRecords(product: BybitEarnProduct): List<EarnRecord> {
        return earnDirectory.listFiles()?.flatMap { file ->
            val lines = file.readLines()
            val header = lines.first()
            val headers = header.split(",")
            val records = lines.subList(1, lines.size)

            val utcTimeIndex = headers.indexOf("UTC_TIME")
            val operationIndex = headers.indexOf("Operation")
            val productIndex = headers.indexOf("Product")
            val coinIndex = headers.indexOf("Coin")
            val changeIndex = headers.indexOf("Change")

            records.mapNotNull { record ->
                val columns = record.split(",")

                val operationValue = BybitEarnOperation.from(columns[operationIndex])
                val productValue = BybitEarnProduct.from(columns[productIndex])
                if (operationValue == BybitEarnOperation.Yield && productValue == product) {
                    val utcTimeValue = columns[utcTimeIndex]
                    val coinValue = columns[coinIndex]
                    val changeValue = columns[changeIndex]

                    val earnedAt = LocalDateTime.parse(utcTimeValue, formatter)
                        .atZone(ZoneOffset.UTC)
                        .withZoneSameInstant(ZoneId.systemDefault())
                    val asset = Asset.from(coinValue)
                    val amount = BigDecimal(changeValue)
                    EarnRecord(
                        earnedAt = earnedAt,
                        asset = asset,
                        amount = amount
                    )
                } else {
                    null
                }
            }
        } ?: emptyList()
    }

    fun importFlexibleStakingRecords(): List<EarnRecord> {
        return importEarnRecords(BybitEarnProduct.FlexibleStaking)
    }

    fun importDeFiMiningRecords(): List<EarnRecord> {
        return importEarnRecords(BybitEarnProduct.DeFiMining)
    }

    fun importLaunchpoolRecords(): List<EarnRecord> {
        return importEarnRecords(BybitEarnProduct.Launchpool)
    }

    fun importAirdropRecords(): List<EarnRecord> {
        val file = File("${airdropDirectory.path}/bybit_airdrop.csv")
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

            val earnedAt = LocalDateTime.parse(dateAndTimeValue, formatter)
                .atZone(ZoneId.systemDefault())
            val asset = Asset.from(coinValue)
            val amount = BigDecimal(quantityValue)

            EarnRecord(
                earnedAt = earnedAt,
                asset = asset,
                amount = amount
            )
        }
    }

}
