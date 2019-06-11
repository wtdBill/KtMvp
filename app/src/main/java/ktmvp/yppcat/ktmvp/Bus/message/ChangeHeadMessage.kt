package ktmvp.yppcat.ktmvp.Bus.message

import ktmvp.yppcat.ktmvp.Bus.BaseMessage
import ktmvp.yppcat.ktmvp.Bus.MessageType

class ChangeHeadMessage : BaseMessage() {

    override fun getMessageType(): Int {
        return MessageType.CHANGE_HEAD
    }
}