package ktmvp.yppcat.ktmvp.db.bmob

import cn.bmob.v3.BmobUser

/**
 * Created by ypp0623 on 19-3-29.
 */

class User(var id: Long, var name: String?, var pwd: String?, var telNumber: Long) : BmobUser()
