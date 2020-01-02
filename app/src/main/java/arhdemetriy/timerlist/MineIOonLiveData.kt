package arhdemetriy.timerlist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MineIOonLiveData<T>(private val get: (T) -> T, private val set: (T) -> T = get, private val nullFlag: T){
    private val pValue: MutableLiveData<T> by lazy {  MutableLiveData<T>() }
    fun observeValue(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) = pValue.observe(lifecycleOwner,observer)
    var value: T
        get() {
            pValue.value = this.get(pValue?.value ?: nullFlag)
            return pValue.value!!
        }
        set(value) {pValue.value = this.set(value)}
}
