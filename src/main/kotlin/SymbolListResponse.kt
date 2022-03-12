import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SymbolListResponse(
    @SerialName("ret_code") val retCode: Int,
    @SerialName("ret_msg") val retMsg: String
)
