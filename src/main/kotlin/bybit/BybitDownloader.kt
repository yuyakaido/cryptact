package bybit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import common.BigDecimalSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import model.ExchangeRecord
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@ExperimentalSerializationApi
object BybitDownloader {

    private val apiKey = System.getProperty("BYBIT_API_KEY")
    private val apiSecret = System.getProperty("BYBIT_API_SECRET")

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(BybitHttpInterceptor(apiKey, apiSecret))
        .addInterceptor(httpLoggingInterceptor)
        .build()
    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = serializersModuleOf(BigDecimalSerializer)
    }
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.bybit.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    private val service = retrofit.create(BybitHttpClient::class.java)

    suspend fun downloadExchangeRecords(): List<ExchangeRecord> {
        val responses = mutableListOf<ExchangeHistoryResponse>()
        var from: Long? = null
        while (true) {
            val response = service.getExchangeHistory(from = from)
            responses.add(response)
            if (response.result.size < 50) {
                break
            } else {
                from = response.result.last().id
            }
        }
        return responses
            .flatMap { it.toExchangeRecords() }
            .distinctBy { it.exchangedAt }
    }

}
