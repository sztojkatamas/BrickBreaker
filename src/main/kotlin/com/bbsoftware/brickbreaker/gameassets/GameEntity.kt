package com.bbsoftware.brickbreaker.gameassets

import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

abstract class GameEntity(open val name :String) {

    open var size = 0f
    abstract var texture :Texture
    open var color = WHITE

    var immortal = false
    var healthpoints = 100

    var moveable = false
    open var position :Vector2 = Vector2(0f, 0f)
//    abstract var x :Float
//    abstract var y :Float
    open var width :Float = 0f
    open var height :Float = 0f

    abstract fun isShape() :Boolean
    abstract fun render(batch: Batch)
    abstract fun render(shaperenderer: ShapeRenderer)

    open fun processEvents() {}
    open fun updatePosition(deltaTime :Float) {}
}