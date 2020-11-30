package by.nalivajr.imagelistdemo

import by.nalivajr.imagelistdemo.ui.model.UiResult
import org.junit.Assert
import org.junit.Test

class UiResultTest {

    @Test
    fun success() {
        val data = "OK"
        val target = UiResult.success(data)
        runTest(target, expectSuccess = true, expectFail = false)
    }

    @Test
    fun failedWithMessage() {
        val target = UiResult.error<String>("error occurred")
        runTest(target, expectSuccess = false, expectFail = true)
    }

    @Test
    fun failedWithException() {
        val target = UiResult.error<String>(RuntimeException())
        runTest(target, expectSuccess = false, expectFail = true)
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