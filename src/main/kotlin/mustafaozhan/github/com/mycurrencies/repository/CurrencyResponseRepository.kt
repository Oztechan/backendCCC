package mustafaozhan.github.com.mycurrencies.repository

import mustafaozhan.github.com.mycurrencies.model.CurrencyResponse
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import org.springframework.data.repository.CrudRepository

/**
 * Created by Mustafa Ozhan on 2018-10-21.
 */
interface CurrencyResponseRepository : CrudRepository<CurrencyResponse, String> {
    fun findCurrencyResponseByBase(currency: Currencies): CurrencyResponse
}