package ktmvp.yppcat.ktmvp.network.bean

/**
 * Created by ypp0623 on 19-2-21.
 */
data class DimenData(
    val reason: String?,
    val result: Result?,
    val error_code: Int?
) {
    data class Result(
        val base64_image: String?
    )
}