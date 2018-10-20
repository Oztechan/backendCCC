package mustafaozhan.github.com.mycurrencies.repository

import mustafaozhan.github.com.mycurrencies.model.OfflineRate
import org.springframework.data.repository.CrudRepository

/**
 * Created by Mustafa Ozhan on 2018-10-20.
 */
interface OfflineRateRepository : CrudRepository<OfflineRate, String> {
//    fun findOfflineRateByName(currency: Currency): OfflineRate
}