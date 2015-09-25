package com.multiversal;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddDevice extends Activity {

	EditText edt_device_no, edt_device_name;
	Button btn_add;
	ListView listView1;
	SQLiteDatabase db;
	String database = "Multiversal.db";
	String ctable;
	boolean dbfound = false;
	String text = "<font color=#FE2E2E>Multi</font><font color=#013ADF>versal Tech</font><font color=#04B404>nologies</font>";
	TextView leftIcon;
	String[] DeviceName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_device);
		listView1 = (ListView) findViewById(R.id.listView1);
		edt_device_no = (EditText) findViewById(R.id.edt_device_no);
		edt_device_name = (EditText) findViewById(R.id.edt_device_name);
		btn_add = (Button) findViewById(R.id.btn_add);
		leftIcon = (TextView) findViewById(R.id.leftIcon);

		getdata();
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String Devicename = edt_device_name.getText().toString().trim();
				if (Devicename.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Please Enter the Device Name", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				SaveDevicename(Devicename);
				edt_device_name.setText("");
			}
		});
	}

	public void getdata() {
		try {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			ArrayList<HashMap<String, String>> datalist = new ArrayList<HashMap<String, String>>();
			Cursor testdata = mDbHelper.getdevicedetails();
			String data = "", data1 = "";
			int i = testdata.getCount() + 1;
			int x = 0;
			DeviceName = new String[testdata.getCount()];
			if (testdata.moveToFirst()) {
				do {

					DeviceName[x] = testdata.getString(0) + "-"
							+ testdata.getString(1);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("Deviceid", testdata.getString(0));
					map.put("Devicename", testdata.getString(1));

					datalist.add(map);
					x++;
				} while (testdata.moveToNext());
			}

			mDbHelper.close();


			LazyAdapter	adapter = new LazyAdapter(AddDevice.this, datalist);

			listView1.setAdapter(adapter);
			
			
			
			
			
			edt_device_no.setText("" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<HashMap<String, String>> data;
		private LayoutInflater inflater = null;
		HashMap<String, String> dataparser;




		public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
			activity = a;
			data = d;

			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		public void clear() {
			// TODO Auto-generated method stub

		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.deviceitem, null);
			dataparser = new HashMap<String, String>();

			dataparser = data.get(position);
			
		
			TextView Deviceid = (TextView) vi
					.findViewById(R.id.tv_Deviceid);
			
			
			Deviceid.setText(dataparser.get("Deviceid"));
			TextView tv_serial = (TextView) vi		.findViewById(R.id.tv_devicename);
			tv_serial.setText(dataparser.get("Devicename"));


			return vi;
		}

	}
	
	public void SaveDevicename(String Name) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		Cursor testdata1 = mDbHelper.getdevicedetails( Name);
		if(testdata1.getCount()>0)
		{
			Toast.makeText(getApplicationContext(), "Sorry Device Name is Already exist",
					Toast.LENGTH_SHORT).show();
		}
		else{
		boolean testdata = mDbHelper.SaveDevice(Name);
		Toast.makeText(getApplicationContext(), "New Device is Added",
				Toast.LENGTH_SHORT).show();
		mDbHelper.close();
		}
		getdata();
	}
}
