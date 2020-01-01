package arhdemetriy.timerlist


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private var timerList : MutableLiveData<Long> = MutableLiveData()

    //инициализируем список
    init { timerList.value = 0 }

    fun getNumber() = timerList

    fun setNumber (n: Long){ timerList.value = n }




}