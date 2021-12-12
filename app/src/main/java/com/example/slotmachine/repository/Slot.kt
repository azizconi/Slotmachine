package com.example.slotmachine.repository

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.slotmachine.R
import kotlinx.android.synthetic.main.slot_scrolling.view.*

class Slot : FrameLayout {
    private lateinit var event: Event

    private var last_result = 0
    private var oldValue = 0

    companion object {
        private const val ANIMATION_DURATION = 150
    }

    val value: Int
        get() = Integer.parseInt(nextImg.tag.toString())

    fun setEvent(event: Event) {
        this.event = event
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.slot_scrolling, this)

        nextImg.translationY = height.toFloat()
    }


    fun setValueRandom(image: Int, num_rotate: Int) {
        currentImg.animate()
            .translationY((-height).toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        nextImg.translationY = nextImg.height.toFloat()

        nextImg.animate().translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    setImage(currentImg, oldValue % 6)

                    currentImg.translationY = 0f
                    if (oldValue != num_rotate) {
                        setValueRandom(image, num_rotate)
                        oldValue++
                    } else {
                        last_result = 0
                        oldValue = 0
                        setImage(nextImg, image)
                        event.eventEnd(image % 6, num_rotate)
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationRepeat(p0: Animator?) {

                }


            }).start()

    }

    private fun setImage(image: ImageView?, value: Int) {
        when (value) {
            Util.bar -> currentImg!!.setImageResource(R.drawable.bar)
            Util.lemon -> currentImg!!.setImageResource(R.drawable.lemon)
            Util.orange -> currentImg!!.setImageResource(R.drawable.orange)
            Util.seven -> currentImg!!.setImageResource(R.drawable.seven)
            Util.triple -> currentImg!!.setImageResource(R.drawable.triple_seven)
            Util.watermelon -> currentImg!!.setImageResource(R.drawable.watermelon)

        }

        image!!.tag = value
        last_result = value
    }

}