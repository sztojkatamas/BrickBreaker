package com.bbsoftware.brickbreaker.gameassets

import com.badlogic.gdx.graphics.Color.MAGENTA
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody
import com.bbsoftware.brickbreaker.Game
import com.bbsoftware.brickbreaker.core.assetmanager.TextureManager

class Wall(
    override val name :String, override var position: Vector2,
    override var width: Float, override var height: Float
    ) :GameEntity(name) {

    override var texture = TextureManager.getTexture("green")
    override var color = MAGENTA
    var body : Body


    init {
        val bodyDef = BodyDef()
        bodyDef.type = StaticBody
        bodyDef.position.set(position.cpy().add(width /2, height /2).scl(1/Game.scale))

        body = Game.world.createBody(bodyDef)


        val shape = PolygonShape()
        shape.setAsBox(width / (2*Game.scale), height / (2*Game.scale))

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 100f
        fixtureDef.restitution = 1f
        //fixtureDef.isSensor = true

        body.createFixture(fixtureDef).userData = name
    }

    override fun isShape() :Boolean { return false }

    override fun render(batch: Batch) {
        position = body.position.cpy().scl(Game.scale)
        batch.draw(texture, position.x -width/2, position.y -height/2, width, height)
    }

    override fun render(shaperenderer: ShapeRenderer) {
        position = body.position.cpy().scl(Game.scale)
        shaperenderer.color = color
        shaperenderer.rect(position.x, position.y, width, height)
    }
}