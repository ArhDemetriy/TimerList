package arhdemetriy.timerlist

import android.util.Log
import androidx.lifecycle.ViewModel

class MetaArrayListLong : ViewModel() {

    //хранилище таймеров
    private val arrayTimers: ArrayList<Long> = ArrayList()

    //сущности на которые будут вешатся observers и public поля через которые читаются/меняются хранимые ими значения

    var t: Int = 1

    val count: MineIOonLiveData<Int> =
        MineIOonLiveData<Int>(
            _get = {
                arrayTimers.count()
            },
            nullFlag = -1
        )


    val firstTimer: MineIOonLiveData<Long> =
        MineIOonLiveData<Long>(
            _get = {
                if (count.value <= 0) {
                    -1
                } else {
                    arrayTimers.get(0)
                }
            },
            _set = {
                if (count.value > 0) {
                    arrayTimers.set(0, it)
                    it
                } else -1
            },
            nullFlag = -1
        )


    private val l: (Int) -> Int = {
        when {
            count.value <= 0 -> -1
            it < 0 -> 0
            it >= count.value -> count.value - 1
            else -> it
        }
    }

    val posMainTimer: MineIOonLiveData<Int> =
        MineIOonLiveData<Int>(
            _get = l,
            nullFlag = -1
        )


    val mainTimer: MineIOonLiveData<Long> =
        MineIOonLiveData<Long>(
            _get = {
                if (count.value<=0 || posMainTimer.value < 0 //|| posMainTimer.value >= count.value
                ) {
                    0
                }
                else {
                    arrayTimers[posMainTimer.value]
                }
            },
            _set = {
                if (count.value<=0 || posMainTimer.value < 0 //|| posMainTimer.value >= count.value
                ) {
                    0
                }
                else {
                    arrayTimers[posMainTimer.value] = it
                    it
                }
            },
            nullFlag = -1
        )


    val timerRunned: MineIOonLiveData<Boolean> =
        MineIOonLiveData<Boolean>(
            _get = {
                Log.v("timerRunned","get begin")
                (arrayTimers.count() > 0) && it
                Log.v("timerRunned","get end")
                (arrayTimers.count() > 0) && it
            },
            nullFlag = false
        )


    val posActiveTimer: MineIOonLiveData<Int> =
        MineIOonLiveData<Int>(
            _get = l,
            nullFlag = -1
        )


    val activeTimer: MineIOonLiveData<Long> =
        MineIOonLiveData<Long>(
            _get = {
                if (it >= 0) it else 0},
            nullFlag = -1
        )


    init {
        count.value
        timerRunned.value=false
        //firstTimer.value=0
        posMainTimer.value=0
        //mainTimer.value=0
        posActiveTimer.value=0
        activeTimer.value=0
    }

    //методы редактирования хранилища таймеров
    fun addTimerAt(t: Long = 0,x: Int = arrayTimers.count()): Unit {
        when {
            arrayTimers == null -> {
                timerRunned.value = false
                return
                }
            x <= 0 -> {
                arrayTimers.add(0,t)
                activeTimer.value = t
            }
            x >= (arrayTimers.count()) -> arrayTimers.add(t)

            else -> arrayTimers.add(x,t)
        }

        var t = posMainTimer.value

        Log.d("render","begin $t")
        if (t >= x && t < arrayTimers.count()-1 ) {
            posMainTimer.value = ++t
            mainTimer.value = arrayTimers[t]
        }
    }

    fun delTimerAt(x: Int = arrayTimers.count()-1): Unit {
        when {
            arrayTimers == null || arrayTimers.count() == 0 -> {
                timerRunned.value = false
                return
            }
            x <= 0 -> arrayTimers.removeAt(0)
            x >= arrayTimers.count() -> arrayTimers.removeAt(arrayTimers.count()-1)
            else -> arrayTimers.removeAt(x)
        }

        var t = posMainTimer.value
        if (x <= t) t--
        if (arrayTimers.count() > 0 && t >= 0){
            posMainTimer.value = t
            mainTimer.value = arrayTimers[t]
        }
    }

    //метод вызываемый таймером
    fun timerTick(): Unit {
        if (arrayTimers == null || count.value <= 0) {
            timerRunned.value = false
            return
        } else if (activeTimer.value <= 0) {
            if (posActiveTimer.value >= arrayTimers.count()-1) {
                posActiveTimer.value=0
                activeTimer.value=arrayTimers[0]
                timerRunned.value = false
                return
            } else {
                posActiveTimer.value++
                activeTimer.value = arrayTimers[posActiveTimer.value]
            }
        } else activeTimer.value--
    }

    //чтение массива
    fun getArrayforEach (a: (Long) -> Unit) = arrayTimers.forEach(a)

}