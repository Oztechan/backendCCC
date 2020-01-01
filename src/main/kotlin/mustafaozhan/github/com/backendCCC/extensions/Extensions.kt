package mustafaozhan.github.com.backendCCC.extensions

import mustafaozhan.github.com.backendCCC.model.Rates

@Suppress("NestedBlockDepth")
fun Rates.setFieldByName(name: String, value: Double) {
    val instance = this
    this::class.java.declaredFields.first { it.name == name }.apply {
        isAccessible = true
        set(instance, value)
    }
}
