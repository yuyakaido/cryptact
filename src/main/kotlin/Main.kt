import bybit.BybitDownloader
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    runBlocking {
        println("Started!")
        BybitDownloader.execute()
        println("Completed!")
        exitProcess(0)
    }
}
