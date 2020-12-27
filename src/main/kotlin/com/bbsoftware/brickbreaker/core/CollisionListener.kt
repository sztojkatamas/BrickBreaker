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

class CollisionListener : ContactListener {

    override fun beginContact(contact: Contact?) {
        if(!Game.countCollisions) { return }

        var ObjectA = Game.scene.getEntityByName(contact?.fixtureA?.userData as String)
        var ObjectB = Game.scene.getEntityByName(contact?.fixtureB?.userData as String)

        //println("${contact?.fixtureA?.userData} is a ${ObjectA::class}")

        if (ObjectA is Brick && ObjectB is Brick) {
            println("graphite on the roof")
            return
        }

        if (ObjectA is Paddle || ObjectB is Paddle) {
            SoundManager.getSound("ballpaddle").play()
        }
        if (ObjectA is Brick || ObjectB is Brick) {
            SoundManager.getSound("ballbrick").play()
        }
        if (ObjectA is Ball && ObjectB is Ball) {
            SoundManager.getSound("ballball").play()
        }


    }

    override fun endContact(contact: Contact?) {

    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }
}