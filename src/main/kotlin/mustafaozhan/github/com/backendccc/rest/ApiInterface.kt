package mustafaozhan.github.com.backendccc.rest

import mustafaozhan.github.com.backendccc.tools.Currencies
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Single

/**
 * Created by Mustafa Ozhan on 2018-10-19.
 */
interface ApiInterface {
    @GET("{base}/{target}")
    fun getAllOnBase(@Path("base") base: Currencies, @Path("target") target: Currencies):
        Single<Double>
}
