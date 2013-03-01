package com.mos7af.husonmuslim;

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

public class SemiChaptersAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public SemiChaptersAdapter(Activity a,
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
			vi = inflater.inflate(R.layout.ly_semi_chapter_item, null);

		TextView semi_chapter_title = (TextView) vi.findViewById(R.id.title);
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image);
		thumb_image.setImageResource(R.drawable.pageicon);
		HashMap<String, String> sura = new HashMap<String, String>();
		sura = data.get(position);
		String fontPath = "fonts/arabic.ttf";
		Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontPath);
		semi_chapter_title.setTypeface(tf);
		semi_chapter_title.setText(activity
				.getString(R.string.semi_chapter_title)+ " " + sura.get("name"));
		return vi;
	}
}