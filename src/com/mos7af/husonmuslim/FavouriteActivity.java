package com.mos7af.husonmuslim;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FavouriteActivity extends Activity {

	ListView list;
	TextView chapter_name;
	FavouriteAdapter favouriteAdapter;
	private FavouriteActivity _scope;
	private GetTask getTask;
	private ArrayList<HashMap<String, String>> semiChaptersList;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_fav);
		_scope = this;
		
		list = (ListView) findViewById(R.id.list);
		chapter_name = (TextView) findViewById(R.id.chapter_name);
		String fontPath = "fonts/arabic.ttf";

		Typeface tf = Typeface.createFromAsset(
				FavouriteActivity.this.getAssets(), fontPath);
		chapter_name.setTypeface(tf);
		
		RelativeLayout relativeclic1 = (RelativeLayout) findViewById(R.id.footer);
		relativeclic1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getTask.cancel(true);
				getTask = new GetTask();
				getTask.execute();
			}
		});
		
		getTask = new GetTask();
		getTask.execute();
	}

	private class GetTask extends AsyncTask<Void, Void, ReturnModel> {
		@Override
		protected ReturnModel doInBackground(Void... params) {
			return GetData();
		}

		@Override
		protected void onPostExecute(ReturnModel result) {

			ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
			
			loading.setVisibility(View.GONE);
			semiChaptersList = result.get_semi_chapters();
			favouriteAdapter = new FavouriteAdapter(_scope,
					semiChaptersList);
			chapter_name.setText(getString(R.string.fav_list));
			list.setAdapter(favouriteAdapter);
			
			list.setOnItemClickListener(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					load_content(position);
					//setResult(200, in);
					//finish();
				}
			});
		}
	}
	public void load_content(int position)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("_id", semiChaptersList.get(position).get("chapter_id"));
		AppManager.content_id =Integer.parseInt( semiChaptersList.get(position).get("content_id"));
		AppManager.dummy_chapter = map;
		AppManager.chapter = AppManager.dummy_chapter;
		AppManager.semi_chapter = semiChaptersList.get(position);
		Intent in = new Intent(getApplicationContext(),
				ContentActivity.class);
		startActivityForResult(in, 300);
	}
	private ReturnModel GetData() {
		semiChaptersList = new ArrayList<HashMap<String, String>>();
		semiChaptersList = HusonMuslimActivity.myDbHelper
				.get_semi_chapters_fav();
		ReturnModel returnModel = new ReturnModel();
		returnModel.set_semi_chapters(semiChaptersList);
		return returnModel;
	}

	private class ReturnModel {
		private ArrayList<HashMap<String, String>> semiChaptersList;

		public ArrayList<HashMap<String, String>> get_semi_chapters() {
			return semiChaptersList;
		}

		public void set_semi_chapters(
				ArrayList<HashMap<String, String>> _semiChaptersList) {
			this.semiChaptersList = _semiChaptersList;
		}

	}

}
