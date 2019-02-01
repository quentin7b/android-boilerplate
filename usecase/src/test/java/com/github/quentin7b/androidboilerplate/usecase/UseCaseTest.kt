package com.github.quentin7b.androidboilerplate.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Test

class UseCaseTest {

    @Test
    fun `do it return the right thing`() {
        val input = "Input"
        val mock = mock<UseCase<String, String>> {
            on { doIt(input) } doReturn input
        }
        assertEquals(input, mock.doIt(input))
    }
}
