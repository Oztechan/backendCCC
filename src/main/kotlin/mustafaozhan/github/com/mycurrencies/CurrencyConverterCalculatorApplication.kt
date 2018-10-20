package mustafaozhan.github.com.mycurrencies


import mustafaozhan.github.com.mycurrencies.rest.ApiClient
import mustafaozhan.github.com.mycurrencies.rest.ApiInterface
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import java.io.IOException
import java.io.FileInputStream
import org.springframework.util.ResourceUtils


@SpringBootApplication
class CurrencyConverterCalculatorApplication

fun main(args: Array<String>) {
    runApplication<CurrencyConverterCalculatorApplication>(*args)

    val properties = Properties()
    try {
        val file = ResourceUtils.getFile("src/main/resources/config.properties").absolutePath
        properties.load(FileInputStream(file))
    } catch (e: IOException) {
        e.printStackTrace()
    }

    Observable
            .interval(1, TimeUnit.SECONDS)
            .doOnError { throwable ->
                throwable.printStackTrace()
            }
            .subscribe {
                Currencies.values()
                        .filter { currency ->
                            currency != Currencies.NULL
                        }
                        .forEach { currency ->
                            ApiClient.get(properties.getProperty("exchange_rates_endpoint"))
                                    .create(ApiInterface::class.java)
                                    .getAllOnBase(currency)
                                    .observeOn(Schedulers.newThread())
                                    .doOnError { throwable ->
                                        throwable.printStackTrace()
                                    }
                                    .subscribe { currencyResponse ->
                                        //TODO
                                        println(it)
                                    }


                        }
            }
}
