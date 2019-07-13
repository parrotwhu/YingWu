package com.example.yingwu.beans.emoji;

public class EmojiIndicatorInfo {
    public EmojiIndicatorInfo(int emojiType, int indicatorIndex) {
        this.emojiType = emojiType;
        this.indicatorIndex = indicatorIndex;
    }

    private int emojiType;

    public int getEmojiType() {
        return emojiType;
    }

    public int getIndicatorIndex() {
        return indicatorIndex;
    }

    private int indicatorIndex;
}

