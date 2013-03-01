package com.mos7af.husonmuslim;

import java.util.HashMap;

import android.graphics.Color;

public class AppManager {
	public static HashMap<String, String> chapter;
	public static HashMap<String, String> dummy_chapter;
	public static HashMap<String, String> semi_chapter;
	public static int semi_chapter_list_position = 0;// to make user go the last
	private static AppManager instance = null;

	public static int font_color = Color.BLACK;
	public static int font_size = 24;
	public static int bg_color = Color.WHITE;
	//public static String[] fonts = {"tashkeel3","arabic","tashkeel8","arabic2","arabic3","tashkeel2","tashkeel4","arabic1","tashkeel7"};
	public static String[] fonts = {"arabic","font2","font1","font3","font4","font5","font6","font7","font8","font9"};
	
	public static int selected_font_index = 0;
	
	public static int content_id = 0;
	public static AppManager getInstance() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	public AppManager() {
		chapter = new HashMap<String, String>();
		chapter.put("_id", "2");// default chapter id

		dummy_chapter = new HashMap<String, String>();
		dummy_chapter.put("_id", "2");// this for dummy when user click on the
										// list

		semi_chapter = new HashMap<String, String>(); // default semi chapter id
		semi_chapter.put("_id", "100");
		semi_chapter.put("chapter_id", "2");

	}

}
