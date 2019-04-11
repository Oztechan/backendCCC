package mustafaozhan.github.com.backendCCC.rest


import io.reactivex.Single
import mustafaozhan.github.com.backendCCC.model.CurrencyResponse
import mustafaozhan.github.com.backendCCC.tools.Currencies
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Mustafa Ozhan on 2018-10-19.
 */
interface ApiInterface {
    @GET("{base}")
    fun getAllOnBase(@Path("base") base: Currencies):
        Single<CurrencyResponse>
}