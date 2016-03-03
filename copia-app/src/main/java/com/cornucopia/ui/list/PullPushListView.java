package com.cornucopia.ui.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cornucopia.R;

/**
 * @author Thomsen
 * @version 1.0
 * @since 12/20/12 10:10 PM
 */
public class PullPushListView extends ListView implements AbsListView.OnScrollListener {

	// 为了能够理解拉动刷新，需要明确几个状态
	// 提示用户拉动可刷新的状态
	// 刷新的临界状态
	// 临界状态前
	// 临界状态后
	// 正在进行刷新的状态
	// 刷新完成的状态
	
	private static final int TAP_TO_REFRESH = 1;
	private static final int PULL_TO_REFRESH = 2;
	private static final int PUSH_TO_REFRESH = 3;
	private static final int RELEASE_TO_REFRESH = 4;
	private static final int REFRESHING = 5;
	
    private OnScrollListener mOnScrollListener;

    private OnRefreshListener mOnRefreshListener;

    private OnPageRefreshListener mOnPageRefreshListener;

    private RotateAnimation mFlipAnimation;

    private RotateAnimation mReverseAnimation;

    private int mRefreshState;
    private int mCurrentScrollState;
    
    private int lastVisiblePosition = 0;
    private int lastVisiblePositionY = 0;
    
    private Context mContext;
    
    private RelativeLayout mPullRefresh;
    private ProgressBar mPullProgress;
    private ImageView mPullImage;
    private TextView mPullText;
    
    private RelativeLayout mPushRefresh;
    private ProgressBar mPushProgress;
    private ImageView mPushImage;
    private TextView mPushText;
    
    private int mPullRefreshHeight;
    private int mPushRefreshHeight;
    private int mPullOriginalTopPadding;
    private int mPushOriginalBottomPadding;
    private int mLastMotionY;
    
    private boolean mBounceHack;
    
    private boolean isPrevRefresh = false;
    private boolean isNextRefresh = false;
    
    /**
     * 自定义View的高度
     */
    private int mHeight;
    
    public PullPushListView(Context context) {
        this(context, null);
    }

    public PullPushListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullPushListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);

        mReverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mReverseAnimation.setInterpolator(new LinearInterpolator());
        mReverseAnimation.setDuration(250);
        mReverseAnimation.setFillAfter(true);

//        TextView tv = new TextView(context);
//        tv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        tv.setText("header");
//        addHeaderView(tv);
        
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPullRefresh = (RelativeLayout) inflater.inflate(R.layout.pull_header_refresh, this, false); 
        mPullProgress = (ProgressBar) mPullRefresh.findViewById(R.id.pull_header_progress);
        mPullImage = (ImageView) mPullRefresh.findViewById(R.id.pull_header_image);
        mPullText = (TextView) mPullRefresh.findViewById(R.id.pull_header_text);
        addHeaderView(mPullRefresh);
        
        mPullImage.setMinimumHeight(50);
//        mPullRefresh.setOnClickListener(new On)
        mPullOriginalTopPadding = mPullRefresh.getPaddingTop();
        
        resetHeader();
        
//        measureView(mPullRefresh);  // onMeasure null pointer exception
//        mPullRefreshHeight = mPullRefresh.getMeasuredHeight();

//        TextView tvv = new TextView(context);
//        tvv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        tvv.setText("footer");
////        tvv.setOnClickListener(new OnClickRefreshListener());
//        addFooterView(tvv);
        
        mPushRefresh = (RelativeLayout) inflater.inflate(R.layout.pull_header_refresh, this, false);
        mPushProgress = (ProgressBar) mPushRefresh.findViewById(R.id.pull_header_progress);
        mPushImage = (ImageView) mPushRefresh.findViewById(R.id.pull_header_image);
        mPushText = (TextView) mPushRefresh.findViewById(R.id.pull_header_text);
        addFooterView(mPushRefresh);
        
    	mPushImage.setMinimumHeight(50);
    	mPushOriginalBottomPadding = mPushRefresh.getPaddingBottom();
    	
    	resetFooter();

