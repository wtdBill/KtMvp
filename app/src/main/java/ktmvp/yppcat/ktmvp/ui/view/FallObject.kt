package ktmvp.yppcat.ktmvp.ui.view

import java.util.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import ktmvp.yppcat.ktmvp.utils.Logger


/**
 * Created by ypp0623 on 19-3-15.
 */
class FallObject {
    private var initY = 0
    private var initX = 0
    private var random: Random? = null
    private var parentWidth = 0
    private var parentHeight = 0
    private var objectWidth = 0
    private var objectHeight = 0
    private var isRandomSpeed = false
    private var isRandomSize = false

    var initSpeed = 1

    var presentX = 0
    var presentY = 0
    var presentSpeed = 0

    private var bitmap: Bitmap? = null
    var builder: Builder? = null

    companion object {
        const val defaultSpeed = 10
    }

    constructor(builder: Builder) {
        this.builder = builder
        initSpeed = builder.initSpeed
        bitmap = builder.bitmap
        isRandomSize = builder.isRandomSize
        isRandomSpeed = builder.isRandomSpeed
    }

    constructor(builder: Builder, parentWidth: Int, parentHeight: Int) {
        random = Random()
        bitmap = builder.bitmap
        this.builder = builder
        isRandomSpeed = builder.isRandomSpeed
        isRandomSize = builder.isRandomSize
        randomSize()
        randomSpeed()
        this.parentWidth = parentWidth
        this.parentHeight = parentHeight
        initX = random?.nextInt(parentWidth)!!
        initY = random!!.nextInt(parentHeight) - parentHeight
        presentX = initX
        presentY = initY
        initSpeed = builder.initSpeed
        objectHeight = bitmap!!.height
        objectWidth = bitmap!!.width

    }

    class Builder(val bitmap: Bitmap) {
        var initSpeed: Int = 0
        var isRandomSpeed = false
        var isRandomSize = false

        init {
            this.initSpeed = defaultSpeed
        }

        fun setSpeed(speed: Int): Builder {
            this.initSpeed = speed
            return this
        }

        fun setSpeed(isRandomSpeed: Boolean): Builder {
            this.isRandomSpeed = isRandomSpeed
            return this
        }

        fun setSize(isRandomSize: Boolean): Builder {
            this.isRandomSize = isRandomSize
            return this
        }

        fun build(): FallObject {
            return FallObject(this)
        }
    }

    fun drawObject(canvas: Canvas) {
        moveObject()
        Logger.e(presentY.toString())
        canvas.drawBitmap(bitmap, presentX.toFloat(), presentY.toFloat(), null)
    }

    private fun moveObject() {
        moveY()
        if (presentY > parentHeight) {
            reset()
        }
    }

    private fun moveY() {
        presentY += presentSpeed
    }

    private fun reset() {
        presentY = -objectHeight
        presentSpeed = initSpeed
    }

    private fun randomSize() {
        if (isRandomSize) {
            val r = (random!!.nextInt(10) + 1) * 0.1f
            val rW = r * builder!!.bitmap.width
            val rH = r * builder!!.bitmap.height
            bitmap = bitmap?.let { changeBitmapSize(it, rW, rH) }
        }
        objectWidth = bitmap!!.width
        objectHeight = bitmap!!.height
    }

    private fun randomSpeed() {
        presentSpeed = if (isRandomSpeed) {
            (((random!!.nextInt(3) + 1) * 0.1 + 1) * initSpeed).toInt()
        } else {
            initSpeed
        }
    }

    private fun changeBitmapSize(bitmap: Bitmap, newW: Float, newH: Float): Bitmap {
        val oldW = bitmap.width
        val oldH = bitmap.height
        val scaleW = newW / oldW
        val scaleH = newH / oldH
        val matrix = Matrix()
        matrix.postScale(scaleW, scaleH)
        return Bitmap.createBitmap(bitmap, 0, 0, oldW, oldH, matrix, true)
    }

}