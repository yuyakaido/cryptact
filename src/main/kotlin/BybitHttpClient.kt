import retrofit2.http.GET

interface BybitHttpClient {
    @GET("/v2/public/symbols")
    suspend fun getSymbols(): SymbolListResponse
}
