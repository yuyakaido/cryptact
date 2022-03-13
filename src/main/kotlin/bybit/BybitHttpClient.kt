package bybit

import kotlinx.serialization.json.JsonElement
import retrofit2.http.GET

interface BybitHttpClient {
    @GET("v2/private/exchange-order/list")
    suspend fun getExchangeHistory(): JsonElement
}
