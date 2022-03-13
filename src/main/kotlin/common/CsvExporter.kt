package common

import model.ExchangeHistory
import java.io.File

object CsvExporter {

    private val outputDirectory = File("${System.getProperty("user.dir")}/outputs")

    fun export(history: ExchangeHistory) {
        outputDirectory.mkdir()
        val csvFile = File("${outputDirectory.path}/${history.fileName}.csv")
        csvFile.createNewFile()
        val writer = csvFile.bufferedWriter()
        history.toCsv().forEach { writer.append(it) }
        writer.close()
    }

}