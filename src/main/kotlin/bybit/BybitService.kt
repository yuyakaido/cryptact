package bybit

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BybitService {

    suspend fun execute() {
        val exchangeRecords = BybitDownloader.downloadExchangeRecords()
        exchangeRecords.forEach { println(it) }
    }

}