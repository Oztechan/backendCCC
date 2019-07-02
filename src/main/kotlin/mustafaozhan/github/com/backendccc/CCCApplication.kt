package mustafaozhan.github.com.backendccc

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import mustafaozhan.github.com.backendccc.extensions.setFieldByName
import mustafaozhan.github.com.backendccc.model.CurrencyResponse
import mustafaozhan.github.com.backendccc.model.Rates
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

const val DELAY: Long = 500
const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
const val CONFIG_PROPERTIES = "/config.properties"
const val CONFIG_BASE_URL = "config.baseUrl"
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
        url = getProperty(CONFIG_BASE_URL)
    }

    try {
        compositeDisposable.add(
            Flowable.interval(0, 1, TimeUnit.DAYS)
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
                        .forEach { base ->
                            //                            Thread.sleep(DELAY)
                            val currencyResponse = CurrencyResponse(base = base.name, rates = Rates())
                            Currencies.values().forEach { target ->
                                ApiClient.get(url)
                                    .create(ApiInterface::class.java)
                                    .getAllOnBase(base, target)
                                    .observeOn(rx.schedulers.Schedulers.io())
                                    .doOnError { throwable ->
                                        count++
                                        println("Error ${base.name} to ${target.name}")
                                        logOnThrowable(throwable)
                                    }
                                    .subscribe { rate ->
                                        count++
                                        println("Success ${base.name} to ${target.name}")
                                        currencyResponse.rates?.setFieldByName(target.name, rate)
                                    }
                            }
                            currencyResponseRepository.save(currencyResponse)
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