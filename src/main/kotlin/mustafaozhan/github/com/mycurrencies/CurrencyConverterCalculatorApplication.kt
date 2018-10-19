package mustafaozhan.github.com.mycurrencies


import mustafaozhan.github.com.mycurrencies.rest.ApiClient
import mustafaozhan.github.com.mycurrencies.rest.ApiInterface
import mustafaozhan.github.com.mycurrencies.tools.Currencies
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@SpringBootApplication
class CurrencyConverterCalculatorApplication

fun main(args: Array<String>) {
    runApplication<CurrencyConverterCalculatorApplication>(*args)
    Observable.interval(1, TimeUnit.HOURS)
            .subscribe { _ ->
                Currencies.values().filter { it != Currencies.NULL }
                        .forEach { currency ->
                            ApiClient.get()
                                    .create(ApiInterface::class.java)
                                    .getAllOnBase(currency)
                                    .observeOn(Schedulers.newThread())
                                    .subscribe { currencyResponse ->
                                        //TODO
                                    }
                        }
            }
}
