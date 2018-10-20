package mustafaozhan.github.com.mycurrencies.extensions

import mustafaozhan.github.com.mycurrencies.model.CurrencyResponse
import mustafaozhan.github.com.mycurrencies.model.OfflineRates
import mustafaozhan.github.com.mycurrencies.model.Rates

/**
 * Created by Mustafa Ozhan on 2018-10-20.
 */
fun CurrencyResponse.toOfflineRates() = OfflineRates(this.rates?.findBase().toString(),
        this.rates?.eUR,
        this.rates?.aUD,
        this.rates?.bGN,
        this.rates?.bRL,
        this.rates?.cAD,
        this.rates?.cHF,
        this.rates?.cNY,
        this.rates?.cZK,
        this.rates?.dKK,
        this.rates?.gBP,
        this.rates?.hKD,
        this.rates?.hRK,
        this.rates?.hUF,
        this.rates?.iDR,
        this.rates?.iLS,
        this.rates?.iNR,
        this.rates?.jPY,
        this.rates?.kRW,
        this.rates?.mXN,
        this.rates?.mYR,
        this.rates?.nOK,
        this.rates?.nZD,
        this.rates?.pHP,
        this.rates?.pLN,
        this.rates?.rON,
        this.rates?.rUB,
        this.rates?.sEK,
        this.rates?.sGD,
        this.rates?.tHB,
        this.rates?.tRY,
        this.rates?.uSD,
        this.rates?.zAR
)


fun OfflineRates.getRates(): Rates? = Rates(
        this.eUR,
        this.aUD,
        this.bGN,
        this.bRL,
        this.cAD,
        this.cHF,
        this.cNY,
        this.cZK,
        this.dKK,
        this.gBP,
        this.hKD,
        this.hRK,
        this.hUF,
        this.iDR,
        this.iLS,
        this.iNR,
        this.jPY,
        this.kRW,
        this.mXN,
        this.mYR,
        this.nOK,
        this.nZD,
        this.pHP,
        this.pLN,
        this.rON,
        this.rUB,
        this.sEK,
        this.sGD,
        this.tHB,
        this.tRY,
        this.uSD,
        this.zAR
)

fun Rates.findBase(): String {
    return when {
        this.eUR == 1.0 -> "EUR"
        this.eUR == 1.0 -> "EUR"
        this.aUD == 1.0 -> "AUD"
        this.bGN == 1.0 -> "BGN"
        this.bRL == 1.0 -> "BRL"
        this.cAD == 1.0 -> "CAD"
        this.cHF == 1.0 -> "CHF"
        this.cNY == 1.0 -> "CNY"
        this.cZK == 1.0 -> "CZK"
        this.dKK == 1.0 -> "DKK"
        this.gBP == 1.0 -> "GBP"
        this.hKD == 1.0 -> "HKD"
        this.hRK == 1.0 -> "HRK"
        this.hUF == 1.0 -> "HUF"
        this.iDR == 1.0 -> "IDR"
        this.iLS == 1.0 -> "ILS"
        this.iNR == 1.0 -> "INR"
        this.jPY == 1.0 -> "JPY"
        this.kRW == 1.0 -> "KRW"
        this.mXN == 1.0 -> "MXN"
        this.mYR == 1.0 -> "MYR"
        this.nOK == 1.0 -> "NOK"
        this.nZD == 1.0 -> "NZD"
        this.pHP == 1.0 -> "PHP"
        this.pLN == 1.0 -> "PLN"
        this.rON == 1.0 -> "RON"
        this.rUB == 1.0 -> "RUB"
        this.sEK == 1.0 -> "SEK"
        this.sGD == 1.0 -> "SGD"
        this.tHB == 1.0 -> "THB"
        this.tRY == 1.0 -> "TRY"
        this.uSD == 1.0 -> "USD"
        this.zAR == 1.0 -> "ZAR"

        this.eUR == null -> "EUR"
        this.eUR == null -> "EUR"
        this.aUD == null -> "AUD"
        this.bGN == null -> "BGN"
        this.bRL == null -> "BRL"
        this.cAD == null -> "CAD"
        this.cHF == null -> "CHF"
        this.cNY == null -> "CNY"
        this.cZK == null -> "CZK"
        this.dKK == null -> "DKK"
        this.gBP == null -> "GBP"
        this.hKD == null -> "HKD"
        this.hRK == null -> "HRK"
        this.hUF == null -> "HUF"
        this.iDR == null -> "IDR"
        this.iLS == null -> "ILS"
        this.iNR == null -> "INR"
        this.jPY == null -> "JPY"
        this.kRW == null -> "KRW"
        this.mXN == null -> "MXN"
        this.mYR == null -> "MYR"
        this.nOK == null -> "NOK"
        this.nZD == null -> "NZD"
        this.pHP == null -> "PHP"
        this.pLN == null -> "PLN"
        this.rON == null -> "RON"
        this.rUB == null -> "RUB"
        this.sEK == null -> "SEK"
        this.sGD == null -> "SGD"
        this.tHB == null -> "THB"
        this.tRY == null -> "TRY"
        this.uSD == null -> "USD"
        this.zAR == null -> "ZAR"

        else -> "NULL"
    }
}
