package com.mos7af.husonmuslim;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import android.widget.TextView;

public class ContentActivity extends Activity {

	public static int selectedImagePosition = 0;
	private TextView txtTitle;
	private TextView content_count;
	private WebView webview;
	public static int zoomFactor = -1;
	ImageView leftArrowImageView;
	ImageView rightArrowImageView;
	GradientDrawable gd;
	private ArrayList<HashMap<String, String>> containerList;
	private ArrayList<HashMap<String, String>> semiChaptersList;
	RelativeLayout root;
	ImageButton btn_fav;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ly_book_content);
		webview = (WebView) findViewById(R.id.webkit);
		
	

		gd = new GradientDrawable();
		
		root=(RelativeLayout)findViewById(R.id.webviewcontainer);
		
		
		String fontPath = "fonts/arabic.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

		setupUI();

		ImageButton semiChapter = (ImageButton) findViewById(R.id.semiChapter);
		semiChapter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						SemiChaptersActivity.class);
				in.putExtra("target", "origin");
				startActivityForResult(in, 100);
			}
		});

		ImageButton search = (ImageButton) findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onSearchRequested();
			}
		});

		ImageButton zoomOut = (ImageButton) findViewById(R.id.ZoomOut);
		zoomOut.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int c = (int) (100 * webview.getScale());
				if (c > 75) {
					webview.zoomOut();
					zoomFactor = (int) (100 * webview.getScale());
				}
			}
		});
		ImageButton zoomIn = (ImageButton) findViewById(R.id.ZoomIn);

		zoomIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int c = (int) (100 * webview.getScale());
				if (c < 185) {
					webview.zoomIn();
					zoomFactor = (int) (100 * webview.getScale());

				}
			}
		});
		
		btn_fav = (ImageButton) findViewById(R.id.btn_fav);
		
		btn_fav.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
			
				//Toast.makeText(getApplicationContext(),containerList.get(0).get("_id") ,
					//	Toast.LENGTH_SHORT).show();
				HusonMuslimActivity.myDbHelper
				.toggle_fav(containerList.get(0).get("_id"));
				
				boolean result =  HusonMuslimActivity.myDbHelper.get_fav_count(containerList.get(0).get("_id"));
				if(result)
				{
					btn_fav.setBackgroundDrawable(getResources().getDrawable(R.drawable.fav_selector));
					Toast.makeText(getApplicationContext(),getString(R.string.fav_add) ,
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					btn_fav.setBackgroundDrawable(getResources().getDrawable(R.drawable.fav_not_selector));
					Toast.makeText(getApplicationContext(),getString(R.string.fav_delete) ,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		

		ImageButton gList = (ImageButton) findViewById(R.id.gList);
		
		gList.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						ChaptersActivity.class);
				startActivityForResult(i, 100);

			}
		});
		int position = 0;
		
		get_book_content(0);
		
		
	}



	private void setupUI() {

		txtTitle = (TextView) findViewById(R.id.textTitle);
		content_count = (TextView) findViewById(R.id.content_count);
		String fontPath = "fonts/arabic.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		txtTitle.setTypeface(tf);
		content_count.setTypeface(tf);
		txtTitle.setSelected(true);
		leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
		rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);

		//get_semi_chapter_data();
		
		leftArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition > 0) {
					--selectedImagePosition;
					AppManager.semi_chapter = semiChaptersList
							.get(selectedImagePosition);
					setSelectedImage(selectedImagePosition);
					rightArrowImageView
							.setVisibility(leftArrowImageView.VISIBLE);
				}
				if (selectedImagePosition == 0) {
					leftArrowImageView
							.setVisibility(leftArrowImageView.INVISIBLE);
				}

			}
		});

		rightArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition < semiChaptersList.size() - 1) {
					++selectedImagePosition;
					AppManager.semi_chapter = semiChaptersList
							.get(selectedImagePosition);
					setSelectedImage(selectedImagePosition);
					leftArrowImageView
							.setVisibility(leftArrowImageView.VISIBLE);
				}
				if (selectedImagePosition == semiChaptersList.size() - 1) {
					rightArrowImageView
							.setVisibility(leftArrowImageView.INVISIBLE);
				}

			}
		});

	}

	

	private void setSelectedImage(int selectedImagePosition) {

		
		txtTitle.setText(getString(R.string.content_loading));
		myHandler.removeCallbacks(mMyRunnable);
		myHandler.postDelayed(mMyRunnable, 10);

	}
	String html;
	private Handler myHandler = new Handler();
	private Runnable mMyRunnable = new Runnable() {
		@Override
		public void run() {
			try {

				
				
				content_count.setText("* " + semiChaptersList.size() + " / "
						+ (selectedImagePosition + 1) + " *");
				
				
					
				containerList = HusonMuslimActivity.myDbHelper
						.get_content_by_id(semiChaptersList.get(
								selectedImagePosition).get("hadith_id"));

				if (!containerList.isEmpty()) {
					
					
					
					txtTitle.setText(" * "
							+ getString(R.string.content_chapter_title)
							+ " "
							+ containerList.get(0).get("chapter_name")
							+" "
							+ getString(R.string.content_semi_chapter_title)
							+" "
							+ containerList.get(0).get("semi_chapter_name")
							+ " * ");
					
					webview.setBackgroundColor(AppManager.bg_color);
					gd.setColor(AppManager.bg_color);
					gd.setCornerRadius(10);
					gd.setStroke(1, 0x444444);
					root.setBackgroundDrawable(gd);
					
					boolean result =  HusonMuslimActivity.myDbHelper.get_fav_count(containerList.get(0).get("_id"));
					if(result)
						btn_fav.setBackgroundDrawable(getResources().getDrawable(R.drawable.fav_selector));
					else
						btn_fav.setBackgroundDrawable(getResources().getDrawable(R.drawable.fav_not_selector));
					
					String font_color = String.format("#%06X", (0xFFFFFF & AppManager.font_color));
					String font_type = AppManager.fonts[AppManager.selected_font_index];
					//final String html ="<html><head><META http-equiv='Content-Type' content='text/html;charset=UTF-8'><META name='apple-mobile-web-app-capable' content='yes'><META name='apple-mobile-web-app-status-bar-style' content='black'><style>@font-face { font-family: arabic;src: url('file:///android_asset/fonts/"+font_type+".ttf')}body   {font-family: arabic;margin:0;font-size: "+AppManager.font_size+"px;font-weight: normal;color:"+font_color+";text-align: right;}</style></head><body>" + (containerList.get(0).get("content"))+ "</body></html>";
					html = (containerList.get(0).get("content"));
					html = html.replace("[1]", font_type);
					html = html.replace("[2]", ""+AppManager.font_size);
					html = html.replace("[3]",font_color);
					 webview.postDelayed(new Runnable() {
					        @Override
					        public void run() {
					        	webview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", "UTF-8");
					        }
					    }, 10);
					
				} else {
					webview.loadData(
							"<html><head><META http-equiv='Content-Type' content='text/html;charset=UTF-8'><META name='apple-mobile-web-app-capable' content='yes'><META name='apple-mobile-web-app-status-bar-style' content='black'><style>@font-face { font-family: arabic;src: url('file:///android_asset/fonts/arabic1.ttf')}body   {font-family: arabic;margin:0;font-size: 22px;font-weight: normal;color:#ff0000;text-align: right;}</style></head><body>"
									+ getString(R.string.content_faild)
									+ "</body></html>", "text/html", "UTF-8");
				}
				webview.requestFocus();

				if (zoomFactor != -1)
					webview.setInitialScale(zoomFactor);

			} catch (Exception e) {

			}
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {
			selectedImagePosition = data.getExtras().getInt("index");
			setSelectedImage(data.getExtras().getInt("index"));
		}
		if (resultCode == 200) {
			get_book_content(0);
		}

	}

	private void get_book_content(int pos) {
		selectedImagePosition = pos;
		txtTitle.setText(getString(R.string.content_chapter_title));
		
		get_semi_chapter_data();
		
		myHandler.removeCallbacks(mMyRunnable);
		myHandler.postDelayed(mMyRunnable, 10);
	}
	private void get_semi_chapter_data()
	{
		semiChaptersList = HusonMuslimActivity.myDbHelper
				.get_semi_chapter_content(AppManager.chapter.get("_id"),
						AppManager.semi_chapter.get("_id"));
		if (semiChaptersList.isEmpty()) {
			leftArrowImageView.setVisibility(leftArrowImageView.INVISIBLE);
			rightArrowImageView.setVisibility(rightArrowImageView.INVISIBLE);

		} else {
			if (semiChaptersList.size() == 1)
				rightArrowImageView
						.setVisibility(rightArrowImageView.INVISIBLE);
			else
				rightArrowImageView.setVisibility(rightArrowImageView.VISIBLE);
			leftArrowImageView.setVisibility(leftArrowImageView.INVISIBLE);
		}
		selectedImagePosition = 0;
		for(int i=0;i<semiChaptersList.size();i++)
		{
			
			if(semiChaptersList.get(i).get("hadith_id").toString().equals(String.valueOf(AppManager.content_id)))
			{
				selectedImagePosition = i;
				break;
			}
		}
		leftArrowImageView.setVisibility(leftArrowImageView.VISIBLE);
		rightArrowImageView.setVisibility(rightArrowImageView.VISIBLE);
		if(selectedImagePosition==semiChaptersList.size()-1)
		{
			
			rightArrowImageView.setVisibility(rightArrowImageView.INVISIBLE);
		}
		if(selectedImagePosition==0)
		{
			leftArrowImageView.setVisibility(leftArrowImageView.INVISIBLE);
		
		}
	}
}