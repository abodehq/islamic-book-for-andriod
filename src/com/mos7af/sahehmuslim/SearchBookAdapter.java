package com.mos7af.sahehmuslim;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchBookAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	// public ImageLoader imageLoader;

	public SearchBookAdapter(Activity a,
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

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.ly_search_item, null);

		TextView chapter = (TextView) vi.findViewById(R.id.chapter);
		TextView semichapter = (TextView) vi.findViewById(R.id.semichapter);
		TextView content = (TextView) vi.findViewById(R.id.content);
		ImageView thumb_image = (ImageView) vi
				.findViewById(R.id.semi_chapter_img);
		thumb_image.setImageResource(R.drawable.pageicon);
		ImageView chapter_img = (ImageView) vi.findViewById(R.id.chapter_img);
		chapter_img.setImageResource(R.drawable.chaptericon);
		HashMap<String, String> sura = new HashMap<String, String>();
		sura = data.get(position);
		String fontPath = "fonts/arabic.ttf";
		Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontPath);
		chapter.setTypeface(tf);
		chapter.setText(sura.get("chaptername"));
		semichapter.setTypeface(tf);
		semichapter.setText(sura.get("semichaptername"));
		fontPath = "fonts/"+AppManager.fonts[AppManager.selected_font_index]+".ttf";
		tf = Typeface.createFromAsset(activity.getAssets(), fontPath);
		content.setTypeface(tf);
		content.setText(sura.get("content"));
		return vi;
	}
}