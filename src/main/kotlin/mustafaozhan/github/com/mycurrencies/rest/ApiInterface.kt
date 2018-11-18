package mustafaozhan.github.com.mycurrencies.rest


import mustafaozhan.github.com.mycurrencies.model.CurrencyResponse
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Single

/**
 * Created by Mustafa Ozhan on 2018-10-19.
 */
interface ApiInterface {
    @GET("{base}")
    fun getAllOnBase(@Path("base") base: Currencies):
            Single<CurrencyResponse>
}