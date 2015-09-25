package com.multiversal;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Simulation extends Activity {
	private TimePicker timePicker1;
	private Button btn_your_time;
	EditText sys_time;
	String[] schid;
	TextView tv_info;
	Runnable updateTask;
	private int hour;
	private int minute;
	private Handler myHandler = new Handler();
	static final int TIME_DIALOG_ID = 999;
	int countvalue = 0;
	String startTime = "";

	String StopTime = "";
	boolean intervalboolean = false;
	boolean intervalprogress = false;
	boolean Codeact = false;
	boolean durationboolean = false;
	boolean increamental = false;
	boolean durationincreamental = false;
	private long interval = 0;
	private long duration = 0;
	private CountDownTimer mcountDownTimer;
	String text = "<font color=#FE2E2E>Multi</font><font color=#013ADF>versal Tech</font><font color=#04B404>nologies</font>";
	TextView leftIcon;
	EditText your_time;
	int togglecount = 0;
	Button btn_play, btn_fast, btn_re;
	static long diffSeconds;
	static long diffMinutes;
	static long diffHours;
	long initialinterval = 0;
	long initialduration = 0;
	String[] time;
	Timer tickTock;
	TimerTask tickTockTask;
	long timeInMillies = 0L;
	long timeSwap = 0L;
	long finalTime = 0L;
	long increasertimer = 0L;
	long reducetimer = 0L;
	long Time1;
	long timercount = 0L;
	Spinner spn_sch;
	TextView tv_taskid;
	TextView tv_devicename;
	TextView tv_dayorder;
	TextView tv_starttime;
	TextView tvstoptime;
	TextView tv_interval;
	TextView tv_duration;
	Button btn_start, btn_Stop, btn_on, btn_Off;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_simulation);
		
		spn_sch = (Spinner) findViewById(R.id.spn_sch);
		btn_your_time = (Button) findViewById(R.id.btn_your_time);
		sys_time = (EditText) findViewById(R.id.sys_time);
		leftIcon = (TextView) findViewById(R.id.leftIcon);
		btn_play = (Button) findViewById(R.id.btn_play);
		btn_fast = (Button) findViewById(R.id.btn_fast);
		btn_re = (Button) findViewById(R.id.btn_re);
		tv_taskid = (TextView) findViewById(R.id.tv_taskid);
		tv_devicename = (TextView) findViewById(R.id.tv_devicename);
		tv_dayorder = (TextView) findViewById(R.id.tv_dayorder);
		tv_starttime = (TextView) findViewById(R.id.tv_starttime);
		tvstoptime = (TextView) findViewById(R.id.tvstoptime);
		tv_interval = (TextView) findViewById(R.id.tv_interval);
		tv_duration = (TextView) findViewById(R.id.tv_duration);
		tv_info = (TextView) findViewById(R.id.tv_info);
		your_time = (EditText) findViewById(R.id.your_time);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_Stop = (Button) findViewById(R.id.btn_Stop);
		btn_on = (Button) findViewById(R.id.btn_on);
		btn_Off = (Button) findViewById(R.id.btn_Off);

		sys_time.setText("1000");
		timercount = 1000;
		getdata();
		your_time.setText("00:00:00");
		btn_your_time.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finalTime = 0L;

				initialinterval = 0;

				initialduration = 0;

				btn_start.setBackgroundColor(getResources().getColor(
						R.color.white));

				btn_Stop.setBackgroundColor(getResources().getColor(
						R.color.white));
				btn_on.setBackgroundColor(getResources()
						.getColor(R.color.white));
				btn_Off.setBackgroundColor(getResources().getColor(
						R.color.white));

			}
		});
		btn_play.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(startTime.length()==0)
				{
					Toast.makeText(getApplicationContext(), "Please Select task ", 1).show();
					return;
				}
				if (togglecount % 2 == 0) {
					final Drawable drawableTop = getResources().getDrawable(
							R.drawable.pause);
					btn_play.setCompoundDrawablesWithIntrinsicBounds(null,
							drawableTop, null, null);
					showtimer();
				} else {
					final Drawable drawableTop = getResources().getDrawable(
							R.drawable.play);
					btn_play.setCompoundDrawablesWithIntrinsicBounds(null,
							drawableTop, null, null);
					myHandler.removeCallbacks(updateTask);
				}
				togglecount++;

			}
		});
		spn_sch.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {

					if (arg2 != 0) {
						GetSingleScheduling(schid[arg2]);
						Toast.makeText(getApplicationContext(),
								"" + schid[arg2], 1).show();
					}

				} catch (Exception e) {
					System.out.println("" + e.toString());
					// Toast.makeText(getApplicationContext(), ""+e.toString(),
					// 1).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		btn_fast.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (timercount != 0) {
					timercount = timercount - 100;
					sys_time.setText("" + timercount);
				} else {
					Toast.makeText(getApplicationContext(),
							"Sorry Timer Value is Too Low", 1).show();
				}
			}
		});
		btn_re.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timercount = timercount + 100;
				sys_time.setText("" + timercount);
			}
		});

		// leftIcon.setText(Html.fromHtml(text));
		sys_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
			}
		});
		
		your_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				showDialog(TIME_DIALOG_ID);
			}
		});

	}

	// display current time
	public void setCurrentTimeOnView() {

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
		sys_time.setText(new StringBuilder().append(pad(hour)).append(":")
				.append(pad(minute)));

		// set current time into timepicker
		timePicker1.setCurrentHour(hour);
		timePicker1.setCurrentMinute(minute);

	}

	public void addListenerOnButton() {

		sys_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

	}

	private void showtimer() {
		// TODO Auto-generated method stub

		Time1 = SystemClock.uptimeMillis();

		myHandler = new Handler();

		updateTask = new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				finalTime++;
				int seconds = (int) (finalTime / 1);
				int minutes = seconds / 60;

				int minvalue = minutes;
				int hh = minutes / 60;

				int hourvalue = hh;
				hourvalue = hourvalue % 24;

				seconds = seconds % 60;
				minvalue = minvalue % 60;
				your_time.setText("" + String.format("%02d", hourvalue) + ":"
						+ String.format("%02d", minvalue) + ":"
						+ String.format("%02d", seconds));

				String runningtime = String.format("%02d", hourvalue) + ":"
						+ String.format("%02d", minvalue);
				System.out.println(runningtime);
				if (startTime.equals(runningtime)) {
					btn_start.setBackgroundColor(getResources().getColor(
							R.color.green));
					
					
					Codeact = true;
				}
				if (StopTime.equals(runningtime)) {
					btn_Stop.setBackgroundColor(getResources().getColor(
							R.color.red));
					btn_on.setBackgroundColor(getResources().getColor(
							R.color.red));
					Codeact = false;
				}
				if (intervalprogress == true) {

					if (Codeact == true) {
						if (intervalboolean == true) {
							durationboolean = false;

							if (initialinterval == 0) {
								btn_on.setBackgroundColor(getResources()
										.getColor(R.color.green));
							}

							initialinterval++;
							long intervalue = interval ;
							System.out.println("Total " + intervalue);
							System.out.println("initialinterval   "
									+ initialinterval);

							System.out.println("\ninterval   " + interval);
							if (initialinterval % intervalue == 0) {
								btn_on.setBackgroundColor(getResources()
										.getColor(R.color.red));
								initialinterval = 0;
								durationboolean = true;
								System.out.println("inside time interval");
							}

						}
						if (durationboolean == true) {
							intervalboolean = false;

							if (initialduration == 0) {
								btn_Off.setBackgroundColor(getResources()
										.getColor(R.color.green));
							}

							long intervalue = duration ;
							System.out.println("Duration intervalue   "
									+ intervalue);
							initialduration++;
							if (initialduration % intervalue == 0) {
								btn_Off.setBackgroundColor(getResources()
										.getColor(R.color.red));
								initialduration = 0;
								intervalboolean = true;
								System.out.println("inside time duration");
							}

						}

					}
				}
				myHandler.postDelayed(this, timercount);
			}
		};

		myHandler.postDelayed(updateTask, 0);

	};

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

			finalTime=(selectedHour*3600)+(selectedMinute*60);
			
			
			
			// set current time into textview
			your_time.setText(new StringBuilder().append(pad(hour)).append(":")
					.append(pad(minute)));

			// set current time into timepicker
