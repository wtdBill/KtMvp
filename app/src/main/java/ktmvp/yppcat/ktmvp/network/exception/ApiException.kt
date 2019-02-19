package ktmvp.yppcat.ktmvp.network.exception

/**
 * Created by ypp0623 on 19-2-19.
 */
class ApiException : RuntimeException {

    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}