package com.bbsoftware.brickbreaker.gameassets

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.bbsoftware.brickbreaker.Game

class GameScene(val width :Int, val height :Int) {

    private val blocksize = 64f
    private var init = false
    private val entities = mutableListOf<GameEntity>()

    fun addBlock(gridX :Int, gridY :Int) {
        if (gridX in 1..10 && gridY in 1..15) {
            entities.add(Brick("Brick-[$gridX][$gridY]", Vector2((gridX-1) * blocksize, Game.heigth - blocksize - (gridY-1) * blocksize)))
        }
    }

    fun addWall(wall_x :Float, wall_y :Float, wall_width: Float, wall_height: Float) {
        entities.add(Wall("Wall-[$wall_x][$wall_y]", Vector2(wall_x, wall_y), wall_width, wall_height))
    }

    fun addEntity(entity :GameEntity) {
        entities.add(entity)
    }

    fun removeEntity(entity :GameEntity) {
        entities.remove(entity)
    }

    fun getEntityByName(entityName :String) :GameEntity {
        return entities.find { it.name == entityName }!!
    }

    fun renderElements(renderer: Any) {
        when (renderer) {
            is PolygonSpriteBatch -> { entities.filter { !it.renderAsShape() }.forEach { it.render(renderer) } }
            is ShapeRenderer -> { entities.filter { it.renderAsShape() }.forEach { it.render(renderer) } }
        }
    }

    fun processEvents() {
        entities.stream().forEach { it.processEvents() }
    }

    fun updateMovements(deltaTime: Float) {
        entities.filter { it.moveable.equals(true) }.forEach { it.updatePosition(deltaTime) }
    }

    fun getEntityCount(): Int {
        return entities.size
    }
}