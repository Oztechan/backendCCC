package mustafaozhan.github.com.mycurrencies


import mustafaozhan.github.com.mycurrencies.extensions.toOfflineRates
import mustafaozhan.github.com.mycurrencies.repository.OfflineRateRepository
import mustafaozhan.github.com.mycurrencies.rest.ApiClient
import mustafaozhan.github.com.mycurrencies.rest.ApiInterface
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.util.ResourceUtils
import rx.Observable
import rx.schedulers.Schedulers
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


@SpringBootApplication
@EnableMongoRepositories
class CurrencyConverterCalculatorApplication

@Autowired
lateinit var offlineRateRepository: OfflineRateRepository

fun main(args: Array<String>) {
    val context = runApplication<CurrencyConverterCalculatorApplication>(*args)
    offlineRateRepository = context.getBean(OfflineRateRepository::class.java)
//    offlineRateRepository = CurrencyConverterCalculatorApplication () as OfflineRateRepository
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
                                    .observeOn(Schedulers.io())
                                    .doOnError { throwable ->
                                        throwable.printStackTrace()
                                    }
                                    .subscribe { currencyResponse ->
                                        offlineRateRepository.save(currencyResponse.toOfflineRates())
                                        println(it)
                                    }
                        }
            }
}
