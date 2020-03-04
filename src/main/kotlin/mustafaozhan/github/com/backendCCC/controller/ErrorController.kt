package mustafaozhan.github.com.backendCCC.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ErrorController : ErrorController {
    companion object {
        private const val PATH = "/error"
    }

    @RequestMapping(value = ["/error"])
    fun error(): String {
        return "<center><h1>Something went wrong</h1></center>"
    }

    override fun getErrorPath(): String {
        return PATH
    }
}
