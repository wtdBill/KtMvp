package ktmvp.yppcat.ktmvp.Bus

class KTBus private constructor() {

    companion object {
        val instance = KTBusHolder.holder
        private val observerArray = arrayListOf<KTObserver>()
    }

    private object KTBusHolder {
        val holder = KTBus()
    }

    fun register(observer: KTObserver) {
        observerArray.add(observer)
    }

    fun unRegister(observer: KTObserver) {
        if (observer in observerArray) {
            observerArray.remove(observer)
        }
    }

    fun postMessage(message: BaseMessage) {
        for (observer in observerArray) {
            observer.handleMessage(message)
        }
    }
}