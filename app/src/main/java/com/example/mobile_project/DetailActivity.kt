package psti.unram.tubesapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private lateinit var handler: Handler
    private var updateSeekBar: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val nama = intent.getStringExtra("nama") ?: ""
        val deskripsi = intent.getStringExtra("deskripsi") ?: ""
        val gambarResId = intent.getIntExtra("gambar", 0)

        val imgTokoh = findViewById<ImageView>(R.id.imgTokoh)
        val tvNama = findViewById<TextView>(R.id.tvNama)
        val tvDeskripsi = findViewById<TextView>(R.id.tvDeskripsi)
        val tvCerita = findViewById<TextView>(R.id.tvCerita)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        imgTokoh.setImageResource(gambarResId)
        tvNama.text = nama
        tvDeskripsi.text = deskripsi

        val textResId = when (nama) {
            "Ir. Soekarno" -> R.raw.soekarno
            "R.A Kartini" -> R.raw.kartini
            "Drs. Mohammad Hatta" -> R.raw.hatta
            else -> R.raw.soekarno
        }
        tvCerita.text = getDetailFromRaw(textResId)

        val audioResId = when (nama) {
            "Ir. Soekarno" -> R.raw.soekar
            "R.A Kartini" -> R.raw.kartini_audio
            "Drs. Mohammad Hatta" -> R.raw.hatta_audio
            else -> R.raw.soekar
        }

        handler = Handler(Looper.getMainLooper())

        btnPlay.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, audioResId)
                seekBar.max = mediaPlayer!!.duration
                mediaPlayer?.start()
                btnPlay.text = "Stop Audio"
                startSeekBarUpdate(seekBar)
            } else {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    btnPlay.text = "Play Audio"
                    stopSeekBarUpdate()
                } else {
                    mediaPlayer?.start()
                    btnPlay.text = "Stop Audio"
                    startSeekBarUpdate(seekBar)
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startSeekBarUpdate(seekBar: SeekBar) {
        updateSeekBar = object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    seekBar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 500)
                }
            }
        }
        handler.post(updateSeekBar!!)
    }

    private fun stopSeekBarUpdate() {
        updateSeekBar?.let { handler.removeCallbacks(it) }
    }

    private fun getDetailFromRaw(resId: Int): String {
        return resources.openRawResource(resId).bufferedReader().use { it.readText() }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        stopSeekBarUpdate()
    }
}
