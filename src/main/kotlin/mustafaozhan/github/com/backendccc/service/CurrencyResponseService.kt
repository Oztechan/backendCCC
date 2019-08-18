package mustafaozhan.github.com.backendccc.service

import mustafaozhan.github.com.backendccc.model.CurrencyResponse
import mustafaozhan.github.com.backendccc.repository.CurrencyResponseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Mustafa Ozhan on 2018-10-21.
 */
interface CurrencyResponseService {
    fun findCurrencyResponseByBase(currency: String): CurrencyResponse
}

@Suppress("unused")
@Service("currencyResponseService")
class CurrencyResponseServiceImpl : CurrencyResponseService {

    @Autowired
    lateinit var currencyResponseService: CurrencyResponseRepository

    override fun findCurrencyResponseByBase(currency: String): CurrencyResponse =
        currencyResponseService.findById(currency).get()
}
