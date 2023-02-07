package com.slimshady.bottomsheetmodule.util.searchWatcher

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchWatcher(context: Context, attrs: AttributeSet?) : SearchView(context, attrs),
    SearchView.OnQueryTextListener {
    private val querySubject = PublishSubject.create<String>()
    fun addTextWatcher(watcher: Watcher, emitTime: Long) {
        querySubject
            .debounce(emitTime, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { item: String? ->
                Observable.just(
                    item
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(s: String) {
                    watcher.onTextChange(s)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        querySubject.onNext(query)
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        querySubject.onNext(newText)
        return false
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val xIcon = (getChildAt(0) as ViewGroup).getChildAt(2)
        xIcon.layoutDirection = LAYOUT_DIRECTION_RTL
        val linearLayout1 = getChildAt(0) as LinearLayout
        val linearLayout2 = linearLayout1.getChildAt(2) as LinearLayout
        val linearLayout3 = linearLayout2.getChildAt(1) as LinearLayout
        val autoComplete = linearLayout3.getChildAt(0) as AutoCompleteTextView
        autoComplete.textSize = 14f
        autoComplete.gravity = Gravity.RIGHT
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    init {
        setOnQueryTextListener(this)
    }
}