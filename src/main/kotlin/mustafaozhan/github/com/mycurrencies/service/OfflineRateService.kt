package mustafaozhan.github.com.mycurrencies.service

import mustafaozhan.github.com.mycurrencies.repository.OfflineRateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Mustafa Ozhan on 2018-10-20.
 */
interface OfflineRateService {
//    fun findOfflineRateByName(currency: Currency): OfflineRate
//}

    @Service("offlineRateService")
    class OfflineRateServiceImpl : OfflineRateService {
        @Autowired
        lateinit var offlineRateRepository: OfflineRateRepository

//    override fun findOfflineRateByName(currency: Currency): OfflineRate {
//        return offlineRateRepository.findOfflineRateByName(currency)
//    }
    }
}