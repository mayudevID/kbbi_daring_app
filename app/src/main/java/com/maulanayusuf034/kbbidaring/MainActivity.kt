

package com.maulanayusuf034.kbbidaring

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.maulanayusuf034.kbbidaring.retrofit.ApiService
import com.maulanayusuf034.kbbidaring.room.Kosakata
import com.maulanayusuf034.kbbidaring.room.KosakataDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*


class MainActivity : AppCompatActivity() {
    private val tagApp: String = "MainActivity"
    private val db by lazy { KosakataDB(this) }
    private lateinit var rvArti: RecyclerView
    private lateinit var progressCircular: ProgressBar
    private lateinit var resultFind: LinearLayout
    private lateinit var resultNotFound: LinearLayout
    private lateinit var initResult: LinearLayout
    private lateinit var lema: TextView
    private lateinit var vocab: TextView
    private var listArti: ArrayList<String> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFindBy()
        setupVisible()
        setupListener()
//        val pBold = ResourcesCompat.getFont(this.baseContext, R.font.poppins_bold)
//        val pMedium = ResourcesCompat.getFont(this.baseContext, R.font.poppins_medium)
//        val pReg = ResourcesCompat.getFont(this.baseContext, R.font.poppins_regular)
//        val pSemiBold = ResourcesCompat.getFont(this.baseContext, R.font.poppins_semibold)
//
//        setFont(arrayOf(pReg, pMedium, pSemiBold, pBold))
    }

    private fun setupFindBy() {
        rvArti = findViewById(R.id.rvArti)
        rvArti.setHasFixedSize(true)

        vocab = findViewById(R.id.vocab)
        lema = findViewById(R.id.lemma)
        progressCircular = findViewById(R.id.progress_circular)
        resultFind = findViewById(R.id.resultFind)
        resultNotFound = findViewById(R.id.resultNotFound)
        initResult = findViewById(R.id.initResult)
    }

    private fun setupVisible() {
        progressCircular.isVisible = false
        progressCircular.isIndeterminate = true
        initResult.isVisible = true
        resultFind.isVisible = false
        resultNotFound.isVisible = false
    }

    private fun setupListener() {
        val textData = findViewById<EditText>(R.id.editTextKosaKata)

        val buttonFind = findViewById<Button>(R.id.findButton)
        buttonFind.setOnClickListener {
            val getData: String = textData.text.toString().trim().lowercase()
            vocab.text = getData
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
            if (getData.isEmpty() || getData == "") {
                val snack = Snackbar.make(it,"Mohon isi kosakata yang ingin dicari",Snackbar.LENGTH_SHORT)
                snack.show()
            } else {
                initResult.isVisible = false
                resultFind.isVisible = false
                resultNotFound.isVisible = false
                progressCircular.isVisible = true
                getDataFromApi(getData)
            }
        }

        val buttonInfo = findViewById<View>(R.id.buttonInfo)
        buttonInfo.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setMessage("Nama: Maulana Yusuf\nNIM: 2010512034\nPraktikum Pemrograman Mobile - A")
                .setCancelable(false)
                .setPositiveButton("Tutup") { _, _ ->

                }

            val alert = dialogBuilder.create()
            alert.setTitle("Informasi")
            alert.show()
        }

        val buttonAddKosakata = findViewById<View>(R.id.addArchive)
        buttonAddKosakata.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.kosakataDB().addKosakata(
                    Kosakata(0, vocab.text.toString(), lema.text.toString(), listArti)
                )
            }
        }

        val activityArchiveButton = findViewById<View>(R.id.buttonArchive)
        activityArchiveButton.setOnClickListener {
            val intent = Intent(this, ListArchiveActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDataFromApi(data: String) {
        ApiService.endpoint.getDataMean(data).enqueue(object: Callback<MainModel> {
            override fun onResponse(call: Call<MainModel>, response: Response<MainModel>) {
                if (response.isSuccessful) {
                    printLog(response.body().toString() + "SUCCESS MESSAGE")

                    listArti.clear()

                    response.body()?.let { listArti.addAll(it.arti) }
                    rvArti.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvArti.adapter = ListResultAdapter(listArti)

                    lema.text = response.body()?.lema

                    progressCircular.isVisible = false
                    resultFind.isVisible = true

                    val deleteVocab = findViewById<View>(R.id.deleteVocab)
                    deleteVocab.setOnClickListener {
                        resultFind.isVisible = false
                        listArti.clear()
                        lema.text = ""
                        initResult.isVisible = true
                    }
                }
            }

            override fun onFailure(call: Call<MainModel>, t: Throwable) {
                printLog(t.toString() + "ERROR MESSAGE")
                progressCircular.isVisible = false
                resultNotFound.isVisible = true
            }

        })
    }

    fun printLog(message: String) {
        Log.d(tagApp, message)
    }

    //    private fun setFont(listFont: Array<Typeface?>) {
//        val textView1 = findViewById<TextView>(R.id.kbbi_daring)
//        textView1.typeface = listFont[3]
//
//        val textView2 =  findViewById<TextView>(R.id.kbbi)
//        textView2.typeface = listFont[1]
//
//        // val textView3 = findViewById<TextView>(R.id.editTextKosaKata)
//        // textView1.typeface = listFont[0]
//
//        val findButton = findViewById<Button>(R.id.findButton)
//        findButton.typeface = listFont[0]
//
//        val lemma = findViewById<TextView>(R.id.lemma)
//        lemma.typeface = listFont[2]
//
//        // val artiKata = findViewById<TextView>(R.id.arti_kata)
//        // artiKata.typeface = listFont[1]
//    }
}