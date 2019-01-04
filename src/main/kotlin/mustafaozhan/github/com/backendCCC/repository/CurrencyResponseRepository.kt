package mustafaozhan.github.com.backendCCC.repository

import mustafaozhan.github.com.backendCCC.model.CurrencyResponse
import mustafaozhan.github.com.backendCCC.tools.Currencies
import org.springframework.data.repository.CrudRepository

/**
 * Created by Mustafa Ozhan on 2018-10-21.
 */
interface CurrencyResponseRepository : CrudRepository<CurrencyResponse, String> {
    fun findCurrencyResponseByBase(currency: Currencies): CurrencyResponse
}