package arhdemetriy.timerlist
const val sec: Long = 60
const val delim = " : "
const val smDelim: String = ":"

class BaseTimer(){
    var longTimer: Long = 0

    var strTimer: String
        get() {
            val v0=longTimer%sec
            val v1=longTimer/sec%sec
            val v2=longTimer/(sec*sec)

            return "$v2$delim$v1$delim$v0"
        }
        set(v) {
            var t: Long =0
            var parts = v.split(delim)
            if (parts.size == 1) parts = v.split(smDelim)
            for (i in 1..parts.size){
                t += parts[parts.size-i].toLong() * when (i) {
                                                        1 -> 1
                                                        2 -> sec
                                                        3 -> sec*sec
                                                        4 -> 24*sec*sec
                                                        else -> 7*24*sec*sec
                                                    }
            }
            longTimer = t
        }

    constructor(timer: Long): this(){
        longTimer = timer
    }
    constructor(timer: String): this(){
        strTimer = timer
    }
}