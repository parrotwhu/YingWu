package com.example.yingwu.ui.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.yingwu.R;
import com.example.yingwu.others.DataCenter;
import com.example.yingwu.widgets.EmojiPanelView;
public class EmojiPanelActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_panel);
        EmojiPanelView emojiPanelView = findViewById(R.id.emoji_panel_view);
        emojiPanelView.initEmojiPanel(DataCenter.emojiDataSources);
        findViewById(R.id.show).setOnClickListener(v -> emojiPanelView.showEmojiPanel());
    }
}


