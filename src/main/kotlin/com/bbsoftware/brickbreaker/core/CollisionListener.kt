package com.bbsoftware.brickbreaker.core

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.bbsoftware.brickbreaker.Game
import com.bbsoftware.brickbreaker.core.assetmanager.SoundManager
import com.bbsoftware.brickbreaker.gameassets.Ball
import com.bbsoftware.brickbreaker.gameassets.Brick
import com.bbsoftware.brickbreaker.gameassets.Paddle
import com.bbsoftware.brickbreaker.gameassets.Wall

class CollisionListener : ContactListener {

    override fun beginContact(contact: Contact?) {
        if(!Game.countCollisions) { return }

        val ObjectA = Game.scene.getEntityByName(contact?.fixtureA?.userData as String)
        val ObjectB = Game.scene.getEntityByName(contact?.fixtureB?.userData as String)

        //println("${contact?.fixtureA?.userData} is a ${ObjectA::class}")

        /**
         * Paddle - Ball
         */
        if ((ObjectA is Ball && ObjectB is Paddle) || (ObjectA is Paddle && ObjectB is Ball)){
            SoundManager.getSound("ballpaddle").play()
        }

        /**
         * Brick - Ball
         */
        if ((ObjectA is Brick && ObjectB is Ball) || (ObjectB is Brick && ObjectA is Ball)) {
            SoundManager.getSound("ballbrick").play()
            if (ObjectA is Brick) { ObjectA.receiveDamage(15) }
            if (ObjectB is Brick) { ObjectB.receiveDamage(15) }
        }

        /**
         * Ball - Ball
         */
        if (ObjectA is Ball && ObjectB is Ball) {
            SoundManager.getSound("ballball").play()
        }

        /**
         * Brick - Brick
         */
        if (ObjectA is Brick && ObjectB is Brick) {
            println("graphite on the roof")
            return
        }

    }

    override fun endContact(contact: Contact?) {
        // Implement when needed
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        // Implement when needed
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
        // Implement when needed
    }
}