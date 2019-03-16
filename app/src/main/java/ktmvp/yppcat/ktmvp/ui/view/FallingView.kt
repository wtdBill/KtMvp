package ktmvp.yppcat.ktmvp.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by ypp0623 on 19-3-15.
 */

class FallingView : View {

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private lateinit var testPaint: Paint
    private var snowY: Int = 0
    private var fallObjects: MutableList<FallObject>? = null

    companion object {
        const val defaultWidth = 600 //默认宽度
        const val defaultHeight = 1000 //默认高度
        const val intervalTime = 5L //重回时间
    }


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = measureSize(defaultHeight, heightMeasureSpec)
        val width = measureSize(defaultWidth, widthMeasureSpec)
        setMeasuredDimension(width, height)

        viewWidth = width
        viewHeight = height
    }

    private fun init() {
        testPaint = Paint()
        testPaint.color = Color.WHITE
        testPaint.style = Paint.Style.FILL
        snowY = 0
        fallObjects = ArrayList()
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        result = if (specMode == View.MeasureSpec.EXACTLY) {
            specSize
        } else {
            Math.max(result, specSize)
        }
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (fallObjects!!.size > 0) {
            for (i in 0 until fallObjects!!.size) {
                canvas?.let { fallObjects!![i].drawObject(it) }
            }
        }
        handler.postDelayed(runnable, intervalTime)
    }

    private val runnable: Runnable = Runnable {
        invalidate()
    }

    fun addFallObject(fallObject: FallObject, num: Int) {
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                viewTreeObserver.removeOnPreDrawListener(this)
                for (i in 0 until num) {
                    val newFallObject = fallObject.builder?.let { FallObject(it, viewWidth, viewHeight) }
                    newFallObject?.let { fallObjects?.add(it) }
                }
                invalidate()
                return true
            }
        })
    }


}
