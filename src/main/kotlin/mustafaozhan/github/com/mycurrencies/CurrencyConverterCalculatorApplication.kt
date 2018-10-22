package mustafaozhan.github.com.mycurrencies


import mustafaozhan.github.com.mycurrencies.repository.CurrencyResponseRepository
import mustafaozhan.github.com.mycurrencies.rest.ApiClient
import mustafaozhan.github.com.mycurrencies.rest.ApiInterface
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.io.FileSystemResource
import rx.Observable
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


@SpringBootApplication
class CurrencyConverterCalculatorApplication

@Autowired
lateinit var currencyResponseRepository: CurrencyResponseRepository

fun main(args: Array<String>) {
    val context = runApplication<CurrencyConverterCalculatorApplication>(*args)
    currencyResponseRepository = context.getBean(CurrencyResponseRepository::class.java)
    val properties = Properties()
    try {
        properties.load(CurrencyConverterCalculatorApplication::class.java.getResourceAsStream("/config.properties"))
    } catch (e: IOException) {
        e.printStackTrace()
    }

    Observable
            .interval(2, TimeUnit.HOURS)
            .doOnError { throwable ->
                throwable.printStackTrace()
            }
            .subscribe {
                Currencies.values()
                        .filter { currency ->
                            currency != Currencies.NULL
                        }
                        .forEach { currency ->
                            ApiClient.get(properties.getProperty("config.url"))
                                    .create(ApiInterface::class.java)
                                    .getAllOnBase(currency)
                                    .observeOn(Schedulers.io())
                                    .doOnError { throwable ->
                                        throwable.printStackTrace()
                                    }
                                    .subscribe { currencyResponse ->
                                        currencyResponseRepository.save(currencyResponse)
                                    }
                        }
            }
}

@Bean
fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
    val properties = PropertySourcesPlaceholderConfigurer()
    properties.setLocation(FileSystemResource("config.properties"))
    properties.setIgnoreResourceNotFound(false)

    return properties
}
