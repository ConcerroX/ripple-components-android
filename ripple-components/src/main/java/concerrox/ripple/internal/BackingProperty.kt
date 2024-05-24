package concerrox.ripple.internal

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner


open class BackingLiveData<T> : LiveData<T> {
    /**
     * Creates a MutableLiveData initialized with the given `value`.
     *
     * @param value initial value
     */
    private val data = this::class.java.superclass.getDeclaredField("mData").apply {
        isAccessible = true
    }

    constructor(value: T) : super(value)

    /**
     * Creates a MutableLiveData with no value assigned to it.
     */
    constructor() : super()

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun getValue(): T {
        return super.getValue()!!
    }

    var valueNoNotification: T?
        set(value) {
            data.set(this, value)
        }
        get() = value

    fun applyObserverByViewTree(view: View, observer: Observer<T>): BackingLiveData<T> {
        view.findViewTreeLifecycleOwner()?.let {
            observe(it, observer)
        }
        return this
    }
}