package com.bbsoftware.brickbreaker

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.bbsoftware.brickbreaker.gameassets.Ball
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.math.Matrix4
import com.bbsoftware.brickbreaker.core.CollisionListener
import com.bbsoftware.brickbreaker.core.assetmanager.SoundManager
import com.bbsoftware.brickbreaker.core.assetmanager.TextureManager
import com.bbsoftware.brickbreaker.gameassets.Brick
import com.bbsoftware.brickbreaker.gameassets.Paddle
import kotlin.random.Random


class BrickBreakerApplication(): ApplicationAdapter() {
    var once :Boolean = true

    private lateinit var spriteBatch :PolygonSpriteBatch //SpriteBatch
    private lateinit var shapeRenderer :ShapeRenderer
    private lateinit var debugRenderer :Box2DDebugRenderer

    private val scene = Game.scene
    var debugMatrix: Matrix4? = null

    override fun create() {
        spriteBatch = PolygonSpriteBatch()//SpriteBatch()
        shapeRenderer = ShapeRenderer()
        Gdx.input.inputProcessor = KeyboardProcessor()
        debugRenderer = Box2DDebugRenderer()
        Game.world.setContactListener(CollisionListener())

        // Splash screen and progress bar... :)
        SoundManager.boot()
        TextureManager.boot()
        while (!SoundManager.isReady() || !TextureManager.isReady()) { print("Loading assets...\r")}


        // Load a game level maybe?
        scene.addWall(-10f, -100f, 10f, Game.heigth.toFloat() + 100f)               // Left
        scene.addWall(Game.width.toFloat(), -100f, 10f, Game.heigth.toFloat() + 100f)      // Right
        scene.addWall(0f, Game.heigth.toFloat(),  Game.width.toFloat(), 10f)                          // Top
        scene.addWall(0f, -100f,  Game.width.toFloat(), 10f)                                   // Bottom

        val votma = mutableSetOf<Int>()
        val rnd = Random(System.currentTimeMillis())
        var hnum = rnd.nextInt(10)
        var snum = rnd.nextInt(7)
        repeat(15) {
            while (votma.contains(hnum *100 + snum)) {
                hnum = rnd.nextInt(10)
                snum = rnd.nextInt(7)
            }
            votma.add(hnum *100 + snum)
            scene.addBlock(hnum,snum)
        }

        scene.addEntity(Paddle("thepaddle", Vector2((Game.width/2).toFloat(), 10f)))

        scene.addEntity(Ball("theball1", Vector2((Game.width/2).toFloat(), 140f)))
//        scene.addEntity(Ball("theball2", Vector2(148f, 140f)))
//        scene.addEntity(Ball("theball3", Vector2(68f, 140f)))
//        scene.addEntity(Ball("theball4", Vector2(368f, 140f)))
//        scene.addEntity(Ball("theball5", Vector2(258f, 140f)))

    }

    override fun render() {
        fullScreenOrWindowed()

        while (Game.hasEventFor("GAME")) {
            val cmd = Game.takeEvent("GAME")
            if (cmd.command == "destroy") {
                val brick = cmd.payload as Brick
                brick.cleanup()
                Game.scene.removeEntity(brick)
            }
        }

        if (!Game.stop) {
            scene.processEvents()
            Game.world.step(Gdx.graphics.deltaTime, 4,4)

            scene.updateMovements(Gdx.graphics.deltaTime)
        }
//------------------------------------------------------------------------------------------------------------------------------------------------------------

        // Render Textured entities
        spriteBatch.begin()
        //batch.draw(TextureManager.getTexture("background"), 0f,0f)
        scene.renderElements(spriteBatch)
        spriteBatch.end()
//------------------------------------------------------------------------------------------------------------------------------------------------------------

        // Render Shape entities
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        scene.renderElements(shapeRenderer)
        shapeRenderer.end()
//------------------------------------------------------------------------------------------------------------------------------------------------------------

        // Render Line entities
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.TEAL
        shapeRenderer.line((Game.width/2).toFloat(), 20f, (Game.width/2).toFloat(), 520f)
        shapeRenderer.end()

//------------------------------------------------------------------------------------------------------------------------------------------------------------


        if (Game.debugvision) {
            debugMatrix = spriteBatch.getProjectionMatrix().cpy().scale(Game.scale, Game.scale, 0f)//.scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0F)
            debugRenderer.render(Game.world, debugMatrix)
        }

        // Stuff at the end
        printDebugInfo()
    }


    private fun printDebugInfo() {
        var ball = scene.getEntityByName("theball1") as Ball
        Gdx.graphics.setTitle("FPS: ${Gdx.graphics.getFramesPerSecond()} " +
                //"Ball [$ballx][$bally]" +
                "Queue{${Game.eventQueue.size}}" +
                "Entities: ${Game.scene.getEntityCount()}" +
                "Ball[${ball.position.x.toInt()}][${ball.position.y.toInt()}][speed:${ball.velocity}]" +
                "<${ball.body.linearVelocity.len2()}>" +
//                "Delta: ${Gdx.graphics.deltaTime} " +
//                "W: ${Gdx.graphics.width} " +
//                "H: ${Gdx.graphics.height}"
        "")
    }

    private fun fullScreenOrWindowed() {

        if (!Game.fullscreen) {
            Gdx.gl.glClearColor(0.2f, 0.6f, 0f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
            //Gdx.graphics.setWindowedMode(1024, 768)

            spriteBatch.projectionMatrix.setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        } else {
            var m: Graphics.DisplayMode? = null
            for (mode in Gdx.graphics.displayModes) {
                if (m == null) {
                    m = mode
                } else {
                    if (m.width < mode.width) {
                        m = mode
                    }
                }
            }

            Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)
            spriteBatch.projectionMatrix.setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
            Gdx.gl.glViewport(0, 0, Gdx.graphics.backBufferWidth, Gdx.graphics.backBufferHeight)
            Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1f)
            if(once) {
                println("W: ${Gdx.graphics.width}")
                println("H: ${Gdx.graphics.height}")
                once = false
            }
        }
    }

    override fun dispose () {
        spriteBatch.dispose()
    }
}