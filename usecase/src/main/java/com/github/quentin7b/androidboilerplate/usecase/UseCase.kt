package com.github.quentin7b.androidboilerplate.usecase

/**
 * Classic use case
 * I is the type of the input
 * O is the type of the output
 */
interface UseCase<I, O> {
    fun doIt(input: I): O
}