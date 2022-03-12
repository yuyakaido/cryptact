import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET

@ExperimentalSerializationApi
fun main() {
    runBlocking {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://httpbin.org/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        val service = retrofit.create(HttpBinService::class.java)
        val response = service.get()
        println("Response = $response")
    }
}

@Serializable
data class GetResponse(
    @SerialName("origin") val origin: String,
    @SerialName("url") val url: String
)

interface HttpBinService {
    @GET("get")
    suspend fun get(): GetResponse
}