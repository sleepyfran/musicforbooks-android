package io.fgonzaleva.musicforbooks.ui.base

import io.reactivex.disposables.Disposable

interface Presenter<V : View> {
    fun attach(view: V)
    fun detach()
    fun addDisposable(disposable: Disposable)
}