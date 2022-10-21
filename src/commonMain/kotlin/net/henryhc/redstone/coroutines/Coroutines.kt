package net.henryhc.redstone.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Asynchronously perform the [Iterable.map] operations on all the elements of an [Iterable].
 * @param context The coroutine context where the mapping performs on.
 * @param block The mapping operation.
 */
suspend inline fun <reified T, R> Iterable<T>.asyncMap(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: (T) -> R
) = coroutineScope { this@asyncMap.map { async(context) { block(it) } }.awaitAll() }

/**
 * Asynchronously perform the [Iterable.map] operations on all the elements of an [Iterable].
 * @param semaphore The semaphore to control the parallelism.
 * @param context The coroutine context where the mapping performs on.
 * @param block The mapping operation.
 */
suspend inline fun <reified T, R> Iterable<T>.asyncMap(
    semaphore: Semaphore,
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend (T) -> R
) = coroutineScope { this@asyncMap.map { async(context) { semaphore.withPermit { block(it) } } }.awaitAll() }

/**
 * Asynchronously perform the [Iterable.filter] operations on all the elements of an [Iterable].
 * @param semaphore The semaphore to control the parallelism.
 * @param context The coroutine context where the mapping performs on.
 * @param block The mapping operation.
 */
suspend inline fun <reified T> Iterable<T>.asyncFilter(
    semaphore: Semaphore,
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend (T) -> Boolean
) = coroutineScope {
    this@asyncFilter.map { async(context) { it to semaphore.withPermit { block(it) } } }.awaitAll().filter { it.second }
        .filter(Pair<T, Boolean>::second)
        .map(Pair<T, Boolean>::first)
}

/**
 * Starts a new coroutine to perform operations with the control of a [Semaphore].
 * @param semaphore The semaphore to control the parallelism.
 * @param context The coroutine context where the mapping performs on.
 * @param block The operations to be executed in the coroutine.
 */
inline fun CoroutineScope.launchWithPermit(
    semaphore: Semaphore,
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend CoroutineScope.() -> Unit
) = launch(context) { semaphore.withPermit { block() } }
