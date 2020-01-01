package arhdemetriy.timerlist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MetaArrayListLong : ViewModel() {

    //хранилище таймеров
    private val arrayTimers: ArrayList<Long> = ArrayList()

    //сущности на которые будут вешатся observers и public поля через которые читаются/меняются хранимые ими значения
    private val pCount: MutableLiveData<Int> = MutableLiveData<Int>()
    fun observeCount(x: LifecycleOwner, y: Observer<in Int>) = pCount.observe(x,y)
    var count: Int
        get() {
            pCount.value = arrayTimers.count()
            if (arrayTimers.count() <= 0) pTimerRunned.value = false
            return arrayTimers.count()}
        set(value) {
            pCount.value = arrayTimers.count()
            if (arrayTimers.count() <= 0) pTimerRunned.value = false}

    private val pFirstTimer: MutableLiveData<Long> = MutableLiveData<Long>()
    fun observeFirstTimer(x: LifecycleOwner, y: Observer<in Long>) = pFirstTimer.observe(x,y)
    var firstTimer: Long
        get() {
            if (count <= 0) {
                pFirstTimer.value = -1
                return -1
            }

            pFirstTimer.value = arrayTimers.get(0)
            return arrayTimers.get(0)
        }
        set(value) {
            if (count > 0) {
                arrayTimers.set(0, value)
                pFirstTimer.value = arrayTimers.get(0)
            }else{
                arrayTimers.add(value)
                count = arrayTimers.count()
                pFirstTimer.value = arrayTimers.get(0)
            }
        }

    private val pPosMainTimer: MutableLiveData<Int> = MutableLiveData<Int>()
    fun observePosMainTimer(x: LifecycleOwner, y: Observer<in Int>) = pPosMainTimer.observe(x,y)
    var posMainTimer: Int
        get() = when {
                    count <= 0 -> {
                        pPosMainTimer.value = -1
                        -1
                    }
                    pPosMainTimer.value!! < 0 -> {
                        pPosMainTimer.value = 0
                        0
                    }
                    pPosMainTimer.value!! > count-1 -> {
                        pPosMainTimer.value = count-1
                        count-1
                    }
                    else -> pPosMainTimer.value!!
                }

        set(value) {
            when {
                count <= 0 -> pPosMainTimer.value = -1
                value < 0 -> pPosMainTimer.value = 0
                value > count-1 -> pPosMainTimer.value = count-1
                else -> pPosMainTimer.value = value
            }
        }

    private val pMainTimer: MutableLiveData<Long> = MutableLiveData<Long>()
    fun observeMainTimer(x: LifecycleOwner, y: Observer<in Long>) = pMainTimer.observe(x,y)
    var mainTimer: Long
        get() = if (count<=0 || posMainTimer < 0 || posMainTimer > count-1) {
                    pMainTimer.value = 0
                    0
                }
                else {
                    pMainTimer.value = arrayTimers[posMainTimer]
                    arrayTimers[posMainTimer]
                }
        set(value) {
            if (count<=0 || posMainTimer < 0 || posMainTimer >= count) {
                pMainTimer.value = 0
            }
            else {
                arrayTimers[posMainTimer] = value
                pMainTimer.value = arrayTimers[posMainTimer]
            }
        }

    private val pTimerRunned: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    fun observeTimerRunned(x: LifecycleOwner, y: Observer<in Boolean>) = pTimerRunned.observe(x,y)
    var timerRunned: Boolean
        get() {
            if (count <= 0) pTimerRunned.value = false
            return pTimerRunned.value!!
        }
        set(value) {pTimerRunned.value = value && arrayTimers.count() > 0}

    val testTimerRunned: MineIOonLiveData<Boolean> by lazy {
        MineIOonLiveData<Boolean>(
            get = {

                it
            },
            set = {
                it
            },
            nullFlag = false
        )
    }


    private val pPosActiveTimer: MutableLiveData<Int> = MutableLiveData<Int>()
    fun observePosActiveTimer(x: LifecycleOwner, y: Observer<in Int>) = pPosActiveTimer.observe(x,y)
    var posActiveTimer: Int
        get() = when {
            count <= 0 -> {
                pPosActiveTimer.value = -1
                -1
            }
            pPosActiveTimer.value!! < 0 -> {
                pPosActiveTimer.value = 0
                0
            }
            pPosActiveTimer.value!! > count-1 -> {
                pPosActiveTimer.value = count-1
                count-1
            }
            else -> pPosActiveTimer.value!!
        }

        set(value) {
            when {
                count <= 0 -> pPosActiveTimer.value = -1
                value < 0 -> pPosActiveTimer.value = 0
                value > count-1 -> pPosActiveTimer.value = count-1
                else -> pPosActiveTimer.value = value
            }
        }

    private val pActiveTimer: MutableLiveData<Long> = MutableLiveData<Long>()
    fun observeActiveTimer(x: LifecycleOwner, y: Observer<in Long>) = pActiveTimer.observe(x,y)
    var activeTimer: Long
        get() = if (pActiveTimer.value!! >= 0) pActiveTimer.value!! else 0
        set(value) {
            pActiveTimer.value = if (value >= 0) value else 0

        }




    init {
        pCount.value=0
        pTimerRunned.value=false
        pFirstTimer.value=0
        pPosMainTimer.value=0
        pMainTimer.value=0
        pPosActiveTimer.value=0
        pActiveTimer.value=0
    }

    //методы редактирования хранилища таймеров
    fun addTimerAt(t: Long = 0,x: Int = count): Unit {
        if (arrayTimers == null) {
            timerRunned = false
            return
        }
        when {
            x >= (arrayTimers.count()) -> arrayTimers.add(t)
            x <= 0 -> arrayTimers.add(0,t)
            else -> arrayTimers.add(x,t)
        }
        count = arrayTimers.count()

        if (x <= posMainTimer) posMainTimer++
        pMainTimer.value = arrayTimers[posMainTimer]

        activeTimer = arrayTimers[posActiveTimer]


    }

    fun delTimerAt(x: Int = count-1): Unit {
        when {
            arrayTimers == null || count == 0 -> {
                timerRunned = false
                return
            }
            x <= 0 -> arrayTimers.removeAt(0)
            x >= count -> arrayTimers.removeAt(count-1)
            else -> arrayTimers.removeAt(x)
        }
        count = arrayTimers.count()

        if (x <= posMainTimer) posMainTimer--
        mainTimer = mainTimer



        if (posActiveTimer >=0) activeTimer = arrayTimers[posActiveTimer]

    }

    //метод вызываемый таймером
    fun timerTick(): Unit {
        if (arrayTimers == null || count <= 0) {
            timerRunned = false
            return
        } else if (activeTimer <= 0) {
            if (posActiveTimer >= count-1) {
                timerRunned = false
                return
            } else {
                posActiveTimer++
                activeTimer = arrayTimers[posActiveTimer]
            }
        } else activeTimer--
    }

    //чтение массива
    fun getArrayforEach (a: (Long) -> Unit) = arrayTimers.forEach(a)

}