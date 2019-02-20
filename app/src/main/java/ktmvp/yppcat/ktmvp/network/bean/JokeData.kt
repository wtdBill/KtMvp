package ktmvp.yppcat.ktmvp.network.bean

/**
 * Created by ypp0623 on 19-2-20.
 */
data class JokeData(
    val reason: String?,
    val result: Result?,
    val error_code: Int?
) {
    data class Result(
        val `data`: List<Data?>?
    ) {
        data class Data(
            val content: String?,
            val hashId: String?,
            val unixtime: Int?,
            val updatetime: String?
        )
    }
}