package com.basebox.ratexchange.util

/**
 * Thrown when there was a error fetching network data
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class CausableError(message: String?, cause: Throwable?) : Throwable(message, cause)