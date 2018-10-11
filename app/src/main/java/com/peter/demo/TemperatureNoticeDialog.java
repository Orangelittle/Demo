package com.peter.demo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
/**
 * Created by Peter on 2018/10/5.
 */
public class TemperatureNoticeDialog extends Dialog {

    private static final String TAG = "TemperatureNoticeDialog";
    private static TemperatureNoticeDialog temperatureNoticeDialog;
    private TextView btn_continue;
    private TextView btn_cancel;

    public TemperatureNoticeDialog(Context context, int style) {
        super(context, style);
    }

    public TemperatureNoticeDialog(Context context, int style, View view) {
        super(context, style);
    }

    public static TemperatureNoticeDialog getInstance(Context context){
        temperatureNoticeDialog = new TemperatureNoticeDialog(context,R.style.temperaturePromptDialog);
        return temperatureNoticeDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.temperature_notice_dialog);
        btn_continue = (TextView) findViewById(R.id.notice_continue);
        btn_cancel = (TextView) findViewById(R.id.notice_cancel);
        btn_continue.setOnClickListener(clickListener);
        btn_cancel.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                  temperatureNoticeDialog.dismiss();
        }
    };

}