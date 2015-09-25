package com.multiversal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileUpload extends Activity {
	int datacount = 0;
	ListView lstfile;
	HashMap<String, String> product1;
	HashMap<String, String> selecteditem;
	ArrayList<HashMap<String, String>> datalist1;
	String FileID="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fileupload);
		lstfile = (ListView) findViewById(R.id.lstfile);
		
		lstfile.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {

		        Log.i("Hello!", "Y u no see me?"+position);
		        selecteditem= datalist1.get(position);
		        
		        
		  String dataresult=      selecteditem.get("FileName");
		  FileID=      selecteditem.get("FileID");
		  //      Toast.makeText(getApplicationContext(), ""+dataresult, 1).show();
		        
		        upload();
		    }

		});
		getdata();
	}

	private void upload()
	{
		final Dialog dialog = new Dialog(FileUpload.this);
		dialog.setContentView(R.layout.fileuploaddialouge);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle("Choose option");
	
		Button	btn_Approval = (Button) dialog.findViewById(R.id.btn_Approval);

		Button	btn_Cancel = (Button) dialog.findViewById(R.id.btn_Cancel);
		
		Button	btn_upload = (Button) dialog.findViewById(R.id.btn_upload);
		
		
		Button	btn_Delete = (Button) dialog.findViewById(R.id.btn_Delete);
		
		btn_Approval.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
				UpdateFile(FileID);
				getdata();
			}
		});
		btn_Cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
				
			
			}
		});
		btn_Delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
				DeleteFile(FileID);
				getdata();
			}
		});
		btn_upload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		dialog.show();
	}
	
	
	public class Fileuploadlist extends BaseAdapter {

		private Activity activity;
		private ArrayList<HashMap<String, String>> data;
		private LayoutInflater inflater = null;

		String itemname, itemdesc, itemprice, itemimage;

		public Fileuploadlist(Activity a, ArrayList<HashMap<String, String>> d) {
			activity = a;
			data = d;
			/*
			 * itemname = name; itemdesc = desc; itemprice = price;
			 */
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

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.filedetails, null);

			LinearLayout li_layer = (LinearLayout) vi
					.findViewById(R.id.ly_layer);

			TextView tv_fileid = (TextView) vi.findViewById(R.id.tv_fileid);
			TextView tv_date = (TextView) vi.findViewById(R.id.tv_date);
			TextView tv_approval = (TextView) vi.findViewById(R.id.tv_approval);

			// Button button1 = (Button) vi.findViewById(R.id.button1);
			product1 = new HashMap<String, String>();

			product1 = data.get(position);
			String pickup = product1.get("Approval");
			if (pickup!=null) {

				tv_approval.setBackgroundColor(Color.GREEN);
			}
			else{
				
				tv_approval.setBackgroundColor(Color.RED);
			}
			// txt_itemcode.setText(product1.get("itemcode"));
			tv_fileid.setText(product1.get("FileName"));
			tv_date.setText(product1.get("FileDate"));
			tv_approval.setText(product1.get("Approval"));

			return vi;
		}

	}

	public void DeleteFile(String filename) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		boolean testdata1 = mDbHelper.DeleteFile(filename);
	System.out.println("Boolena "+testdata1);
		mDbHelper.close();
	
		
		Toast.makeText(getApplicationContext(), "File Approval Sucessfully",
				Toast.LENGTH_SHORT).show();
	}
	public void UpdateFile(String filename) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		boolean testdata1 = mDbHelper.UpdateFile(filename);
	System.out.println("Boolena "+testdata1);
		mDbHelper.close();
	
		
		Toast.makeText(getApplicationContext(), "File Approval Sucessfully",
				Toast.LENGTH_SHORT).show();
	}
	public void getdata() {
		try {
			datalist1 = new ArrayList<HashMap<String, String>>();
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			Cursor testdata = mDbHelper.Getfile();
			String data = "", data1 = "";
			datacount = testdata.getCount();
			if (testdata.moveToFirst()) {
				do {
				
					HashMap<String, String> ItemDetailHashMap = new HashMap<String, String>();
					Map<String, String> map1 = new HashMap<String, String>();


					ItemDetailHashMap.put("FileID", testdata.getString(0));
					ItemDetailHashMap.put("FileDate",testdata.getString(1));
					ItemDetailHashMap.put("Approval",testdata.getString(3));
					ItemDetailHashMap.put("FileName",testdata.getString(2));
					datalist1.add(ItemDetailHashMap);
					
					System.out.println("Aprroval "+testdata.getString(3));
				} while (testdata.moveToNext());
			}

			mDbHelper.close();

			if (datacount == 0) {
				Toast.makeText(getApplicationContext(), "Sorry No Data Found ",
						Toast.LENGTH_SHORT).show();

			}
			Fileuploadlist	Ladapter1 = new Fileuploadlist(FileUpload.this, datalist1);
			lstfile.setAdapter(Ladapter1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
