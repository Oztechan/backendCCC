@file:Suppress("TooGenericExceptionCaught")

package mustafaozhan.github.com.backendCCC

import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import mustafaozhan.github.com.backendCCC.extensions.setFieldByName
import mustafaozhan.github.com.backendCCC.model.CurrencyResponse
import mustafaozhan.github.com.backendCCC.model.Rates
import mustafaozhan.github.com.backendCCC.repository.CurrencyResponseRepository
import mustafaozhan.github.com.backendCCC.rest.ApiClient
import mustafaozhan.github.com.backendCCC.rest.ApiInterface
import mustafaozhan.github.com.backendCCC.tools.Currencies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

@SpringBootApplication
@ComponentScan(basePackages = ["mustafaozhan.github.com.backendCCC"])
class CCCApplication

private const val DAY = (24 * 60 * 60 * 1000).toLong()
private const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
private const val CONFIG_PROPERTIES = "/config.properties"
private const val CONFIG_BASE_URL = "config.baseUrl"

@Autowired
lateinit var currencyResponseRepository: CurrencyResponseRepository

@Suppress("SpreadOperator")
fun main(args: Array<String>) = try {
    val properties = Properties()
    val context = runApplication<CCCApplication>(*args)

    currencyResponseRepository = context.getBean(CurrencyResponseRepository::class.java)

    try {
        properties.load(CCCApplication::class.java.getResourceAsStream(CONFIG_PROPERTIES))
    } catch (e: Exception) {
        println()
        logOnException(e)
    }
    val url = properties.getProperty(CONFIG_BASE_URL)

    runBlocking {
        while (isActive) {
            try {
                updateCurrencies(url)
            } catch (e: Exception) {
                println()
                logOnException(e)
            }
            delay(DAY)
        }
    }
} catch (e: Exception) {
    // General exception handling
    logOnException(e)
}

@Suppress("NestedBlockDepth")
private suspend fun updateCurrencies(url: String) {
    println("~~~ Update Started ! ${getDate()} ~~~")
    var baseCount = 0
    Currencies.values().forEach { base ->
        var targetCount = 0
        val currencyResponse = CurrencyResponse(base = base.name, rates = Rates())
        Currencies.values().forEach { target ->
            try {
                ApiClient.get(url)
                    .create(ApiInterface::class.java)
                    .getAllOnBase(base, target).let { rate ->
                        print("${base.name}($baseCount) => ${target.name}($targetCount) | ")
                        currencyResponse.rates?.setFieldByName(target.name, rate)
                    }
            } catch (e: Exception) {
                println()
                println("\nError ${base.name}($baseCount) to ${target.name}($targetCount)")
                logOnException(e)
            }
            targetCount++
        }
        println()
        currencyResponseRepository.save(currencyResponse)
        baseCount++
        targetCount = 0
    }
    println("~~~ Update Finished ! ~~~")
    baseCount = 0
}

private fun getDate() = SimpleDateFormat(DATE_FORMAT).format(Date())

private fun logOnException(exception: Exception) {
    println("~~~ Error ${getDate()} ~~~")
    exception.printStackTrace()
    println("~~~")
}
