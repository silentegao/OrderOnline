package com.example.rui.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rui.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16/016.
 */

public class MySpinner extends LinearLayout implements View.OnClickListener {
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";
    Context context;
    private static final int DEFAULT_ELEVATION = 16;
    private static final int MAX_LEVEL = 10000;
    public static final int VERTICAL_OFFSET = 1;
    private int dropDownListPaddingBottom = 0;
    private PopupWindow popupWindow;
    private ListView listView;
    private int selectBackground;
    private int selectTextColor;
    private int selectTextSize;

    private int listBackground;
    private int listTextColor;
    private int listTextSize;
    int selectedIndex = 0;
    TextView textViewSelect;
    RelativeLayout relativeLayoutArrow;
    ImageView imageViewArrow;
    MySpinnerAdapter adapter;
    private int displayHeight;
    private int parentVerticalOffset;
    List<String> lst = new ArrayList<>();
    List<String> lstCd = new ArrayList<>();
    private AdapterView.OnItemSelectedListener onItemSelectedListener;

    public MySpinner(Context context) {
        super(context);
        init(context, null);
        measureDisplayHeight();
    }

    public MySpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        measureDisplayHeight();
    }

    public MySpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        measureDisplayHeight();
    }

    void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySpinner);

        selectBackground = typedArray.getResourceId(R.styleable.MySpinner_selectBackground, R.drawable.spinner_left_fill_bg);
        selectTextColor = typedArray.getColor(R.styleable.MySpinner_selectTextColor, getDefaultTextColor(getContext()));
        selectTextSize = typedArray.getDimensionPixelSize(R.styleable.MySpinner_selectTextSize, getResources().getDimensionPixelOffset(R.dimen.defalut_text_size));

        listBackground = typedArray.getResourceId(R.styleable.MySpinner_listBackground, R.drawable.spinner_popup_bg);
        listTextColor = typedArray.getColor(R.styleable.MySpinner_listTextColor, getDefaultTextColor(getContext()));
        listTextSize = typedArray.getDimensionPixelSize(R.styleable.MySpinner_listTextSize, getResources().getDimensionPixelOffset(R.dimen.defalut_text_size));

        View v = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null);
        v.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(v);
        setOnClickListener(this);
        setEnabled(true);
        textViewSelect = (TextView) v.findViewById(R.id.txt_select);
        relativeLayoutArrow = (RelativeLayout) v.findViewById(R.id.rl_arrow);
        imageViewArrow = (ImageView) v.findViewById(R.id.img_arrow);

        textViewSelect.setTextColor(selectTextColor);
        textViewSelect.setTextSize(TypedValue.COMPLEX_UNIT_PX, selectTextSize);
        textViewSelect.setBackgroundResource(selectBackground);

        initView();
    }

    void initView() {
        listView = new ListView(context);
        listView.setId(getId());
        listView.setDivider(null);
        listView.setItemsCanFocus(true);
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setBackgroundResource(listBackground);
        adapter = new MySpinnerAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(parent, view, position, id);
                }
                textViewSelect.setText(lst.get(selectedIndex));
                dismissDropDown();
            }
        });
        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(listView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(DEFAULT_ELEVATION);
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner_drawable));
        } else {
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.drop_down_shadow));
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                animateArrow(false);

            }
        });
    }

    @Override
    public void onClick(View view) {
        textViewSelect.setBackgroundResource(selectBackground);
        if (isSHowKeyboard(getContext(), view)) {
            ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!popupWindow.isShowing()) {
                        showDropDown();
                    } else {
                        dismissDropDown();
                    }
                }
            }, 200);
        } else {
            if (!popupWindow.isShowing()) {
                showDropDown();
            } else {
                dismissDropDown();
            }
        }
