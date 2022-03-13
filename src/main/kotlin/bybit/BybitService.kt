package bybit

import common.CsvExporter
import kotlinx.serialization.ExperimentalSerializationApi
import model.ExchangeHistory

@ExperimentalSerializationApi
object BybitService {

    suspend fun execute() {
        val exchangeRecords = BybitDownloader.downloadExchangeRecords()
        exchangeRecords.forEach { println(it) }
        CsvExporter.export(
            ExchangeHistory(
                fileName = "bybit_exchange_history",
                records = exchangeRecords
            )
        )
    }

}