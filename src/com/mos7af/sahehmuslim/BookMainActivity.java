package com.mos7af.sahehmuslim;

import android.R.color;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.margaritov.preference.colorpicker.*;
public class BookMainActivity extends Activity {
	public static DataBaseHelper myDbHelper;
	public static String PACKAGE_NAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_book_main);
		//ColorPickerDialog c = new ColorPickerDialog(BookMainActivity.this, color.holo_blue_bright);
		//c.show();
		PACKAGE_NAME = getApplicationContext().getPackageName();
		try {

			myDbHelper = new DataBaseHelper(BookMainActivity.this);
		} catch (Exception e) {

		}

		TextView book_txt = (TextView) findViewById(R.id.book_txt);
		String fontPath = "fonts/header.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		book_txt.setTypeface(tf);
		book_txt.setText(getString(R.string.book_name));

		fontPath = "fonts/arabic.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		TextView init_txt = (TextView) findViewById(R.id.init_txt);
		init_txt.setTypeface(tf);
		init_txt.setText(getString(R.string.loading_book));

		if (myDbHelper.checkDataBase()) {
			RelativeLayout initlayout = (RelativeLayout) findViewById(R.id.initlayout);
			initlayout.setVisibility(initlayout.INVISIBLE);

		}
		AppManager appManager = AppManager.getInstance();

		ImageButton hadith_details_btn = (ImageButton) findViewById(R.id.hadith_details_btn);

		hadith_details_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						ContentActivity.class);
				startActivityForResult(i, 100);
			}
		});

		ImageButton search_btn = (ImageButton) findViewById(R.id.search_btn);

		search_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onSearchRequested();
			}
		});

		ImageButton setting_btn = (ImageButton) findViewById(R.id.settings_btn);

		setting_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						SettingsActivity.class);
				startActivityForResult(i, 100);
			}
		});
		myHandler.removeCallbacks(mMyRunnable);
		myHandler.postDelayed(mMyRunnable, 1500);
	}

	private Handler myHandler = new Handler();
	private Runnable mMyRunnable = new Runnable() {
		@Override
		public void run() {
			myDbHelper.InitDB();
			RelativeLayout initlayout = (RelativeLayout) findViewById(R.id.initlayout);
			initlayout.setVisibility(initlayout.INVISIBLE);
		}

	};

}
