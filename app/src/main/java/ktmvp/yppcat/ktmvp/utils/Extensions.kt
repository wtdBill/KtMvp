package ktmvp.yppcat.ktmvp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.hardware.input.InputManager
import android.support.annotation.*
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import ktmvp.yppcat.ktmvp.application.MyApplication

/**
 * Created by ypp0623 on 19-2-21.
 */
fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun View.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据大小格式化 total：字节数
 */
fun Context.dataFormat(total: Long): String {
    val result: String
    val speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100).toString() + " MB"
    }
    return result
}

fun View.getString(stringResId: Int) = resources.getString(stringResId)

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        it.height = value
        requestLayout()
    }
}

fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        it.width = value
        requestLayout()
    }
}

fun View.resieze(width: Int, height: Int) {
    val lp = layoutParams
    lp?.let {
        it.width = width
        it.height = height
        requestLayout()
    }
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun View.remove(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}

fun Context.getInteger(@IntegerRes id: Int) = resources.getInteger(id)

fun Context.getString(@StringRes id: Int) = resources.getString(id)

fun Context.getBoolean(@BoolRes id: Int) = resources.getBoolean(id)

fun Context.getColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

