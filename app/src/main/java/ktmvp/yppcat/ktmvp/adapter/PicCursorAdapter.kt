package ktmvp.yppcat.ktmvp.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter

/**
 * Created by ypp0623 on 19-3-4.
 */
class PicCursorAdapter : CursorAdapter {

    constructor(context: Context,c:Cursor,autoRequery:Boolean) : super(context,c,autoRequery) {

    }


    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}