//        if(((BaseActivity)getContext()).getWindow().getAttributes().softInputMode== WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
//        {
//            //隐藏软键盘
//            ((BaseActivity)getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!popupWindow.isShowing()) {
//                    showDropDown();
//                } else {
//                    dismissDropDown();
//                }
//            }
//        },200);

    }

    public boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    public void showDropDown() {
        initView();
        animateArrow(true);
        measurePopUpDimension();
        popupWindow.showAsDropDown(this, 0, DensityUtils.dp2px(getContext(), -1));
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        textViewSelect.setText(lst.get(selectedIndex));
    }

    public void setSelectedIndexCallBack(int index) {
        selectedIndex = index;
        textViewSelect.setText(lst.get(selectedIndex));
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(null, null, selectedIndex, 0);
        }
    }

    public void setSelectedIndex(int index, boolean max) {
        if (index > lst.size() - 1 || index < 0) {
//             selectedIndex = lst.size() - 1;
            return;
        } else {
            selectedIndex = index;
        }
        textViewSelect.setText(lst.get(selectedIndex));
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public String getSelected() {
        if (selectedIndex >= lst.size()) {
            return "";
        }
        if ("请选择".equals(textViewSelect.getText()) || selectedIndex == -1) {
            return null;
        }
        return lst.get(selectedIndex);
    }

    public void setSelected(String value) {
        if (!lst.contains(value)) {
            selectedIndex = -1;
            textViewSelect.setText(value);
            return;
        }
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).equals(value)) {
                selectedIndex = i;
                break;
            }
        }
        textViewSelect.setText(value);
    }

    private void measurePopUpDimension() {
        int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);

        int heightSpec = MeasureSpec.makeMeasureSpec(displayHeight - getParentVerticalOffset() - getMeasuredHeight(),
                MeasureSpec.AT_MOST);
        listView.measure(widthSpec, heightSpec);
        popupWindow.setWidth(listView.getMeasuredWidth());
        popupWindow.setHeight(listView.getMeasuredHeight() - dropDownListPaddingBottom);
    }

    public void setData(List<String> lst) {
        this.lst.clear();
        this.lst.addAll(lst);
        selectedIndex = -1;
        textViewSelect.setText("请选择");
//        if (lst.size() > 0) {
//            setSelectedIndex(selectedIndex);
//        }
        adapter.notifyDataSetChanged();
    }

    // 显示民族数据库中已选的下拉选项
    public void setBaseDataRace(List<String> lst, int selectedIndexs) {
        this.lst.clear();
        this.lst.addAll(lst);
        if (lst.size() > 0 || selectedIndexs > lst.size() - 1) {
            this.selectedIndex = selectedIndexs;
            setSelectedIndex(selectedIndex);
        }
        adapter.notifyDataSetChanged();
    }

    // 显示其他数据库中已选的下拉选项
    public void setBaseData(List<String> lst, String selectedName) {
        this.lst.clear();
        this.lst.addAll(lst);
        for (int i = 0; i < lst.size(); i++) {
            if (selectedName.equalsIgnoreCase(lst.get(i))) {
                this.selectedIndex = i;
            }
        }
        setSelectedIndex(selectedIndex);
        adapter.notifyDataSetChanged();
    }

    // 健康信息下拉类型对应
    public void setBaseDataCd(List<String> lst, List<String> lstCd, String typeCd) {
        this.lst.clear();
        this.lstCd.clear();
        this.lst.addAll(lst);
        this.lstCd.addAll(lstCd);
        for (int i = 0; i < lst.size(); i++) {
            if (typeCd.equalsIgnoreCase(lstCd.get(i))) {
                this.selectedIndex = i;
            }
        }
        setSelectedIndex(selectedIndex);
        adapter.notifyDataSetChanged();
    }

    private int getParentVerticalOffset() {
//        if (parentVerticalOffset > 0) {
//            return parentVerticalOffset;
//        }
        int[] locationOnScreen = new int[2];
        getLocationOnScreen(locationOnScreen);
        return parentVerticalOffset = locationOnScreen[VERTICAL_OFFSET];
    }

    private int getDefaultTextColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme()
                .resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data,
                new int[]{android.R.attr.textColorPrimary});
        int defaultTextColor = typedArray.getColor(0, Color.BLACK);
        typedArray.recycle();
        return defaultTextColor;
    }

    private void measureDisplayHeight() {
        displayHeight = getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public void dismissDropDown() {

        animateArrow(false);

        popupWindow.dismiss();
    }

    private void animateArrow(boolean shouldRotateUp) {
        if (shouldRotateUp) {
            Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.spinner_img_rotate);
            rotate.setFillAfter(true);
            imageViewArrow.startAnimation(rotate);
        } else {
            Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.spinner_img_rotate_down);
            rotate.setFillAfter(true);
            imageViewArrow.startAnimation(rotate);
        }
//        int start = shouldRotateUp ? 0 : MAX_LEVEL;
//        int end = shouldRotateUp ? MAX_LEVEL : 0;
//        ObjectAnimator animator = ObjectAnimator.ofInt(imageViewArrow.getDrawable(), "level", start, end);
//        animator.setInterpolator(new LinearOutSlowInInterpolator());
//        animator.start();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_INDEX, selectedIndex);

        if (popupWindow != null) {
            bundle.putBoolean(IS_POPUP_SHOWING, popupWindow.isShowing());
        }
        return bundle;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof Bundle) {
            Bundle bundle = (Bundle) savedState;
            selectedIndex = bundle.getInt(SELECTED_INDEX);
            if (bundle.getBoolean(IS_POPUP_SHOWING)) {
                if (popupWindow != null) {
                    // Post the show request into the looper to avoid bad token exception
                    post(new Runnable() {
                        @Override
                        public void run() {
                            showDropDown();
                        }
                    });
                }
            }
            savedState = bundle.getParcelable(INSTANCE_STATE);
        }
        super.onRestoreInstanceState(savedState);
    }

    public void setTextViewSelectBackgroundToRed() {
        textViewSelect.setBackgroundResource(R.drawable.edittext_bg_red);
    }

    class MySpinnerAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return lst.size();
        }

        @Override
        public Object getItem(int i) {
            return lst.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Context context = parent.getContext();

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.spinner_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.text_view_spinner);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(getItem(position).toString());
            viewHolder.textView.setTextColor(listTextColor);
            viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, listTextSize);
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
