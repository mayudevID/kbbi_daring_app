package com.maulanayusuf034.kbbidaring.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maulanayusuf034.kbbidaring.room.Kosakata
import com.maulanayusuf034.kbbidaring.room.KosakataDB

class KosakataViewModel(app: Application) : AndroidViewModel(app) {
    private var allKosakata : MutableLiveData<List<Kosakata>?> = MutableLiveData()

    init {
        getAllKosakata()
    }

    fun getAllKosakataObservers(): MutableLiveData<List<Kosakata>?> {
        return allKosakata
    }

    fun getAllKosakata() {
        val adata = KosakataDB.getAppDatabase(getApplication())?.kosakataDB()
        val listData = adata?.getKosakata()

        allKosakata.postValue(listData)
    }

    fun insertData(data: Kosakata) {
        val adata = KosakataDB.getAppDatabase(getApplication())?.kosakataDB()
        adata?.addKosakata(data)
        getAllKosakata()
    }

    fun deleteData(data: Kosakata) {
        val adata = KosakataDB.getAppDatabase(getApplication())?.kosakataDB()
        adata?.deleteKosakata(data)
        getAllKosakata()
    }
}