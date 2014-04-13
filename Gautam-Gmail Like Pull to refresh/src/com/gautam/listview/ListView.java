package com.gautam.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

public class ListView extends android.widget.ListView {

	private OnScrollListener onScrollListener;
	private OnDetectScrollListener onDetectScrollListener;

	public ListView(Context context) {
		super(context);
		onCreate(context, null, null);
	}

	public ListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		onCreate(context, attrs, null);
	}

	public ListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		onCreate(context, attrs, defStyle);
	}

	@SuppressWarnings("UnusedParameters")
	private void onCreate(Context context, AttributeSet attrs, Integer defStyle) {
		setListeners();
	}

	private void setListeners() {
		super.setOnScrollListener(new OnScrollListener() {

			private int oldTop;
			private int oldFirstVisibleItem;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (onScrollListener != null) {
					onScrollListener.onScrollStateChanged(view, scrollState);
				}
				final int currentFirstVisibleItem = getFirstVisiblePosition();

				if (onDetectScrollListener != null) {
					onDetectedListScroll(view, currentFirstVisibleItem);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (onScrollListener != null) {
					onScrollListener.onScroll(view, firstVisibleItem,
							visibleItemCount, totalItemCount);
				}
		        
			}

			private void onDetectedListScroll(AbsListView absListView,
					int firstVisibleItem) {
				View view = absListView.getChildAt(0);
				int top = (view == null) ? 0 : view.getTop();

				if (firstVisibleItem == oldFirstVisibleItem) {
					if (top > oldTop) {
						onDetectScrollListener.onUpScrollBegin();
					} else if (top < oldTop) {
						onDetectScrollListener.onDownScrollBegin();
					}
				} else {
					if (firstVisibleItem < oldFirstVisibleItem) {
						onDetectScrollListener.onUpScrollBegin();
					} else {
						onDetectScrollListener.onDownScrollBegin();
					}
				}

				oldTop = top;
				oldFirstVisibleItem = firstVisibleItem;
			}
		});
	}

	@Override
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	public void setOnDetectScrollListener(
			OnDetectScrollListener onDetectScrollListener) {
		this.onDetectScrollListener = onDetectScrollListener;
	}
}