package com.example.games

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.example.game.GameTask
import java.lang.Exception

class GameView(var c :Context,var gameTask: GameTask):View(c)
{
    public var myPaint:Paint? = null
    public var speed = 1
    public var time = 0
    public var score = 0
    public var myBall00nPosition = 0
    public val othercactus = ArrayList<HashMap<String,Any>>()

    var viewWidth = 0
    var viewHeight = 0
    init{
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if(time % 700 < 10 +speed){
            val map = HashMap<String,Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            othercactus.add(map)
        }
        time = time + 10 + speed
        val carWidth = viewWidth / 5
        val carHeight = carWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.balloon,null)

        d.setBounds(
            myBall00nPosition * viewWidth / 3 +viewWidth / 15 + 25,
            viewHeight-2 - carHeight,
            myBall00nPosition * viewWidth / 3 + viewWidth / 15 + carWidth - 25,
            viewHeight - 2
        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for(i in othercactus.indices){
            try{
                val cactusX= othercactus[i]["lane"]as Int * viewWidth / 3 + viewWidth /15
                val cactusY = time - othercactus[i]["startTime"]as Int
                val d2 = resources.getDrawable(R.drawable.cactusimage,null)
                d2.setBounds(
                    cactusX + 25 , cactusY - carHeight, cactusX + carWidth -25, cactusY,
                )
                d2.draw(canvas!!)
                if(othercactus[i]["lane"]as Int == myBall00nPosition){
                    if(cactusY > viewHeight - 2 - carHeight
                        && cactusY < viewHeight - 2){

                        gameTask.closeGame((score))
                    }
                }
                if (cactusY > viewHeight + carHeight)
                {
                    othercactus.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if(score > highScore){
                        highScore = score
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }

        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score",80f,80f,myPaint!!)
        canvas.drawText("Speed : $speed",380f,80f,myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myBall00nPosition > 0) {
                        myBall00nPosition--
                    }
                }
                if(x1 > viewWidth / 2){
                    if(myBall00nPosition<2) {
                        myBall00nPosition++
                    }
                }


            }
            MotionEvent.ACTION_UP -> {}
        }


        return true

    }
}
