package com.example.phoneclient;

import java.io.DataOutputStream;

import android.os.Vibrator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.Locale;

import com.michaelmarner.gestures.Gesture;
import com.michaelmarner.gestures.GestureListener;
import com.michaelmarner.gestures.drawingtest;

import android.R.color;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;


public class MainActivity extends Activity implements GestureListener,OnItemSelectedListener  {
	public Button privateButton,setupButton; 
	public Socket CommandSocket,receivingSocket,sendingSocket,sendMsgSocket; 
	public OutputStream outputStream,outputStreamSendSelectAndTagID;//outputStream is belong to command_Socket;
	DataOutputStream doDataOutputStream;
	InputStream inputStream,inputMsgStream;
	public EditText tagID;
	Intent intent;
	String data;
	String[] Msg = new String[2];
	byte[] bytes;
	int i;
	BitmapView bitmapView;
	int reader;
	InputStream input;
	Bitmap bitmap;
	Bitmap scaledBitmap;
	Bitmap cropBitmap;
	private Bitmap bMap;
	Bitmap bMapScaled;
	int byteSize;
	String X;
	String Y;
	float screenWidth = 0;
	float screenHeight = 0;
	float totalWidth = 0;
	float totalHeight = 0;
	int temx = 1500;
	int temy = 800;
	ImageView  image;
	Vibrator vibrator;
	TextToSpeech tSpeech;
	Button shartButton;
	Thread receiveImageThread,receiveMsgThread,receiveThread,sendingThread;
	drawingtest drawingtestLayout;
	LinearLayout mAinLayout;
	LinearLayout textViewLayout;
	LinearLayout gestureInterfaceLayout;
	LinearLayout dropdownlistLayout;
	LinearLayout paragraphSelectLayout;
	TextView paraTextView;
	Spinner spinner;
	String[] pathStrings;
	Button chineseButton,spanishButton,germanButton;
	String textviewTextChinese,textviewTextSpanish,textviewTextGerman;
	Button goToMainScreenButtonInTranslationView,goToMainScreenInDropDownList;
	int iCurrentSelect;
	
