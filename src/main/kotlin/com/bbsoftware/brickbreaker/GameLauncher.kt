package com.bbsoftware.brickbreaker

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

class GameLauncher {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GameLauncher().createApplication()
        }
    }

    private fun createApplication(): Lwjgl3Application? {
        return Lwjgl3Application(BrickBreakerApplication(), getDefaultConfiguration())
    }

    private fun getDefaultConfiguration(): Lwjgl3ApplicationConfiguration? {
        val configuration = Lwjgl3ApplicationConfiguration()
        configuration.setTitle("BrickBreaker")
        configuration.setWindowedMode(Game.width, Game.heigth)
        configuration.setResizable(false)
        configuration.setWindowIcon("assets/libgdx128.png", "assets/libgdx64.png", "assets/libgdx32.png", "assets/libgdx16.png")
        return configuration
    }
}