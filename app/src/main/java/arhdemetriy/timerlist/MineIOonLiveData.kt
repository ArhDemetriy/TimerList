package arhdemetriy.timerlist

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MineIOonLiveData<T>(private val get: (T) -> T, private val nullFlag: T){
    private var setter: () -> Unit = {}
    private var temp: T = nullFlag;

    constructor(get: (T) -> T, set: (T) -> T = get, nullFlag: T): this(get,nullFlag){
        setter = {
            Log.d("MineIOonLiveData","set begin ${pValue.value.toString()} => ${get.toString()}")
            val t = get(pValue?.value ?: nullFlag)
            if (pValue.value != t) pValue.value = t
            Log.d("MineIOonLiveData","set end ${t.toString()} <= ${get.toString()}")
        }
    }


    private val pValue: MutableLiveData<T> = MutableLiveData<T>()
    fun observeValue(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) = pValue.observe(lifecycleOwner,observer)
    var value: T
        get() {
            Log.d("MineIOonLiveData","get begin ${pValue.value.toString()} => ${get.toString()}")
            val t = get(pValue?.value ?: nullFlag)
            if (pValue.value != t) pValue.value = t
            Log.d("MineIOonLiveData","get end ${t.toString()} <= ${get.toString()}")
            return t
        }
        set(value) {
            temp = value
            setter()
        }
}
