package ktmvp.yppcat.ktmvp.Bus.message

import ktmvp.yppcat.ktmvp.Bus.BaseMessage
import ktmvp.yppcat.ktmvp.Bus.MessageType

class ExitMessage : BaseMessage() {

    override fun getMessageType(): Int {
        return MessageType.EXIT_APP
    }
}