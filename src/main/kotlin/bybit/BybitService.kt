package bybit

import common.CsvExporter
import kotlinx.serialization.ExperimentalSerializationApi
import model.BybitHistory
import model.DistributionHistory
import model.ExchangeHistory

@ExperimentalSerializationApi
object BybitService {

    suspend fun execute() {
        val exchangeRecords = BybitDownloader.downloadExchangeRecords()
        val flexibleStakingRecords = BybitImporter.importFlexibleStakingRecords()
        val deFeMiningRecords = BybitImporter.importDeFiMiningRecords()
        val launchpoolRecords = BybitImporter.importLaunchpoolRecords()
        val airdropRecords = BybitImporter.importAirdropRecords()
        CsvExporter.export(
            BybitHistory(
                exchange = ExchangeHistory(exchangeRecords),
                flexibleStaking = DistributionHistory(
                    action = "STAKING",
                    source = "Bybit - Flexible Staking",
                    records = flexibleStakingRecords
                ),
                deFiMining = DistributionHistory(
                    action = "MINING",
                    source = "Bybit - DeFi Mining",
                    records = deFeMiningRecords
                ),
                launchpool = DistributionHistory(
                    action = "BONUS",
                    source = "Bybit - Launchpool",
                    records = launchpoolRecords
                ),
                airdrop = DistributionHistory(
                    action = "BONUS",
                    source = "Bybit - Airdrop",
                    records = airdropRecords
                )
            )
        )
    }

}