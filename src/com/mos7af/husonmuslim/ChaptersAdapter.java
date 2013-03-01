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

public class ChaptersAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;

	public ChaptersAdapter(Activity a,
			ArrayList<HashMap<String, String>> _source) {
		data = _source;
		activity = a;
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
			vi = inflater.inflate(R.layout.ly_chapter_item, null);

		HashMap<String, String> chapter = new HashMap<String, String>();
		chapter = data.get(position);
		String fontPath = "fonts/arabic.ttf";
		TextView txtChapterTitle = (TextView) vi.findViewById(R.id.textView1);
		ImageView imgChapter = (ImageView) vi.findViewById(R.id.imageView1);
		String name = chapter.get("name");
		//if (name.length() > 15) {
		//	name = name.substring(0, 14) + "...";
		//}
		txtChapterTitle.setText(name);
		imgChapter.setImageResource(R.drawable.chaptericon);
		imgChapter.setImageDrawable(activity.getResources().getDrawable(activity.getResources().getIdentifier(chapter.get("img"),"drawable", activity.getPackageName())));
		Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontPath);
		txtChapterTitle.setTypeface(tf);
		return vi;
	}

}
