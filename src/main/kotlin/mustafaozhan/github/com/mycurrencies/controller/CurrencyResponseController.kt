package mustafaozhan.github.com.mycurrencies.controller

import mustafaozhan.github.com.mycurrencies.model.CurrencyResponse
import mustafaozhan.github.com.mycurrencies.service.CurrencyResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * Created by Mustafa Ozhan on 2018-10-21.
 */
@RestController
@RequestMapping("/currency")
class CurrencyResponseController {
    @Autowired
    lateinit var currencyResponseService: CurrencyResponseService

    @RequestMapping(value = ["/byBase"], method = [RequestMethod.GET])
    @ResponseBody
    fun getCurrencyResponseByBase(@RequestParam("base") base: String): CurrencyResponse =
            currencyResponseService.findCurrencyResponseByBase(base)
}