//        setOnScrollListener(this); // 没有调用到本地的onScroll
        super.setOnScrollListener(this); // 执行到了onScroll
        
        measureView(mPullRefresh);  // 报null pointer不是位置的关系，而是inflate方法中使用this，而不是null。但改过后，仍报错，按上述的inflate方法后没有报错
        mPullRefreshHeight = mPullRefresh.getMeasuredHeight();
        Log.i("thom", "pull refresh height: " + mPullRefreshHeight);
        
        measureView(mPushRefresh);
        mPushRefreshHeight = mPushRefresh.getMeasuredHeight();
        Log.i("thom", "push refresh height: " + mPushRefreshHeight);
        
        // 得到屏幕的高度
        int windowHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        Log.i("thom", "height pixels: " + windowHeight);
        
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        
//        int sdkVersion = Build.VERSION.SDK_INT;
//        if (sdkVersion > 13) {
//        	Point point = new Point();
//        	wm.getDefaultDisplay().getSize(point);
//        	windowHeight = point.y;
//        } else {
//          windowHeight = wm.getDefaultDisplay().getHeight(); // deprecated
//        }
        
        Log.i("thom", "wm height: " + windowHeight);
        
        Rect outRect = new Rect();
        getWindowVisibleDisplayFrame(outRect);
        Log.i("thom", "top height: " + outRect.top);
        Log.i("thom", "title height: " + (outRect.height() - getHeight()));
    }
    
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	
    	mHeight = getHeight();
    	
//    	mPushImage.clearAnimation();
//    	mPushImage.setAnimation(mReverseAnimation);
    	
//    	Rect outRect = new Rect();
//        getWindowVisibleDisplayFrame(outRect);
//        Log.i("thom", "draw top height: " + outRect.top);
//        Log.i("thom", "draw title height: " + (outRect.height() - getHeight()));
    }

	private void measureView(View view) {
    	ViewGroup.LayoutParams lp = view.getLayoutParams();
    	if (lp == null) {
    		lp = new ViewGroup.LayoutParams(
    				ViewGroup.LayoutParams.MATCH_PARENT,
    				ViewGroup.LayoutParams.WRAP_CONTENT);
    	}
    	
    	int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0+0, lp.width);
    	int lpHeight = lp.height;
    	int childHeightSpec;
    	if (lpHeight > 0) {
    		childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
    	} else {
    		childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    	}
    	view.measure(childWidthSpec, childHeightSpec);
    }
    
    protected void onAttachedToWindow() {
    	super.onAttachedToWindow();
    	
    	setSelection(1);
    }
    
    public void setAdapter(ListAdapter adapter) {
    	super.setAdapter(adapter);
    	
    	setSelection(1);
    }

	@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        
		// SCROLL_STATE_FLING 用户此前一直滚动使用触摸，并进行了一扔
		// SCROLL_STATE_IDLE 该视图没有滚动
		// SCROLL_STATE_TOUCH_SCROLL 用户触摸滚动，并且手指还在屏幕上
		
		// 得到最后一项
//		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//            if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
////                View v = view.getChildAt(view.getCheckedItemCount() - 1);
//                View v = view.getChildAt(view.getChildCount() - 1);
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//                int y = location[1];
//
//                Log.e("thom",  "x " + location[0] + "y " + location[1]);
//                if (view.getLastVisiblePosition() != lastVisiblePosition
//                        && lastVisiblePositionY != y) {
////                    Toast.makeText(view.getContext(), "again", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this.getContext(), "again", Toast.LENGTH_SHORT).show();
//                    lastVisiblePosition = view.getLastVisiblePosition();
//                    lastVisiblePositionY = y;
//
//                    return; // in order to twice
//
//                } else if (view.getLastVisiblePosition() == lastVisiblePosition
//                        && lastVisiblePositionY == y) {
//                    Toast.makeText(this.getContext(), "success", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            lastVisiblePosition = 0;
//            lastVisiblePositionY = 0;
//        }
		
