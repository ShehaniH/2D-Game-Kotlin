package com.example.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.BoringLayout
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.game.GameTask
import android.content.SharedPreferences
import android.content.Context

class MainActivity : AppCompatActivity(),GameTask{
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn:Button
    lateinit var mGameView: GameView
    lateinit var highScoreTextView:TextView
    lateinit var score: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private var highScore:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.activity_main))
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.highScore)
        sharedPreferences = getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)
        // Retrieve the high score from SharedPreferences
        highScore = sharedPreferences.getInt("highScore", 0)
        highScoreTextView.text = "High Score: $highScore"

        mGameView = GameView(this,this)

        startBtn.setOnClickListener {
            // Reset the score
            score.text = "Score:0"

            // Reset the variables in the GameView class
            mGameView= GameView(this,this)
            mGameView.time = 0
            mGameView.score = 0
            mGameView.speed = 1
            mGameView.othercactus.clear()

            // Add the GameView to the root layout
            mGameView.setBackgroundResource(R.drawable.images)
            rootLayout.addView(mGameView)

            // Hide the Start button and score TextView
            startBtn.visibility = View.GONE
            score.visibility = View.GONE

            // Invalidate the GameView to redraw it
            mGameView.invalidate()
        }
    }

    override fun closeGame(mscore: Int) {
        score.text="Score : $mscore"
        if (mscore > highScore) {
            highScore = mscore
            highScoreTextView.text = "High Score: $highScore"
            // Save the new high score to SharedPreferences
            sharedPreferences.edit().putInt("highScore", highScore).apply()
        }
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
    }


}