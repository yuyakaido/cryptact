package bybit

import common.CsvExporter
import kotlinx.serialization.ExperimentalSerializationApi
import model.BybitHistory
import model.EarnHistory
import model.ExchangeHistory

@ExperimentalSerializationApi
object BybitService {

    suspend fun execute() {
        val exchangeRecords = BybitDownloader.downloadExchangeRecords()
        val flexibleStakingRecords = BybitImporter.importFlexibleStakingRecords()
        val deFiMiningRecords = BybitImporter.importDeFiMiningRecords()
        val launchpoolRecords = BybitImporter.importLaunchpoolRecords()
        val airdropRecords = BybitImporter.importAirdropRecords()
        CsvExporter.export(
            BybitHistory(
                exchange = ExchangeHistory(
                    records = exchangeRecords
                ),
                flexibleStaking = EarnHistory(
                    action = "STAKING",
                    source = "Bybit - Flexible Staking",
                    records = flexibleStakingRecords
                ),
                deFiMining = EarnHistory(
                    action = "MINING",
                    source = "Bybit - DeFi Mining",
                    records = deFiMiningRecords
                ),
                launchpool = EarnHistory(
                    action = "BONUS",
                    source = "Bybit - Launchpool",
                    records = launchpoolRecords
                ),
                airdrop = EarnHistory(
                    action = "BONUS",
                    source = "Bybit - Airdrop",
                    records = airdropRecords
                )
            )
        )
    }

}
