package mustafaozhan.github.com.mycurrencies.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias

/**
 * Created by Mustafa Ozhan on 2018-10-20.
 */
@TypeAlias("offlineRates")
data class OfflineRates(
        @Id var base: String,
        var eUR: Double? = null,
        var aUD: Double? = null,
        var bGN: Double? = null,
        var bRL: Double? = null,
        var cAD: Double? = null,
        var cHF: Double? = null,
        var cNY: Double? = null,
        var cZK: Double? = null,
        var dKK: Double? = null,
        var gBP: Double? = null,
        var hKD: Double? = null,
        var hRK: Double? = null,
        var hUF: Double? = null,
        var iDR: Double? = null,
        var iLS: Double? = null,
        var iNR: Double? = null,
        var jPY: Double? = null,
        var kRW: Double? = null,
        var mXN: Double? = null,
        var mYR: Double? = null,
        var nOK: Double? = null,
        var nZD: Double? = null,
        var pHP: Double? = null,
        var pLN: Double? = null,
        var rON: Double? = null,
        var rUB: Double? = null,
        var sEK: Double? = null,
        var sGD: Double? = null,
        var tHB: Double? = null,
        var tRY: Double? = null,
        var uSD: Double? = null,
        var zAR: Double? = null
)