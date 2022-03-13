package bybit

import retrofit2.http.GET
import retrofit2.http.Query

interface BybitHttpClient {
    @GET("v2/private/exchange-order/list")
    suspend fun getExchangeHistory(
        @Query("from") from: Long? = null,
        @Query("limit") limit: Int = 50
    ): ExchangeHistoryResponse
}
