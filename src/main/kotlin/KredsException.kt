/**
 * Base exception for all exceptions throws by Kreds
 */
open class KredsException: RuntimeException{
    companion object {
        @JvmStatic val serialVersionUID = -342312132189773098L
    }
    constructor(message: String): super(message)
    constructor(throwable: Throwable): super( throwable)
    constructor(message: String, throwable: Throwable): super(message, throwable)
}