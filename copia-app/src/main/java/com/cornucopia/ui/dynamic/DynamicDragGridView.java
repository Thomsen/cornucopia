package com.cornucopia.ui.dynamic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class DynamicDragGridView extends GridView {

	protected int dropPosition;
	protected int dragPosition;
	protected int startPosition;
	protected int specialPosition;
	
	protected int lastX;
	protected int lastY;
	protected boolean isCountXY;
	protected int halfItemWidth;
	protected int halfItemHeight;
	protected int itemTotalCount;
	protected int mColumns;
	protected int mRows;
	protected int mReminder;
	protected int leftBottomPosition;
	
//	protected ViewGroup mDragItemView;
	protected View mDragItemView;
	private ImageView mDragImageView;
	
	protected boolean isMoving;
	
	private WindowManager mWindowManager;
	private android.view.WindowManager.LayoutParams mWindowParams;

	public DynamicDragGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public DynamicDragGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DynamicDragGridView(Context context) {
		this(context, null);
	}
	
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			return setOnItemLongClickListener(event);
		}
		return super.onInterceptTouchEvent(event);
	}

	public boolean setOnItemLongClickListener(final MotionEvent event) {
		this.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ������ʼ��һЩȫ�ֱ���
				
				int curX = (int) event.getX();
				int curY = (int) event.getY();
				
				startPosition = dragPosition = dropPosition = position;
				
				lastX = curX;
				lastY = curY;
				
				Log.i("thom", "pos: " + getLastVisiblePosition());
				if (dragPosition == AdapterView.INVALID_POSITION) {
					
				}
//				ViewGroup itemView = (ViewGroup) getChildAt(
//						dragPosition - getFirstVisiblePosition());  // ���adapter���ǵ�����view�����ת���쳣
				View itemView = getChildAt(dragPosition - getFirstVisiblePosition());
				if (!isCountXY) {
					halfItemWidth = itemView.getWidth() / 2;
					halfItemHeight = itemView.getHeight() / 2;
					
					itemTotalCount = getCount();
//					mColumns = getNumColumns();		// NoSuchMethodError
					mColumns = 2;
					int rows = itemTotalCount / mColumns;
					mReminder = itemTotalCount % mColumns;
					mRows = (mReminder == 0 ? rows : rows + 1);
					
					// ?
					specialPosition = itemTotalCount - 1 - mReminder;
				
					if (mReminder != 1) {
						leftBottomPosition = mColumns * (mRows - 1);
					}
					
					isCountXY = true;
				}
				if (specialPosition != dragPosition && dragPosition != -1) {
					// TODO ���ж�����һ��
				}
				if (leftBottomPosition != dragPosition && dragPosition != -1) {
					// TODO ���ж����һ��
				}
				
				
				mDragItemView = itemView;
				itemView.destroyDrawingCache();
				itemView.setDrawingCacheEnabled(true);
				itemView.setDrawingCacheBackgroundColor(0x000000);
				Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache(true));
				Bitmap bitmap = Bitmap.createBitmap(bm, 8, 8, bm.getWidth()-8, bm.getHeight()-8);
				startDrag(bitmap, curX, curY);
				bm.recycle();
				hideDropItem();
				itemView.setVisibility(View.INVISIBLE);
				isMoving = false;
				
//				return false;
				return true;
			}
			
		});
//		return false;
		return super.onInterceptTouchEvent(event);
	}

	protected void hideDropItem() {
		// TODO Auto-generated method stub
		
	}

	protected void startDrag(Bitmap bitmap, int curX, int curY) {
		stopDrag();
		mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
		mWindowParams.x = mDragItemView.getLeft() + 5;
		mWindowParams.y = mDragItemView.getBottom() + 5;
		mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.alpha = 0.5f;
		
		ImageView iv = new ImageView(getContext());
		iv.setImageBitmap(bitmap);
		mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		mWindowManager.addView(iv, mWindowParams);
		
		mDragImageView = iv;
		
	}

	private void stopDrag() {
		if (mDragImageView != null) {
//			mWidnowManager.
			mDragImageView = null;
		}
	}
}
