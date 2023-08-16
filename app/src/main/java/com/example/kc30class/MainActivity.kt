package com.example.kc30class

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import com.example.kc30class.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieStatus: TextView
    private lateinit var movieTitle: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieRating: TextView
    private lateinit var coverImage: ImageView
    private lateinit var descriptionButton: ImageButton
    private lateinit var root: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addConstraintSetAnimation()
    }

    private fun  addConstraintSetAnimation() {
            movieStatus = binding.status
            movieTitle = binding.movieTitle
            movieDescription = binding.desc
            coverImage = binding.cover
            movieRating = binding.rating
            descriptionButton = binding.descriptionButton
            root = binding.root

        var isCoverView = false
        var isDescriptionView = false

        val initialConstraint = ConstraintSet()
        initialConstraint.clone(root)
g
        val coverConstraint = ConstraintSet()
        coverConstraint.clone(this, R.layout.cover_layout)

        val descriptionConstraint = ConstraintSet()
        descriptionConstraint.clone(this, R.layout.description_view)

        coverImage.setOnClickListener {
            if(!isCoverView) {
                TransitionManager.beginDelayedTransition(root)
                coverConstraint.applyTo(root)

                val anim = ValueAnimator()
                anim.setIntValues(Color.BLACK, Color.WHITE)
                anim.setEvaluator(ArgbEvaluator())
                anim.addUpdateListener {
                    binding.menuButton.setColorFilter(it.animatedValue as Int)
                    movieStatus.setTextColor(it.animatedValue as Int)
                    movieTitle.setTextColor(it.animatedValue as Int)
                    movieDescription.setTextColor(it.animatedValue as Int)
                    movieRating.setTextColor(it.animatedValue as Int)
                    descriptionButton.setColorFilter(it.animatedValue as Int)

                }

                anim.duration = 300
                anim.start()
                isCoverView = true
                isDescriptionView = false
            }
        }

        binding.menuButton.setOnClickListener {
            if(isCoverView) {
                TransitionManager.beginDelayedTransition(root)
                initialConstraint.applyTo(root)

                val anim = ValueAnimator()
                anim.setIntValues(Color.WHITE, Color.BLACK)
                anim.setEvaluator(ArgbEvaluator())
                anim.addUpdateListener {
                    binding.menuButton.setColorFilter(it.animatedValue as Int)
                    movieStatus.setTextColor(it.animatedValue as Int)
                    movieTitle.setTextColor(it.animatedValue as Int)
                    movieDescription.setTextColor(it.animatedValue as Int)
                    movieRating.setTextColor(it.animatedValue as Int)
                    descriptionButton.setColorFilter(it.animatedValue as Int)

                }

                anim.duration = 300
                anim.start()
                isCoverView = false
                isDescriptionView = false
            } else if(isDescriptionView) {
                TransitionManager.beginDelayedTransition(root)
                initialConstraint.applyTo(root)

                isCoverView = false
                isDescriptionView = false
            }
        }

        descriptionButton.setOnClickListener {
            if(!isDescriptionView) {
                TransitionManager.beginDelayedTransition(root)
                descriptionConstraint.applyTo(root)

                if(isCoverView) {
                    val anim = ValueAnimator()
                    anim.setIntValues(Color.BLACK, Color.WHITE)
                    anim.setEvaluator(ArgbEvaluator())
                    anim.addUpdateListener {
                        binding.menuButton.setColorFilter(it.animatedValue as Int)
                        movieStatus.setTextColor(it.animatedValue as Int)
                        movieTitle.setTextColor(it.animatedValue as Int)
                        movieDescription.setTextColor(it.animatedValue as Int)
                        movieRating.setTextColor(it.animatedValue as Int)
                        descriptionButton.setColorFilter(it.animatedValue as Int)

                    }

                    anim.duration = 300
                    anim.start()
                    isCoverView = false
                }

                isDescriptionView = true
            }
        }

        val mapOfDays: Map<TextView, TextView>  = mapOf(
            binding.day1 to binding.date1,
            binding.day2 to binding.date2,
            binding.day3 to binding.date3,
        )

        val days: List<TextView> = listOf(binding.day1,binding.day2, binding.day3)

        for(day in days) {
            day.setOnClickListener { selectDate(it as TextView, descriptionConstraint) }
        }

        for(day in mapOfDays) {
            day.value.setOnClickListener { selectDate(day.key, descriptionConstraint) }
        }

    }

    fun selectDate(day: TextView, destinationConstraint: ConstraintSet) {
        destinationConstraint.connect(
            binding.dateSelector.id,
            ConstraintSet.START,
            day.id,
            ConstraintSet.START
        )

        destinationConstraint.connect(
            binding.dateSelector.id,
            ConstraintSet.END,
            day.id,
            ConstraintSet.END
        )
        TransitionManager.beginDelayedTransition(root)
        destinationConstraint.applyTo(root)
    }

}