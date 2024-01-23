package com.mayudev.kbbidaring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mayudev.kbbidaring.adapter.ListResultAdapter

class VocabDetailActivity : AppCompatActivity() {

    private lateinit var rvArtiDetail: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocab_detail)

        supportActionBar?.hide()

        val buttonBack = findViewById<View>(R.id.back_from_detail)
        buttonBack.setOnClickListener {
            finish()
        }

        val title =  findViewById<TextView>(R.id.vocab_detail)
        title.text = intent.getStringExtra("VOCAB")

        val lema = findViewById<TextView>(R.id.lema_detail)
        lema.text = intent.getStringExtra("LEMA")

        rvArtiDetail = findViewById(R.id.rv_arti_details)
        rvArtiDetail.setHasFixedSize(true)
        rvArtiDetail.layoutManager = LinearLayoutManager(this)

        val listArti = intent.getStringArrayListExtra("ARTI")
        rvArtiDetail.adapter = listArti?.let { ListResultAdapter(it) }
    }
}