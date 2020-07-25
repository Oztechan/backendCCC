package mustafaozhan.github.com.backendCCC.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Mustafa Ozhan on 2018-10-19.
 */
class ApiClient {
    companion object {
        fun get(baseUrl: String): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }
}
