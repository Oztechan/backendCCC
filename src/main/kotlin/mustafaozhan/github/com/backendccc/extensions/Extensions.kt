package mustafaozhan.github.com.backendccc.extensions

import mustafaozhan.github.com.backendccc.model.CurrencyResponse
import mustafaozhan.github.com.backendccc.model.NewResponse
import mustafaozhan.github.com.backendccc.model.Rates

@Suppress("NestedBlockDepth")
fun NewResponse.toCurrencyResponse(): CurrencyResponse {
    val oldRates = Rates()
    rates?.forEach { newRate ->
        if (newRate.currencyCode != "EEK") {
            newRate.rate?.let { value ->
                oldRates::class.java.declaredFields.first { it.name == newRate.currencyCode }.apply {
                    isAccessible = true
                    set(oldRates, value)
                }
            }
        }
    }
    return CurrencyResponse(baseCurrency, rates = oldRates)
}