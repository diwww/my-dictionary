package org.maxsur.mydictionary.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Обертка над Rx шедулерами для удобства тестирования.
 */
class RxSchedulers {

    val main: Scheduler = AndroidSchedulers.mainThread()
    val io: Scheduler = Schedulers.io()
    val computation = Schedulers.computation()
}