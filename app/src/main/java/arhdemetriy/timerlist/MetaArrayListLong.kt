package arhdemetriy.timerlist

import androidx.lifecycle.ViewModel

class MetaArrayListLong : ViewModel() {

    //хранилище таймеров
    private val arrayTimers: ArrayList<Long> = ArrayList()

    //сущности на которые будут вешатся observers и public поля через которые читаются/меняются хранимые ими значения

    val count: MineIOonLiveData<Int> by lazy {
        MineIOonLiveData<Int>(
            get = {
                arrayTimers.count()
            },
            nullFlag = -1
        )
    }

    val firstTimer: MineIOonLiveData<Long> by lazy {
        MineIOonLiveData<Long>(
            get = {
                if (count.value <= 0) {
                    -1
                } else {
                    arrayTimers.get(0)
                }
            },
            set = {
                if (count.value > 0) {
                    arrayTimers.set(0, it)
                }else{
                    arrayTimers.add(it)
                    count.value = arrayTimers.count()
                }
                it
            },
            nullFlag = -1
        )
    }

    private val l: (Int) -> Int = {
        when {
            count.value <= 0 -> -1
            it < 0 -> 0
            it >= count.value -> count.value - 1
            else -> it
        }
    }

    val posMainTimer: MineIOonLiveData<Int> by lazy {
        MineIOonLiveData<Int>(
            get = l,
            nullFlag = -1
        )
    }

    val mainTimer: MineIOonLiveData<Long> by lazy {
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
    }

    val timerRunned: MineIOonLiveData<Boolean> by lazy {
        MineIOonLiveData<Boolean>(
            get = {
                (count.value > 0) && it
            },
            nullFlag = false
        )
    }

    val posActiveTimer: MineIOonLiveData<Int> by lazy {
        MineIOonLiveData<Int>(
            get = l,
            nullFlag = -1
        )
    }

    val activeTimer: MineIOonLiveData<Long> by lazy {
        MineIOonLiveData<Long>(
            get = {
                if (it >= 0) it else 0},
            nullFlag = -1
        )
    }

  /*  init {/*
        pCount.value=0
        pTimerRunned.value=false
        pFirstTimer.value=0
        pPosMainTimer.value=0
        pMainTimer.value=0
        pPosActiveTimer.value=0
        pActiveTimer.value=0*/
    }*/

    //методы редактирования хранилища таймеров
    fun addTimerAt(t: Long = 0,x: Int = arrayTimers.count()): Unit {
        when {
            arrayTimers == null -> {
                timerRunned.value = false
                return
                }
            x <= 0 -> arrayTimers.add(0,t)
            x >= (arrayTimers.count()) -> arrayTimers.add(t)

            else -> arrayTimers.add(x,t)
        }
        count.value = arrayTimers.count()

        if (x <= posMainTimer.value) posMainTimer.value++
        mainTimer.value = arrayTimers[posMainTimer.value]
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
        count.value = arrayTimers.count()

        if (x <= posMainTimer.value) posMainTimer.value--
        mainTimer.value = arrayTimers[posMainTimer.value]
    }

    //метод вызываемый таймером
    fun timerTick(): Unit {
        if (arrayTimers == null || count.value <= 0) {
            timerRunned.value = false
            return
        } else if (activeTimer.value <= 0) {
            if (posActiveTimer.value >= arrayTimers.count()-1) {
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