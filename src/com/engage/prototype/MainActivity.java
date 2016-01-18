package com.engage.prototype;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.xml.sax.Parser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;

public class MainActivity extends Activity {

	//FileInputStream result;
	InputStream in;
	LinearLayout linLayout;
	RelativeLayout rLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		//linLayout = new LinearLayout(this);
		rLayout = new RelativeLayout(this);
		LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); 
		//linLayout.setOrientation(LinearLayout.VERTICAL);
		try {
			parse();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); 

		//LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		/*	TextView tv = new TextView(this);
		tv.setText("Text Box");
		tv.setLayoutParams(lpView);
		linLayout.addView(tv);

		Button btn = new Button(this);
		btn.setText("Ï Am a Button");
        linLayout.addView(btn, lpView);*/


		setContentView(rLayout, linLayoutParam);
	}

	public void parse() throws XmlPullParserException, IOException{
		Log.v("XmlParsing", "intial");
		try{
			Log.v("XmlParsing", "1");
			/*FileInputStream result = new FileInputStream(new File(Environment.getExternalStorageDirectory().getPath()+ "/" + "form.xml" ));
			result = new FileInputStream(new File( "/storage/extSdCard/" + "form.xml" ));*/



			in = getApplicationContext().getAssets().open("form.xml");
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);

			readFeed(parser);

			Log.v("XmlParsing", "2");

			return ; 


		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(XmlPullParserException e){
			e.printStackTrace();
		}
		finally {
			in.close();
		}
	}

	private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

		Log.v("XmlParsing", "3");
		int eventType = parser.getEventType();
		TextView tv = null;
		ImageView  image= null;
		int positionX = 0;
		int positionY = 0;
		String txtWidth;

		while (eventType != XmlPullParser.END_DOCUMENT) {
			Log.v("XmlParsing", "4");
			String name = null;

			switch(eventType){


			case XmlPullParser.START_DOCUMENT:
				//Log.v("XmlParsing", "doc start");



				break;

			case XmlPullParser.START_TAG:
				Log.v("XmlParsing", "Start tag");
				name = parser.getName();
				if(name.equalsIgnoreCase("TextBox")){
					Log.v("XmlParsing", "reading tag");
					tv = new TextView(this);
				}else if(tv != null){

					if(name.equalsIgnoreCase("TextBoxText")){
						Log.v("XmlParsing", "reading text");
						tv.setText(parser.nextText());
					}else if(name.equalsIgnoreCase ("FontSize")){

						tv.setTextSize(Integer.parseInt(parser.nextText()));

					}else if(name.equalsIgnoreCase("FontType")){
						if(parser.nextText().equalsIgnoreCase("san_serif")){
							tv.setTypeface(Typeface.SANS_SERIF);
						}else if(parser.nextText().equalsIgnoreCase("san_serifB")){
							tv.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
						}else if(parser.nextText().equalsIgnoreCase("san_serifI")){
							tv.setTypeface(Typeface.SANS_SERIF,Typeface.ITALIC);
						}else if(parser.nextText().equalsIgnoreCase("serifB")){
							tv.setTypeface(Typeface.SERIF,Typeface.BOLD);
						}else if(parser.nextText().equalsIgnoreCase("serifI")){
							tv.setTypeface(Typeface.SERIF,Typeface.ITALIC);
						}else if(parser.nextText().equalsIgnoreCase("serif")){
							tv.setTypeface(Typeface.SANS_SERIF);
						}else if(parser.nextText().equalsIgnoreCase("monospace")){
							tv.setTypeface(Typeface.MONOSPACE);
						}else if(parser.nextText().equalsIgnoreCase("monospaceB")){
							tv.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);
						}else if(parser.nextText().equalsIgnoreCase("monospaceI")){
							tv.setTypeface(Typeface.MONOSPACE,Typeface.ITALIC);
						}

					}
					else if(name.equalsIgnoreCase("TextColor")){

						tv.setTextColor(Integer.parseInt(parser.nextText()));
					}else if(name.equalsIgnoreCase("PositionX")){
						positionX = Integer.parseInt(parser.nextText());

					}else if(name.equalsIgnoreCase("PositionY")){

						positionY = Integer.parseInt(parser.nextText());
					}
				}

				if(name.equalsIgnoreCase("Image")){
					Log.v("XmlParsing", "reading tag");
					image = new ImageView(this);
				}else if(image != null){

					if(name.equalsIgnoreCase("FileName")){
						Log.v("XmlParsing", "reading path");
						File imgFile = new  File(Environment.getExternalStorageDirectory().getPath()+ "/" + parser.nextText());
						Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						image.setImageBitmap(myBitmap);
					}else if(name.equalsIgnoreCase("PositionX")){
						positionX = Integer.parseInt(parser.nextText());

					}else if(name.equalsIgnoreCase("PositionY")){

						positionY = Integer.parseInt(parser.nextText());
					}
					
				}
				break;
			case XmlPullParser.END_TAG:
				Log.v("XmlParsing", "end tag");
				name = parser.getName();

				if(name.equalsIgnoreCase("TextBox")&& tv!= null){


					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lp.setMargins(positionX, positionY, 0, 0);
					tv.setLayoutParams(lp);
					
					rLayout.addView(tv);
					
				}
				if(name.equalsIgnoreCase("Image")&& image!= null){


					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lp.setMargins(positionX, positionY, 0, 0);
					tv.setLayoutParams(lp);
					image.setLayoutParams(lp);
					rLayout.addView(image);
				}
			}
			Log.v("XmlParsing", "next tag");
			eventType = parser.next();
		}

	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
