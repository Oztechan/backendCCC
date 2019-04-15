package mustafaozhan.github.com.backendCCC

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import mustafaozhan.github.com.backendCCC.repository.CurrencyResponseRepository
import mustafaozhan.github.com.backendCCC.rest.ApiClient
import mustafaozhan.github.com.backendCCC.rest.ApiInterface
import mustafaozhan.github.com.backendCCC.tools.Currencies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties
import java.util.concurrent.TimeUnit

@SpringBootApplication
class CurrencyConverterCalculatorApplication

@Autowired
lateinit var currencyResponseRepository: CurrencyResponseRepository

fun main(args: Array<String>) {

    var url: String
    val context =
        runApplication<CurrencyConverterCalculatorApplication>(*args)
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
        Flowable.interval(15, 15, TimeUnit.MINUTES)
            .observeOn(Schedulers.io())
            .onBackpressureLatest()
            .doOnError { throwable ->
                logOnThrowable(throwable)
            }
            .subscribe {
                var count = 0
                println(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()))
                println("Update Started !")
                Currencies.values()
                    .forEach { currency ->
                        Thread.sleep(500)
                        ApiClient.get(url)
                            .create(ApiInterface::class.java)
                            .getAllOnBase(currency)
                            .observeOn(rx.schedulers.Schedulers.io())
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
                Thread.sleep(500)
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