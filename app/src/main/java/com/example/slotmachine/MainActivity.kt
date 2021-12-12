package com.example.slotmachine

import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.slotmachine.repository.Event
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), Event {

    private var count_down = 0
    private var click = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE


        image.setEvent(this@MainActivity)
        image1.setEvent(this@MainActivity)
        image2.setEvent(this@MainActivity)



        start.setOnClickListener {
            if (!click) {
                click = true

                start.setBackgroundColor(Color.GRAY)
                txt_score.visibility = View.VISIBLE


                if (Common.score >= 50) {
                    image.setValueRandom(
                        (Math.random() * (6 - 1) + 1).toInt(),
                        (Math.random() * (16 - 5 + 1) + 5).toInt()
                    )

                    image1.setValueRandom(
                        (Math.random() * (6 - 1) + 1).toInt(),
                        (Math.random() * (16 - 5 + 1) + 5).toInt()
                    )


                    image2.setValueRandom(
                        (Math.random() * (6 - 1) + 1).toInt(),
                        (Math.random() * (16 - 5 + 1) + 5).toInt()
                    )

                    Common.score -= 50
                    txt_score.text = Common.score.toString()

                } else {
                    Toast.makeText(this, "У вас нету средств!", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }


    override fun eventEnd(result: Int, count: Int) {


        if (count_down < 2) {
            count_down++
        } else {
            start.setBackgroundColor(Color.RED)

            count_down = 0

            if (image.value == image1.value && image1.value == image2.value) {
                Toast.makeText(this, "Вы получили большой приз", Toast.LENGTH_SHORT).show()
                Common.score += 100
                txt_score.text = Common.score.toString()
                click = false
            } else if (image.value == image1.value || image1.value == image2.value || image.value == image2.value) {
                Toast.makeText(this, "Вы получили маленький приз", Toast.LENGTH_SHORT).show()
                Common.score += 50
                txt_score.text = Common.score.toString()
                click = false
            } else {
                Toast.makeText(this, "Вы проиграли", Toast.LENGTH_SHORT).show()
                click = false
            }

        }
    }
}