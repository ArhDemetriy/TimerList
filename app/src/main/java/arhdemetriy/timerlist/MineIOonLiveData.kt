package arhdemetriy.timerlist

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MineIOonLiveData<T>(private val _get: (T) -> T, private val _set: (T) -> T = _get, private val nullFlag: T){
    private val pValue: MutableLiveData<T> = MutableLiveData<T>()
    fun observeValue(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) = pValue.observe(lifecycleOwner,observer)
    var value: T
        get() {
            Log.d("MineIOonLiveData","get begin ${pValue.value.toString()} => ${_get.toString()}")
            val t = _get(pValue?.value ?: nullFlag)
            if (pValue.value != t) pValue.value = t
            Log.d("MineIOonLiveData","get end ${t.toString()} <= ${_get.toString()}")
            return t
        }
        set(value) {
            Log.d("MineIOonLiveData","set begin ${value.toString()} ${pValue.value.toString()} => ${_set.toString()}")
            pValue.value = _set(value)
            Log.d("MineIOonLiveData","set end ${value.toString()} ${pValue.value.toString()} <= ${_set.toString()}")
        }
}
