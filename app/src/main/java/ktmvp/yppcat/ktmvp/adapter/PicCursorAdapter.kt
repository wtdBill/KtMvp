package ktmvp.yppcat.ktmvp.adapter

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.application.MyApplication

/**
 * Created by ypp0623 on 19-3-21.
 */

class PicCursorAdapter(context: Context, c: Cursor?, autoRequery: Boolean) : CursorAdapter(context, c, autoRequery) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View? {
        return LayoutInflater.from(context).inflate(R.layout.item_pic,parent,false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val mImageView  = view.findViewById<ImageView>(R.id.local_pic)
        val  path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(MyApplication.screenWidth / 3, MyApplication.screenWidth / 3)
                .centerCrop()
        Glide.with(context)
                .load(path)
                .apply(requestOptions)
                .into(mImageView)
    }

}
