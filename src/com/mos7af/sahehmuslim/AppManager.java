package com.mos7af.sahehmuslim;

import java.util.HashMap;

import android.graphics.Color;

public class AppManager {
	public static HashMap<String, String> chapter;
	public static HashMap<String, String> dummy_chapter;
	public static HashMap<String, String> semi_chapter;
	public static int semi_chapter_list_position = 0;// to make user go the last
	private static AppManager instance = null;

	public static int font_color = Color.BLACK;
	public static int font_size = 26;
	public static int bg_color = Color.WHITE;
	public static String[] fonts = {"arabic1","arabic","tashkeel8","arabic2","arabic3","tashkeel2","tashkeel3","tashkeel4","tashkeel7"};
	public static int selected_font_index = 0;
	public static AppManager getInstance() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	public AppManager() {
		chapter = new HashMap<String, String>();
		chapter.put("_id", "1");// default chapter id

		dummy_chapter = new HashMap<String, String>();
		dummy_chapter.put("_id", "1");// this for dummy when user click on the
										// list

		semi_chapter = new HashMap<String, String>(); // default semi chapter id
		semi_chapter.put("_id", "1");
		semi_chapter.put("chapter_id", "1");

	}

}
