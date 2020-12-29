package com.bbsoftware.brickbreaker.gameassets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType.*
import com.bbsoftware.brickbreaker.Game
import com.bbsoftware.brickbreaker.core.Command
import com.bbsoftware.brickbreaker.core.assetmanager.TextureManager
import ktx.math.minus

class Brick(override val name :String, override var position: Vector2) :GameEntity(name) {

    override var texture = TextureManager.getTexture("green")
    override var width = 64f
    override var height = 64f
    var body : Body


    init {
        val bodyDef = BodyDef()
        bodyDef.type = KinematicBody //DynamicBody
        bodyDef.position.set(position.cpy().add(width /2, height /2).scl(1/Game.scale))

        body = Game.world.createBody(bodyDef)


        val shape = PolygonShape()
        shape.setAsBox(width/(2*Game.scale), height/(2*Game.scale))

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 10000f
        //fixtureDef.isSensor = true

        val fixture = body.createFixture(fixtureDef)
        fixture.userData = name

    }

    override fun renderAsShape() :Boolean { return true }

    override fun render(batch: Batch) {
        position = body.position.cpy().scl(Game.scale).minus(Vector2(width /2f, height /2f))

        val sprite = Sprite(texture, width.toInt(), height.toInt())
        sprite.setPosition(position.x, position.y)
        sprite.rotation = body.transform.orientation.angleDeg()
        sprite.draw(batch)
    }

    override fun render(shaperenderer: ShapeRenderer) {
        position = body.position.cpy().scl(Game.scale).minus(Vector2(width /2f, height /2f))
        shaperenderer.color = calculateColorAccordingToHealth()
        shaperenderer.rect(position.x, position.y, width, height)
    }

    private fun calculateColorAccordingToHealth(): Color {
        val rgb = healthpoints/100f
        return Color(rgb,rgb, rgb, 1f)
    }

    fun receiveDamage(damage :Int) {
        healthpoints -= damage
        if (healthpoints < 1) {
            //Game.world.destroyBody(body)
            //Game.scene.removeEntity(this)
            Game.addEvent(Command("GAME", "destroy", this))
        }
    }

    fun cleanup() {
        Game.world.destroyBody(body)
    }
}