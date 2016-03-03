package com.cornucopia.ui.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

public class DynamicLayoutUtil {

	private final int TEXT_SIZE = 24;
	
	private Context mContext;
	
	private LinearLayout.LayoutParams mLinearParams;
	
//	private LinearLayout mLayout;
	
	public DynamicLayoutUtil(Context context) {
		mContext = context;
		mLinearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT); // ע��TableLayout�Ĳ��ֲ�����������
	}
	
	// DatePicker, TimerPicker
	
	public void addLinearView(ViewGroup layout, ArrayList<LayoutDto> listLayout) {
		for (LayoutDto lay : listLayout) {
			switch (lay.getType()) {
			case 1: {
				// TextView
				addTextView(layout, lay);
				break;
			}
			case 2: {
				// Spinner
				addSpinner(layout, lay);
				break;
			}
			case 0: {
				// EditText
				addEditText(layout, lay);
				break;
			}
			case 3: {
				// RadioGroup
				addRadioButton(layout, lay);
			}
			default:
				break;
			}
		}
		
//		mLayout = layout;
	}

	/////////////////// ע�⣺layout�Ķ�̬��ӣ���Ҫ����ӵ�viewһ��tag ////////////////
	
	private void addTextView(ViewGroup layout, LayoutDto lay) {
		TextView tv = new TextView(mContext);
		tv.setText(lay.getLabel());
		tv.setTag(lay.getLabel());
		layout.addView(tv);
		layout.invalidate();
	}
	
	private void addEditText(ViewGroup layout, LayoutDto lay) {
		EditText et = new EditText(mContext);
		et.setLayoutParams(mLinearParams);
//		et.setEnabled(lay.isEnabled());
		et.setFocusable(true);
//		et.setText(String.valueOf(lay.getValue()));
		et.setTag(lay.getLabel());
		
		layout.addView(generateHorizontalLayout(lay.getLabel(), et));
	}
	
	private void addSpinner(ViewGroup layout, LayoutDto lay) {
		
		Spinner spinner = new Spinner(mContext);
		spinner.setLayoutParams(mLinearParams);
//		ArrayList<String> listValue = new ArrayList<String>();
//		if (lay.getValue() != null) { // CommonUtil 
//			listValue.add(lay.getValue());
//		}
		ArrayList<String> listValue = new ArrayList<String>();
		if (lay.getValue() != null) {
			listValue = (ArrayList<String>) lay.getValue();
		}	
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, listValue);
//		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
//		layout.addView(spinner);
		
		spinner.setTag(lay.getLabel());
		
		// TODO: �ж��Ƿ���ҪLabel
		// FIXED: ���Խ�View������TextView��
		layout.addView(spinner);
//		layout.addView(generateHorizontalLayout(lay.getLabel(), spinner));
	}
	
	private void addRadioButton(final ViewGroup layout, final LayoutDto lay) {
		RadioButton radioButton = new RadioButton(mContext);
		radioButton.setText(lay.getLabel());
		radioButton.setTag(lay.getLabel());
		radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
//					Toast.makeText(mContext.getApplicationContext(), "view id: " + buttonView.getId(), Toast.LENGTH_SHORT).show();
//					addLinearView(layout, lay.getInlineLayout());
					addLinearView((LinearLayout) layout.getParent(), lay.getInlineLayout());
				} else {
					removeLinearView((LinearLayout) layout.getParent(), lay.getInlineLayout());
				}
			}

			
		});
		layout.addView(radioButton);
	}
	
	private void removeLinearView(ViewGroup layout,
			ArrayList<LayoutDto> listLayout) {
		for (LayoutDto lay : listLayout) {
			View view = layout.findViewWithTag(lay.getLabel());
			layout.removeView(view);
		}
	}
	
	private View generateHorizontalLayout (String label, View view) {
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setLayoutParams(mLinearParams);
		
		TextView tvLabel = new TextView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL;
		params.leftMargin = 10;
		params.rightMargin = 4;
		tvLabel.setLayoutParams(params);
		tvLabel.setTextSize(TEXT_SIZE);
		tvLabel.setGravity(Gravity.CENTER_VERTICAL);
		tvLabel.setText(label);
		
		linearLayout.addView(tvLabel);
		linearLayout.addView(view);
		
		return linearLayout;
	}
	
