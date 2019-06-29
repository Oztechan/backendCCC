package mustafaozhan.github.com.backendccc.rest

import mustafaozhan.github.com.backendccc.model.CurrencyResponse
import mustafaozhan.github.com.backendccc.model.NewResponse
import mustafaozhan.github.com.backendccc.tools.Currencies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Single

/**
 * Created by Mustafa Ozhan on 2018-10-19.
 */
interface ApiInterface {
    @GET("{base}.json")
    fun getAllOnBase(@Path("base") base: Currencies):
        Single<NewResponse>
}