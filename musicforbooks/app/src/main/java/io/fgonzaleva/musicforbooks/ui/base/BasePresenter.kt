package io.fgonzaleva.musicforbooks.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : View> : Presenter<V> {
    protected var view: V? = null
    private val compositeDisposable = CompositeDisposable()

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}