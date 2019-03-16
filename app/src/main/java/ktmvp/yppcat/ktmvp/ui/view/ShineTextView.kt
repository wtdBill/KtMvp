package ktmvp.yppcat.ktmvp.ui.view

import android.content.Context
import android.graphics.*
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * Created by ypp0623 on 19-3-12.
 */

class ShineTextView : AppCompatTextView {

    private lateinit var mGradientMatrix: Matrix
    private lateinit var mLinearGradient: LinearGradient
    private var mWidth: Int? = 0
    private var mHeight: Int? = 0
    private var mTranslate: Int = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mLinearGradient = LinearGradient(0f, 0f, w.toFloat(), 0f, intArrayOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN), floatArrayOf(0f, 0.3f, 0.6f, 1f), Shader.TileMode.CLAMP)
        paint.shader = mLinearGradient
        mGradientMatrix = Matrix()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mTranslate += mWidth!! / 5
        if (mTranslate > 1.5 * mWidth!!) {
            mTranslate = -mWidth!! / 2
        }
        mGradientMatrix.setTranslate(mTranslate.toFloat(), 0f)
        mLinearGradient.setLocalMatrix(mGradientMatrix)
        postInvalidateDelayed(100)
    }
}