//	public View getLayout() {
//		return mLayout;
//	}
	
	public void setLayoutValue(LinearLayout layout, ArrayList<LayoutDataDto> listData) { // ����������ģ���ʱ��һ����ʼ��
		if (listData == null) {
			return ; // or throw new exception
		}
		for (LayoutDataDto lay : listData) {
			switch (lay.getType()) {
			case 1: {
				// TextView
				TextView tv = (TextView) layout.findViewWithTag(lay.getLabel());
				break;
			}
			case 2: {
				// Spinner
				Spinner sp = (Spinner) layout.findViewWithTag(lay.getLabel());
				ArrayAdapter adapter = (ArrayAdapter) sp.getAdapter();
				sp.setSelection(adapter.getPosition(lay.getValue()));
				break;
			}
			case 0: {
				// EditText
				EditText et = (EditText) layout.findViewWithTag(lay.getLabel());
				et.setText(String.valueOf(lay.getValue()));
				break;
			}
			default:
				break;
			}
		}
	}
	
	public ArrayList<LayoutDataDto> getLayoutValue(LinearLayout layout, ArrayList<LayoutDto> layDto) {
		ArrayList<LayoutDataDto> listData = new ArrayList<LayoutDataDto>();
		
		LayoutDataDto layoutData = null;
		
		for (LayoutDto lay : layDto) {
			switch (lay.getType()) {
			case 1: {
				// TextView
				TextView tv = (TextView) layout.findViewWithTag(lay.getLabel());
				break;
			}
			case 2: {
				// Spinner
				Spinner sp = (Spinner) layout.findViewWithTag(lay.getLabel());
				if (sp == null) {
//					return null;
					break;
				}
				sp.getSelectedItem();
				sp.getSelectedItemPosition();
				
				layoutData = new LayoutDataDto();
				layoutData.setType(lay.getType());
				layoutData.setLabel(lay.getLabel());
				layoutData.setValue(String.valueOf(sp.getSelectedItem()));
				listData.add(layoutData);
				
				break;
			}
			case 0: {
				// EditText
				EditText et = (EditText) layout.findViewWithTag(lay.getLabel());

//				listData.get(listData.indexOf(lay.getLabel())).setValue(String.valueOf(et.getText()));
				
				if (et == null) {
//					return null;
					break;
				}
				
				layoutData = new LayoutDataDto();
				layoutData.setType(lay.getType());
				layoutData.setLabel(lay.getLabel());
				layoutData.setValue(String.valueOf(et.getText()));
				listData.add(layoutData);
				
				break;
			}
			default:
				break;
			}
			
			if (lay.getInlineLayout() != null) {
				layoutData = new LayoutDataDto();
				layoutData.setInlineLayoutData(getLayoutValue(layout, lay.getInlineLayout()));
				listData.add(layoutData);
			}
		}
		
		return listData;
	}

	/**
	 * ���Ļ�ȡÿ��view��ֵ
	 * @author Thomsen
	 * @date 2013-1-18 ����4:41:11
	 * @param layout
	 * @param lay
	 */
	private LayoutDataDto getLayoutValue(LinearLayout layout, LayoutDto lay) {
		LayoutDataDto layoutData = new LayoutDataDto();
		switch (lay.getType()) {
		case 1: {
			// TextView
			TextView tv = (TextView) layout.findViewWithTag(lay.getLabel());
			break;
		}
		case 2: {
			// Spinner
			Spinner sp = (Spinner) layout.findViewWithTag(lay.getLabel());
			sp.getSelectedItem();
			sp.getSelectedItemPosition();
			
			layoutData = new LayoutDataDto();
			layoutData.setType(lay.getType());
			layoutData.setLabel(lay.getLabel());
			layoutData.setValue(String.valueOf(sp.getSelectedItem()));
			break;
		}
		case 0: {
			// EditText
			EditText et = (EditText) layout.findViewWithTag(lay.getLabel());

//			listData.get(listData.indexOf(lay.getLabel())).setValue(String.valueOf(et.getText()));
			
			layoutData = new LayoutDataDto();
			layoutData.setType(lay.getType());
			layoutData.setLabel(lay.getLabel());
			layoutData.setValue(String.valueOf(et.getText()));
			break;
		}
		default:
			break;
		}
		
		return layoutData;
	}

	public HashMap<String, ArrayList<LayoutDataDto>> getLayoutValue(
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
		}
		
		
		return null;
	}

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
			
			ArrayList<LayoutDataDto> dataValue = getLayoutValue(mLayoutGroup.get(key), value); // ���expand��չ���Ļ���mLayoutGroup�Ͳ�����layout�Ĺ�����ϵ
			
			layoutData.put(key, dataValue);
			
			Log.i("thom", key);
		}
		
		return layoutData;
	}

	public void addQueryView(final LinearLayout convertView) {
		/*
		 * [radio:"value", layout:[type:"value", label:"value", value:Object]] // +isSelected
		 * to LayoutDto
		 * [type:3, label:"value", layout:[type:int, label:"value", value:Object]]
		 */
		
		RadioGroup rg = new RadioGroup(mContext);
//		rg.setLayoutParams(mLinearParams);
		rg.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
				RadioGroup.LayoutParams.WRAP_CONTENT));
		rg.setOrientation(RadioGroup.HORIZONTAL);
		
		RadioButton rb = new RadioButton(mContext);
		rb.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
				RadioGroup.LayoutParams.WRAP_CONTENT));
		rb.setText("��");
//		rb.setSelected(true);
//		rb.setChecked(true); // Ĭ��ѡ�к��޷���ѡ���������������rb(���ⲻ����)
		rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			TextView tv = new TextView(mContext);
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				tv.setText("�� ��ѡ");
				if (isChecked) {
					tv.setText(String.valueOf(buttonView.getText()));
					convertView.addView(tv);
				} else {
					convertView.removeView(tv);
				}
			}
		});
		rg.addView(rb);

//		rb = new RadioButton(mContext);
		RadioButton rb2 = new RadioButton(mContext);
		rb2.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
				RadioGroup.LayoutParams.WRAP_CONTENT));
		rb2.setText("Ů");
		rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			TextView tv = new TextView(mContext);
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(mContext.getApplicationContext(), "radio", Toast.LENGTH_SHORT).show();
//				tv.setText("�� ��ѡ");
//				convertView.removeView(tv);
				if (isChecked) {
					tv.setText(String.valueOf(buttonView.getText()));
					convertView.addView(tv);
				} else {
					convertView.removeView(tv);
				}
			}
		});
		rg.addView(rb2);
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Toast.makeText(mContext.getApplicationContext(), "checked id: " + checkedId, Toast.LENGTH_SHORT).show();
			}
		});
		
		
		convertView.addView(rg);
	}
	
}
