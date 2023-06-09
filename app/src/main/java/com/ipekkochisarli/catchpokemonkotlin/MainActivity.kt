package com.ipekkochisarli.catchpokemonkotlin

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ipekkochisarli.catchpokemonkotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var runnable: Runnable = Runnable { }
    var handler: Handler = Handler()

    var score = 0
    val imageArray = mutableListOf<ImageView>()
    val random = java.util.Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // pass images to array list for easy access
        imageArray.apply {
            add(binding.imageView1)
            add(binding.imageView2)
            add(binding.imageView3)
            add(binding.imageView4)
            add(binding.imageView5)
            add(binding.imageView6)
            add(binding.imageView7)
            add(binding.imageView8)
            add(binding.imageView9)
        }
        // hide images when app start
        hideImages()

        object : CountDownTimer(15500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = "Time = ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                binding.timerTextView.text = "Time = 0"
                // stop runnable when time is up
                handler.removeCallbacks(runnable)
                // hide all images
                binding.gridLayout.visibility = View.INVISIBLE

                // end game alert
                var alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart Game?")
                alert.setPositiveButton("Yes") { dialog, which ->
                    // to restart the game we need to restart the ACTIVITY
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_LONG).show()
                    finish()
                }
                alert.show()
            }
        }.start()
    }

    // used runnable for hide images to not block main thread
    fun hideImages() {
        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable, 300)
            }
        }
        handler.post(runnable)
    }

    // when user click on image, score will increase
    fun onPokemonClick(view: View) {
        score += 1
        binding.scoreTextView.text = "Score = ${score}"
    }
}