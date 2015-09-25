package com.multiversal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class TestAdapter {
	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;

	public TestAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public Cursor geturldata() {
		try {
			String sql = "SELECT * from urlmaster";

			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "geturlmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor getuserdata() {
		try {
			String sql = "SELECT * from usermaster";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getusermaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public TestAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public TestAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public boolean SaveDevice(String name) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("DeviceName", name);
			mDb.insert("DeviceDetails", null, cv);
			Log.d("DeviceDetails", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("usermaster db", ex.toString());
			return false;
		}
	}

	public boolean updateSchedulingMaster(String schid, String DeviceID,
			String DeviceName, String start_time, String stop_time,
			String interval, String duration, String Dayorder) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("DeviceID", DeviceID);
			cv.put("DeviceName", DeviceName);
			cv.put("start_time", start_time);
			cv.put("stop_time", stop_time);
			cv.put("interval", interval);
			cv.put("duration", duration);
			cv.put("Dayorder", Dayorder);
			mDb.update("SchedulingMaster", cv, "SchedulingID='" + schid + "'",
					null);
			Log.d("SchedulingMaster", "information is updated");
			return true;
		} catch (Exception ex) {
			Log.d("Tagmaster db", ex.toString());
			return false;
		}
	}

	public boolean SaveSchedulingMaster(String DeviceID, String DeviceName,
			String start_time, String stop_time, String interval,
			String duration, String Dayorder) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("DeviceID", DeviceID);
			cv.put("DeviceName", DeviceName);
			cv.put("start_time", start_time);
			cv.put("stop_time", stop_time);
			cv.put("interval", interval);
			cv.put("duration", duration);
			cv.put("Dayorder", Dayorder);
			mDb.insert("SchedulingMaster", null, cv);
			Log.d("SchedulingMaster", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("usermaster db", ex.toString());
			return false;
		}
	}
	public boolean updateSchedulingMaster(String FileID) {
		try {
			ContentValues cv = new ContentValues();
				cv.put("FileID", FileID);
			mDb.update("SchedulingMaster", cv, "FileID is NULL",
					null);
			Log.d("SchedulingMaster", "information is updated");
			return true;
		} catch (Exception ex) {
			Log.d("SchedulingMaster db", ex.toString());
			return false;
		}
	}
	public boolean DeleteFile(String id) {
		try {
			mDb.execSQL("Delete from FileMaster where FileID=" + id
					+ "");
			Log.d("SchedulingMaster", "informationdeleted");
			return true;

		} catch (Exception ex) {
			Log.d("SchedulingMaster db", ex.toString());
			return false;
		}
	}
	
	public boolean UpdateFile(String FileID) {
		try {
			
			Calendar c = Calendar.getInstance();
			String cdate = new SimpleDateFormat("dd-MM-yyyy")
					.format(new Date());

			System.out.println(cdate+"  "+FileID);
			
			ContentValues cv = new ContentValues();
				cv.put("Approval", cdate);
			mDb.update("FileMaster", cv, "FileID="+FileID+"",null);
			Log.d("FileMaster", "information is updated");
			return true;
		} catch (Exception ex) {
			Log.d("FileMaster db", ex.toString());
			return false;
		}
	}
	public long  SaveFileMaster(String FileName) {
		 long rowId = -1;
		try {

			Calendar c = Calendar.getInstance();
			String cdate = new SimpleDateFormat("dd-MM-yyyy")
					.format(new Date());

			ContentValues cv = new ContentValues();
			cv.put("FileDate", cdate);
			cv.put("FileName", FileName);
			rowId=	mDb.insert("FileMaster", null, cv);
			Log.d("FileMaster", "informationsaved");
			return rowId;

		} catch (Exception ex) {
			Log.d("usermaster db", ex.toString());
			return rowId;
		}
	}

	public Cursor getdevicedetails() {
		try {
			String sql = "SELECT * from DeviceDetails";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor Getsavefile() {
		try {
			String sql = "SELECT * from SchedulingMaster where FileID IS NULL";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor Getfile() {
		try {
			String sql = "SELECT * from FileMaster";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor getdevicedetails(String name) {
		try {
			String sql = "SELECT * from DeviceDetails where DeviceName='"
					+ name + "'";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor GetallSchedulingMaster() {
		try {
			String sql = "SELECT * from SchedulingMaster";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor GetSingleScheduling(String SchedulingID) {
		try {
			String sql = "SELECT * from SchedulingMaster where SchedulingID='"
					+ SchedulingID + "'";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor GetSingledayorderScheduling(String order) {
		try {
			String sql = "SELECT * from SchedulingMaster where DayOrder='"
					+ order + "'";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public boolean deletescheduling(String id) {
		try {
			mDb.execSQL("Delete from SchedulingMaster where SchedulingID=" + id
					+ "");
			Log.d("SchedulingMaster", "informationdeleted");
			return true;

		} catch (Exception ex) {
			Log.d("SchedulingMaster db", ex.toString());
			return false;
		}
	}

	public Cursor GetSingleDayorderScheduling() {
		try {
			String sql = "SELECT Distinct DayOrder from SchedulingMaster";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "Tagmaster >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public void close() {
		mDbHelper.close();
	}

}
