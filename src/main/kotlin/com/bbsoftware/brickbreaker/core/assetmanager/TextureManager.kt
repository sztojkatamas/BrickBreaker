package com.bbsoftware.brickbreaker.core.assetmanager

import com.badlogic.gdx.graphics.Texture

object TextureManager : AssetManager {
    private var ready = false
    private val vault = mutableMapOf<String, Texture>()

    init {
        val start = System.currentTimeMillis()
            vault["ball"] = Texture("assets/ball.png")
            vault["paddle"] = Texture("assets/paddle.png")
            vault["green"] = Texture("assets/green.png")
            vault["libgd16"] = Texture("assets/libgdx16.png")
            vault["libgd32"] = Texture("assets/libgdx32.png")
            vault["libgd64"] = Texture("assets/libgdx64.png")
            vault["libgd128"] = Texture("assets/libgdx128.png")

            println("Loaded ${vault.size} images in ${System.currentTimeMillis() - start} ms")
            ready = true
    }

    fun getTexture(name :String) :Texture {
        return vault[name]!!
    }

    override fun boot() {
        println("Booting")
    }

    override fun isReady(): Boolean {
        return ready
    }
}