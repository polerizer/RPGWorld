package com.nokarateclass.rpgworld;

import java.io.File;

import com.nokarateclass.rpgworld.characters.*;
import com.nokarateclass.rpgworld.editor.EditorGrid;
import com.nokarateclass.rpgworld.grid.BackgroundGrid;
import com.nokarateclass.rpgworld.grid.CharacterGrid;
import com.nokarateclass.rpgworld.grid.Location;
import com.nokarateclass.rpgworld.grid.MainCharacterGrid;
import com.nokarateclass.rpgworld.io.FileIO;
import com.nokarateclass.rpgworld.ui.ImageGridView;
import com.nokarateclass.rpgworld.world.Zone;
import com.nokarateclass.test.TestZone;
//import com.nokarateclass.rpgworld.characters.CharacterActor;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
//import android.content.Context;
import android.graphics.Point;
import android.util.Log;
//import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class Grid extends Activity {

	MainCharacterGrid grid;
	BackgroundGrid bgGrid;
	Button btnEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);
		btnEdit = (Button) findViewById(R.id.btnOpenEditor);
		btnEdit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startEditor();
			}
			
		});
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		LinearLayout holder = new LinearLayout(this);
		holder.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		int size = 0, rows = 10, cols = 10;
		WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		if(point.x <= point.y){
			size = point.x/cols;
//			rows = point.y/size;
		} else{
			size = point.y/rows;
//			cols = point.x/size;
		}
		ImageGridView view = new ImageGridView(this, rows, cols, size);
		view.setBackground(getResources().getDrawable(R.drawable.gray));
		holder.addView(view);
		layout.addView(holder);
		
		Log.i("Loading","Loading zone...");
		Zone zone = loadZone();
		Log.i("Loading", zone.toString());
		bgGrid = new BackgroundGrid(view);
		grid = new MainCharacterGrid(view);
		Log.i("Loading","Loading grids...");
		zone.loadBackgroundGrid(this, bgGrid);
		zone.loadCharacterGrid(this, grid);

		Log.i("Loading","Grids loaded. Starting simulation.");
		grid.beginBeat();
		//TestZone.populateZone(grid);
		//TestZone.setBackground(bgGrid);
		
		//TODO: remove unnecessary Log calls from code to speed things up
	}

	public void startEditor(){
		Intent intent = new Intent(this, MapEditor.class);
		startActivity(intent);
	}
	
	public Zone loadZone(){
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		if(!mExternalStorageWriteable || !mExternalStorageAvailable) return new Zone();
		
		File dir = new File(getExternalFilesDir(null), "autosave.zon");
		FileIO file = new FileIO(dir.toString());
		Zone zone = file.<Zone>readType();
		if(zone == null) return new Zone();
		else return zone;
	}
	
	private void fill(){
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		LinearLayout holder = new LinearLayout(this);
		holder.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		int size = 0, rows = 10, cols = 10;
		WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		if(point.x <= point.y){
			size = point.x/cols;
//			rows = point.y/size;
		} else{
			size = point.y/rows;
//			cols = point.x/size;
		}
		ImageGridView view = new ImageGridView(this, rows, cols, size);
		AndroidCharacter character = new AndroidCharacter(this);
		view.setBackground(getResources().getDrawable(R.drawable.gray));
		holder.addView(view);
		layout.addView(holder);
		bgGrid = new BackgroundGrid(view);
		grid = new MainCharacterGrid(view);
		grid.addCharacter(character, 3, 3);
		grid.beginBeat(0,1000,4);
		character.getLocation().setDirection(Location.SOUTH);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		grid.endBeat();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		grid.endBeat();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		grid.beginBeat();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}

}
