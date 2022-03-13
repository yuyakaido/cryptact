package bybit

import common.Security
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.Response
import java.time.Instant

@ExperimentalSerializationApi
class BybitHttpInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val originalParameterMap = (0 until originalUrl.querySize).associate { index ->
            originalUrl.queryParameterName(index) to originalUrl.queryParameterValue(index)
        }

        // The parameters must be ordered in alphabetical order.
        // https://bybit-exchange.github.io/docs/inverse/#t-authentication
        val targetParameterMap = originalParameterMap.plus(
            listOf(
                "api_key" to apiKey,
                "timestamp" to Instant.now().toEpochMilli().toString()
            )
        ).toSortedMap()
        val targetString = buildString {
            val iterator = targetParameterMap.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                append("${entry.key}=${entry.value}")
                if (iterator.hasNext()) {
                    append("&")
                }
            }
        }
        val newParameterMap = targetParameterMap.plus(
            "sign" to Security.generateSignature(apiSecret, targetString)
        )

        val newUrl = originalUrl.newBuilder()
            .apply {
                newParameterMap.forEach { entry ->
                    setEncodedQueryParameter(entry.key, entry.value)
                }
            }
            .build()
        val newRequest = originalRequest
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

}
