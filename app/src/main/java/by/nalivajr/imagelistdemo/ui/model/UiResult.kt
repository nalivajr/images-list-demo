package by.nalivajr.imagelistdemo.ui.model

class UiResult<T>(
    val data: T?,
    val message: String?,
    val error: Throwable?
) {
    val isSuccess: Boolean
    get() = error == null && message == null

    fun onSuccess(action: (T?) -> Unit): UiResult<T> {
        if (isSuccess) {
            action.invoke(data)
        }
        return this
    }

    fun onError(action: (String?, Throwable?) -> Unit): UiResult<T> {
        if (!isSuccess) {
            action.invoke(message, error)
        }
        return this
    }

    companion object {

        fun <T> success(data: T?): UiResult<T> {
            return UiResult(data, null, null)
        }

        fun <T> error(message: String, error: Throwable? = null): UiResult<T> {
            return UiResult(null, message, null)
        }

        fun <T> error(error: Throwable): UiResult<T> {
            return UiResult(null, null, error)
        }
    }
}