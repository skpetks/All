package com.multiversal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
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
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CumulativeSimulation extends Activity {
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
	ArrayList<String> Startarray;
	ArrayList<String> Stoparray;
	ArrayList<String> Devicename;
	ArrayList<String> intervalva;
	ArrayList<String> durationdas;
	ArrayList<String> booleanstats;
	ArrayList<Integer> runninginitialinterval;
	ArrayList<Integer> runninginitiaduration;
	ArrayList<String> Overallstatus;
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
	LinearLayout layoutscroll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_csimulation);
		spn_sch = (Spinner) findViewById(R.id.spn_sch);
		btn_your_time = (Button) findViewById(R.id.btn_your_time);
		sys_time = (EditText) findViewById(R.id.sys_time);

		btn_play = (Button) findViewById(R.id.btn_play);
		btn_fast = (Button) findViewById(R.id.btn_fast);
		btn_re = (Button) findViewById(R.id.btn_re);
		layoutscroll = (LinearLayout) findViewById(R.id.layoutscroll);
		your_time = (EditText) findViewById(R.id.your_time);

		sys_time.setText("1000");
		timercount = 1000;
		getdata();
		btn_your_time.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finalTime = 0L;

				initialinterval = 0;

				initialduration = 0;
				your_time.setText("00:00:00");
			}
		});
		btn_play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(startTime.length()==0)
				{
					Toast.makeText(getApplicationContext(), "Please Select task ", 1).show();
					return;
				}
				if (togglecount % 2 == 0) {
					@SuppressWarnings("deprecation")
					final Drawable drawableTop = getResources().getDrawable(
							R.drawable.pause);
					btn_play.setCompoundDrawablesWithIntrinsicBounds(null,
							drawableTop, null, null);
					showtimer();
				} else {
					@SuppressWarnings("deprecation")
					final Drawable drawableTop = getResources().getDrawable(
							R.drawable.play);
					btn_play.setCompoundDrawablesWithIntrinsicBounds(null,
							drawableTop, null, null);
					myHandler.removeCallbacks(updateTask);
				}
				togglecount++;

			}
		});
		
		your_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				showDialog(TIME_DIALOG_ID);
			}
		});
		spn_sch.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {

					if (arg2 != 0) {

						String dayorder = schid[arg2];

						String order = "";

						if (dayorder.contains("Sunday")) {
							order = "0";
						}
						if (dayorder.contains("Monday")) {
							order = "1";
						}
						if (dayorder.contains("Tuesday")) {
							order = "2";
						}
						if (dayorder.contains("Wednesday")) {
							order = "3";
						}
						if (dayorder.contains("Thrusday")) {
							order = "4";
						}
						if (dayorder.contains("Friday")) {
							order = "5";
						}
						if (dayorder.contains("Saturday")) {
							order = "6";
						}

						GetSingleScheduling(order);
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
				System.out.println(Startarray.size());

				for (int i = 0; i < Startarray.size(); i++) {
					View v = null;
					v = layoutscroll.getChildAt(i);
					try{
					
						if (Startarray.get(i).contains(runningtime)) {					
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.on));
						booleanstats.set(i, "true");
					}}
					catch(Exception ex)
					{
						System.out.println(ex.toString());
					}
		

					try{
					
					if (Stoparray.get(i).contains(runningtime)) {
						booleanstats.set(i, "none");
						v = layoutscroll.getChildAt(i);
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.off));
					}
					}
					catch(Exception ex)
					{
						System.out.println(ex.toString());
					}
					
					System.out.println(booleanstats.get(i).toString()+"  :              boolean stasfffffffffff");
					if(Overallstatus.get(i).contains("True"))
					{
					//On Timer Interval 
						if(	booleanstats.get(i).contains("true"))
						{
							v = layoutscroll.getChildAt(i);
							System.out.println("i  "+ minvalue);
										int currentrunning=Integer.parseInt(intervalva.get(i).toString());								
										if(runninginitialinterval.get(i)==0)
										{
											v.setBackgroundDrawable(getResources().getDrawable(R.drawable.on));
																}
										int valu=runninginitialinterval.get(i)+1;
										runninginitialinterval.set(i,valu);
									

										System.out.println("Current interval time "+currentrunning+"   Running interval time :"+runninginitialinterval.get(i));
							
										if(runninginitialinterval.get(i)>0)
										{
										if (runninginitialinterval.get(i) % currentrunning == 0) {
											
									
											runninginitialinterval.set(i, 0);
											runninginitiaduration.set(i, 0);
											booleanstats.set(i,"false");
										//	booleanstats.get(i).replace("true", "false");
											System.out.println(booleanstats.get(i).toString()+"  :              boolean stasfffffffffff");
											System.out.println("inside time interval");
										//	v.setBackgroundDrawable(getResources().getDrawable(R.drawable.timer));
										}
										}
									
						}
						//Off  Timer Interval Starts 
						 if(booleanstats.get(i).contains("false"))
							{v = layoutscroll.getChildAt(i);
								int currentrunning=Integer.parseInt(durationdas.get(i).toString());	
								System.out.println("Current durationdas time "+currentrunning);
								if(runninginitiaduration.get(i)==0)
								{
									v.setBackgroundDrawable(getResources().getDrawable(R.drawable.timer));
								}
								int valu=runninginitiaduration.get(i)+1;
								runninginitiaduration.set(i,valu);
							
								System.out.println("Running Duration time :"+runninginitiaduration.get(i));
								
								
								if(runninginitiaduration.get(i)>0)
								{
								if (runninginitiaduration.get(i) % currentrunning == 0) {
									v.setBackgroundDrawable(getResources().getDrawable(R.drawable.on));
																runninginitiaduration.set(i, 0);
									booleanstats.set(i, "true");
									System.out.println("inside time duration");
								}
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
			your_time.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)).append(":00"));

		

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
			Cursor testdata = mDbHelper.GetSingleDayorderScheduling();
			String data = "", data1 = "";
			int i = 1;

			schid = new String[testdata.getCount() + 1];
			schid[0] = "Please Select Operation Day";
			if (testdata.moveToFirst()) {
				do {

					String dayorder = "";
					if (testdata.getString(0).contains("0")) {
						dayorder = "Sunday";
					}

					if (testdata.getString(0).contains("1")) {
						dayorder = "Monday";
					}

					if (testdata.getString(0).contains("2")) {
						dayorder = "Tuesday";
					}

					if (testdata.getString(0).contains("3")) {
						dayorder = "Wednesday";
					}

					if (testdata.getString(0).contains("4")) {
						dayorder = "Thrusday";
					}

					if (testdata.getString(0).contains("5")) {
						dayorder = "Friday";
					}

					if (testdata.getString(0).contains("6")) {
						dayorder = "Saturday";
					}
					schid[i] = dayorder;
					i++;
				} while (testdata.moveToNext());
			}
			mDbHelper.close();
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					CumulativeSimulation.this,
					android.R.layout.simple_list_item_1, schid);
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
			Cursor testdata = mDbHelper
					.GetSingledayorderScheduling(SchedulingID);
			String data = "", data1 = "";
			int i = 0;
			Startarray = new ArrayList<String>();
			Stoparray = new ArrayList<String>();
			Devicename = new ArrayList<String>();
			intervalva = new ArrayList<String>();
			durationdas = new ArrayList<String>();
			booleanstats=new ArrayList<String>();
			Overallstatus=new ArrayList<String>();
