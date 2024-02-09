package org.example

import com.raylib.Jaylib
import com.raylib.Jaylib.WHITE
import com.raylib.Raylib.*
import objects.Ball
import objects.Paddle

const val WIDTH = 1280
const val HEIGHT = 800

var playerScore = 0
var cpuScore = 0

val ball = Ball(WIDTH / 2, HEIGHT / 2, 20, 7, 7)

val GREEN = Jaylib.Color(38, 185, 154, 255)
val DARK_GREEN = Jaylib.Color(20, 160, 133, 255)
val LIGHT_GREEN = Jaylib.Color(129, 204, 184, 255)
val YELLOW = Jaylib.Color(243, 213, 91, 255)
var IS_PAUSED = true

fun main() {
    InitWindow(WIDTH, HEIGHT, "Pong Game")
    SetTargetFPS(60)

    val player = Paddle(WIDTH - 25 - 10, HEIGHT / 2 - 120 / 2, 25, 120, 6) { player: Paddle? ->
        if (IsKeyDown(KEY_UP) && player!!.y >= 0) {
            player.y -= player.speed
        }
        if (IsKeyDown(KEY_DOWN) && player!!.y + player.height <= GetScreenHeight()) {
            player.y += player.speed
        }
    }
    val cpuPlayer = Paddle(10, HEIGHT / 2 - 120 / 2, 25, 120, 6) { paddle: Paddle? ->
        if (paddle!!.y + paddle.height / 2 > ball.y) {
            paddle.y -= paddle.speed
        }
        if (paddle.y + paddle.height <= ball.y) {
            paddle.y += paddle.speed
        }
    }

    while (!WindowShouldClose()) {
        BeginDrawing()

        // Checking for collisions
        if (CheckCollisionCircleRec(
                Jaylib.Vector2(ball.x.toFloat(), ball.y.toFloat()),
                ball.radius.toFloat(),
                Jaylib.Rectangle(
                    player.x.toFloat(),
                    player.y.toFloat(),
                    player.width.toFloat(),
                    player.height.toFloat()
                )
            )
        ) {
            ball.speedX *= -1
        }

        if (CheckCollisionCircleRec(
                Jaylib.Vector2(ball.x.toFloat(), ball.y.toFloat()),
                ball.radius.toFloat(),
                Jaylib.Rectangle(
                    cpuPlayer.x.toFloat(),
                    cpuPlayer.y.toFloat(),
                    cpuPlayer.width.toFloat(),
                    cpuPlayer.height.toFloat()
                )
            )
        ) {
            ball.speedX *= -1
        }


        // Drawing
        ClearBackground(DARK_GREEN)
        DrawRectangle(WIDTH / 2, 0, WIDTH / 2, HEIGHT, GREEN)
        DrawCircle(WIDTH / 2, HEIGHT / 2, 150F, LIGHT_GREEN)
        DrawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT, WHITE)
        ball.draw()
        player.draw()
        cpuPlayer.draw()

        if (IS_PAUSED) {
            DrawText("CLICK SPACE TO START", WIDTH / 2 - 550, HEIGHT / 2 + 30, 85, WHITE)
            if (IsKeyDown(KEY_SPACE)) {
                IS_PAUSED = false
            }
        } else {
            // Updating
            ball.update()
            player.update()
            cpuPlayer.update()
            updateScore()
        }

        DrawText("$cpuScore", WIDTH / 4 - 20, 20, 80 , WHITE)
        DrawText("$playerScore", 3 * WIDTH / 4 - 20, 20, 80 , WHITE)


        EndDrawing()
    }

    CloseWindow()
}

fun updateScore() {
    if (ball.x + ball.radius >= GetScreenWidth()) {
        cpuScore++
        resetBall()
        IS_PAUSED = true
    }
    if (ball.x - ball.radius <= 0) {
        playerScore++
        resetBall()
        IS_PAUSED = true
    }
}

fun resetBall() {
    ball.x = GetScreenWidth() / 2
    ball.y = GetScreenHeight() / 2

    val speedChoice = arrayOf(1, -1)

    ball.speedX *= speedChoice[(0..1).random()]
    ball.speedY *= speedChoice[(0..1).random()]

}