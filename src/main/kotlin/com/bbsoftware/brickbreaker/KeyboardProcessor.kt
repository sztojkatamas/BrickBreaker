package com.bbsoftware.brickbreaker

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.bbsoftware.brickbreaker.core.Command
import ktx.log.*

class KeyboardProcessor() : InputProcessor {
    private val keyDown = mutableSetOf<Int>()

    fun isKeyPressedDown(keycode: Int): Boolean {
        return keyDown.contains(keycode)
    }

    override fun keyDown(keycode: Int): Boolean {
        keyDown.add(keycode)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        keyDown.remove(keycode)

        when (keycode) {
            ESCAPE -> { Gdx.app.exit() }
            F10 -> { Game.fullscreen = !Game.fullscreen }
            F11 -> { Game.debugvision = !Game.debugvision }
            SPACE -> { Game.stop = !Game.stop }
        }
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        info { "-----: {$character}" }
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        Game.countCollisions = true
        repeat(1) {
            Game.addEvent(Command("theball${it+1}", "moveto", Vector2(screenX.toFloat(), screenY.toFloat())))
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}