	public void sendmessage()
	{
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		startActivity(intent);

	}
	public void TranslateMessages(String Msg)
	{
	    Log.d("AsyncTask","Received Msg= "+Msg);
	    int intIndex = Msg.indexOf("end/>");
	    System.out.println("The intIndex===="+intIndex);
	    String newMessageS=Msg.substring(0, intIndex+5);
	    Log.d("AsyncTask","Received Msg= "+newMessageS);
	    if (newMessageS.endsWith("/>"))
	    {
	    	String[] msgPart=newMessageS.split("/>");
	        Log.d("AsyncTask","Multi part message Detected, count="+msgPart.length);            	
	    	for (int i=0;i<msgPart.length;i++) {
	            Log.d("AsyncTask","Msg#: "+ msgPart[i]);
	        	TranslateMessage(msgPart[i]);
			}
	    }
	    else //invalid message
	    	return;
	}
	public String translate(String content,String language)
	{
		String hello = null;

		Translate.setClientId("PhoneClient");
		Translate.setClientSecret("zheng.huang@ndsu.edu");
		if(language.equals("French"))
		{
			try {
			hello = Translate.execute(content, Language.FRENCH);
			//System.out.println(hello);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if (language.equals("Chinese"))
		{
			try {
				hello = Translate.execute(content, Language.CHINESE_SIMPLIFIED);
				//System.out.println(hello);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (language.equals("Spanish"))
		{
			try {
				hello = Translate.execute(content, Language.SPANISH);
				//System.out.println(hello);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (language.equals("German"))
		{
			try {
				hello = Translate.execute(content, Language.GERMAN);
				//System.out.println(hello);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hello;
	}
	
	public void TranslateMessage(String Msg)
	{
		if (Msg.length()==0) return;
		
		String[] inMsg=Msg.split(";");
		if(inMsg[0].startsWith("speech"))
		{
			speakOut(inMsg[3]);
			
		}
		else if (inMsg[0].startsWith("vibrator"))
		{
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        	v.vibrate(200);
		}
		else if (inMsg[0].startsWith("success"))
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				    mAinLayout.setVisibility(View.GONE);
				    gestureInterfaceLayout.setVisibility(View.VISIBLE);
				    
					
				
					 
		    System.out.println("get the visbility: "+drawingtestLayout.getVisibility());
				}
			});
		}
		else if (inMsg[0].startsWith("failure"))
		{
			//
		}
		else if (inMsg[0].startsWith("dropdownList"))
		{
			String[] pathStrings ={"My favorite","My address","My hobby"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,pathStrings);
		    spinner.setAdapter(adapter);

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					mAinLayout.setVisibility(View.GONE);
					dropdownlistLayout.setVisibility(View.VISIBLE);

				}
			});
			
			//onitemSelected
		}
		else if(inMsg[0].startsWith("textbox"))
		{
			final String text =inMsg[3];
			//translate(inMsg[3]);
			textviewTextChinese=translate(inMsg[3],"Chinese");
			textviewTextGerman=translate(inMsg[3], "German");
			textviewTextSpanish=translate(inMsg[3], "Spanish");
		

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					mAinLayout.setVisibility(View.GONE);
					paraTextView.setText(text);
					paraTextView.setTextSize(16);
					
					paragraphSelectLayout.setVisibility(View.VISIBLE);

				}
			});
		}

		
		
	}
	private void speakOut(String txt ) {
        //Get the text typed
        //If no text is typed, tts will read out 'You haven't typed text'
        //else it reads out the text you typed
       if (txt.length() > 0) {
           tSpeech.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
			
       }

   }
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
	vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
	shartButton = (Button)findViewById(R.id.Share_MOde);
	
	goToMainScreenButtonInTranslationView =(Button)findViewById(R.id.goToMainScreen);
	goToMainScreenButtonInTranslationView.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			paragraphSelectLayout.setVisibility(View.GONE);
			mAinLayout.setVisibility(View.VISIBLE);

			
			
		}
	});
	
	goToMainScreenInDropDownList =(Button)findViewById(R.id.goToMainScreenInDropDownList);
	goToMainScreenInDropDownList.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dropdownlistLayout.setVisibility(View.GONE);
			mAinLayout.setVisibility(View.VISIBLE);
		}
	});
	
	textViewLayout=(LinearLayout)findViewById(R.id.textViewLayout);
	drawingtestLayout=(drawingtest)findViewById(R.id.drawingtest1);
	
	gestureInterfaceLayout=(LinearLayout)findViewById(R.id.GestureInterface);
	gestureInterfaceLayout.setVisibility(View.GONE);
	
	dropdownlistLayout=(LinearLayout)findViewById(R.id.dropdownLayout);
	dropdownlistLayout.setVisibility(View.GONE);
	
	paragraphSelectLayout=(LinearLayout)findViewById(R.id.ParagraphTranslation);
	paraTextView=(TextView)findViewById(R.id.paragraphView);
	chineseButton =(Button)findViewById(R.id.Chinese);
	spanishButton=(Button)findViewById(R.id.Spanish);
	germanButton=(Button)findViewById(R.id.German);
	
	
	
	
	chineseButton.setOnClickListener(new View.OnClickListener() {
		
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
              
              paraTextView.setText(textviewTextChinese);
			//paraTextView.setText("我们的目的是丰富的人，我们接触到的生活。通过提供最高性能的金融产品和服务，降低风险，增加资产，我们帮助个人和雇主履行他们的职责，并建立更好的明天。我们的文化是基于诚信和公平交易的坚定不移的信念，对待我们的客户和对方以尊严和尊重......我们采取审慎的风险，共同努力，以确保我们的成功和盈利能力在未来......我们努力不断增强的可访问性，专业性，性能和深度以及我们与客户的长期关系，协商的质量信誉。我们努力加以重视作为一个行业领导者在客户满意度，销售增长，产品性能，财务实力和盈利能力。");
		}
	});
	spanishButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			paraTextView.setText(textviewTextSpanish);
			//paraTextView.setText("Nuestro propósito es enriquecer las vidas de las personas que tocamos. Al proporcionar mayor rendimiento de productos y servicios que reducen los activos de riesgo y aumentar las financieras, ayudamos a las personas y los empleadores cumplan con sus responsabilidades y construir un mañana mejor. Nuestra cultura se basa en una creencia inquebrantable en la integridad y justas relaciones, tratar a nuestros clientes y con los demás con dignidad y respeto ... Tomamos riesgos prudentes y trabajar juntos para asegurar el éxito y la rentabilidad en el futuro ... Trabajamos duro para mejorar continuamente nuestra reputación para la accesibilidad, la profesionalidad, el rendimiento y la profundidad y la calidad de nuestras relaciones de consulta a largo plazo con los clientes ... Nos esforzamos en ser valorado como una empresa líder en la satisfacción del cliente, el crecimiento de las ventas, el rendimiento del producto, la solidez financiera y rentabilidad.");
		}
	});
	germanButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			paraTextView.setText(textviewTextGerman);
			//paraTextView.setText("Unser Ziel ist es, das Leben von Menschen, die wir berühren, zu bereichern. Durch die Bereitstellung von leistungsstärksten Finanzprodukte und Dienstleistungen, die Risiken und Steigerung Vermögen zu reduzieren, helfen wir Einzelpersonen und Arbeitgeber ihrer Verantwortung nachkommen und bauen besser morgen. Unsere Kultur basiert auf einem unerschütterlichen Glauben an die Integrität und fairen Umgang basiert, die Behandlung unserer Kunden und einander mit Würde und Respekt ... Wir nehmen umsichtige Risiken und zusammenarbeiten, um den Erfolg und die Rentabilität in der Zukunft zu gewährleisten ... Wir arbeiten hart daran, verbessern kontinuierlich unsere Reputation für die Barrierefreiheit, Professionalität, Leistung und die Tiefe und Qualität unserer langfristigen beratende Beziehungen zu Kunden ... Wir bemühen uns als Branchenführer in der Kundenzufriedenheit, Umsatzwachstum, Produktleistung, Finanzkraft und bewerten Rentabilität.");
		}
	});
	
	spinner=(Spinner)findViewById(R.id.mySpinner);
	spinner.setOnItemSelectedListener(this);
	
	

	
	mAinLayout=(LinearLayout)findViewById(R.id.MainLinear);
	//drawingtestLayout.setVisibility(View.GONE);
	drawingtestLayout.addListener(this);
	setupButton = (Button)findViewById(R.id.Setup_Mode);// setup button is used to setup  command connection with the server
	setupButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new Connection().execute();//start command_Socket
			setupButton.setEnabled(false);
			//collect the tagID at the Edit field and add the mode name
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			MsgFormate command = MsgFormate.newSetup(tagID.getText().toString(), "Setup", "");
			System.out.println("Comand="+command.getMessageText());//debug the TagID
			byte[] arrayByte = command.getMessageText().getBytes();// convert the TagID into array byte
			
			try {
				outputStream.write(arrayByte);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	});
	
	shartButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			tSpeech.speak("server is connected", TextToSpeech.QUEUE_FLUSH, null);
			vibrator.vibrate(500);
			//new Connection().execute();
		}
	});
		tSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		tSpeech.setLanguage(Locale.ENGLISH);
		
	}
});
		image = (ImageView)findViewById(R.id.Image_test);		
		privateButton = (Button)findViewById(R.id.private_Mode);	
		
		tagID=(EditText)findViewById(R.id.tag_id);
		bitmapView=(BitmapView)findViewById(R.id.Image_test);

