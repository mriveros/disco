package com.android4dev.navigationview;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

 @SuppressWarnings("deprecation")
@SuppressLint("HandlerLeak")
public class EnvioReservasActivity extends Activity {
	 private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/DISCO/";
	 private File file = new File(ruta_fotos);
	 private Button botonFoto;
	 String[] resultadosJson;
	 @SuppressWarnings("unused")
	 private Button botonEnviar;
	 //textBox y demas elementos del activity
	 private EditText txtNombre;
	 private Spinner sMotivos;
	 private EditText txtobs;
	 private EditText txtTelefono;
	 private String photoCode="noctua";

	 private Handler puente = new Handler() {
		 @Override
		 public void handleMessage(Message msg) {
			 //Mostramos el mensage recibido del servido en pantalla
			 Toast.makeText(getApplicationContext(), (String) msg.obj,
					 Toast.LENGTH_LONG).show();
		 }
	 };


	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_envio);
		 //---------------------------------------------------------------------------------------------------------
		 txtNombre = (EditText) findViewById(R.id.txtNombre);
		 txtobs = (EditText) findViewById(R.id.txtObs);
		 sMotivos = (Spinner) findViewById(R.id.spMotivos);
		 txtTelefono = (EditText) findViewById(R.id.TxtTelefono);
		 txtNombre.requestFocus();
		 new Connection().execute();

		 //----------------------------Este es el codigo que he agregado--------------------------------------------
	  /*
  txtCI.setOnFocusChangeListener(new OnFocusChangeListener() {
	  @Override
	  public void onFocusChange(View v, boolean hasFocus) {
	      if(hasFocus){
	          Toast.makeText(getApplicationContext(), "Estas en el Focus", Toast.LENGTH_LONG).show();
	      }else {
	          Toast.makeText(getApplicationContext(), "Saliste del focus", Toast.LENGTH_LONG).show();
	          cargarSpiner();
	      }
	     }
	  });
*/
		 //--------------------------------------Usando la Camara--------------------------------------------------
		 //======== codigo nuevo ========
		 botonFoto = (Button) findViewById(R.id.btnTomarFoto);
		 //Si no existe crea la carpeta donde se guardaran las fotos
		 file.mkdirs();
		 //accion para el boton
		 botonFoto.setOnClickListener(new View.OnClickListener() {

			 @Override
			 public void onClick(View v) {
				 String file = ruta_fotos + getCode() + ".jpg";
				 File mi_foto = new File(file);
				 try {
					 mi_foto.createNewFile();
				 } catch (IOException ex) {
					 Log.e("ERROR ", "Error:" + ex);
				 }
				 //
				 Uri uri = Uri.fromFile(mi_foto);
				 //Abre la camara para tomar la foto
				 Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 //Guarda imagen
				 cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				 //Retorna a la actividad
				 startActivityForResult(cameraIntent, 0);
			 }

		 });
		 //====== codigo nuevo:end ======
	 }

	 private class Connection extends AsyncTask {
		 @Override
		 protected Object doInBackground(Object... arg0) {
			 cargarSpiner();
			 return null;

		 }
	 }





/**
  * Metodo privado que genera un codigo unico segun la hora y fecha del sistema
  * @return photoCode
  * */
  @SuppressLint("SimpleDateFormat")
 private String getCode()
  {
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
   String date = dateFormat.format(new Date() );
   //concatenamos el ultimo codigo para que pueda enviar
   photoCode = "_disco_" + date;
   return photoCode;
  }

  @Override
 public boolean onCreateOptionsMenu(Menu menu) {
   // Inflate the menu; this adds items to the action bar if it is present.
   getMenuInflater().inflate(R.menu.main, menu);
   return true;
  }


@SuppressLint("SimpleDateFormat")
public void enviarDatos(View Button){
	txtNombre = (EditText) findViewById( R.id.txtNombre);
	txtobs=(EditText) findViewById( R.id.txtObs);
	sMotivos = (Spinner) findViewById(R.id.spMotivos);
	txtTelefono=(EditText) findViewById( R.id.TxtTelefono);
	if (verificarDatos(sMotivos.getSelectedItem().toString(),txtNombre.getText().toString(),
			txtTelefono.getText().toString(),txtobs.getText().toString())==true){
		new SendData().execute();
	}
	}


