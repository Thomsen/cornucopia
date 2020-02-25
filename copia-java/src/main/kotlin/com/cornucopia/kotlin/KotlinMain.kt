package com.cornucopia.kotlin

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable


fun main() {
    var list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    list.toObservable()
            .filter { it.length > 4}
            .subscribeBy (
                onNext = { println(it) },
                onComplete = { println("Done!")},
                onError = { it.printStackTrace() }
            )

}