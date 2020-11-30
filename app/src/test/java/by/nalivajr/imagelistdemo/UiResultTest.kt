package by.nalivajr.imagelistdemo

import by.nalivajr.imagelistdemo.ui.model.UiResult
import org.junit.Assert
import org.junit.Test

class UiResultTest {

    @Test
    fun success() {
        val data = "OK"
        val target = UiResult.success(data)
        runTest(target, true, false)
    }

    @Test
    fun failedWithMessage() {
        val target = UiResult.error<String>("error occurred")
        runTest(target, false, true)
    }

    @Test
    fun failedWithException() {
        val target = UiResult.error<String>(RuntimeException())
        runTest(target, false, true)
    }

    private fun runTest(uiResult: UiResult<*>, expectSuccess: Boolean, expectFail: Boolean) {
        var successCallbackCalled = false
        var errorCallbackCalled = false
        uiResult.onSuccess {
            successCallbackCalled = true
        }.onError { _, _ ->
            errorCallbackCalled = true
        }
        Assert.assertEquals(expectSuccess, successCallbackCalled)
        Assert.assertEquals(expectFail, errorCallbackCalled)
    }
}