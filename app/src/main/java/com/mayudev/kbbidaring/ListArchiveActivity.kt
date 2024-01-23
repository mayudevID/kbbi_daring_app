package com.mayudev.kbbidaring

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mayudev.kbbidaring.adapter.ListArchiveAdapter
import com.mayudev.kbbidaring.room.Kosakata
import com.mayudev.kbbidaring.viewmodel.KosakataViewModel

class ListArchiveActivity : AppCompatActivity() {
    private lateinit var rvListArchive: RecyclerView
    private lateinit var listArchiveAdapter: ListArchiveAdapter
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var kosakataViewModel: KosakataViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_archive)
        supportActionBar?.hide()

        layoutEmpty = findViewById(R.id.arsip_kosong)

        val buttonBack = findViewById<View>(R.id.back)
        buttonBack.setOnClickListener {
            finish()
        }

        setupRecyclerView()

        kosakataViewModel = ViewModelProvider(this)[KosakataViewModel::class.java]
        kosakataViewModel.getAllKosakataObservers().observe( this, Observer {
            val data = it?.let { it1 -> ArrayList(it1) }
            if (data != null) {
                if (data.isEmpty()) {
                    rvListArchive.isVisible = false
                    layoutEmpty.isVisible = true
                } else {
                    listArchiveAdapter.setData(data)
                    layoutEmpty.isVisible = false
                    rvListArchive.isVisible = true
                }
            }
            listArchiveAdapter.notifyDataSetChanged()
        } )
    }

//    private fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val listKosakata = db.kosakataDB().getKosakata()
//            Log.d("MainActivity","dbResponse: $listKosakata")
//            withContext(Dispatchers.Main) {
//                listArchiveAdapter.setData(listKosakata)
//            }
//
//        }
//    }

    private fun setupRecyclerView() {
        listArchiveAdapter = ListArchiveAdapter(arrayListOf())
        rvListArchive = findViewById(R.id.rvListArchive)
        rvListArchive.layoutManager = LinearLayoutManager(this)
        rvListArchive.adapter = listArchiveAdapter

        listArchiveAdapter.setOnItemClickCallback(object : ListArchiveAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Kosakata) {
                val intent = Intent(applicationContext, VocabDetailActivity::class.java).apply {
                    putExtra("VOCAB", data.vocab)
                    putExtra("LEMA", data.lema)
                    putStringArrayListExtra("ARTI", ArrayList(data.arti))
                }
                startActivity(intent)
            }

            override fun onItemDeleted(data: Kosakata) {
//                CoroutineScope(Dispatchers.IO).launch {
//                    db.kosakataDB().deleteKosakata(data)
//                    loadData()
//                }
                kosakataViewModel.deleteData(data)
                val snack = Snackbar.make(findViewById<ConstraintLayout>(R.id.mainView),"\"${data.vocab}\" dihapus dari Arsip" ,
                    Snackbar.LENGTH_SHORT)
                snack.show()
            }
        })
    }


}