package mustafaozhan.github.com.backendCCC

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties
import java.util.concurrent.TimeUnit

@SpringBootApplication
@ComponentScan(basePackages = ["mustafaozhan.github.com.backendCCC"])
class CCCApplication

const val DELAY: Long = 500
const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
const val CONFIG_PROPERTIES = "/config.properties"
const val CONFIG_BASE_URL = "config.baseUrl"

@Autowired
lateinit var currencyResponseRepository: CurrencyResponseRepository

@Suppress("TooGenericExceptionCaught", "SpreadOperator", "LongMethod")
fun main(args: Array<String>) {

    val properties = Properties()
    val context = runApplication<CCCApplication>(*args)
    val compositeDisposable = CompositeDisposable()

    currencyResponseRepository = context.getBean(CurrencyResponseRepository::class.java)

    try {
        properties.load(CCCApplication::class.java.getResourceAsStream(CONFIG_PROPERTIES))
    } catch (e: IOException) {
        logOnException(e)
    }
    val url = properties.getProperty(CONFIG_BASE_URL)

    compositeDisposable.add(
        Flowable.interval(0, 1, TimeUnit.DAYS)
            .subscribeOn(Schedulers.io())
            .onBackpressureLatest()
            .subscribe({
                var baseCount = 0
                println(SimpleDateFormat(DATE_FORMAT).format(Date()))
                println("Update Started !")
                Currencies.values()
                    .forEach { base ->
                        Thread.sleep(DELAY)
                        var targetCount = 0
                        val currencyResponse = CurrencyResponse(base = base.name, rates = Rates())
                        Currencies.values().forEach { target ->

                            ApiClient.get(url)
                                .create(ApiInterface::class.java)
                                .getAllOnBase(base, target)
                                .observeOn(rx.schedulers.Schedulers.io())
                                .subscribe({ rate ->
                                    print("Success ${base.name}($baseCount) to ${target.name}($targetCount)-")
                                    targetCount++
                                    currencyResponse.rates?.setFieldByName(target.name, rate)

                                }, { throwable ->
                                    println("\nError ${base.name}($baseCount) to ${target.name}($targetCount)")
                                    targetCount++
                                    logOnThrowable(throwable)
                                })
                        }
                        currencyResponseRepository.save(currencyResponse)
                        baseCount++
                        targetCount = 0
                    }
                Thread.sleep(DELAY)
                println("Update Finished !")
                baseCount = 0
            }, ::logOnThrowable)
    )
}

private fun logOnException(exception: Exception) {
    println(SimpleDateFormat(DATE_FORMAT).format(Date()))
    exception.printStackTrace()
}

private fun logOnThrowable(throwable: Throwable) {
    println(SimpleDateFormat(DATE_FORMAT).format(Date()))
    throwable.printStackTrace()
}