//			timePicker1.setCurrentHour(hour);
//			timePicker1.setCurrentMinute(minute);

		}
	};

	
	
	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public void getdata() {
		try {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			Cursor testdata = mDbHelper.GetallSchedulingMaster();
			String data = "", data1 = "";
			int i = 1;

			schid = new String[testdata.getCount() + 1];
			schid[0] = "Select Scheduled Task";
			if (testdata.moveToFirst()) {
				do {

					schid[i] = testdata.getString(0);

					i++;
				} while (testdata.moveToNext());
			}

			mDbHelper.close();

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					Simulation.this, android.R.layout.simple_list_item_1, schid);
			spn_sch.setAdapter(arrayAdapter);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GetSingleScheduling(String SchedulingID) {
		try {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			Cursor testdata = mDbHelper.GetSingleScheduling(SchedulingID);
			String data = "", data1 = "";
			int i = 0;

			String dataresult = "";
			if (testdata.moveToFirst()) {
				do {

					tv_taskid.setText(testdata.getString(0));
					tv_devicename.setText(testdata.getString(2));

					tv_starttime.setText(testdata.getString(3));
					tvstoptime.setText(testdata.getString(4));
					tv_interval.setText(testdata.getString(5));
					tv_duration.setText(testdata.getString(6));

		

					startTime = testdata.getString(3);
					StopTime = testdata.getString(4);
					
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
						day="Thrus";
					}

					else if(testdata.getString(7).contains("5"))
					{
						day="Fri";
					}

					else if(testdata.getString(7).contains("6"))
					{
						day="Sat";
					}
					tv_dayorder.setText(day);
					if (testdata.getString(5) != null) {
						interval = Integer.parseInt(testdata.getString(5));
						intervalboolean = true;
					}
					if (testdata.getString(6) != null) {
						duration = Integer.parseInt(testdata.getString(6));
						durationboolean = true;
					}

					if (testdata.getString(5) != null
							&& testdata.getString(6) != null) {
						intervalprogress = true;
					}

					btn_start.setText("Start\n" + startTime);
					btn_Stop.setText("Stop\n" + StopTime);
					btn_on.setText("ON\n" + testdata.getString(5));
					btn_Off.setText("OFF\n" + testdata.getString(6));

					i++;
				} while (testdata.moveToNext());
			}

			mDbHelper.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
