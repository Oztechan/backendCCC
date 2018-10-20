package mustafaozhan.github.com.mycurrencies.repository

import mustafaozhan.github.com.mycurrencies.model.OfflineRate
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Created by Mustafa Ozhan on 2018-10-20.
 */
interface OfflineRateRepository : CrudRepository<OfflineRateRepository, String> {
    fun findOfflineRateByName(currency: Currency): OfflineRate
}