import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@ExperimentalSerializationApi
object BybitDownloader {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.bybit.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    private val service = retrofit.create(BybitHttpClient::class.java)

    suspend fun execute() {
        val response = service.getSymbols()
        println(response)
    }

}
