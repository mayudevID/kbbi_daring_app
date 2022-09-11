package com.maulanayusuf034.kbbidaring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maulanayusuf034.kbbidaring.room.KosakataDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListArchiveActivity : AppCompatActivity() {
    val db by lazy { KosakataDB(this) }
    private lateinit var rvListArchive: RecyclerView
    private lateinit var listArchiveAdapter: ListArchiveAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_archive)

        val buttonBack = findViewById<View>(R.id.back)
        buttonBack.setOnClickListener {
            finish()
        }

        setupRecycleView()
    }

    private fun setupRecycleView() {
        listArchiveAdapter = ListArchiveAdapter(arrayListOf())
        rvListArchive = findViewById(R.id.rvListArchive)
        rvListArchive.layoutManager = LinearLayoutManager(this)
        rvListArchive.adapter = listArchiveAdapter
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val listKosakata = db.kosakataDB().getKosakata()
            Log.d("MainActivity","dbResponse: $listKosakata")
            withContext(Dispatchers.Main) {
                listArchiveAdapter.setData(listKosakata)
            }
        }
    }
}