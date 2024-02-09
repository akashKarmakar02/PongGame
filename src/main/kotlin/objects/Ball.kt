package objects

import com.raylib.Jaylib
import com.raylib.Raylib

class Ball(var x: Int, var y: Int, var radius: Int, var speedX: Int, var speedY: Int) {
    fun draw() {
        Raylib.DrawCircle(x, y, radius.toFloat(), Jaylib.WHITE)
    }

    fun update() {
        x += speedX
        y += speedY

        if (y + radius >= Raylib.GetScreenHeight() || y - radius <= 0) {
            speedY *= -1
        }
        if (x + radius >= Raylib.GetScreenWidth() || x - radius <= 0) {
            speedX *= -1
        }
    }
}