package com.maulanayusuf034.kbbidaring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.maulanayusuf034.kbbidaring.room.Kosakata
import com.maulanayusuf034.kbbidaring.room.KosakataDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListArchiveActivity : AppCompatActivity() {
    private val db by lazy { KosakataDB(this) }
    private lateinit var rvListArchive: RecyclerView
    private lateinit var listArchiveAdapter: ListArchiveAdapter
    private lateinit var textEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_archive)

        //textEmpty = findViewById(R.id.arsip_kosong)

        val buttonBack = findViewById<View>(R.id.back)
        buttonBack.setOnClickListener {
            finish()
        }

        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val listKosakata = db.kosakataDB().getKosakata()
            Log.d("MainActivity","dbResponse: $listKosakata")
            withContext(Dispatchers.Main) {
                listArchiveAdapter.setData(listKosakata)
            }

        }
    }

    private fun setupRecycleView() {
        listArchiveAdapter = ListArchiveAdapter(arrayListOf())
        rvListArchive = findViewById(R.id.rvListArchive)
        rvListArchive.layoutManager = LinearLayoutManager(this)
        rvListArchive.adapter = listArchiveAdapter

        listArchiveAdapter.setOnItemClickCallback(object : ListArchiveAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Kosakata) {
                // MOVE
            }

            override fun onItemDeleted(data: Kosakata) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.kosakataDB().deleteKosakata(data)
                    loadData()
                }
                val snack = Snackbar.make(findViewById<ConstraintLayout>(R.id.mainView),"\"${data.vocab}\" dihapus dari Arsip" ,
                    Snackbar.LENGTH_SHORT)
                snack.show()
            }
        })
    }


}