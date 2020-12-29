package com.bbsoftware.brickbreaker.gameassets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.bbsoftware.brickbreaker.Game
import com.bbsoftware.brickbreaker.core.assetmanager.TextureManager
import ktx.math.minus
import com.badlogic.gdx.physics.box2d.FixtureDef

class Ball(override val name :String, override var position: Vector2) :GameEntity(name) {
    override var texture = TextureManager.getTexture("ball")

    ///private val ME = name
    var heading = Vector2(0f, 0f)
    var velocity = 20f
    var body : Body

    init {
        color = Color.CLEAR
        size = 32f
        moveable = true

        var bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(position.cpy().scl(1/Game.scale))
        bodyDef.linearDamping = 0f
        bodyDef.angularDamping = 1f
        bodyDef.bullet = true
        body = Game.world.createBody(bodyDef)

        var shape = CircleShape()
        shape.radius =  (32f / 2) / Game.scale

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 0.5f
        fixtureDef.restitution = 1f

        //fixtureDef.isSensor = true

        body.createFixture(fixtureDef).userData = name


    }

    override fun renderAsShape(): Boolean { return false }

    override fun render(batch: Batch) {
        batch.draw(texture, position.x - (size/2), position.y - (size/2f), size, size)
    }

    override fun render(shaperenderer: ShapeRenderer) {
        shaperenderer.color = color
        shaperenderer.circle(position.x, position.y, size/2)
    }

    override fun processEvents() {
        while (Game.hasEventFor(name)) {
            val event = Game.takeEvent(name)
            when (event.command) {
                "moveto" -> {
                    val mousePos  = event.payload as Vector2
                    heading = Vector2(mousePos.x, Game.heigth - mousePos.y).minus(position).nor()

                    body.applyForceToCenter(heading.cpy().scl(velocity), true)
                 }
                "speed" -> {
                    velocity *= event.payload as Float
                    if (velocity < 0f) {
                        velocity = 0f
                    }
                }
            }
        }
    }

    override fun updatePosition(deltaTime :Float) {
        position = body.position.cpy().scl(Game.scale) // The rigidbody "drags" the position
    }
}