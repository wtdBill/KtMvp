package ktmvp.yppcat.ktmvp.font;

import com.joanzapata.iconify.Icon;

/**
 * Created by wtdbill on 19-5-14.
 */

public enum EcIcons implements Icon {
    icon_shuidi('\ue600')

    ;


    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
