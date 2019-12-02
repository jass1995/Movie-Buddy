package com.example.movieapp.utils;

import android.content.Context;
import android.content.SharedPreferences;



public class PrefsManager {


  public static final String KEY_FIRSTNAME = "firstname";
  public static final String KEY_LASTNAME = "lastname";
  public static final String KEY_EMAIL = "email";
  public static final String KEY_MOBILE = "mobile";
  public static final String KEY_GENDER = "gender";
  public static final String KEY_ALREADY_LOGIN = "already_login";


  private String mPrefsName = "MOVIE";

  private SharedPreferences sharedpreferences;

  private SharedPreferences.Editor mEditor;


  public PrefsManager(Context context) {
    sharedpreferences = context.getSharedPreferences(mPrefsName, Context.MODE_PRIVATE);

    mEditor = sharedpreferences.edit();

  }

  /**
   * Load Preference any string value
   *
   * @paramContext - Context of class
   * @paramKey - To get value corresponding to KEY_VALUE
   */
  public String getPreferenceString(String key, String defValue) {
    return sharedpreferences.getString(key, defValue);
  }



  /**
   * Load Preference any string value
   *
   * @paramContext - Context of class
   * @paramKey - To get value corresponding to KEY_VALUE
   */
  public int getPreferenceInt(String key, int defValue) {
    return sharedpreferences.getInt(key, defValue);
  }

  /**
   * Load Preference any Boolean value
   *
   * @paramContext - Context of class
   * @paramKey - To get value corresponding to KEY_VALUE
   */
  public boolean getPreferenceBoolean(String key, boolean defValue) {
    return sharedpreferences.getBoolean(key, defValue);
  }

  /**
   * Save boolean value in Preference for future use.
   */
  @SuppressWarnings("static-access")
  public void savePreferenceBoolean(String key,
                                    boolean value) {
    mEditor.putBoolean(key, value);
    mEditor.commit();
  }

  /**
   * Save Preference for future use.
   */
  @SuppressWarnings("static-access")
  public void savePreferenceString(String key,
                                   String value) {
    mEditor.putString(key, value);
    mEditor.commit();
  }



  /**
   * Save Preference for future use.
   */
  @SuppressWarnings("static-access")
  public void savePreferenceInt(String key,
                                int value) {
    mEditor.putInt(key, value);
    mEditor.commit();
  }

  /**
   * Save {@link java.util.ArrayList} in {@link SharedPreferences} in
   * Gson form.
   */
  @SuppressWarnings("static-access")
  public void savePreferenceList(String key, String json) {
    mEditor.putString(key, json);
    mEditor.commit();
  }

  /**
   * Reset shared-preference state.
   */
  public void clearAutoPreference() {
    mEditor.clear();
    mEditor.commit();
  }

}