//		if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//			if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//				Log.w("thom", "last position: " + view.getLastVisiblePosition());
//
////				mPushRefresh.setVisibility(View.VISIBLE);
////				Log.i("thom", "push refresh top: " + mPushRefresh.getTop() + "push refresh height: " + mPushRefreshHeight);
////				Log.i("thom", "push refresh bottom: " + mPushRefresh.getBottom()); // 到时与y进行比较，通过增加padding bottom
//				
//				
//				int pushTopHeight = mHeight - mPushRefreshHeight;
//				Log.i("thom", "push to height: " + pushTopHeight);
//				Log.i("thom", "push top: " + mPushRefresh.getTop());
//				Log.i("thom", "push bottom: " + mPushRefresh.getBottom());
//				
//				if ((mPushRefresh.getTop() <= pushTopHeight || mPushRefresh.getBottom() <= mHeight) 
//						&& mRefreshState != RELEASE_TO_REFRESH) {
//					
//					isNextRefresh = true;
//					mRefreshState = RELEASE_TO_REFRESH;
//					
//					mPushText.setText("释放刷新...");
//					mPushImage.clearAnimation();
//					mPushImage.setAnimation(mReverseAnimation);
//					
//				} else if (mPushRefresh.getTop() > pushTopHeight
//						&& mRefreshState != PULL_TO_REFRESH) {
//					isNextRefresh = false;
//					mRefreshState = PULL_TO_REFRESH;
//
//					mPushText.setText("拉动翻页...");
//					mPushImage.clearAnimation();
//					mPushImage.setAnimation(mFlipAnimation);
//				}
//				
////				int pushTopHeight = mHeight - mPushRefreshHeight;
////				Log.i("thom", "push top height: " + pushTopHeight);
////				
//////				View lastView = view.getChildAt(view.getChildCount() - 1);
////				View lastView = view.getChildAt(view.getChildCount() - 1);
////				if (lastView instanceof RelativeLayout) {
////					Toast.makeText(mContext, "last view is rl", Toast.LENGTH_SHORT).show();
////				}
////				int[] location = new int[2];
////				lastView.getLocationOnScreen(location);
////				Log.d("thom", "location: " + location[0] + "  " + location[1]);
////				int y  = location[1];
////				
////				if (y == mLastMotionY) {
////					Log.i("thom", "last motion y: " + y);
////				}
////				
////				if (mPushRefresh.getBottom() <= y
////						&& mRefreshState != RELEASE_TO_REFRESH) {
////					Toast.makeText(mContext, "push refresh", Toast.LENGTH_SHORT).show();
////					
////					mRefreshState = RELEASE_TO_REFRESH;
////				} else if (mPushRefresh.getTop() > y) {
////					Toast.makeText(mContext, "before refresh", Toast.LENGTH_SHORT).show();
////					
////					mRefreshState = PULL_TO_REFRESH;
////				}
//			}
//		}
    	
    	mCurrentScrollState = scrollState;
    	
    	if (mCurrentScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
    		mBounceHack = false;
    	}
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        if (mOnScrollListener != null) {
//            mOnScrollListener.onScroll(view, );
//        }
//        Log.i("thom", "on scroll " +  " f " + firstVisibleItem + " v " + visibleItemCount + " t  " + totalItemCount);
    	
    	// 方法的调用，需要设置监听
    	
//    	Toast.makeText(mContext, "" + mCurrentScrollState, Toast.LENGTH_SHORT).show();
    	
		if (mCurrentScrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
				&& mRefreshState != REFRESHING ) {
    		if (firstVisibleItem == 0) {
    			mPullImage.setVisibility(View.VISIBLE);
    			// 设置刷新的临界状态，该状态的敏感程度在于顶部只从数据项开始显示，使用onAttachedToWindow
        		if ((mPullRefresh.getBottom() >= mPullRefreshHeight + 20
        				|| mPullRefresh.getTop() >= 0) && mRefreshState != RELEASE_TO_REFRESH) {
        			// 状态后
        			isPrevRefresh = true;
        			mPullImage.clearAnimation();
        			mPullImage.startAnimation(mFlipAnimation);
        			mPullText.setText("释放刷新...");
        			mRefreshState = RELEASE_TO_REFRESH;
        		} else if (mPullRefresh.getBottom() < mPullRefreshHeight + 20
        				&& mRefreshState != PULL_TO_REFRESH) {
        			// 状态前
        			if (mRefreshState != TAP_TO_REFRESH) {
        				mPullImage.clearAnimation();
            			mPullImage.startAnimation(mReverseAnimation);
        			}
        			mPullText.setText("拉动刷新...");
        			mRefreshState = PULL_TO_REFRESH;
        		}
//            		else {
//            			mPullImage.setVisibility(View.VISIBLE);
//            			mPullImage.clearAnimation();
//            			mPullImage.startAnimation(mReverseAnimation);
//            			mPullText.setText("拉动刷新...");
//            			mRefreshState = PULL_TO_REFRESH;
//            		}
    		} else if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
    			mPushImage.setVisibility(View.VISIBLE);
				int pushTopHeight = mHeight - mPushRefreshHeight;
				Log.i("thom", "push to height: " + pushTopHeight);
				Log.i("thom", "push top: " + mPushRefresh.getTop());
				Log.i("thom", "push bottom: " + mPushRefresh.getBottom());
				
				if ((mPushRefresh.getTop() <= pushTopHeight || mPushRefresh.getBottom() <= mHeight) 
						&& mRefreshState != RELEASE_TO_REFRESH) {
					
					isNextRefresh = true;
					mRefreshState = RELEASE_TO_REFRESH;
					
					mPushText.setText("释放刷新...");
					mPushImage.clearAnimation();
					mPushImage.setAnimation(mReverseAnimation);
					
				} else if (mPushRefresh.getTop() > pushTopHeight
						&& mRefreshState != PULL_TO_REFRESH) {
					isNextRefresh = false;
					mRefreshState = PULL_TO_REFRESH;
					
					if (mRefreshState != TAP_TO_REFRESH) {
						mPushImage.clearAnimation();
						mPushImage.setAnimation(mFlipAnimation);
					}
					mPushText.setText("拉动翻页...");
					
				}
			} else {
				
			}
    	} else if (mCurrentScrollState == OnScrollListener.SCROLL_STATE_FLING
    			&& firstVisibleItem == 0 && mRefreshState != REFRESHING) {
    		if (isPrevRefresh) {
    			resetHeader();
    		} else if (isNextRefresh) {
    			resetFooter();
    		}
    		mBounceHack = true;
    	} else if (mBounceHack && mCurrentScrollState == OnScrollListener.SCROLL_STATE_FLING) {
    		setSelection(1);
    	}
    	
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	
    	final int y = (int) ev.getY();
    	mBounceHack = false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
            	mLastMotionY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
            	// 判断pull or push
            	if (isPrevRefresh) {
            		applyHeaderPadding(ev);
            	}
            	if (isNextRefresh) {
            		applyFooterPadding(ev);
            	}
     
            	// 如何区分是上一页还是下一页刷新
            	
                break;
            }
            case MotionEvent.ACTION_UP: {
//                if (getFirstVisiblePosition() == 0) {
//                    Log.i("thom", "first up");
//                }
            	
            	 if (!isVerticalScrollBarEnabled()) {
                     setVerticalScrollBarEnabled(true);
                 }
                 if (getFirstVisiblePosition() == 0 && mRefreshState != REFRESHING) {
                     if ((mPullRefresh.getBottom() >= mPullRefreshHeight
                             || mPullRefresh.getTop() >= 0)
                             && mRefreshState == RELEASE_TO_REFRESH) {
                         mRefreshState = REFRESHING;
                         prepareForRefresh();
                         onRefresh();
                     } else if (mPullRefresh.getBottom() < mPullRefreshHeight
                             || mPullRefresh.getTop() <= 0) {
                         resetHeader();
                         setSelection(1);
                     }
                 }
                 
                 if (getLastVisiblePosition() == (getCount() -1) && mRefreshState != REFRESHING) {
                	 if ((mPushRefresh.getTop() <= (mHeight - mPushRefreshHeight)
                			 || mPushRefresh.getBottom() <= mHeight) 
                			 && mRefreshState == RELEASE_TO_REFRESH) {
                		 mRefreshState = REFRESHING;
                         prepareForRefresh();
                         onRefresh();
                         Toast.makeText(mContext, "refreshing", Toast.LENGTH_SHORT).show();
                	 } else if (mPushRefresh.getTop() > (mHeight - mPushRefreshHeight)
                			 || mPushRefresh.getBottom() > mHeight) {
                		resetFooter();
                		setSelection(1);
                		Toast.makeText(mContext, "on refresh", Toast.LENGTH_SHORT).show();		
                	 }
                			 
                 }
            	
//            	resetHeader();
            	
                break;
            }
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }
    
	private void prepareForRefresh() {
    	
    	
    	if (isPrevRefresh) {
    		resetHeaderPadding();
    		mPullImage.setVisibility(View.GONE);
        	mPullImage.setImageDrawable(null); // 为了保证再次拉动，图片保持原来的，还需要在resetHeader中设置drawable，还要清除animation
        	mPullProgress.setVisibility(View.VISIBLE);
        	mPullText.setText("正在刷新...");
        	
    	}
    	
    	if (isNextRefresh) {
    		resetFooterPadding();
    		mPushImage.setVisibility(View.GONE);
    		mPushImage.setImageDrawable(null);
    		mPushProgress.setVisibility(View.VISIBLE);
    		mPushText.setText("正在刷新...");
    	}
    	
    	mRefreshState = REFRESHING;
    }

    private void applyHeaderPadding(MotionEvent ev) {
    	int pointerCount = ev.getHistorySize(); // 
//    	Log.i("thom", "pointer count: " + pointerCount);
    	for (int p=0; p<pointerCount; p++) {
    		if (mRefreshState == RELEASE_TO_REFRESH) {
    			
    			int historicalY = (int) ev.getHistoricalY(p);
    			int topPadding = (int) (((historicalY - mLastMotionY) - mPullRefreshHeight) / 1.7);
    		
//    			Log.i("thom", "==================================================");
//    			Log.i("thom", "historical y: " + historicalY);
//    			Log.i("thom", "pull refresh height: " + mPullRefreshHeight);
//    			Log.i("thom", "topPadding: " + topPadding);
//    			Log.i("thom", "==================================================");
    			
    			mPullRefresh.setPadding(
    					mPullRefresh.getPaddingLeft(), 
    					topPadding, 
    					mPullRefresh.getPaddingRight(), 
    					mPullRefresh.getPaddingBottom());
    		}
    	}
	}

    private void applyFooterPadding(MotionEvent ev) {
    	int pointerCount = ev.getHistorySize();
    	for (int p=0; p<pointerCount; p++) {
    		if (mRefreshState == RELEASE_TO_REFRESH) {
    			int historicalY = (int) ev.getHistoricalY(p);
    			int bottomPadding = (int) (((mLastMotionY - historicalY) - mPushRefreshHeight) / 1.7);
    			
    			Log.i("thom", "==================================================");
    			Log.i("thom", "historical y: " + historicalY);
    			Log.i("thom", "pull refresh height: " + mPullRefreshHeight);
    			Log.i("thom", "topPadding: " + bottomPadding);
    			Log.i("thom", "==================================================");
    			
    			mPushRefresh.setPadding(
    					mPushRefresh.getPaddingLeft(),
    					mPushRefresh.getPaddingTop(),
    					mPushRefresh.getPaddingRight(),
    					bottomPadding);
    		}
    	}
	}
    
    private void resetHeaderPadding() {
    	mPullRefresh.setPadding(
    			mPullRefresh.getPaddingLeft(), 
    			mPullOriginalTopPadding, 
    			mPullRefresh.getPaddingRight(),
    			mPullRefresh.getPaddingBottom());
    }
    
    private void resetHeader() {
    	if (mRefreshState != TAP_TO_REFRESH) {
    		mRefreshState = TAP_TO_REFRESH;
    		
    		resetHeaderPadding();
    		
    		mPullText.setText("拉动刷新");
    		mPullImage.setImageResource(R.drawable.ic_pulltorefresh_arrow);
    		mPullImage.clearAnimation();
    		mPullImage.setVisibility(View.GONE);
    		mPullProgress.setVisibility(View.GONE);
    	}
    }
    
    private void resetFooterPadding() {
    	mPushRefresh.setPadding(
    			mPushRefresh.getPaddingLeft(), 
    			mPushRefresh.getPaddingTop(), 
    			mPushRefresh.getPaddingRight(), 
    			mPushOriginalBottomPadding);
    }

    private void resetFooter() {

//    	if (mRefreshState != TAP_TO_REFRESH) {
    		mRefreshState = TAP_TO_REFRESH;
    		
    		resetFooterPadding();
    		
    		mPushText.setText("拉动翻页");
    		mPushImage.setImageResource(R.drawable.ic_pulltorefresh_arrow);
    		mPushImage.clearAnimation();
    		mPushImage.setAnimation(mReverseAnimation);
    		mPushImage.setVisibility(View.GONE);
    		mPushProgress.setVisibility(View.GONE);
//    	}
    }

	@Override
    public void addFooterView(View v, Object data, boolean isSelectable) {
        super.addFooterView(v, data, isSelectable);
    }

    @Override
    public void addFooterView(View v) {
        super.addFooterView(v);
//        v.setBackgroundColor(Color.BLUE);
//
//        v.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (mOnPageRefreshListener != null) {
//                    mOnPageRefreshListener.pushRefresh();
//                }
//            }
//        });


    }

    @Override
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        super.addHeaderView(v, data, isSelectable);
    }

    @Override
    public void addHeaderView(View v) {
        super.addHeaderView(v);
//        v.setBackgroundColor(Color.GREEN);
//
//        v.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (mOnPageRefreshListener != null) {
//                    mOnPageRefreshListener.pullRefresh();
//                }
//            }
//        });

    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public void setOnPageRefreshListener(OnPageRefreshListener mOnPageRefreshListener) {
        this.mOnPageRefreshListener = mOnPageRefreshListener;
    }

    private void onPreRefresh() {

    }

    private void onRefresh() {
        Log.i("thom", "on refresh");

        if (mOnRefreshListener != null ) {
            mOnRefreshListener.onRefresh();
        }
    }
    
    public void onRefreshComplete() {
    	if (isPrevRefresh) {
    		isPrevRefresh = false;
        	resetHeader();
        	if (mPullRefresh.getBottom() > 0) {
        		invalidateViews();
        		setSelection(1);
        	}
    	}
    	
    	if (isNextRefresh) {
    		isNextRefresh = false;
    		resetFooter();
    		if (mPushRefresh.getTop() < mHeight) {
    			invalidateViews();
    			setSelection(1);
    		}
    	}
    }

    private void onPostRefresh() {

    }

    public class OnClickRefreshListener implements  OnClickListener {

        @Override
        public void onClick(View v) {
            if (mRefreshState != REFRESHING) {
                onPreRefresh();
                onRefresh();
            }
        }
    }

    public interface OnRefreshListener {
        public void onRefresh();
    }

    public interface OnPageRefreshListener {

        public void pullRefresh();

        public void pushRefresh();

    }
}
