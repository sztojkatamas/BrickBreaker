package com.bbsoftware.brickbreaker

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.bbsoftware.brickbreaker.core.Command
import com.bbsoftware.brickbreaker.gameassets.GameScene

object Game {
    var fullscreen = false
    val width = 64 * 10
    val heigth = 64 * 15
    val scale = 100f
    var countCollisions = false

    val scene = GameScene(width, heigth)

    //var mousePos = Vector2()
    var stop = false

    val world = World(Vector2(0.0f, 0.0f), true)
    val eventQueue = mutableListOf<Command>()

    fun addEvent(event :Command) {
        eventQueue.add(event)
    }

    fun takeEvent(recipient :String) :Command {
        val cmd = eventQueue.first { it.recipient.equals(recipient) }
        eventQueue.remove(cmd)
        return cmd
    }

    fun hasEventFor(recipient: String): Boolean {
        return Game.eventQueue.filter { it.recipient.equals(recipient) }.count() > 0
    }
}
