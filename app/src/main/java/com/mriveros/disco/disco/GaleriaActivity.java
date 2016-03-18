package com.mriveros.disco.disco;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mriveros.disco.R;


public class GaleriaActivity extends Activity {

	private TextView mostrar;
	private Button buscar;
	private ListView listaa;
	String searchTerm ;
	String URL = "http://104.236.113.194/disco/RestServices/GaleriaJson.php";
	String EVENTOSELECT;
	
	Activity a;
	Context context;
	static ArrayList<Galerias> lista;
	JSONArray pers;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        lista = new ArrayList<Galerias>();
        a=this;
        context=getApplicationContext();
        listaa = (ListView) findViewById(R.id.listViewLista);
		Bundle bundle = getIntent().getExtras();
		EVENTOSELECT=bundle.getString("codigo");
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
	            pDialog = new ProgressDialog(GaleriaActivity.this);
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
						if (c.getString("eve_nom").equals(EVENTOSELECT)) {

							String name = c.getString("eve_nom");
							String especialidad = c.getString("img_obs");
							String imagen = c.getString("img_picture");


							Galerias e = new Galerias();
							e.setURLimagen(imagen);
							e.setNombre(name);
							e.setProfesion(especialidad);
							lista.add(e);
						}
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
class CargarListTask extends AsyncTask<Void,String,AdapterGaleria>{
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	    }
	  
	    protected AdapterGaleria doInBackground(Void... arg0) {
	        // TODO Auto-generated method stub
	  
	        try{

	        }catch(Exception ex){
	            ex.printStackTrace();
	        }

			AdapterGaleria adaptador = new AdapterGaleria(a,lista);
	        return adaptador;
	    }
	  
	    @Override
	    protected void onPostExecute(AdapterGaleria result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        listaa.setAdapter(result);

	    }
	}
	}
}