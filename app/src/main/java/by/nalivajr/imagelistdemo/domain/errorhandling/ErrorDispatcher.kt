package by.nalivajr.imagelistdemo.domain.errorhandling

interface ErrorDispatcher {

    fun dispatchError(error: Throwable?): String
}