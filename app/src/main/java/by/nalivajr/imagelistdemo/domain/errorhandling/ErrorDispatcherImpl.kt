package by.nalivajr.imagelistdemo.domain.errorhandling

import android.content.Context
import by.nalivajr.imagelistdemo.R
import java.io.IOException
import java.net.UnknownHostException

class ErrorDispatcherImpl(val context: Context): ErrorDispatcher {

    override fun dispatchError(error: Throwable?): String {
        return when (error) {
            is UnknownHostException, is IOException -> context.getString(R.string.error_no_network)
            else -> context.getString(R.string.error_unexpected_try_again)
        }
    }
}