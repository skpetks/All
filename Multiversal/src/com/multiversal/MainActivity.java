package com.multiversal;

import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
	Spinner sp_device_no, sp_device_name;
	private ArrayAdapter<String> adapter1, adapter2;

	TextView btn_add, btn_device_simulation;
	TextView ed_duration, ed_interval, ed_stop_time, ed_start_time;
	private int hour;
	

	Boolean Selectionstate = false;
	String[] DeviceID;
	String[] DeviceName;
	private int minute;
	StringBuilder selected_time;
	int selected_item;
	public String edevice_no = "";
	public String edevice_name = "";
	public String estart_time = "";
	public String estop_time = "";
	public String einterval = "";
	public String eduration = "";
	public String schedule_id = "", device_name, start_time, stop_time,
			interval, duration, Dayorder;

	private String Salutation;
	private boolean touched;
	private String[] sal_array = { "Mr", "Mrs", "Miss" };

	SQLiteDatabase db;
	String database = "Multiversal.db";
	String ctable;
	boolean dbfound = false;
	protected String select_device_name;
	protected String select_device_no;
	protected String select_day;
	String device_no;
	MultiSelectionSpinner spinner;
	private int spinnerPosition;
	private String update;
	String text = "<font color=#FE2E2E>Multi</font><font color=#013ADF>versal Tech</font><font color=#04B404>nologies</font>";
	TextView leftIcon;
	
	String ontime,offtime;

	static final int TIME_DIALOG_ID = 999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Intent in = getIntent();

		sp_device_no = (Spinner) findViewById(R.id.spinner2);
		sp_device_name = (Spinner) findViewById(R.id.spinner3);
		btn_add = (TextView) findViewById(R.id.btn_add);

		btn_device_simulation = (TextView) findViewById(R.id.btn_device_simulation);
		ed_duration = (TextView) findViewById(R.id.ed_duration);
		ed_interval = (TextView) findViewById(R.id.ed_interval);
		ed_stop_time = (TextView) findViewById(R.id.ed_stop_time);
		ed_start_time = (TextView) findViewById(R.id.ed_start_time);
		leftIcon = (TextView) findViewById(R.id.leftIcon);

		spinner = (MultiSelectionSpinner) findViewById(R.id.spinner1);
		spinner.setItems(getResources().getStringArray(R.array.days));

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			device_no = bundle.getString("device_no");
			device_name = bundle.getString("device_name");
			start_time = bundle.getString("start_time");
			stop_time = bundle.getString("stop_time");
			interval = bundle.getString("interval");

			duration = bundle.getString("duration");
			schedule_id = bundle.getString("schedule_id");
			Dayorder = bundle.getString("Dayorder");
			int Dayordervalue = Integer.parseInt(Dayorder);
			spinner.setSelection(Dayordervalue);
			spinner.setEnabled(false);
			sp_device_no.setEnabled(false);
			sp_device_name.setEnabled(false);
			ed_start_time.setText(start_time);
			ed_stop_time.setText(stop_time);
			ed_interval.setText(interval);
			ed_duration.setText(duration);

			btn_add.setText("Update");
		}

		ed_start_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID);
				selected_item = 1;

			}
		});
		ed_stop_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selected_item = 2;
				showDialog(TIME_DIALOG_ID);

			}
		});
		ed_duration.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Selectionstate = true;
				show();
			}
		});
		ed_interval.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// selected_item = 4;
				// showDialog(TIME_DIALOG_ID);
				Selectionstate = false;
				show();

			}
		});
		getdata();
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String stop_time = ed_stop_time.getText().toString().trim();
				String start_time = ed_start_time.getText().toString().trim();
				String interval = ed_interval.getText().toString().trim();
				String duration = ed_duration.getText().toString().trim();
				if (btn_add.getText().toString().trim().contains("Update")) {
					UpdateSchedulingMaster(schedule_id, sp_device_no
							.getSelectedItem().toString(), sp_device_name
							.getSelectedItem().toString(), start_time,
							stop_time, interval, duration, Dayorder);

				} else {

					List<Integer> li = spinner.getSelectedIndicies();
					System.out.println("Sixr" + li.size());

					if (li.size() == 0) {
						Toast.makeText(getApplicationContext(),
								"Please select operation day", 1).show();
						return;
					}
					if (ed_start_time.getText().toString().trim().length() == 0) {
						Toast.makeText(getApplicationContext(),
								"Please select device start time", 1).show();
						return;
					}
					if (ed_stop_time.getText().toString().trim().length() == 0) {
						Toast.makeText(getApplicationContext(),
								"Please select device stop time", 1).show();
						return;
					}

					for (int i = 0; i < li.size(); i++) {
						System.out.println("spinner selected pos" + li.get(i));
						stop_time = ed_stop_time.getText().toString().trim();
						start_time = ed_start_time.getText().toString().trim();
						interval = ed_interval.getText().toString().trim();
						duration = ed_duration.getText().toString().trim();
						SaveSchedulingMaster(sp_device_no.getSelectedItem()
								.toString(), sp_device_name.getSelectedItem()
								.toString(), start_time, stop_time, interval,
								duration, li.get(i).toString());
					}

				}

				Intent intent = new Intent(getApplicationContext(),
						Menuscreen.class);
				startActivity(intent);
			}

		});

	}

	public void onClick(View v) {
		String s = spinner.getSelectedItemsAsString();
		System.out.println("days" + s);
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					false);

		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			// set current time into textview
			selected_time = new StringBuilder().append(pad(hour)).append(":")
					.append(pad(minute));

			switch (selected_item) {
			case 1:
				ed_start_time.setText("" + selected_time);
				break;
			case 2:
				ed_stop_time.setText("" + selected_time);
				break;

			default:
				break;
			}
		}

	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public void SaveSchedulingMaster(String DeviceID, String DeviceName,
			String start_time, String stop_time, String interval,
			String duration, String Dayorder) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		boolean testdata = mDbHelper.SaveSchedulingMaster(DeviceID, DeviceName,
				start_time, stop_time, interval, duration, Dayorder);
		Toast.makeText(getApplicationContext(), "New Scheduling is Added",
				Toast.LENGTH_SHORT).show();
		mDbHelper.close();
		getdata();
	}

	public void UpdateSchedulingMaster(String schid, String DeviceID,
			String DeviceName, String start_time, String stop_time,
			String interval, String duration, String Dayorder) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		boolean testdata = mDbHelper
				.updateSchedulingMaster(schid, DeviceID, DeviceName,
						start_time, stop_time, interval, duration, Dayorder);
		Toast.makeText(getApplicationContext(),
				"Existing  Scheduling is Updated", Toast.LENGTH_SHORT).show();
		mDbHelper.close();
		getdata();
	}

	public void show() {

		final Dialog d = new Dialog(MainActivity.this);
		d.setTitle("Please Select the Seconds Interval");
		d.setContentView(R.layout.timerlevel);
		Button btnminplus = (Button) d.findViewById(R.id.btn_minplus);
		Button btnsecplus = (Button) d.findViewById(R.id.btn_secplus);
		Button btnminsub = (Button) d.findViewById(R.id.btn_minsub);
		Button btnsecsub = (Button) d.findViewById(R.id.btn_secsub);
		Button btnset = (Button) d.findViewById(R.id.btn_set);
		Button btncancel = (Button) d.findViewById(R.id.btn_cancel);
		final EditText ed_min = (EditText) d.findViewById(R.id.ed_min);

		final EditText ed_sec = (EditText) d.findViewById(R.id.ed_sec);
		ed_min.setText("0");
		ed_sec.setText("0");
		/*
		 *
		 */

		btnminplus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String value = ed_min.getText().toString().trim();
				if (value.length() != 0) {
					int valuedata = Integer.parseInt(value);
					if (valuedata == 59) {
						ed_min.setText("0");
					} else {
						valuedata++;

						ed_min.setText("" + valuedata);

					}

				} else {
					ed_min.setText("0");

				}

			}
		});
		btnminsub.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String value = ed_min.getText().toString().trim();
				if (value.length() != 0) {
					int valuedata = Integer.parseInt(value);
					if (valuedata == 0) {
						ed_min.setText("59");
					} else {
						valuedata--;

						ed_min.setText("" + valuedata);

					}

				} else {
					ed_min.setText("0");

				}

			}
		});
		btnsecplus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String value = ed_sec.getText().toString().trim();
				if (value.length() != 0) {
					int valuedata = Integer.parseInt(value);
					if (valuedata == 59) {
						ed_sec.setText("0");
					} else {
						valuedata++;

						ed_sec.setText("" + valuedata);

					}

				} else {
					ed_sec.setText("0");

				}

			}
		});
		btnsecsub.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String value = ed_sec.getText().toString().trim();
				if (value.length() != 0) {
					int valuedata = Integer.parseInt(value);
					if (valuedata == 0) {
						ed_sec.setText("59");
					} else {
						valuedata--;

						ed_sec.setText("" + valuedata);

					}

				} else {
					ed_sec.setText("0");

				}

			}
		});
		
		
		btnset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int valuedata=0;
				int valuedatamin=0;
				int valuedata1=0;
				String value = ed_min.getText().toString().trim();
				if (value.length() != 0) {
					 valuedata = Integer.parseInt(value);
					 valuedatamin = valuedata * 60;
					

				} else {
					ed_min.setText("0");

				}
				String value1 = ed_sec.getText().toString().trim();
				if (value1.length() != 0) {
					 valuedata1 = Integer.parseInt(value1);
		
				} else {
					ed_sec.setText("0");

				}
				
			int total=	valuedatamin+valuedata1;
				
		
			 if(Selectionstate==true) {
				 
				 ed_duration.setText(String.valueOf(total)); //set the value to textview
				 
			 
			 } else {
				 ed_interval.setText(Integer.toString(total)); //set the value  to textview
				 }
			 d.cancel();
			}
		});
		
		btncancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
d.cancel();

			}
		});
		d.show();

	}

	public void getdata() {
		try {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			Cursor testdata = mDbHelper.getdevicedetails();
			String data = "", data1 = "";
			int i = 0;
			DeviceName = new String[testdata.getCount()];
			DeviceID = new String[testdata.getCount()];

			if (testdata.moveToFirst()) {
				do {

					DeviceName[i] = testdata.getString(1);
					DeviceID[i] = testdata.getString(0);

					i++;
				} while (testdata.moveToNext());
			}

			mDbHelper.close();

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					MainActivity.this, R.layout.spinner_item, DeviceName);
			ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
					MainActivity.this, R.layout.spinner_item, DeviceID);
			sp_device_name.setAdapter(arrayAdapter);

			sp_device_no.setAdapter(arrayAdapter1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