runninginitialinterval= new ArrayList<Integer>();
		runninginitiaduration= new ArrayList<Integer>();
	 Overallstatus= new ArrayList<String>();
			String dataresult = "";
			int countdata = layoutscroll.getChildCount();
			layoutscroll.removeAllViewsInLayout();
			if (testdata.moveToFirst()) {
				do {

					Button bt = new Button(this);

					startTime = testdata.getString(3);
					StopTime = testdata.getString(4);
					Startarray.add(startTime);
					Stoparray.add(StopTime);
					booleanstats.add("none");
				
					runninginitialinterval.add(0);
					runninginitiaduration.add(0);
					Devicename.add(testdata.getString(2));
					
					System.out.println("Inter  :"+testdata.getString(5));
					System.out.println("dura  :"+testdata.getString(6));
					
					if (testdata.getString(5)   != " ") {
				//		interval = Integer.parseInt();
						intervalva.add(testdata.getString(5));
					}
					else{
						intervalva.add("0");
					}
					if (testdata.getString(6) != " ") {
//						duration = Integer.parseInt(testdata.getString(6));
						durationdas.add(testdata.getString(6));
					}
					else{
						durationdas.add("0");
					}

					if (testdata.getString(5) != null
							|| testdata.getString(6) != null) {
						intervalprogress = true;
						Overallstatus.add("True");
					}
					else
					{
						Overallstatus.add("false");
					}
					bt.setTextSize(12);    
				//	bt.setText(testdata.getString(2) + "--START TIME :" + startTime + "--STOP TIME :"+ StopTime+"--Interval :"+interval+"MIN--Duration :"+duration+"MIN");
					
					bt.setText(testdata.getString(2) + "--" + startTime + "--"+ StopTime+"--"+testdata.getString(5)+"Sec  "+testdata.getString(6)+"Sec");
					
					bt.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					layoutscroll.addView(bt);

				} while (testdata.moveToNext());
			}

			mDbHelper.close();
			Toast.makeText(getApplicationContext(), "" + Startarray.size(), 1)
					.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
