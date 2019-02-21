package ktmvp.yppcat.ktmvp.network.bean

/**
 * Created by ypp0623 on 19-2-20.
 */
data class NewsData(
        val reason: String?,
        val result: Result?,
        val error_code: Int?
) {
    data class Result(
            val stat: String?,
            val `data`: List<Data?>?
    ) {
        data class Data(
                val uniquekey: String?,
                val title: String?,
                val date: String?,
                val category: String?,
                val author_name: String?,
                val url: String?,
                val thumbnail_pic_s: String?,
                val thumbnail_pic_s02: String?,
                val thumbnail_pic_s03: String?
        )
    }
}