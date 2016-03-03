package com.cornucopia.ui.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class DynamicLayoutWrapper {
	
	private Context mContext;
	
	private ViewGroup.LayoutParams mParams;
	
	private ArrayList<RadioButton> mListRadio;
	
	public DynamicLayoutWrapper(Context context) {
		mContext = context;
		mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		mListRadio = new ArrayList<RadioButton>();
	}
	
	public DynamicLayoutWrapper(Context context, ViewGroup.LayoutParams params) {
		mContext = context;
		mParams = params;
	}
	
	//============================== ��̬���� ================================//
	
	public void addGroupView(ViewGroup layout, List<LayoutDto> listLayoutDto) {
		if (listLayoutDto == null || listLayoutDto.size() <= 0) {
			// throw exception
		}
		for (LayoutDto layDto : listLayoutDto) {
			addView(layout, layDto);
			
			// ������viewֻ���ڵ��ʱ����ʾ
//			if (layDto.getInlineLayout() != null && layDto.getType() != TYPE_RADIOBUTTON) {
//				addGroupView(layout, layDto.getInlineLayout());
//			}
		}
	}

	/**
	 * ���˷����������
	 * @author Thomsen
	 * @date 2013-1-23 ����10:08:14
	 * @param layout
	 * @param listLayoutDto
	 */
	public void addLinearGroupView(ViewGroup layout, List<LayoutDto> listLayoutDto) {
		LinearLayout llayout = new LinearLayout(mContext);
		llayout.setLayoutParams(mParams);
		llayout.setOrientation(LinearLayout.HORIZONTAL);
		
		// Tag ��������
		
		for (LayoutDto layDto : listLayoutDto) {
			addView(llayout, layDto);
		}
		
		layout.addView(llayout);
	}
	
	public void addView(ViewGroup layout, LayoutDto layoutDto) {
		if (layoutDto == null) {
			// throw exception
		}
		switch (layoutDto.getType()) {
		case TYPE_TEXTVIEW: {
			addTextView(layout, layoutDto);
			break;
		}
		case TYPE_EDITTEXT: {
			addEditText(layout, layoutDto);
			break;
		}
		case TYPE_BUTTON: {
			addButton(layout, layoutDto);
			break;
		}
		case TYPE_SPINNER: {
			addSpinner(layout, layoutDto);
			break;
		}
		case TYPE_RADIOBUTTON: {
			addRadioButton(layout, layoutDto);
			break;
		}
		case TYPE_CHECKBOX: {
			addCheckBox(layout, layoutDto);
			break;
		}
		case TYPE_DATEPICKER: {
			addDatePicker(layout, layoutDto);
			break;
		}
		case TYPE_TIMEPICKER: {
			addTimePicker(layout, layoutDto);
			break;
		}
		default: {
			break;
		}
		}
	}
	
	private void addTextView(ViewGroup layout, LayoutDto layoutDto) {
		TextView tv = new TextView(mContext);
		tv.setLayoutParams(mParams);
		tv.setTag(layoutDto.getLabel());
		
		tv.setText(toStringValue(layoutDto.getValue()));
		
		layout.addView(tv);
	}

	private void addEditText(ViewGroup layout, LayoutDto layoutDto) {
		EditText et = new EditText(mContext);
		et.setLayoutParams(mParams);
		et.setTag(layoutDto.getLabel());
		
//		layout.addView(et);
		
		if (layoutDto.isLabelVisiable()) {
			layout.addView(generateHorizontalView(layoutDto.getLabel(), et));
		} else {
			layout.addView(et);
		}

	}

	private void addButton(ViewGroup layout, LayoutDto layoutDto) {
		Button btn = new Button(mContext);
		btn.setLayoutParams(mParams);
		btn.setTag(layoutDto.getLabel());
		
		btn.setText(toStringValue(layoutDto.getValue()));
		
		layout.addView(btn);
	}

	private void addSpinner(ViewGroup layout, LayoutDto layoutDto) {
		Spinner sp = new Spinner(mContext);
		sp.setLayoutParams(mParams);

		ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>(mContext, 
				android.R.layout.simple_spinner_item, (ArrayList) layoutDto.getValue());
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		sp.setAdapter(adapter);
		sp.setTag(layoutDto.getLabel());
		
//		layout.addView(sp);

		if (layoutDto.isLabelVisiable()) {
			layout.addView(generateHorizontalView(layoutDto.getLabel(), sp));
		} else {
			layout.addView(sp);
		}
		
	}

	private void addRadioButton(final ViewGroup layout, final LayoutDto layoutDto) {
		RadioButton rb = new RadioButton(mContext);
		rb.setTag(layoutDto.getLabel());
		
//		rb.setText(toStringValue(layoutDto.getValue()));
		rb.setText(toStringValue(layoutDto.getLabel()));
		
//		layoutDto.setEnabled(false); // ����Ĭ��Ϊtrue
		rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		
			// ��ֹ������������
			ArrayList<LayoutDto> listLayoutDto = null;
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				layoutDto.setEnabled(isChecked); // ��ʾ��һ�ѡ����
//				if (isChecked) {
//					// ��ʾ�����Ĳ�ѯ����
//					listLayoutDto = new ArrayList<LayoutDto>();
//					for (LayoutDto layDto : layoutDto.getInlineLayout()) {
//						layDto.setIsLabelVisiable(false);
//						listLayoutDto.add(layDto);
//					}
//					addGroupView((LinearLayout) layout.getParent(), listLayoutDto);
//				} else {
//					removeView((LinearLayout) layout.getParent(), layoutDto.getInlineLayout());
//					if (listLayoutDto != null) {
//						listLayoutDto.clear();
//						listLayoutDto = null;
//					}
//				}
				
//				if (!isChecked) {
//					removeView((LinearLayout) layout.getParent().getParent().getParent(), layoutDto.getInlineLayout()); // ����ط���ƵĲ������
//				}
				
				if (onRadioCheckedListener != null) {
					onRadioCheckedListener.onChecked(buttonView, isChecked, layoutDto.getInlineLayout());
				}
				
//				if (onRadioCheckedListener != null) {
//					if (isChecked) {
//						onRadioCheckedListener.checked(layoutDto.getInlineLayout());
//					} else {
//						onRadioCheckedListener.unChecked();
//					}
//				}
			}
		});
		
		// Ϊ�˷��е�ѡ
		mListRadio.add(rb);
		
		layout.addView(rb);
	}

	private void addCheckBox(ViewGroup layout, LayoutDto layoutDto) {
		CheckBox cb = new CheckBox(mContext);
		cb.setTag(layoutDto.getLabel());
		
//		cb.setText(toStringValue(layoutDto.getValue()));
		cb.setText(toStringValue(layoutDto.getLabel()));
		
		layout.addView(cb);
	}

	private void addDatePicker(ViewGroup layout, LayoutDto layoutDto) {
		DatePicker cp = new DatePicker(mContext);
		cp.setTag(layoutDto.getLabel());
		
//		layout.addView(cp);
		
		layout.addView(generateHorizontalView(layoutDto.getLabel(), cp));
	}
	
	private void addTimePicker(ViewGroup layout, LayoutDto layoutDto) {
		TimePicker tp = new TimePicker(mContext);
		tp.setTag(layoutDto.getLabel());
		
//		layout.addView(tp);
		
		layout.addView(generateHorizontalView(layoutDto.getLabel(), tp));
	}
	
	private View generateHorizontalView(String label, View et) {
		LinearLayout llayout = new LinearLayout(mContext);
		llayout.setLayoutParams(mParams);
		llayout.setOrientation(LinearLayout.HORIZONTAL);
		llayout.setTag(TAG_LAYOUT_LABEL + label); // Ϊ���ܹ����tag�������ÿؼ���ֵ
		
		TextView tv = new TextView(mContext);
		tv.setTextSize(LABEL_TEXT_SIZE);
		tv.setTextColor(LABEL_TEXT_COLOR);
		tv.setText(label);
		
		llayout.addView(tv);
		llayout.addView(et);
		
		return llayout;
	}
	
	public void removeView(ViewGroup layout,
			ArrayList<LayoutDto> listLayoutDto) {
		if (listLayoutDto == null) {
			return;
		}
		for (LayoutDto layDto : listLayoutDto) {
			View view = layout.findViewWithTag(layDto.getLabel());
			View layView = layout.findViewWithTag(TAG_LAYOUT_LABEL + layDto.getLabel());
			layout.removeView(view);
			layout.removeView(layView);
			layout.invalidate();
		}
	}
	
	//============================== �������  =====================================//
	
	public void setLayoutValue(ViewGroup layout, List<LayoutDataDto> listLayoutDataDto) {
		if (listLayoutDataDto == null) {
			// throw 
			return ;
		}
		for (LayoutDataDto layDataDto : listLayoutDataDto) {
			setValue(layout, layDataDto);
		}
	}
	
	private void setValue(ViewGroup layout, LayoutDataDto layDataDto) {
		switch (layDataDto.getType()) {
		case TYPE_TEXTVIEW: {
			break;
		}
		case TYPE_EDITTEXT: {
			EditText et = (EditText) layout.findViewWithTag(layDataDto.getLabel());
			et.setText(toStringValue(layDataDto.getValue()));
			break;
		}
		case TYPE_BUTTON: {
			break;
		}
		case TYPE_SPINNER: {
			Spinner sp = (Spinner) layout.findViewWithTag(layDataDto.getLabel());
			ArrayAdapter adapter = (ArrayAdapter) sp.getAdapter();
			sp.setSelection(adapter.getPosition(layDataDto.getValue()));
			break;
		}
		case TYPE_RADIOBUTTON: {
			break;
		}
		case TYPE_CHECKBOX: {
			break;
		}
		case TYPE_DATEPICKER: {
			break;
		}
		case TYPE_TIMEPICKER: {
			break;
		}
		default: {
			break;
		}
		}
	}
	
	//============================== ��ȡ���  =====================================//
	
	public HashMap<String, ArrayList<LayoutDataDto>> getLayoutValue(
			HashMap<String, LinearLayout> mLayoutGroup,
			HashMap<String, ArrayList<LayoutDto>> groupChild) {
		HashMap<String, ArrayList<LayoutDataDto>> layoutData = 
				new HashMap<String, ArrayList<LayoutDataDto>>();
		if (groupChild == null) {
			return layoutData;
		}
		Iterator iter = groupChild.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Entry) iter.next();
			String key = (String) entry.getKey();
			ArrayList<LayoutDto> value = (ArrayList<LayoutDto>) entry.getValue();
			
			Log.i("thom", key);
			
			ArrayList<LayoutDataDto> dataValue = getLayoutValue(mLayoutGroup.get(key), value); // ���expand��չ���Ļ���mLayoutGroup�Ͳ�����layout�Ĺ�����ϵ
			
			layoutData.put(key, dataValue);
		}
		
		return layoutData;
	}
	
	public ArrayList<LayoutDataDto> getLayoutValue(ViewGroup layout, List<LayoutDto> listLayoutDto) {
		ArrayList<LayoutDataDto> listData = new ArrayList<LayoutDataDto>();
		if (listLayoutDto == null) {
			// throw exception
			return listData;
		}
		
		for (LayoutDto layDto : listLayoutDto) {
//			listData.add(getValue(layout, layDto));
			LayoutDataDto data = getValue(layout, layDto);
			if (data != null) {
				listData.add(data);
			}
		}
		
		return listData;
	}
	
	
	private LayoutDataDto getValue(ViewGroup layout, LayoutDto layDto) {
//		LayoutDataDto layoutData = new LayoutDataDto();
//		layoutData.setType(layDto.getType());
//		layoutData.setLabel(layDto.getLabel());  // ����������
		
		LayoutDataDto layoutData = null;
		
		switch (layDto.getType()) {
		case TYPE_TEXTVIEW: {
			
			break;
		}
		case TYPE_EDITTEXT: {
			EditText et = (EditText) layout.findViewWithTag(layDto.getLabel());
			if (et == null) {
				break;
			}
			layoutData = new LayoutDataDto();
			layoutData.setType(layDto.getType());
			layoutData.setLabel(layDto.getLabel());
			layoutData.setValue(toStringValue(et.getText()));
			break;
		}
		case TYPE_BUTTON: {
			break;
		}
		case TYPE_SPINNER: {
			Spinner sp = (Spinner) layout.findViewWithTag(layDto.getLabel());
			if (sp == null) {
				break;
			}
			layoutData = new LayoutDataDto();
			layoutData.setType(layDto.getType());
			layoutData.setLabel(layDto.getLabel());
			layoutData.setValue(toStringValue(sp.getSelectedItem()));
			break;
		}
		case TYPE_RADIOBUTTON: {
//			if (layDto.isEnabled()) { // ������listener�еĲ����޷����?û���ı�
//				
//			}
			RadioButton rb = (RadioButton) layout.findViewWithTag(layDto.getLabel());
			if (rb == null) {
				break;
			}
		
			layoutData = new LayoutDataDto();
			layoutData.setLabel(layDto.getLabel()); // Ҫȫ���������Ҫ���¼�����ڿ�ʼ��
//			if (rb.isSelected()) {
//				layoutData.setType(layDto.getType()); // ��Ҫȫ��
//			}
//			if (rb.isChecked()) {
//				layoutData.setType(layDto.getType()); // Ϊ�����ã�
//			}
			
			break;
		}
		case TYPE_CHECKBOX: {
			break;
		}
		case TYPE_DATEPICKER: {
			break;
		}
		case TYPE_TIMEPICKER: {
			break;
		}
		default: {
			break;
		}
		}
		
		return layoutData;
	}

	private String toStringValue(Object obj) {
		String result = null;
		if (obj == null) {
			result = "";
		} else {
			result = String.valueOf(obj);
		}
		return result;
	}
	
	public OnRadioCheckedListener onRadioCheckedListener;
	
	public void setOnRadioCheckedListener(OnRadioCheckedListener listener) {
		onRadioCheckedListener = listener;
	}
	
	public ArrayList<RadioButton> getListRadio() {
		return mListRadio;
	}

	public void setListRadio(ArrayList<RadioButton> listRadio) {
		this.mListRadio = listRadio;
	}

	public interface OnRadioCheckedListener {

		public void onChecked(CompoundButton buttonView, boolean isChecked,
				ArrayList<LayoutDto> inlineLayout);
		
//		public void unChecked();
//		
//		public void checked(ArrayList<LayoutDto> arrayList);
	}

	private static final String TAG_LAYOUT_LABEL = "layout_";
	
	private static final float LABEL_TEXT_SIZE = 20;
	private static final int LABEL_TEXT_COLOR = Color.BLACK;

	private static final int TYPE_TEXTVIEW = 1;
	private static final int TYPE_EDITTEXT = 0;
	private static final int TYPE_BUTTON = 5;
	private static final int TYPE_SPINNER = 2;
	private static final int TYPE_RADIOBUTTON = 3;
	private static final int TYPE_CHECKBOX = 6;
	private static final int TYPE_DATEPICKER = 7;
	private static final int TYPE_TIMEPICKER = 8;
}
