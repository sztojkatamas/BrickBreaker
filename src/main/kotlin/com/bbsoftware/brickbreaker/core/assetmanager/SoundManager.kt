package com.bbsoftware.brickbreaker.core.assetmanager

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound

object SoundManager : AssetManager {

    private var ready = false
    private val vault = mutableMapOf<String, Sound>()

    init {
        val start = System.currentTimeMillis()
        vault["ballbrick"] = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/ballbrick.wav"))
        vault["ballpaddle"] = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/ballpaddle.wav"))
        vault["ballball"] = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/ballball.wav"))

        println("Loaded ${vault.size} sounds in ${System.currentTimeMillis() - start} ms")
        ready = true
    }

    fun getSound(name :String) :Sound {
        return vault[name]!!
    }

    override fun boot() {
        println("Booting")
    }

    override fun isReady() :Boolean {
        return ready
    }
}