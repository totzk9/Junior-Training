package com.lig.juntrain.presenter

import java.lang.ref.WeakReference

abstract class BasePresenter<V> {

    private var view : WeakReference<V>? = null

    fun getView() : V? = view?.get()

    fun setView(viewSet : V) {

        view = WeakReference(viewSet)

    }
}