//use onTouchListener to send the Point of mobile to the server
bitmapView.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getAction()== event.ACTION_DOWN)
		{
					MsgFormate msg = MsgFormate.newObjectClick(
							tagID.getText().toString(),
							"objectClick",
							Float.toString(event.getX()) + " "
									+ Float.toString(event.getY()));
					System.out.println("objectclick msg="
							+ msg.getMessageText());
					bytes = msg.getMessageText().getBytes();

		//vibrator.vibrate(1000);

					try {
						outputStreamSendSelectAndTagID.write(bytes);// tell the
																	// server
																	// object is
																	// select
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				return false;
			}
		});



privateButton.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	//new Connection().execute();// connect to command socket
	tSpeech.speak("You are at the cross device mode", TextToSpeech.QUEUE_FLUSH, null);
	vibrator.vibrate(500);
	String command = "crossDevice";
	System.out.println("Comand="+command);
	byte[] arrayByte = command.getBytes();
	
	try {
		outputStream.write(arrayByte);
	} catch (Exception e) {
		// TODO: handle exception
	}
	receiveImageThread.start();
	receiveMsgThread.start();
	receiveThread.start();
	sendingThread.start();
	

	
	}
});

//this thread is used for receiving the Image 
receiveImageThread = new Thread (new Runnable() {
	private byte[] data1;

	public void setByteSize(int size) {
		data1 = new byte[size];

	}

	public byte[] getByteSize() {
		return data1;

	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			receivingSocket = new Socket("134.129.125.177",42342);
			input = receivingSocket.getInputStream();
			System.out.println("receiveImageThread is connected.");
			System.out.println("getInputStream : port 33333 and for receiving the image");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		while (true) {
			int totalBytesRead = 0;
			int chunkSize = 0;
			int tempRead = 0;
			String msg = null;
			// byte[] data = null;

			byte[] tempByte = new byte[1024 * 1024 * 4];

			// read from the inputstream
			try {
				tempRead = input.read(tempByte);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// tempbyte has x+4 byte

			System.out.println("Fist time read:" + tempRead);
			// convert x+4 byte into String

			String message = new String(tempByte, 0, tempRead);
			//cut the from 0 to 6 as message size
			msg = message.substring(0, 6);
			int msgSize = Integer.parseInt(msg);
			
			System.out.println("msgSize="+msgSize);
			data1 = new byte[msgSize];
			// for(int i =0;i<tempRead-4;i++)
			// {
			// data[i]=tempByte[i+4];
			// }
			
			//put the each bit into real imageByte array: data
			//becasue the first 6 is the message size, so we only put the after 6 bit
			for (int i = 0; i < msgSize; i++) {
				data1[i] = tempByte[i + 6];
			}
			// cut the header into imageMsg
			// String imageMsg = new String(tempByte, 4, tempRead - 4);
			// convert string into byte
			System.out.println("message head:" + msg);
			byteSize = Integer.parseInt(msg);
			System.out.println("ByteSize:" + byteSize);
			// convert String into byte[]

			totalBytesRead = tempRead - 6;
			System.out.println("total Byte Read=" + totalBytesRead);

			// get string for the size of image

			try {

				while (totalBytesRead != getByteSize().length) {
					System.out.println("data length:"
							+ getByteSize().length);
					chunkSize = input.read(getByteSize(), totalBytesRead,
							getByteSize().length - totalBytesRead);
					System.out.println("chunkSize is " + chunkSize);

					totalBytesRead += chunkSize;
					// System.out.println("Total byte read "
					// + totalBytesRead);

				}

				System.out.println("Complete reading - total read = "
						+ totalBytesRead);
			} catch (Exception e) {

			}
			// convert the data into bitmap
			bitmap = BitmapFactory.decodeByteArray(data1, 0, data1.length);
			// scale the bitmap fit the screen
			bMap = Bitmap.createScaledBitmap(bitmap, 1920, 1100, true);
		
			
		}
		
	}
});
//receiveImageThread.start();

receiveMsgThread = new Thread (new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			sendMsgSocket = new Socket("134.129.125.177" ,22222);
			inputMsgStream= sendMsgSocket.getInputStream();
			System.out.println("receivingMsgThread is connected.");
			byte[] msgbytes = new byte[1024];
			while (inputMsgStream.read(msgbytes,0,msgbytes.length)!=0)
			{
				
				String msg = new String(msgbytes);
				TranslateMessages(msg);
				
	
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
});
//receiveMsgThread.start();



//this thread is used for receiving the coordinate formate
receiveThread = new Thread(new Runnable() {
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			receivingSocket = new Socket("134.129.125.177" ,33342);
			inputStream= receivingSocket.getInputStream();
			System.out.println("receivingSocket is connected.");
			
			String message=null;
			while (true) {
				bytes = new byte[2046];
				try {
					reader = inputStream.read(bytes, 0, bytes.length);
					System.out.println("how many bits readed: "+reader);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					message = new String(bytes, 0, reader);
				} catch (Exception e) {
					// TODO: handle exception
				  System.out.println(e.toString());
				}
				
				System.out.println(message);
				Msg = trim(message);
				for (String nString : Msg) {

					if (nString.startsWith("X")) {
						X = nString;
						System.out.println(X);
						String xkString = X.substring(2);// remove the "X=" ,return the pure number(string)
						screenWidth = Float.parseFloat(xkString);// convert pure number into float(tabletop coordinate)
						temx = Integer
								.parseInt(X.substring(2).split("\\.")[0]);
						// in order to prevent out of border
						if (temx >= 1580) {
							temx = 1580;
						}
						System.out.println(temx); // print/get the coordinate of X

						// x1=Float.parseFloat(X.substring(2));
						// x1 = Float.parseFloat(X);
					} else if (nString.startsWith("Y")) {
						Y = nString;

						System.out.println(Y);
						String ykString = Y.substring(2);
						screenHeight = Float.parseFloat(ykString);// tabletop coordinate
						temy = Integer
								.parseInt(Y.substring(2).split("\\.")[0]);
						if (temy >= 865) {
							temy = 865;
						}
						System.out.println(temy);
						
					}

					if (bitmap != null)
						runOnUiThread(new Runnable() {
							public void run() {

								bMapScaled = Bitmap.createBitmap(bMap,
										temx, temy, 170,130);// scale the bMap which is from 8080
								
								image.setImageBitmap(bMapScaled);


								
							}
						});

				}
			}
			

				
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
});
//receiveThread.start();

// this thread suppose to send the TagID and objectSelect to the tabletop,
// but based on new requirement, I need make it only send the objectSelect only.
sendingThread= new Thread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			sendingSocket = new Socket("134.129.125.177",52342);
			
			outputStreamSendSelectAndTagID=sendingSocket.getOutputStream();
			System.out.println("sendingThread is connected.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
});
//sendingThread.start();
		
	}
	public class Connection extends AsyncTask{

		@Override
		protected Object doInBackground(Object... arg0) {
			
			// TODO Auto-generated method stub
			try {
				CommandSocket = new Socket("134.129.125.177",62442);
				//receivingSocket = new Socket("134.129.125.126",52312);
				//sendingSocket = new Socket("134.129.125.126",42322);
			outputStream = CommandSocket.getOutputStream();
//			String command = "1";
//			byte[] arrayByte = command.getBytes();
//			
//			outputStream.write(arrayByte);
//			
//			
			System.out.println("send succussful");
			} catch (Exception e) {
				// TODO: handle exception
			}
	

			
			return null;
		}
		
	}
	
	

