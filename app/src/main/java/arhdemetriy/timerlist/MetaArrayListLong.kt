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
            get = {
                arrayTimers.count()
            },
            nullFlag = -1
        )


    val firstTimer: MineIOonLiveData<Long> =
        MineIOonLiveData<Long>(
            get = {
                if (arrayTimers.count() <= 0) {
                    -1
                } else {
                    arrayTimers.get(0)
                }
            },
            set = {
                if (arrayTimers.count() > 0) {
                    arrayTimers.set(0, it)
                    it
                } else -1
            },
            nullFlag = -1
        )


    private val l: (Int) -> Int = {
        when {
            arrayTimers.count() <= 0 -> -1
            it < 0 -> 0
            it >= arrayTimers.count() -> arrayTimers.count() - 1
            else -> it
        }
    }

    val posMainTimer: MineIOonLiveData<Int> =
        MineIOonLiveData<Int>(
            get = l,
            set = l,
            nullFlag = -1
        )


    val mainTimer: MineIOonLiveData<Long> =
        MineIOonLiveData<Long>(
            get = {
                if (count.value<=0 || posMainTimer.value < 0 //|| posMainTimer.value >= count.value
                ) {
                    0
                }
                else {
                    arrayTimers[posMainTimer.value]
                }
            },
            set = {
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

    val posActiveTimer: MineIOonLiveData<Int> =
        MineIOonLiveData<Int>(
            get = l,
            set = l,
            nullFlag = -1
        )

    val activeTimer: MineIOonLiveData<Long> =
        MineIOonLiveData<Long>(
            get = {
                if (it >= 0) it else 0},
            set = {
                if (it >= 0) it else 0},
            nullFlag = -1
        )

    val timerRunned: MineIOonLiveData<Boolean> =
        MineIOonLiveData<Boolean>(
            get = {
                (arrayTimers.count() > 0) && it
            },
            set = {
                (arrayTimers.count() > 0) && it
            },
            nullFlag = false
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
            x <= 0 -> {
                arrayTimers.add(0,t)
                posActiveTimer.value=0
                activeTimer.value = t
            }
            x >= (arrayTimers.count()) -> arrayTimers.add(t)

            else -> arrayTimers.add(x,t)
        }

        var temp = posMainTimer.value

        Log.d("addTimerAt","begin $temp, ${count.value}")
        if (temp >= x && temp < arrayTimers.count()-1 ) {
            posMainTimer.value = ++temp
            mainTimer.value = arrayTimers[temp]
        }
        count.value
    }

    fun delTimerAt(x: Int = arrayTimers.count()-1): Unit {
        when {
            arrayTimers.count() == 0 -> {
                timerRunned.value = false
                //count.value
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
        count.value
    }

    //метод вызываемый таймером
    fun timerTick(): Unit {
        if (count.value <= 0) {
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