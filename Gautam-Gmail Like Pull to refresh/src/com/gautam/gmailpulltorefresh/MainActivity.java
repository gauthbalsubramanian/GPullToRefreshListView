package com.gautam.gmailpulltorefresh;

import java.util.Timer;
import java.util.TimerTask;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gautam.listview.OnDetectScrollListener;
import com.rahuljiresal.ptr_demo.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		Fragment fragment = null;
		fragment = new SimpleListViewFragment();
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class SimpleListViewFragment extends Fragment {

		private PullToRefreshLayout mPullToRefreshLayout;
		private com.gautam.listview.ListView myList;

		public SimpleListViewFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			String[] listContent = { "January", "February", "March", "April",
					"May", "June", "July", "August", "September", "October",
					"November", "December", "January", "February", "March",
					"April", "May", "June", "July", "August", "September",
					"October", "November", "December", "January", "February",
					"March", "April", "May", "June", "July", "August",
					"September", "October", "November", "December", "January",
					"February", "March", "April", "May", "June", "July",
					"August", "September", "October", "November", "December"

			};

			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			myList = (com.gautam.listview.ListView) rootView
					.findViewById(R.id.ptr_list);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1,
					listContent);

			myList.setOnDetectScrollListener(new OnDetectScrollListener() {
				@Override
				public void onUpScrolling() {
				
				}

				@Override
				public void onDownScrolling() {


				}

				@Override
				public void onUpScrollBegin() {
					System.out.println("UP!!");
					getActivity().getActionBar().show();
				}

				@Override
				public void onDownScrollBegin() {
					System.out.println("DOWN!!");
					getActivity().getActionBar().hide();
				}
			});

			myList.setAdapter(adapter);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			// This is the View which is created by ListFragment
			ViewGroup viewGroup = (ViewGroup) view;

			// We need to create a PullToRefreshLayout manually
			mPullToRefreshLayout = new PullToRefreshLayout(
					viewGroup.getContext());

			// We can now setup the PullToRefreshLayout
			ActionBarPullToRefresh.from(getActivity())
					.insertLayoutInto(viewGroup) // We need to insert the
													// PullToRefreshLayout into
													// the Fragment's ViewGroup
					.theseChildrenArePullable(myList) // We need to mark the
														// ListView and it's
														// Empty View as
														// pullable This is
														// because they are
														// not direct
														// children of the
					// ViewGroup
					.options(
							Options.create()
									.refreshingText(
											"Fetching A lot of Stuff...")
									.pullText("Pull me down!")
									.refreshOnUp(true)
									.scrollDistance(.6f)
									.releaseText("Let go of me!!!")
									.titleTextColor(android.R.color.black)
									.progressBarColor(
											android.R.color.holo_orange_light)
									.headerBackgroundColor(
											android.R.color.holo_blue_light)
									.progressBarStyle(
											Options.PROGRESS_BAR_STYLE_OUTSIDE)
									.build()).listener(new OnRefreshListener() { // We
																					// can
																					// now
																					// complete
																					// the
																					// setup
																					// as
																					// desired
								@Override
								public void onRefreshStarted(View view) {

									Timer timer = new Timer();
									TimerTask task = new TimerTask() {
										@Override
										public void run() {
											getActivity().runOnUiThread(
													new Runnable() {
														@Override
														public void run() {
															mPullToRefreshLayout
																	.setRefreshComplete();
														}
													});
										}
									};
									timer.schedule(task, 5000);
								}
							}).setup(mPullToRefreshLayout);
		}
	}

}
