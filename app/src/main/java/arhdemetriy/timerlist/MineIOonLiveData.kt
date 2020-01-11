package arhdemetriy.timerlist

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MineIOonLiveData<T>(private val get: (T) -> T, private val nullFlag: T){
    private var setteblyValue = nullFlag
    private var tempSet = get
    private var setter: () -> Unit = {}

    private val pValue: MutableLiveData<T> = MutableLiveData<T>()
    init {
        pValue.value = nullFlag
    }

    constructor(get: (T) -> T, set: (T) -> T, nullFlag: T): this(get,nullFlag){
        tempSet = set
        setter = {
            Log.d("MineIOonLiveData","set begin ${setteblyValue.toString()} ${pValue.value.toString()} => ${tempSet.toString()}")
            val t = tempSet(setteblyValue)
            if (pValue.value != t) pValue.value = t
            Log.d("MineIOonLiveData","set end ${setteblyValue.toString()} ${pValue.value.toString()} <= ${tempSet.toString()}")
        }
    }

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
            setteblyValue = value
            setter()
        }
}