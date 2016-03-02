package com.android4dev.navigationview;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EventosActivity extends Activity {


	private ListView listaa;
	String URL = "http://104.236.113.194/disco/RestServices/EventosJson.php";
	
	Activity a;
	Context context;
	static ArrayList<Eventos> lista;
	JSONArray pers;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        lista = new ArrayList<Eventos>();
        a=this;
        context=getApplicationContext();
        listaa = (ListView) findViewById(R.id.listViewListaEvento);
		new GetContacts(listaa).execute();

    	
	}
	private class GetContacts extends AsyncTask<Void, Void, Void> {
		 ListView list;
        private ProgressDialog pDialog;
	      public GetContacts(ListView listaa) {
			this.list=listaa;
		}

		@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(EventosActivity.this);
	            pDialog.setMessage("Cargando Datos ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	      }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // CREAMOS LA INSTANCIA DE LA CLASE
            JSONParser sh = new JSONParser();
 
            String jsonStr = sh.makeServiceCall(URL, JSONParser.GET);

 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    pers = jsonObj.getJSONArray("Registros");
 
                    // looping through All Equipos
                    for (int i = 0; i < pers.length(); i++) {
                        JSONObject c = pers.getJSONObject(i);
                        
                        String nombre = c.getString("eve_nom");
                        String descripcion = c.getString("eve_des");
                        String fecha = c.getString("eve_fecha");
                        String imagen = c.getString("eve_imagen");


 
                        Eventos e=new Eventos();
                        e.setURLimagen(imagen);
						e.setNombre(nombre);
						e.setDescripicion(descripcion);
						e.setFecha(fecha);
                        lista.add(e);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
        	}
        	new CargarListTask().execute();
    }
//HILO PARA CARGAR LOS DATOS EN EL LISTVIEW
class CargarListTask extends AsyncTask<Void,String,AdapterEvento>{
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	    }
	  
	    protected AdapterEvento doInBackground(Void... arg0) {
	        // TODO Auto-generated method stub
	  
	        try{

	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	  
	        AdapterEvento adaptador = new AdapterEvento(a,lista);
	        return adaptador;
	    }
	  
	    @Override
	    protected void onPostExecute(AdapterEvento result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        listaa.setAdapter(result);

	    }
	}
	}
}