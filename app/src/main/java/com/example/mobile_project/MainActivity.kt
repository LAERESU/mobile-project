package psti.unram.tubesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TokohAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataTokoh = listOf(
            Tokoh("Ir. Soekarno", "Bapak Proklamator Indonesia", R.drawable.soekarno),
            Tokoh("Drs. Mohammad Hatta", "Tokoh Perjuangan", R.drawable.hatta),
            Tokoh("R.A Kartini", "Tokoh Perempuan Indonesia", R.drawable.kartini)
            // Tambahkan data lainnya jika perlu
        )

        adapter = TokohAdapter(dataTokoh)
        recyclerView.adapter = adapter
    }
}
