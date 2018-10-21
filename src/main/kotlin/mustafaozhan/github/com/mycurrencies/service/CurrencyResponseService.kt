package mustafaozhan.github.com.mycurrencies.service

import mustafaozhan.github.com.mycurrencies.model.CurrencyResponse
import mustafaozhan.github.com.mycurrencies.repository.CurrencyResponseRepository
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Mustafa Ozhan on 2018-10-21.
 */
interface CurrencyResponseService {
    fun findCurrencyResponseByBase(currency: Currencies): CurrencyResponse
}

@Service("currencyResponseService")
class CurrencyResponseServiceImpl : CurrencyResponseService {

    @Autowired
    lateinit var currencyResponseService: CurrencyResponseRepository

    override fun findCurrencyResponseByBase(currency: Currencies): CurrencyResponse =
            currencyResponseService.findCurrencyResponseByBase(currency)
}