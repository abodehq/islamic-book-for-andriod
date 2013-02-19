package com.mos7af.sahehmuslim;

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

public class SemiChaptersActivity extends Activity {

	ListView list;
	TextView chapter_name;
	SemiChaptersAdapter semiChaptersAdapter;
	private SemiChaptersActivity _scope;
	private GetTask getTask;
	private ArrayList<HashMap<String, String>> semiChaptersList;
	private HashMap<String, String> chapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_semi_chapters);
		_scope = this;
		RelativeLayout relativeclic1 = (RelativeLayout) findViewById(R.id.footer);
		relativeclic1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSearchRequested();

			}
		});
		String target = getIntent().getStringExtra("target");

		if (target.equals("dummy")) {
			chapter = AppManager.dummy_chapter;

		} else {
			chapter = AppManager.chapter;

		}

		list = (ListView) findViewById(R.id.list);
		chapter_name = (TextView) findViewById(R.id.chapter_name);
		String fontPath = "fonts/arabic.ttf";

		Typeface tf = Typeface.createFromAsset(
				SemiChaptersActivity.this.getAssets(), fontPath);
		chapter_name.setTypeface(tf);
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
			semiChaptersAdapter = new SemiChaptersAdapter(_scope,
					semiChaptersList);
			chapter_name.setText(semiChaptersList.get(0).get("chapter_name"));
			list.setAdapter(semiChaptersAdapter);
			list.setSelection(AppManager.semi_chapter_list_position);
			list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent in = new Intent(getApplicationContext(),
							ChaptersActivity.class);
					AppManager.chapter = AppManager.dummy_chapter;
					AppManager.semi_chapter_list_position = position;
					AppManager.semi_chapter = semiChaptersList.get(position);
					setResult(200, in);
					finish();
				}
			});
		}
	}

	private ReturnModel GetData() {
		semiChaptersList = new ArrayList<HashMap<String, String>>();
		semiChaptersList = BookMainActivity.myDbHelper
				.get_semi_chapter_by_id(chapter.get("_id"));
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
