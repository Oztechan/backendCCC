package mustafaozhan.github.com.backendCCC


import mustafaozhan.github.com.backendCCC.repository.CurrencyResponseRepository
import mustafaozhan.github.com.backendCCC.rest.ApiClient
import mustafaozhan.github.com.backendCCC.rest.ApiInterface
import mustafaozhan.github.com.backendCCC.tools.Currencies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import rx.Observable
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@SpringBootApplication
class CurrencyConverterCalculatorApplication

@Autowired
lateinit var currencyResponseRepository: CurrencyResponseRepository

fun main(args: Array<String>) {
    var url: String
    val context = runApplication<CurrencyConverterCalculatorApplication>(*args)
    currencyResponseRepository = context.getBean(CurrencyResponseRepository::class.java)
    val properties = Properties()
    try {
        properties.load(CurrencyConverterCalculatorApplication::class.java.getResourceAsStream("/config.properties"))
    } catch (e: Exception) {
        logOnException(e)
    }
    properties.apply {
        url = getProperty("config.baseUrl") + getProperty("config.apiKey") + getProperty("config.apiSecret")
    }

    try {
        Observable
                .interval(1, TimeUnit.MINUTES, Schedulers.io())
                .take(90)
                .map { min -> 90 - min }
                .doOnError { throwable ->
                    logOnThrowable(throwable)
                }
                .subscribe {
                    var count = 0
                    println(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()))
                    println("Update Started !")
                    Currencies.values()
                            .filter { currency ->
                                currency != Currencies.NULL
                            }
                            .forEach { currency ->
                                Thread.sleep(333)
                                ApiClient.get(url)
                                        .create(ApiInterface::class.java)
                                        .getAllOnBase(currency)
                                        .observeOn(Schedulers.io())
                                        .doOnError { throwable ->
                                            count++
                                            println(currency.name + " error $count")
                                            logOnThrowable(throwable)
                                        }
                                        .subscribe { currencyResponse ->
                                            count++
                                            println(currency.name + " success $count")
                                            currencyResponseRepository.save(currencyResponse)
                                        }
                            }
                    println("Update Finished !")
                    count = 0
                }
    } catch (e: Exception) {
        logOnException(e)
    }
}

fun logOnException(exception: Exception) {
    println(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()))
    exception.printStackTrace()

}

fun logOnThrowable(throwable: Throwable) {
    println(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()))
    throwable.printStackTrace()
}