package mustafaozhan.github.com.backendccc

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import mustafaozhan.github.com.backendccc.repository.CurrencyResponseRepository
import mustafaozhan.github.com.backendccc.rest.ApiClient
import mustafaozhan.github.com.backendccc.rest.ApiInterface
import mustafaozhan.github.com.backendccc.tools.Currencies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties
import java.util.concurrent.TimeUnit

@SpringBootApplication
class CCCApplication

const val REFRESH_PERIOD: Long = 10
const val DELAY: Long = 500
const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
const val CONFIG_PROPERTIES = "/config.properties"
const val CONFIG_BASE_URL = "config.baseUrl"
const val CONFIG_API_KEY = "config.apiKey"
const val CONFIG_API_SECRET = "config.apiSecret"

@Autowired
lateinit var currencyResponseRepository: CurrencyResponseRepository

@Suppress("TooGenericExceptionCaught", "SpreadOperator")
fun main(args: Array<String>) {

    var url: String
    val properties = Properties()
    val context = runApplication<CCCApplication>(*args)
    val compositeDisposable = CompositeDisposable()

    currencyResponseRepository = context.getBean(CurrencyResponseRepository::class.java)

    try {
        properties.load(CCCApplication::class.java.getResourceAsStream(CONFIG_PROPERTIES))
    } catch (e: IOException) {
        logOnException(e)
    }
    properties.apply {
        url = getProperty(CONFIG_BASE_URL) + getProperty(CONFIG_API_KEY) + getProperty(CONFIG_API_SECRET)
    }

    try {
        compositeDisposable.add(
            Flowable.interval(REFRESH_PERIOD, REFRESH_PERIOD, TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .onBackpressureLatest()
                .doOnError { throwable ->
                    logOnThrowable(throwable)
                }
                .subscribe {
                    var count = 0
                    println(SimpleDateFormat(DATE_FORMAT).format(Date()))
                    println("Update Started !")
                    Currencies.values()
                        .forEach { currency ->
                            Thread.sleep(DELAY)

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
                    Thread.sleep(DELAY)
                    println("Update Finished !")
                    count = 0
                }
        )
    } catch (e: Exception) {
        logOnException(e)
    }
}

private fun logOnException(exception: Exception) {
    println(SimpleDateFormat(DATE_FORMAT).format(Date()))
    exception.printStackTrace()
}

private fun logOnThrowable(throwable: Throwable) {
    println(SimpleDateFormat(DATE_FORMAT).format(Date()))
    throwable.printStackTrace()
}