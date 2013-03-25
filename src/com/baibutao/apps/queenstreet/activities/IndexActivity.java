package com.baibutao.apps.queenstreet.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.baibutao.apps.queenstreet.R;
import com.baibutao.apps.queenstreet.activities.common.BaseActivity;
import com.baibutao.apps.queenstreet.androidext.ui.FlowView;
import com.baibutao.apps.queenstreet.androidext.ui.LazyScrollView;
import com.baibutao.apps.queenstreet.androidext.ui.LazyScrollView.OnScrollListener;
import com.baibutao.apps.queenstreet.util.Constants;

/**
 * @author lsb
 * 
 * @date 2012-5-29 下午10:52:49
 */
public class IndexActivity extends BaseActivity {

	private LazyScrollView waterfall_scroll;
	private LinearLayout waterfall_container;
	private ArrayList<LinearLayout> waterfall_items;
	private Display display;
	private AssetManager asset_manager;
	private List<String> image_filenames;
	private final String image_path = "images";
	private int item_width;

	private int column_count = Constants.COLUMN_COUNT;// 显示列数
	private int page_count = Constants.PICTURE_COUNT_PER_LOAD;// 每次加载30张图片

	private int current_page = 0;// 当前页数

	private int[] topIndex;
	private int[] bottomIndex;
	private int[] lineIndex;
	private int[] column_height;// 每列的高度

	private HashMap<Integer, String> pins;

	private int loaded_count = 0;// 已加载数量

	private HashMap<Integer, Integer>[] pin_mark = null;

	private Context context;

