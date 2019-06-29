package mustafaozhan.github.com.backendccc.model

import com.google.gson.annotations.SerializedName


data class NewResponse(
    @SerializedName("baseCountry") var baseCountry: String? = null,
    @SerializedName("baseCurrency") var baseCurrency: String? = null,
    @SerializedName("rates") var rates: List<Rate>? = null
)

data class Rate(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("name_zh") var nameZh: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("currency_name") var currencyName: String? = null,
    @SerializedName("currency_name_zh") var currencyNameZh: String? = null,
    @SerializedName("currency_code") var currencyCode: String? = null,
    @SerializedName("rate") var rate: Double? = null,
    @SerializedName("hits") var hits: Int? = null,
    @SerializedName("selected") var selected: Int? = null,
    @SerializedName("top") var top: Int? = null
)