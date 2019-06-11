package ktmvp.yppcat.ktmvp.Bus

interface KTObserver {

    fun handleMessage(message: BaseMessage)
}