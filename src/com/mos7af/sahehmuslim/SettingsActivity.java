package com.mos7af.sahehmuslim;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class SettingsActivity extends Activity {

	private int selectedColorOption = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_setting);
		final ColorPickerDialog d = new ColorPickerDialog(this, 0xffffffff);
		//d.setAlphaSliderVisible(true);
		
		Spinner font_size=(Spinner) findViewById(R.id.font_size);
		//font_size.setSelection(1);
		for(int i=0;i<font_size.getCount();i++)
		{
			if(Integer.parseInt(font_size.getItemAtPosition(i).toString())==AppManager.font_size)
			{
				AppManager.font_size = Integer.parseInt(font_size.getItemAtPosition(i).toString());
				font_size.setSelection(i);
				break;
			}
		}
		font_size.setOnItemSelectedListener(new OnItemSelectedListener() {
        
            public void onItemSelected(AdapterView<?> arg0,  View arg1,
                    int pos, long arg3) {
            
            	AppManager.font_size = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
            }

        
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

		
		Spinner font_type=(Spinner) findViewById(R.id.font_type);
		//font_size.setSelection(1);
		font_type.setSelection(AppManager.selected_font_index);
		font_type.setOnItemSelectedListener(new OnItemSelectedListener() {
        
            public void onItemSelected(AdapterView<?> arg0,  View arg1,
                    int pos, long arg3) {
            
            	AppManager.selected_font_index = pos;
            }

        
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
		
		final Button bg_color_btn=(Button) findViewById(R.id.bg_color_btn);
		bg_color_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectedColorOption = 0;
				d.show();
			}
		});
		
		final Button font_color_btn=(Button) findViewById(R.id.font_color_btn);
		font_color_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectedColorOption = 1;
				d.show();
			}
		});
		
		final EditText bg_color_dummy=(EditText) findViewById(R.id.bg_color_dummy);
		final EditText font_color_dummy=(EditText) findViewById(R.id.font_color_dummy);

		d.setButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(selectedColorOption==0)
				{
					AppManager.bg_color = d.getColor();
					bg_color_dummy.setBackgroundColor(d.getColor());
					bg_color_btn.setText(String.format("#%06X", (0xFFFFFF & d.getColor())));
				}else
				{
					AppManager.font_color = d.getColor();
					font_color_dummy.setBackgroundColor(d.getColor());
					font_color_btn.setText(String.format("#%06X", (0xFFFFFF & d.getColor())));
				}
			//d.getColor();
				

			}
		});

		d.setButton2("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		font_color_dummy.setBackgroundColor(AppManager.font_color);
		font_color_btn.setText(String.format("#%06X", (0xFFFFFF & AppManager.font_color)));
		
		bg_color_dummy.setBackgroundColor(AppManager.bg_color);
		bg_color_btn.setText(String.format("#%06X", (0xFFFFFF & AppManager.bg_color)));
		
	}

	
}
