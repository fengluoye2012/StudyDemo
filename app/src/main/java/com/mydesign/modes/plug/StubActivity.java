package com.mydesign.modes.plug;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StubActivity extends BasePlugActivity {

    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = this;

        TextView textView = new TextView(act);
        textView.setText("StubView");

        setContentView(textView);
    }
}
