package com.nokarateclass.rpgworld;

import java.io.File;

import com.nokarateclass.rpgworld.editor.CharacterFactory;
import com.nokarateclass.rpgworld.editor.EditorGrid;
import com.nokarateclass.rpgworld.grid.BackgroundGrid;
import com.nokarateclass.rpgworld.grid.MainCharacterGrid;
import com.nokarateclass.rpgworld.io.FileIO;
import com.nokarateclass.rpgworld.ui.ImageGridView;
import com.nokarateclass.rpgworld.world.Zone;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MapEditor extends Activity {

	//variables
	ImageButton btnHero, btnMonster, btnAndroid, btnRock, btnCactus, btnTree, btnSand, btnGrass;
	LinearLayout mLayout;
	ToggleButton btnSim;
	EditorGrid mView;
	MainCharacterGrid mCharacterGrid;
	BackgroundGrid mBackgroundGrid;
	ImageButton[] mCharacters, mBackgrounds; //currently unused but maybe later we'll use it
	int defaultId = 20002;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_editor);
		
		//find views
		btnHero = (ImageButton) findViewById(R.id.btnHero);
		btnMonster = (ImageButton) findViewById(R.id.btnMonster);
		btnAndroid = (ImageButton) findViewById(R.id.btnAndroid);
		btnRock = (ImageButton) findViewById(R.id.btnRock);
		btnCactus = (ImageButton) findViewById(R.id.btnCactus);
		btnTree = (ImageButton) findViewById(R.id.btnTree);
		btnSand = (ImageButton) findViewById(R.id.btnSand);
		btnGrass = (ImageButton) findViewById(R.id.btnGrass);
		btnSim = (ToggleButton) findViewById(R.id.btnSim);
		mLayout = (LinearLayout) findViewById(R.id.editGrid);
		
		//make arrays
		mCharacters = new ImageButton[]{btnHero, btnMonster, btnAndroid, btnRock, btnCactus, btnTree};
		mBackgrounds = new ImageButton[]{btnSand, btnGrass};
		
		//make objects
		int size = 0, rows = 10, cols = 10;
		WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		if(point.x <= point.y){
			size = point.x/cols;
		} else{
			size = point.y/rows;
		}
		mView = new EditorGrid(this, rows, cols, size);
		mCharacterGrid = new MainCharacterGrid(mView);
		mBackgroundGrid = new BackgroundGrid(mView);
		
		//set default background
		CharacterFactory mFac = new CharacterFactory(this);
		if(mFac.makeBackground(defaultId) != null){
			for(int i = 0; i < mBackgroundGrid.getRows(); i++){
				for(int j = 0; j < mBackgroundGrid.getCols(); j++){
					mBackgroundGrid.addCharacter(mFac.makeBackground(defaultId), i, j);
				}
			}
		}
		
		//set listeners
		btnHero.setOnClickListener(new IdListener(CharacterFactory.HERO, EditorGrid.MODE_CHARACTER, "Selected Hero"));
		btnMonster.setOnClickListener(new IdListener(CharacterFactory.MONSTER, EditorGrid.MODE_CHARACTER, "Selected Monster"));
		btnAndroid.setOnClickListener(new IdListener(CharacterFactory.ANDROID, EditorGrid.MODE_CHARACTER, "Selected Android"));
		btnRock.setOnClickListener(new IdListener(CharacterFactory.ROCK, EditorGrid.MODE_CHARACTER, "Selected Rock"));
		btnCactus.setOnClickListener(new IdListener(CharacterFactory.CACTUS, EditorGrid.MODE_CHARACTER, "Selected Cactus"));
		btnTree.setOnClickListener(new IdListener(CharacterFactory.TREE, EditorGrid.MODE_CHARACTER, "Selected Tree"));
		btnSand.setOnClickListener(new IdListener(CharacterFactory.SAND, EditorGrid.MODE_BACKGROUND, "Selected Sand"));
		btnGrass.setOnClickListener(new IdListener(CharacterFactory.GRASS, EditorGrid.MODE_BACKGROUND, "Selected Grass"));
		btnSim.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
				if(isChecked){
					mCharacterGrid.beginBeat(0, 500, 4);
					mView.setMode(EditorGrid.MODE_SIM);
				} else {
					mCharacterGrid.endBeat();
					if(mView.getMode() == EditorGrid.MODE_SIM) mView.setMode(EditorGrid.MODE_NONE);
				}
			}
			
		});
		
		//add views
		mLayout.addView(mView);
		mLayout.invalidate();
	}
	
	public class IdListener implements OnClickListener{

		private int mId;
		private int mMode;
		private String mMessage;
		
		public IdListener(int id, int mode, String text){
			mId = id;
			mMode = mode;
			mMessage = text;
		}
		
		@Override
		public void onClick(View v) {
			mView.setCharacterId(mId);
			mView.setMode(mMode);
			btnSim.setChecked(false);
			Toast.makeText(getApplicationContext(), mMessage, Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_editor, menu);
		return true;
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		mCharacterGrid.endBeat();
	}
	
	@Override
	protected void onStop(){
		super.onStop();
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
		
		if(!mExternalStorageWriteable || !mExternalStorageAvailable) return;
		
		Log.i("Saving","Making new zone...");
		Zone zone = new Zone();
		Log.i("Saving","Zone created.");
		Log.i("Saving","Saving grids...");
		zone.save(mCharacterGrid, mBackgroundGrid);
		Log.i("Saving","Grids saved.");
		Log.i("Saving","Prepping for serialization...");
		zone.prepareForSerialization();
		//ImageGridView view = mCharacterGrid.getView();
		//mCharacterGrid.setView(null);
		//mBackgroundGrid.setView(null);
		File dir = new File(getExternalFilesDir(null), "autosave.zon");
		//String sep = File.separator;
		//FileIO file = new FileIO(dir.toString() + sep +  "zones" + sep + "autosave.zon");
		FileIO file = new FileIO(dir.toString());
		Log.i("Saving","Saving zone...");
		file.save(zone);
		Log.i("Saving","Zone saved.");
		//mCharacterGrid.setView(view);
		//mBackgroundGrid.setView(view);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		mCharacterGrid.endBeat();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(btnSim.isChecked()) mCharacterGrid.beginBeat(0, 500, 4);;
	}
}
