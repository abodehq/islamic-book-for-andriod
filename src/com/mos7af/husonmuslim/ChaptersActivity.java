package com.mos7af.husonmuslim;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ChaptersActivity extends Activity {

	ChaptersAdapter chaptersAdapter;
	private ChaptersActivity _scope;
	private GetTask getTask;
	private ArrayList<HashMap<String, String>> chaptersList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_chapters);
		RelativeLayout relativeclic1 = (RelativeLayout) findViewById(R.id.footer);
		relativeclic1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSearchRequested();

			}
		});
		_scope = this;
		getTask = new GetTask();
		getTask.execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 200) {

			Intent in = new Intent(getApplicationContext(),
					ContentActivity.class);
			setResult(200, in);
			finish();
		}
	}

	private class GetTask extends AsyncTask<Void, Void, ReturnModel> {
		@Override
		protected ReturnModel doInBackground(Void... params) {
			return GetData();
		}

		@Override
		protected void onPostExecute(ReturnModel result) {
			ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
			;
			loading.setVisibility(View.GONE);
			chaptersList = result.get_chapters();
			chaptersAdapter = new ChaptersAdapter(_scope, chaptersList);
			GridView gridView = (GridView) findViewById(R.id.gridView1);
			gridView.setAdapter(chaptersAdapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					AppManager.dummy_chapter = chaptersList.get(position);
					Intent in = new Intent(getApplicationContext(),
							SemiChaptersActivity.class);
					in.putExtra("target", "dummy");
					startActivityForResult(in, 100);
				}
			});

		}
	}

	private ReturnModel GetData() {

		ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
		;
		loading.setVisibility(View.VISIBLE);
		chaptersList = new ArrayList<HashMap<String, String>>();
		DataBaseHelper myDbHelper = HusonMuslimActivity.myDbHelper;
		chaptersList = myDbHelper.get_all_chapters();
		ReturnModel returnModel = new ReturnModel();
		returnModel.set_chapters(chaptersList);
		return returnModel;
	}

	private class ReturnModel {
		private ArrayList<HashMap<String, String>> chaptersList;

		public ArrayList<HashMap<String, String>> get_chapters() {
			return chaptersList;
		}

		public void set_chapters(
				ArrayList<HashMap<String, String>> _chaptersList) {
			this.chaptersList = _chaptersList;
		}
	}
}
