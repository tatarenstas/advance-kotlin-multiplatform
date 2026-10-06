package ua.edu.chnu.kkn.advancedkotlinmultiplatform

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.Result
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.map

class ResultMapTest {
    @Test
    fun transformsSuccessData() {
        val result: Result<Int> = Result.Success(21)

        assertEquals(Result.Success("42"), result.map { (it * 2).toString() })
    }

    @Test
    fun preservesFailureWithoutCallingTransform() {
        val failure: Result<Int> = Result.Failure("Request failed")
        var transformCalled = false

        val mapped = failure.map {
            transformCalled = true
            it.toString()
        }

        assertTrue(mapped === failure)
        assertFalse(transformCalled)
    }
}
