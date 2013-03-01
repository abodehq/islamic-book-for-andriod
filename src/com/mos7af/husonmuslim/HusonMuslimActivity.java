package com.mos7af.husonmuslim;

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
import android.widget.Toast;
//import net.margaritov.preference.colorpicker.*;
public class HusonMuslimActivity extends Activity {
	public static DataBaseHelper myDbHelper = null;
	public static String PACKAGE_NAME;
	RelativeLayout footer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_book_main);
		AppManager appManager = AppManager.getInstance();
		//ColorPickerDialog c = new ColorPickerDialog(HusonMuslimActivity.this, color.holo_blue_bright);
		//c.show();
		PACKAGE_NAME = getApplicationContext().getPackageName();
		try {
			if(myDbHelper==null)
				myDbHelper = new DataBaseHelper(HusonMuslimActivity.this);
		} catch (Exception e) {

		}

		footer = (RelativeLayout) findViewById(R.id.footer);
		footer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						FavouriteActivity.class);
				startActivityForResult(i, 100);

			}
		});
		
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
		TextView txt_fav = (TextView) findViewById(R.id.txt_fav);
		txt_fav.setTypeface(tf);
		footer.setVisibility(footer.INVISIBLE);
		if (myDbHelper.checkDataBase()) {
			RelativeLayout initlayout = (RelativeLayout) findViewById(R.id.initlayout);
			initlayout.setVisibility(initlayout.INVISIBLE);
			footer.setVisibility(footer.VISIBLE);
		}
		

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
			footer.setVisibility(footer.VISIBLE);
		}

	};
	@Override
	public void onBackPressed() {
	  
		myDbHelper.close();
	    finish();
	}

}
