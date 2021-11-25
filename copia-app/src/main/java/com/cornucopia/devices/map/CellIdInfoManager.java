package com.cornucopia.devices.map;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

public class CellIdInfoManager {

	private Context mContext;
	
	public CellIdInfoManager(Context context) {
		mContext = context;
	}
	
	/**
	 * 得到手机的信号信息
	 * @date 2012-9-12 下午2:19:35
	 * @return
	 */
	@SuppressLint("NewApi")
	public List<CellIdInfo> getCellIdInfo() {
		List<CellIdInfo> listInfo = new ArrayList<CellIdInfo>();
		
		GsmCellLocation gsmLoc = null;
		CellIdInfo cellInfo = new CellIdInfo();
		
		TelephonyManager manager = (TelephonyManager) mContext.getSystemService(
				Context.TELEPHONY_SERVICE); 
		if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
			// 移动或联通2G
			gsmLoc = (GsmCellLocation) manager.getCellLocation();
			
			if (gsmLoc == null) {
				return null;
			}
			if (manager.getNetworkOperator() == null 
					|| manager.getNetworkOperator().length() == 0) {
				return null;
			}
			
			cellInfo.setCellId(gsmLoc.getCid());
			cellInfo.setMcc(Integer.parseInt(
					manager.getNetworkOperator().substring(0, 3)));
			cellInfo.setMnc(Integer.parseInt(
					manager.getNetworkOperator().substring(3, 5)));
			cellInfo.setLac(gsmLoc.getLac());
			cellInfo.setRadioType("gsm");
			
			listInfo.add(cellInfo);
			
//			List<NeighboringCellInfo> listNeighboring = manager.getNeighboringCellInfo();
//			for (NeighboringCellInfo info : listNeighboring) {
//
//				cellInfo.setCellId(info.getCid());
//
//				listInfo.add(cellInfo);
//
//			}
			
		} else if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
			// 电信2G
			CdmaCellLocation cdmaLoc = (CdmaCellLocation) manager.getCellLocation();
			
			if (cdmaLoc == null) {
				return null;
			}
			if (manager.getNetworkOperator() == null 
					|| manager.getNetworkOperator().length() == 0) {
				return null;
			}
			
			cellInfo.setCellId(cdmaLoc.getBaseStationId());
			cellInfo.setMcc(Integer.parseInt(manager.getNetworkOperator()));
			cellInfo.setMnc(cdmaLoc.getSystemId());
			cellInfo.setLac(cdmaLoc.getNetworkId());
			cellInfo.setRadioType("cdma");
			
			listInfo.add(cellInfo);
		}
		
		return listInfo;
	}
	
}