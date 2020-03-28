package mustafaozhan.github.com.backendCCC.controller

import mustafaozhan.github.com.backendCCC.model.CurrencyResponse
import mustafaozhan.github.com.backendCCC.service.CurrencyResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

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
    fun getCurrencyResponseByBase(@RequestParam("base") base: String): CurrencyResponse? =
        currencyResponseService.findCurrencyResponseByBase(base)
}
