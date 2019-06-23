package ktmvp.yppcat.ktmvp.font

import com.joanzapata.iconify.IconFontDescriptor

/**
 * Created by wtdbill on 19-5-14.
 */
class FontEcModule : IconFontDescriptor{
    override fun ttfFileName(): String {
        return "iconfont.ttf"
    }

    override fun characters(): Array<EcIcons> {
        return EcIcons.values()

    }
}