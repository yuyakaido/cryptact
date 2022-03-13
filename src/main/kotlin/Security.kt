import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Security {

    enum class Algorithm(val value: String) {
        HMAC_SHA_256("HmacSHA256")
    }

    private fun ByteArray.toHexString(): String {
        return joinToString("") { String.format("%02x", it) }
    }

    fun generateSignature(
        apiSecret: String,
        target: String
    ): String {
        val algorithm = Algorithm.HMAC_SHA_256
        val mac = Mac.getInstance(algorithm.value)
        mac.init(SecretKeySpec(apiSecret.toByteArray(), algorithm.value))
        return mac.doFinal(target.toByteArray()).toHexString()
    }

}
