package com.bbsoftware.brickbreaker

import com.badlogic.gdx.math.Vector2
import ktx.math.minus
import org.junit.jupiter.api.Test

class AppTest {
    @Test
    fun `Vector tests`() {

        var gugu = Vector2(100f, 100f)
        var pos = Vector2(3f, 1f)
        var target = Vector2(3f, 5f)
        var heading = target.minus(pos).nor()
//        println("${heading}\t\t${heading.len()}")
//        println("${heading.nor()}\t\t${heading.nor().len()}")



        println("gugu - $gugu")
        gugu = pos.cpy()
        pos.scl(2f)
        println("gugu - $gugu")



    }
}
