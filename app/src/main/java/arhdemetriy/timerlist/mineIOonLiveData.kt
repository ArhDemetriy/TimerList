package arhdemetriy.timerlist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class mineIOonLiveData<T>(private val _get: (T) -> T, private val _set: (T) -> T){
    private val pValue: MutableLiveData<T> by lazy {  MutableLiveData<T>() }
    fun observeValue(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) = pValue.observe(lifecycleOwner,observer)
    var value: T
        get() = _get(pValue.value!!)
        set(value) {pValue.value = _set(value)}
}