public boolean verificarDatos(String motivo,String nombre,String telefono,String obs){

	if(motivo.equals("")){
		 Toast toast = Toast.makeText(this, "Es necesario seleccionar un Evento", Toast.LENGTH_SHORT);
         toast.show();
		return false;
	}
	else if(nombre.equals("")){
		 Toast toast = Toast.makeText(this, "Es necesario ingresar un Nombre", Toast.LENGTH_SHORT);
         toast.show();
		txtNombre.requestFocus();
		return false;
	}
	else if(telefono.equals("")){
		Toast toast = Toast.makeText(this, "Es necesario ingresar su numero de telefono", Toast.LENGTH_SHORT);
		toast.show();
		txtTelefono.requestFocus();
		return false;
	}
	else if(obs.equals("")){
		Toast toast = Toast.makeText(this, "Es necesario ingresar una observacion", Toast.LENGTH_SHORT);
		toast.show();
		txtobs.requestFocus();
		return false;
	}
	return true;
	}
@SuppressWarnings("rawtypes")
private class SendData extends AsyncTask {
	@Override
	protected Object doInBackground(Object... arg0) {
		Datasend();
		return null;
	}
}

	 private void Datasend(){
	    //Utilizamos la clase Httpclient para conectar
	    HttpClient httpclient = new DefaultHttpClient();
	    //Utilizamos la HttpPost para enviar lso datos
	    //A la url donde se encuentre nuestro archivo receptor
	    HttpPost httppost = new HttpPost("http://104.236.113.194/disco/datareceptor.php");
	    try {
	    List<NameValuePair> postValues = new ArrayList<NameValuePair>(2);
	    postValues.add(new BasicNameValuePair("evento", sMotivos.getSelectedItem().toString()));
	    postValues.add(new BasicNameValuePair("nombre", txtNombre.getText().toString()));
	    postValues.add(new BasicNameValuePair("observacion", txtobs.getText().toString()));
		postValues.add(new BasicNameValuePair("telefono", txtTelefono.getText().toString()));
		postValues.add(new BasicNameValuePair("imagen", photoCode));
		//postValues.add(new BasicNameValuePair("telefono", tx.getText().toString()));
	    //Encapsulamos
	    httppost.setEntity(new UrlEncodedFormEntity(postValues));
	    //Lanzamos la petici�n
	    HttpResponse respuesta = httpclient.execute(httppost);
	    //Conectamos para recibir datos de respuesta
	    HttpEntity entity = respuesta.getEntity();
	    //Creamos el InputStream como su propio nombre indica
	    InputStream is = entity.getContent();
	    //Limpiamos el codigo obtenido atraves de la funcion
	    //StreamToString explicada m�s abajo
	    String resultado= StreamToString(is);

	    //Enviamos el resultado LIMPIO al Handler para mostrarlo
	    Message sms = new Message();
	    sms.obj = resultado;
		if(photoCode=="noctua"){
			puente.sendMessage(sms);
			Intent inten = new Intent(EnvioReservasActivity.this,MainActivity.class);
			startActivity(inten);
		}else{
			doFileUpload();
			puente.sendMessage(sms);
			Intent inten = new Intent(EnvioReservasActivity.this,MainActivity.class);
			startActivity(inten);
		}
	}catch (IOException e) {
	    //TODO Auto-generated catch block
	 }
	}
	//Funcion para 'limpiar' el codigo recibido
	public String StreamToString(InputStream is) {
	    //Creamos el Buffer
	    BufferedReader reader =
	        new BufferedReader(new InputStreamReader(is));
	   StringBuilder sb = new StringBuilder();
	 String line = null;
	 try {
	 //Bucle para leer todas las l�neas
	 //En este ejemplo al ser solo 1 la respuesta
	 //Pues no har�a falta
	 while ((line = reader.readLine()) != null) {
	      sb.append(line + "\n");
	 }
	 } catch (IOException e) {
	     e.printStackTrace();
	 } finally {
	    try {
	    is.close();
	    } catch (IOException e) {
	    e.printStackTrace();
	    }
	 }
	 //retornamos el codigo l�mpio
	return sb.toString();
	}


	//---------------------------ENVIAR FOTO---------------------------------------------------------
	private void doFileUpload() {

	    HttpURLConnection conn = null;
	    DataOutputStream dos = null;
	    DataInputStream inStream = null;
	    //obtiene el archivo
		String existingFileName;
		existingFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/DISCO/" + photoCode + ".jpg";
		String lineEnd = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "*****";
	    int bytesRead, bytesAvailable, bufferSize;
	    byte[] buffer;
	    int maxBufferSize = 1 * 1024 * 1024;
	    String responseFromServer = "";
	    String urlString = "http://104.236.113.194/disco/upload.php";

	    try {

	        //------------------ CLIENT REQUEST

	        FileInputStream fileInputStream = new FileInputStream(new File(existingFileName));
	        // open a URL connection to the Servlet
	        URL url = new URL(urlString);
	        // Open a HTTP connection to the URL
	        conn = (HttpURLConnection) url.openConnection();
	        // Allow Inputs
	        conn.setDoInput(true);
	        // Allow Outputs
	        conn.setDoOutput(true);
	        // Don't use a cached copy.
	        conn.setUseCaches(false);
	        // Use a post method.
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	        dos = new DataOutputStream(conn.getOutputStream());
	        dos.writeBytes(twoHyphens + boundary + lineEnd);
	        dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + existingFileName + "\"" + lineEnd);
	        dos.writeBytes(lineEnd);
	        // create a buffer of maximum size
	        bytesAvailable = fileInputStream.available();
	        bufferSize = Math.min(bytesAvailable, maxBufferSize);
	        buffer = new byte[bufferSize];
	        // read file and write it into form...
	        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

	        while (bytesRead > 0) {

	            dos.write(buffer, 0, bufferSize);
	            bytesAvailable = fileInputStream.available();
	            bufferSize = Math.min(bytesAvailable, maxBufferSize);
	            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

	        }

	        // send multipart form data necesssary after file data...
	        dos.writeBytes(lineEnd);
	        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	        // close streams
	        Log.e("Debug", "File is written");
	        fileInputStream.close();
	        dos.flush();
	        dos.close();

	    } catch (MalformedURLException ex) {
	        Log.e("Debug", "error: " + ex.getMessage(), ex);
	    } catch (IOException ioe) {
	        Log.e("Debug", "error: " + ioe.getMessage(), ioe);
	    }

	    //------------------ read the SERVER RESPONSE
	    try {

	        inStream = new DataInputStream(conn.getInputStream());
	        String str;

	        while ((str = inStream.readLine()) != null) {

	            Log.e("Debug", "Server Response " + str);

	        }

	        inStream.close();

	    } catch (IOException ioex) {
	        Log.e("Debug", "error: " + ioex.getMessage(), ioex);
	    }
	}

	 //************************DESDE ACA todo es para el SPINNER***********************************************
	 //funcion para traer string y cargar en el spinner
	 private StringBuilder inputStreamToString(InputStream is)
	 {
		 String line = "";
		 StringBuilder stringBuilder = new StringBuilder();
		 BufferedReader rd = new BufferedReader( new InputStreamReader(is) );
		 try
		 {
			 while( (line = rd.readLine()) != null )
			 {
				 stringBuilder.append(line);
			 }
		 }
		 catch( IOException e)
		 {
			 e.printStackTrace();
		 }

		 return stringBuilder;
	 }
	 private void cargarSpiner() {

		 @SuppressWarnings({"deprecation", "resource"})
		 HttpClient httpclient = new DefaultHttpClient();
		 String HTTP_RESTFUL = "http://104.236.113.194/disco/RestServices/EventosJson.php";
		 HttpPost httppost = new HttpPost(HTTP_RESTFUL);
		 String strResultado = "NaN";
		 try {
			 //ejecuta
			 HttpResponse response = httpclient.execute(httppost);
			 //Obtiene la respuesta del servidor
			 String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
			 JSONObject object = new JSONObject(jsonResult);
			 //obtiene el status
			 String status = object.getString("status");
			 //200 -> todo esta bien
			 if (status.equals("200")) {

				 //se Selecciona Eventos
				 JSONArray array = new JSONArray(object.getString("Registros"));
				 resultadosJson = new String[array.length()];

				 for (int i = 0; i < array.length(); i++) {
					 //recorre cada registro y concatena el resultado
					 JSONObject row = array.getJSONObject(i);
					 String codigo = row.getString("eve_cod");
					 String nombre = row.getString("eve_nom");
					 resultadosJson[i] =nombre;
				 }


				 runOnUiThread(new Runnable() {
					 @Override
					 public void run() {
						 ArrayAdapter<String> adaptador = new ArrayAdapter<String>(EnvioReservasActivity.this, android.R.layout.simple_list_item_1, resultadosJson);
						 sMotivos.setAdapter(adaptador);
					 }
				 });
			 }
		 } catch (Exception ex) {
			 Log.e("ServicioRest", "Error!", ex);
			 Toast.makeText(this, "No existen nuevos Eventos", Toast.LENGTH_LONG).show();
		 }
	 }


 }