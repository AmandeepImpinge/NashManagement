package com.runtwo.main;

import java.util.ArrayList;
import java.util.HashMap;
import com.runtwo.constants.GlobalConstants;
import com.runtwo.webservice.WebServiceHandler;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFollowersScreen extends Fragment {

	LayoutInflater inflater;
	EditText searchEdit;
	ListView searchList;
	//ProgressDialog mProgressDialog = null;
	Globals global;
	ArrayList<HashMap<String, String>> followersArrayList = new ArrayList<HashMap<String, String>>();
	ActionBarRun ab;

	@Override
	public View onCreateView(LayoutInflater infl, ViewGroup container,
			Bundle savedInstanceState) {
		inflater = infl;

		container = (LinearLayout) inflater.inflate(R.layout.search_followers,
				null);

		ab = (ActionBarRun) container.findViewById(R.id.action_bar);
		ab.findViewById(R.id.run_txt).setVisibility(View.INVISIBLE);

		global = (Globals) getActivity().getApplicationContext();

		searchEdit = (EditText) container.findViewById(R.id.search_edit);
		searchList = (ListView) container.findViewById(R.id.search_list);

		searchEdit.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					String searchtext = "" + searchEdit.getText().toString();
					if (searchtext.length() > 0) {
						new SearchFollowers().execute("");
					}
				}
				return false;
			}
		});

		return container;
	}

	class SearchFollowers extends AsyncTask<String, Integer, String> {

		ProgressDialog mProgressDialog = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			/*mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setMessage("Searching...");
			mProgressDialog.show();*/
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Loading...");
		}

		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.searchFriendsFollowersService(
						getActivity(), "1", GlobalConstants.FOLLOWER_KEY,
						searchEdit.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			mProgressDialog.dismiss();
			
			Log.e("m dialog", ": " + mProgressDialog.isShowing());
			if (result.equalsIgnoreCase("true")) {
				if (global.getFollowersSearchList().size() > 0) {
					followersArrayList = global.getFollowersSearchList();
					searchList.setAdapter(new FollowerFollowingAdapter());
					if (mProgressDialog.isShowing()) {
						mProgressDialog.dismiss();
					}
				} else {
					Toast.makeText(getActivity(), "No result found...",
							Toast.LENGTH_SHORT).show();
				}
			} else if (result.equalsIgnoreCase("false")) {
				Toast.makeText(getActivity(),
						"" + global.getMessageOfResponse(), Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(), "Error Connecting to server.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class FollowerFollowingAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return followersArrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return followersArrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.follower_list_item,
						null);

				holder.username = (TextView) convertView
						.findViewById(R.id.user_name);
				holder.img = (ImageView) convertView
						.findViewById(R.id.user_img);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			HashMap<String, String> map = followersArrayList.get(position);

			holder.username.setText(""
					+ map.get(GlobalConstants.FOLLOWERS_USERNAME));

			String imgUrl = "" + map.get(GlobalConstants.FOLLOWERS_PROFILE_IMG);

			if (imgUrl.length() > 0) {
				// Load Image Here
			}

			if (position == 0) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.search_edit_back));
			} else if (position % 2 == 0) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.search_edit_back));
			} else {
				convertView.setBackgroundColor(Color.WHITE);
			}

			return convertView;
		}
	}

	class ViewHolder {
		TextView username;
		ImageView img;
	}

}