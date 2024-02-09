package objects

import com.raylib.Jaylib
import com.raylib.Raylib

class Paddle(var x: Int, var y: Int, var width: Int, var height: Int, var speed: Int, var update: (paddle: Paddle) -> Unit) {
    fun draw() {
        Raylib.DrawRectangleRounded(Jaylib.Rectangle(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat()), 0.8F, 0, Jaylib.WHITE)
    }

    fun update() {
        update.invoke(this)
    }
}