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
public class TemperatureDialog extends Dialog {

    private static final String TAG = "TemperatureDialog";
    private RadioGroup stateGroup;
    private EditText note_editText;
    private RadioGroup note_radioGroup;
    private TextView commit_result;

    private String status;
    private String note;
    private TextView temperature_value;

    private static TemperatureDialog temperatureDialog;

    public TemperatureDialog(Context context, int style) {
        super(context, style);
    }

    public TemperatureDialog(Context context, int style, View view) {
        super(context, style);
    }

    public static TemperatureDialog getInstance(Context context){
        temperatureDialog = new TemperatureDialog(context,R.style.temperaturePromptDialog);
        return temperatureDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.temperature_detection_dialog);
        stateGroup = ((RadioGroup) findViewById(R.id.status_layout));
        stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.state_getup_before:
                        status = "state_getup_before";
                        break;
                    case R.id.state_takepills_before:
                        status = "state_takepills_before";
                        break;
                    case R.id.state_takepills_after:
                        status = "state_takepills_after";
                        break;
                    case R.id.state_others:
                        status = "state_others";
                        break;
                }
            }
        });

        note_editText = ((EditText) findViewById(R.id.note_edittext));
        note_radioGroup = (RadioGroup) findViewById(R.id.note_radiogroup);
        commit_result = (TextView) findViewById(R.id.dialog_commit_result);
        temperature_value = ((TextView) findViewById(R.id.dialog_temperature_value));

        note_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.default_radio1:
                        note = "default_radio1";
                        break;
                    case R.id.default_radio2:
                        note = "default_radio2";
                        break;
                }
            }
        });

        commit_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note_editText.getText() != null) {
                    note =note_editText.getText().toString();
                }

                Log.d(TAG, "onClick: status: "+status +" note: "+note);
                // TODO: 2018/10/8 传值出去 判断

                temperatureDialog.dismiss();
            }
        });
    }






}