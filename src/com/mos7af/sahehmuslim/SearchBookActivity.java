package com.mos7af.sahehmuslim;

import android.app.Activity;
import android.app.SearchManager;
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
import android.widget.Toast;


public class SearchBookActivity extends Activity {
	ListView list;
	TextView chapter_name;
	SearchBookAdapter reciterItemAdapter;
	private SearchBookActivity _scope;
	private GetTask getTask;
	private ArrayList<HashMap<String, String>> recitersList;
	private String value;

	public void onCreat1(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_search);

		value = getIntent().getStringExtra(SearchManager.QUERY);
		ArrayList<HashMap<String, String>> surasList = BookMainActivity.myDbHelper
				.getSearch(value);
		String res = "empty";
		if (!surasList.isEmpty())
			res = "-" + surasList.size();
		Toast.makeText(SearchBookActivity.this, res, Toast.LENGTH_SHORT).show();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_search);
		_scope = this;

		value = getIntent().getStringExtra(SearchManager.QUERY);

		list = (ListView) findViewById(R.id.list);
		chapter_name = (TextView) findViewById(R.id.chapter_name);
		String fontPath = "fonts/arabic.ttf";

		Typeface tf = Typeface.createFromAsset(
				SearchBookActivity.this.getAssets(), fontPath);
		chapter_name.setTypeface(tf);
		getTask = new GetTask();
		getTask.execute();
		TextView searchTitle = (TextView) findViewById(R.id.searchTitle);
		searchTitle.setTypeface(tf);

		RelativeLayout relativeclic1 = (RelativeLayout) findViewById(R.id.footer);
		relativeclic1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSearchRequested();

			}
		});
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

			recitersList = result.getheadlines();

			reciterItemAdapter = new SearchBookAdapter(_scope, recitersList);
			chapter_name.setText(getString(R.string.search_result));
			list.setAdapter(reciterItemAdapter);

			list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

				}
			});

		}
	}

	private ReturnModel GetData() {

		recitersList = new ArrayList<HashMap<String, String>>();

		recitersList = BookMainActivity.myDbHelper.getSearch(value);

		ReturnModel returnModel = new ReturnModel();
		returnModel.setheadlines(recitersList);
		return returnModel;
	}

	private class ReturnModel {
		private ArrayList<HashMap<String, String>> recitersList;

		public ArrayList<HashMap<String, String>> getheadlines() {
			return recitersList;
		}

		public void setheadlines(ArrayList<HashMap<String, String>> _songsList) {
			this.recitersList = _songsList;
		}

	}

}
