package com.bbsoftware.brickbreaker.gameassets

import com.badlogic.gdx.Gdx
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
import com.bbsoftware.brickbreaker.KeyboardProcessor
import com.bbsoftware.brickbreaker.core.assetmanager.TextureManager
import ktx.math.minus

class Paddle(override val name :String, override var position: Vector2) :GameEntity(name) {

    private val ME = "thepaddle"
    override var texture = TextureManager.getTexture("paddle")
    override var color = Color.MAGENTA
    override var width = 130f
    override var height = 16f
    var body : Body
    private val origPosY :Float


    init {
        moveable = true
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.KinematicBody
        bodyDef.position.set(position.cpy().add(width /2, height /2).scl(1/100f))

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
        origPosY = body.position.y
        println("orig pos: ${body.position.y}")
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
    }

    override fun processEvents() {
        var lr = 0f
        if ((Gdx.input.inputProcessor as KeyboardProcessor).isKeyPressedDown(21)) {
            lr = -1f
        } else if ((Gdx.input.inputProcessor as KeyboardProcessor).isKeyPressedDown(22)) {
            lr = 1f
        }

        if(lr != 0f) {
            body.linearVelocity = Vector2(lr, 0f)
        }
    }
}