public String[] trim(String msg)

	{
		String[] Msg = msg.split(" ");

		return Msg;

	}

@Override
public void handleGesture(Gesture g) {
	// TODO Auto-generated method stub
	
}
@Override
public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	byte[] arrayByte=null;
	if(iCurrentSelect!=arg2)
	{
	TextView textView =(TextView)arg1;
	
	
	Toast.makeText(this, "You Selected "+textView.getText(), Toast.LENGTH_SHORT).show();
	
	if(textView.getText().toString().equals("My hobby"))
	{
	MsgFormate command = MsgFormate.newItemSelect(tagID.getText().toString(), textView.getText().toString(), "My hobby: play basketball, listen music, travel");
	System.out.println("Comand="+command.getMessageText());//debug the TagID
	arrayByte = command.getMessageText().getBytes();// convert the TagID into array byte
	}
	
	else if(textView.getText().toString().equals("My address"))
	{
	MsgFormate command = MsgFormate.newItemSelect(tagID.getText().toString(), textView.getText().toString(), "My address: CS department,North Dakota State University,Fargo,ND");
	System.out.println("Comand="+command.getMessageText());//debug the TagID
	arrayByte = command.getMessageText().getBytes();// convert the TagID into array byte
	}
	else if(textView.getText().toString().equals("My favorite"))
	{
	MsgFormate command = MsgFormate.newItemSelect(tagID.getText().toString(), textView.getText().toString(), "My favorite: Read books and chatting");
	System.out.println("Comand="+command.getMessageText());//debug the TagID
	arrayByte = command.getMessageText().getBytes();// convert the TagID into array byte
	}
	
	iCurrentSelect=arg2;
	try {
		outputStreamSendSelectAndTagID.write(arrayByte);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		
		e.printStackTrace();
	}
	}
	
}
@Override
public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
	
}
	

}
