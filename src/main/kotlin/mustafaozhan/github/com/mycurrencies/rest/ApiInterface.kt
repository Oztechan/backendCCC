package mustafaozhan.github.com.mycurrencies.rest


import mustafaozhan.github.com.mycurrencies.model.CurrencyResponse
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

/**
 * Created by Mustafa Ozhan on 2018-10-19.
 */
interface ApiInterface {
    @GET("apirates/5bd374048b281/311f76e81ec4bfae07f2b1ba60a27029")
    fun getAllOnBase(@Query("base") base: Currencies):
            Single<CurrencyResponse>
}