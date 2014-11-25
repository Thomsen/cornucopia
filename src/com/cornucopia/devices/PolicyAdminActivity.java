package com.cornucopia.devices;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.cornucopia.R;

/**
 * {@link DevicePolicyManager#getCameraDisabled(ComponentName)} need api level 14+ 
 * (from baidu yun not uninstall)
 */
public class PolicyAdminActivity extends PreferenceActivity implements
    OnPreferenceChangeListener, OnPreferenceClickListener {

    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    
    // UI elements
    private CheckBoxPreference mEnableCheckbox;
    private CheckBoxPreference mDisableCameraCheckbox;

    protected DevicePolicyManager mDPM;
    protected ComponentName mPolicyAdmin;
    private boolean isAdminActive;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mPolicyAdmin = new ComponentName(this, PolicyAdminReceiver.class);
        
        addPreferencesFromResource(R.xml.device_admin_general);
        mEnableCheckbox = (CheckBoxPreference) findPreference(getString(R.string.key_enable_admin));
        mEnableCheckbox.setOnPreferenceChangeListener(this);
        mDisableCameraCheckbox = (CheckBoxPreference) findPreference(getString(R.string.key_disable_camera));
        mDisableCameraCheckbox.setOnPreferenceChangeListener(this);
    }

    // At onResume time, reload UI with current values as required
    @Override
    public void onResume() {
        super.onResume();
        isAdminActive = isAdminActive();
        mEnableCheckbox.setChecked(isAdminActive);
        enableDeviceCapabilitiesArea(isAdminActive);

        if (isAdminActive) {
            mDPM.setCameraDisabled(mPolicyAdmin, mDisableCameraCheckbox.isChecked());
            reloadSummaries();
        }
    }
    
    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean value = (Boolean) newValue;
        if (preference == mEnableCheckbox) {
            if (value != isAdminActive) {
                if (value) {
                    // Launch the activity to have the user enable our admin.
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mPolicyAdmin);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            this.getString(R.string.add_admin_extra_app_text));
                    // android.app.device_admin error, not intent
                    startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN); 
                    // return false - don't update checkbox until we're really active
                    return false;
                } else {
                    mDPM.removeActiveAdmin(mPolicyAdmin);
                    enableDeviceCapabilitiesArea(false);
                    isAdminActive = false;
                }
            }
        } else if (preference == mDisableCameraCheckbox) {
            mDPM.setCameraDisabled(mPolicyAdmin, value);
            reloadSummaries();
        }
        return true;
    }

    public boolean isAdminActive() {
        if (null != mDPM) {
            return mDPM.isAdminActive(mPolicyAdmin);
        } else {
            return false;
        }
    }

    protected void reloadSummaries() {
        String cameraSummary = getString(mDPM.getCameraDisabled(mPolicyAdmin)
                ? R.string.camera_disabled : R.string.camera_enabled);
        mDisableCameraCheckbox.setSummary(cameraSummary);
    }

    /** Updates the device capabilities area (dis/enabling) as the admin is (de)activated */
    private void enableDeviceCapabilitiesArea(boolean enabled) {
        mDisableCameraCheckbox.setEnabled(enabled);
    }
    
    /**
     * DeviceAdminReceiver (api level 8+)
     * static must
     */
    public static class PolicyAdminReceiver extends DeviceAdminReceiver {
        
        @Override
        public void onEnabled(Context context, Intent intent) {
            super.onEnabled(context, intent);
            Toast.makeText(context, "policy admin enable", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onDisabled(Context context, Intent intent) {
            super.onDisabled(context, intent);
            Toast.makeText(context, "policy admin disabled", Toast.LENGTH_SHORT).show();
        }
    }

}
