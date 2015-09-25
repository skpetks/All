package com.multiversal;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Menuscreen extends Activity {
int datacount=0;

TextView btn_add,btn_Devicedetails,btn_Scheduling,btn_simulator,btnsave,btnsaveas,btnfileupload;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menuscreen);
		btn_add=(TextView)findViewById(R.id.btn_add);
		btn_Devicedetails=(TextView)findViewById(R.id.btn_Devicedetails);
		btn_simulator=(TextView)findViewById(R.id.btn_simulator);
		btn_Scheduling=(TextView)findViewById(R.id.btn_Scheduling);
		btnfileupload=(Button)findViewById(R.id.btnfileupload);
		btnfileupload=(Button)findViewById(R.id.btnfileupload);
		btnsave=(Button)findViewById(R.id.btnsave);
		btnfileupload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), FileUpload.class);
				startActivity(intent);
					
			}
		});
		
		btnsave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getdata();
			}
		});
		btn_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), AddDevice.class);
				startActivity(intent);
			}
		});
		btn_Devicedetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		btn_simulator.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		
		NewTaskPopup();
	}
});
		btn_Scheduling.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), Scheduling.class);
		startActivity(intent);
	}
});
	}
	@SuppressWarnings("deprecation")
	private void NewTaskPopup() {
		// TODO Auto-generated method stub

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Menuscreen.this, android.R.layout.select_dialog_item);


		  adapter.add("Single Simulation");
		 adapter.add("Cumulative Simulation");



		AlertDialog.Builder builder = new AlertDialog.Builder(Menuscreen.this);
		builder.setTitle("Simulation Option");

		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
			String	mNewTaskType = adapter.getItem(item).toString();
				if (mNewTaskType.contains("Single")) {
					dialog.cancel();
					Intent intent = new Intent(getApplicationContext(), Simulation.class);
					startActivity(intent);
				} else if (mNewTaskType.contains("Cumulative")) {
					Intent intent = new Intent(Menuscreen.this,
							CumulativeSimulation.class);
					startActivity(intent);
				} 


			}
		});
		AlertDialog alert = builder.create();

		alert.setButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		alert.show();
		alert.setCancelable(false);
		alert.setCanceledOnTouchOutside(false);

	}
	public void SaveFile(String filename) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		long testdata1 = mDbHelper.SaveFileMaster(filename);
		
		
		
		mDbHelper.updateSchedulingMaster(""+testdata1);

		mDbHelper.close();
	
		
		Toast.makeText(getApplicationContext(), "New File Saved Sucessfully",
				Toast.LENGTH_SHORT).show();
	}
	
	private void showdialog()
	{
		final Dialog dialog = new Dialog(Menuscreen.this);
		dialog.setContentView(R.layout.file_save);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle("Please enter file name");
		final EditText	file_name = (EditText) dialog.findViewById(R.id.file_name);
		Button	btn_save = (Button) dialog.findViewById(R.id.btn_save);

		Button	btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
		
		btn_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String data=file_name.getText().toString().trim();
				
				if(data.length()!=0)
				{
					SaveFile(data);
					dialog.cancel();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please enter the file name", 1).show();
					
				}
			
			}
		});
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
	

		dialog.show();
	}

	public void getdata() {
		try {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			Cursor testdata = mDbHelper.Getsavefile();
			String data = "", data1 = "";
			 datacount = testdata.getCount();

	
			mDbHelper.close();

			if(datacount!=0)
			{
			//	SaveFile();
				
				showdialog();
				
			}
			else{
				
				
				Toast.makeText(getApplicationContext(), "Sorry All files are updated",
						Toast.LENGTH_SHORT).show();
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
