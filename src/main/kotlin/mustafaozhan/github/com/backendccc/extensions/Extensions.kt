package mustafaozhan.github.com.backendccc.extensions

import mustafaozhan.github.com.backendccc.model.Rates

@Suppress("NestedBlockDepth")
fun Rates.setFieldByName(name: String, value: Double) {
    val instance = this
    this::class.java.declaredFields.first { it.name == name }.apply {
        isAccessible = true
        set(instance, value)
    }
}