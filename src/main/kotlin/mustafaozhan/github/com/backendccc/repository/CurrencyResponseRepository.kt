package mustafaozhan.github.com.backendccc.repository

import mustafaozhan.github.com.backendccc.model.CurrencyResponse
import mustafaozhan.github.com.backendccc.tools.Currencies
import org.springframework.data.repository.CrudRepository

/**
 * Created by Mustafa Ozhan on 2018-10-21.
 */
@Suppress("unused")
interface CurrencyResponseRepository : CrudRepository<CurrencyResponse, String> {
    fun findCurrencyResponseByBase(currency: Currencies): CurrencyResponse
}