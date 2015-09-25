package com.multiversal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Scheduling extends Activity {

	ListView lv_search;
	List<HashMap<String, String>> aList;
	SimpleAdapter adapter;

	SQLiteDatabase db;
	String database = "Multiversal.db";
	String ctable;
	boolean dbfound = false;

	TextView tv_device_no, tv_device_name, tv_start_time, tv_stop_time,
			tv_interval, tv_duration, save_file;
	private Dialog mDialog, mDialog1;
	private TextView message;
	private TextView update;
	private TextView delete, file_upload;
	EditText file_name;
	private TextView cancel;
	String text = "<font color=#FE2E2E>Multi</font><font color=#013ADF>versal Tech</font><font color=#04B404>nologies</font>";
	TextView leftIcon;
	protected String stringTxtData = "";

	Button save,cancell;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scheduling);
		leftIcon = (TextView) findViewById(R.id.leftIcon);
//		leftIcon.setText(Html.fromHtml(text));
	
	
		lv_search = (ListView) findViewById(R.id.scheduling_list);
		


		tv_device_no = (TextView) findViewById(R.id.tv_device_no);
		tv_device_name = (TextView) findViewById(R.id.tv_device_name);
		tv_start_time = (TextView) findViewById(R.id.tv_start_time);
		tv_stop_time = (TextView) findViewById(R.id.tv_stop_time);
		tv_interval = (TextView) findViewById(R.id.tv_interval);
		tv_duration = (TextView) findViewById(R.id.tv_duration);
		save_file = (TextView) findViewById(R.id.save_file);
		file_upload = (TextView) findViewById(R.id.file_upload);

		final View view = View.inflate(Scheduling.this,
				R.layout.operation_list, null);
		mDialog = new Dialog(Scheduling.this, R.style.NewDialog);
		mDialog.setContentView(view);
		mDialog.setCancelable(true);

		final View view1 = View.inflate(Scheduling.this, R.layout.file_save,
				null);
		mDialog1 = new Dialog(Scheduling.this, R.style.NewDialog);
		mDialog1.setContentView(view1);
		mDialog1.setCancelable(true);
		save = (Button) mDialog1.findViewById(R.id.btn_save);
		cancell = (Button) mDialog1.findViewById(R.id.btn_cancel);
		file_name = (EditText) mDialog1.findViewById(R.id.file_name);
		message = (TextView) mDialog.findViewById(R.id.message);
		update = (TextView) mDialog.findViewById(R.id.update);
		delete = (TextView) mDialog.findViewById(R.id.delete);
		cancel = (TextView) mDialog.findViewById(R.id.cancel);



	

		lv_search.setOnItemClickListener(new OnItemClickListener() {

			public String device_no;
			public String device_name;
			public String start_time;
			public String stop_time;
			public String interval;
			public String duration;
			public String schedule_id;
			public String Dayorder;
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stubv
				aList.get(position);
				HashMap<String, String> map = new HashMap<String, String>();
				map = (HashMap<String, String>) aList.get(position);
				device_no = (String) map.get("device_no");
				device_name = (String) map.get("device_name");
				start_time = (String) map.get("start_time");
				stop_time = (String) map.get("stop_time");
				interval = (String) map.get("interval");
				duration = (String) map.get("duration");
				schedule_id = (String) map.get("schedule_id");
				Dayorder= (String) map.get("Dayorder");
				mDialog.show();

				message.setText("Are you want to edit this \n Device No :"
						+ device_no + "\n Device Name:" + device_name);
				update.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Scheduling.this,
								MainActivity.class);
						intent.putExtra("device_no", device_no);
						intent.putExtra("device_name", device_name);
						intent.putExtra("start_time", start_time);
						intent.putExtra("stop_time", stop_time);
						intent.putExtra("interval", interval);
						intent.putExtra("duration", duration);
						intent.putExtra("update", "update");
						intent.putExtra("schedule_id", schedule_id);
						intent.putExtra("Dayorder", Dayorder);
						
						mDialog.dismiss();
						startActivity(intent);
						finish();
						mDialog.dismiss();
						
						Toast.makeText(getApplicationContext(), ""+schedule_id, 1).show();
					}
				});
				delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// db.execSQL("DELETE FROM Scheduling WHERE device_no='"
						// + device_no + "'", null);
						mDialog.dismiss();
						deletescheduling(schedule_id);
						
						getdata();
					}
				});
				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDialog.dismiss();
					}
				});
			}
		});


		file_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Scheduling.this, PrintDemo.class);
				Bundle bundle = new Bundle();
				bundle.putString("Bluetooth_text", stringTxtData);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		save_file.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mDialog1.show();
				save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							File myFile = new File("/sdcard/"
									+ file_name.getText().toString() + ".txt");
							myFile.createNewFile();
							FileOutputStream fOut = new FileOutputStream(myFile);
							OutputStreamWriter myOutWriter = new OutputStreamWriter(
									fOut);
							myOutWriter.append(stringTxtData);
							myOutWriter.close();
							fOut.close();
							Toast.makeText(getBaseContext(),
									"Done writing SD 'mysdfile.txt'",
									Toast.LENGTH_SHORT).show();
							mDialog1.dismiss();
						} catch (Exception e) {
							Toast.makeText(getBaseContext(), e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
				cancell.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDialog1.dismiss();
					}
				});

			}
		});
		getdata();
	}



	
	public void getdata() {
		try {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			Cursor testdata = mDbHelper.GetallSchedulingMaster();
			String data = "", data1 = "";
			int i = 0;

			stringTxtData="";
			aList = new ArrayList<HashMap<String, String>>();
			if (testdata.moveToFirst()) {
				do {
				
					stringTxtData += testdata.getString(1) + "\t" + testdata.getString(2) + "\t"
							+  testdata.getString(3) + "\t" + testdata.getString(4) + "\t"
							+ testdata.getString(5) + "\t" + testdata.getString(6) + "\n";
					testdata.getString(1);
					
					String day="";
					if(testdata.getString(7).contains("0"))
					{
						day="Sun";
					}
					else if(testdata.getString(7).contains("1"))
					{
						day="Mon";
					}
					else if(testdata.getString(7).contains("2"))
					{
						day="Tue";
					}

					else if(testdata.getString(7).contains("3"))
					{
						day="Wed";
					}

					else if(testdata.getString(7).contains("4"))
					{
						day="Thurs";
					}

					else if(testdata.getString(7).contains("5"))
					{
						day="Fri";
					}

					else if(testdata.getString(7).contains("6"))
					{
						day="Sat";
					}

					HashMap<String, String> hm = new HashMap<String, String>();
					hm.put("device_no", day+"-"+testdata.getString(1));
					hm.put("device_name", testdata.getString(2));
					hm.put("start_time", testdata.getString(3));
					hm.put("stop_time", testdata.getString(4));
					hm.put("interval", testdata.getString(5));
					hm.put("duration", testdata.getString(6));
					hm.put("schedule_id", testdata.getString(0));
					hm.put("Dayorder",  testdata.getString(7));
					aList.add(hm);

					i++;
				} while (testdata.moveToNext());
			}

			mDbHelper.close();
			String[] from = { "device_no", "device_name", "start_time",
					"stop_time", "interval", "duration" };
			int[] to = { R.id.tv_device_no, R.id.tv_device_name,
					R.id.tv_start_time, R.id.tv_stop_time, R.id.tv_interval,
					R.id.tv_duration };
			adapter = new SimpleAdapter(getBaseContext(), aList,
					R.layout.listview_search, from, to);

			adapter.notifyDataSetChanged();
			lv_search.setAdapter(adapter);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deletescheduling(String id)
	{
		TestAdapter mDbHelper = new TestAdapter(this);         
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		boolean testdata = mDbHelper.deletescheduling(id); 
		Toast.makeText(getApplicationContext(), "Scheduling Deleted Sucessfully", Toast.LENGTH_SHORT).show();
		mDbHelper.close();
	}
}
