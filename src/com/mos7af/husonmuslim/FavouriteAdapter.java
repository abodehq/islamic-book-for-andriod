package com.mos7af.husonmuslim;

import java.util.ArrayList;
import java.util.HashMap;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FavouriteAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	WebView content;
	public FavouriteAdapter(Activity a,
			ArrayList<HashMap<String, String>> _source) {
		activity = a;
		data = _source;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	HashMap<String, String> sura;
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.ly_fav_item, null);

		sura = new HashMap<String, String>();
		sura = data.get(position);

		TextView semi_chapter_title = (TextView) vi.findViewById(R.id.title);
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image);
		thumb_image.setImageResource(R.drawable.delete_selector);
		thumb_image.setTag(position);
		thumb_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sura = data.get(Integer.parseInt(String.valueOf(v.getTag())));
			
				// TODO Auto-generated method stub
				HusonMuslimActivity.myDbHelper.delete_fav(sura.get("favourite_id"));
				Toast.makeText(activity.getApplicationContext(),activity.getString(R.string.fav_delete) ,
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent(activity.getApplicationContext(),
						FavouriteActivity.class);
				activity.startActivityForResult(i, 100);
				activity.finish();
				
			}
		});
		
		
		ImageView btn_more = (ImageView) vi.findViewById(R.id.btn_more);
		btn_more.setImageResource(R.drawable.more_selector);
		btn_more.setTag(position);
		btn_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				FavouriteActivity absdbsdb= (FavouriteActivity)activity;
				absdbsdb.load_content(Integer.parseInt(String.valueOf(v.getTag())));
			}
		});
		
		
		
		
		String fontPath = "fonts/arabic.ttf";
		Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontPath);
		semi_chapter_title.setTypeface(tf);
		semi_chapter_title.setText(activity
				.getString(R.string.semi_chapter_title)+ " " + sura.get("name"));
		
		String font_color = String.format("#%06X", (0xFFFFFF & AppManager.font_color));
		String font_type = AppManager.fonts[AppManager.selected_font_index];
		
		content = (WebView) vi.findViewById(R.id.webkit);
		
		content.setFocusable(false);
		content.setFocusableInTouchMode(false);
		content.setBackgroundColor(AppManager.bg_color);
		
		String html = sura.get("content");
		html = html.replace("[1]", font_type);
		html = html.replace("[2]", ""+AppManager.font_size);
		html = html.replace("[3]",font_color);
		content.loadDataWithBaseURL(null,html , "text/html", "UTF-8", "UTF-8");
		return vi;
	}
}