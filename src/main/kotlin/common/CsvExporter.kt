package common

import model.BybitHistory
import java.io.File

object CsvExporter {

    private val outputDirectory = File("${System.getProperty("user.dir")}/outputs")

    fun export(history: BybitHistory) {
        outputDirectory.mkdir()
        val csvFile = File("${outputDirectory.path}/bybit_history.csv")
        csvFile.createNewFile()
        val writer = csvFile.bufferedWriter()
        history.toCsv().forEach { writer.append(it) }
        writer.close()
    }

}