	private HashMap<Integer, FlowView> iviews;
	int scroll_height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.index);
		initDefault();
		initView();
	}

	private void initDefault() {
		context = this;
		display = this.getWindowManager().getDefaultDisplay();
		item_width = display.getWidth() / column_count;// 根据屏幕大小计算每列大小
		asset_manager = getAssets();

		column_height = new int[column_count];
		iviews = new HashMap<Integer, FlowView>();
		pins = new HashMap<Integer, String>();
		pin_mark = new HashMap[column_count];

		this.lineIndex = new int[column_count];
		this.bottomIndex = new int[column_count];
		this.topIndex = new int[column_count];

		for (int i = 0; i < column_count; i++) {
			lineIndex[i] = -1;
			bottomIndex[i] = -1;
			pin_mark[i] = new HashMap();
		}
	}

	private void initView() {
		waterfall_scroll = (LazyScrollView) findViewById(R.id.waterfall_scroll);
		waterfall_scroll.getView();

		waterfall_container = (LinearLayout) findViewById(R.id.waterfall_container);
		waterfall_scroll.setOnScrollListener(new WaterFallScrollerListener());

		waterfall_items = new ArrayList<LinearLayout>();

		for (int i = 0; i < column_count; i++) {
			LinearLayout itemLayout = new LinearLayout(this);
			LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(
					item_width, LayoutParams.WRAP_CONTENT);

			itemLayout.setPadding(2, 2, 2, 2);
			itemLayout.setOrientation(LinearLayout.VERTICAL);

			itemLayout.setLayoutParams(itemParam);
			waterfall_items.add(itemLayout);
			waterfall_container.addView(itemLayout);
		}

		// 加载所有图片路径
		try {
			image_filenames = Arrays.asList(asset_manager.list(image_path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 第一次加载
		addItemToContainer(current_page, page_count);
	}

	private Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.HANDLER_WHAT:

				FlowView v = (FlowView) msg.obj;
				int w = msg.arg1;
				int h = msg.arg2;
				String f = v.getFileName();

				// 此处计算列值
				int columnIndex = getMinValue(column_height);

				v.setColumnIndex(columnIndex);

				column_height[columnIndex] += h;

				pins.put(v.getId(), f);
				iviews.put(v.getId(), v);
				waterfall_items.get(columnIndex).addView(v);

				lineIndex[columnIndex]++;

				pin_mark[columnIndex].put(lineIndex[columnIndex],
						column_height[columnIndex]);
				bottomIndex[columnIndex] = lineIndex[columnIndex];
				break;
			}

		}

		@Override
		public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
			return super.sendMessageAtTime(msg, uptimeMillis);
		}
	};

	private int getMinValue(int[] array) {
		int m = 0;
		int length = array.length;
		for (int i = 0; i < length; ++i) {
			if (array[i] < array[m]) {
				m = i;
			}
		}
		return m;
	}

	private class WaterFallScrollerListener implements OnScrollListener {
		@Override
		public void onTop() {
			// 滚动到最顶端
			Log.d("LazyScroll", "Scroll to top");
		}

		@Override
		public void onScroll() {

		}

		@Override
		public void onBottom() {
			// 滚动到最低端
			addItemToContainer(++current_page, page_count);
		}

		@Override
		public void onAutoScroll(int l, int t, int oldl, int oldt) {
			scroll_height = waterfall_scroll.getMeasuredHeight();

			Log.d("MainActivity", "scroll_height:" + scroll_height);

			if (t > oldt) {// 向下滚动
				if (t > 2 * scroll_height) {// 超过两屏幕后

					for (int k = 0; k < column_count; k++) {

						LinearLayout localLinearLayout = waterfall_items.get(k);

						if (pin_mark[k].get(Math.min(bottomIndex[k] + 1,
								lineIndex[k])) <= t + 3 * scroll_height) {// 最底部的图片位置小于当前t+3*屏幕高度

							((FlowView) waterfall_items.get(k).getChildAt(
									Math.min(1 + bottomIndex[k], lineIndex[k])))
									.Reload();

							bottomIndex[k] = Math.min(1 + bottomIndex[k],
									lineIndex[k]);

						}
						Log.d("MainActivity",
								"headIndex:" + topIndex[k] + "  footIndex:"
										+ bottomIndex[k] + "  headHeight:"
										+ pin_mark[k].get(topIndex[k]));
						if (pin_mark[k].get(topIndex[k]) < t - 2
								* scroll_height) {// 未回收图片的最高位置<t-两倍屏幕高度

							int i1 = topIndex[k];
							topIndex[k]++;
							((FlowView) localLinearLayout.getChildAt(i1))
									.recycle();
							Log.d("MainActivity", "recycle,k:" + k
									+ " headindex:" + topIndex[k]);

						}
					}

				}
			} else {// 向上滚动
				if (t > 2 * scroll_height) {// 超过两屏幕后
					for (int k = 0; k < column_count; k++) {
						LinearLayout localLinearLayout = waterfall_items.get(k);
						if (pin_mark[k].get(bottomIndex[k]) > t + 3
								* scroll_height) {
							((FlowView) localLinearLayout
									.getChildAt(bottomIndex[k])).recycle();

							bottomIndex[k]--;
						}

						if (pin_mark[k].get(Math.max(topIndex[k] - 1, 0)) >= t
								- 2 * scroll_height) {
							((FlowView) localLinearLayout.getChildAt(Math.max(
									-1 + topIndex[k], 0))).Reload();
							topIndex[k] = Math.max(topIndex[k] - 1, 0);
						}
					}
				}

			}

		}
	}

	private void addImage(String filename, int rowIndex, int id) {

		FlowView item = new FlowView(context);
		// item.setColumnIndex(columnIndex);

		item.setRowIndex(rowIndex);
		item.setId(id);
		item.setViewHandler(handler);
		item.setFileName(image_path + "/" + filename);
		item.setItemWidth(item_width);
		item.LoadImage();
		// waterfall_items.get(columnIndex).addView(item);

	}

	private void addItemToContainer(int pageindex, int pagecount) {

		int currentIndex = pageindex * pagecount;
		int imagecount = Constants.PICTURE_TOTAL_COUNT;// image_filenames.size();

		for (int i = currentIndex; i < pagecount * (pageindex + 1)
				&& i < imagecount; i++) {
			loaded_count++;
			Random rand = new Random();
			int r = rand.nextInt(image_filenames.size());
			addImage(image_filenames.get(r),
					(int) Math.ceil(loaded_count / (double) column_count),
					loaded_count);
		}

	}

	public void handleReflush(View v) {
		alert("点击了刷新");
	}
}
