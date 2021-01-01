package com.bbsoftware.brickbreaker.gameassets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.bbsoftware.brickbreaker.Game
import com.bbsoftware.brickbreaker.core.assetmanager.TextureManager
import ktx.math.minus
import kotlin.math.abs
import kotlin.math.sign

class Paddle(override val name :String, override var position: Vector2) :GameEntity(name) {

    override var texture = TextureManager.getTexture("paddle")
    override var color = Color.MAGENTA
    override var width = 130f
    override var height = 16f
    var body : Body
    private var lastMouseX :Float = Game.width/2f


    init {
        moveable = true
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.KinematicBody
        bodyDef.position.set(position.cpy().add(0f, height /2).scl(1/100f))

        body = Game.world.createBody(bodyDef)


        val shape = PolygonShape()
        shape.setAsBox(width/(2*100f), height/(2*100f))

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 100f
        bodyDef.linearDamping = 0.5f
        bodyDef.angularDamping = 1f
        bodyDef.fixedRotation = true
        //fixtureDef.isSensor = true

        body.createFixture(fixtureDef).userData = name
        //origPosY = body.position.y
    }

    override fun renderAsShape() :Boolean { return false }

    override fun render(batch: Batch) {
        val sprite = Sprite(texture, width.toInt(), height.toInt())
        sprite.setPosition(position.x, position.y)
        sprite.rotation = body.transform.orientation.angleDeg()
        sprite.draw(batch)
    }

    override fun render(shaperenderer: ShapeRenderer) {
        shaperenderer.color = color
        shaperenderer.rect(position.x, position.y, width, height)
    }

    override fun updatePosition(deltaTime :Float) {

        position = body.position.cpy().scl(Game.scale).minus(Vector2(width /2f, height /2f))

        if (position.x <= 0 || position.x >= Game.width - width) {
            body.linearVelocity = Vector2(0f, 0f)
        }

        val diff = abs(lastMouseX - position.x - width/2)
        if (diff < 30f) {
            //println(diff)
            body.linearVelocity = body.linearVelocity.cpy().scl(0.9f)
        }

    }

    override fun processEvents() {
        while (Game.hasEventFor(name)) {
            val event = Game.takeEvent(name)
            when (event.command) {
                "moveto" -> {
                    val mousePos  = event.payload as Vector2
                    val diff = mousePos.x - position.x - width/2

                    //println(diff)
                    body.linearVelocity = Vector2(sign(diff), 0f).nor().scl(abs(diff)/10f)
                    lastMouseX = mousePos.x
                }
            }
        }
    }
}