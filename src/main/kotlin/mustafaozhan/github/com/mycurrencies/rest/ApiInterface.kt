package mustafaozhan.github.com.mycurrencies.rest


import mustafaozhan.github.com.mycurrencies.model.CurrencyResponse
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Mustafa Ozhan on 2018-10-19.
 */
interface ApiInterface {
    @GET("latest")
    fun getAllOnBase(@Query("base") base: Currencies):
            Observable<CurrencyResponse>
}