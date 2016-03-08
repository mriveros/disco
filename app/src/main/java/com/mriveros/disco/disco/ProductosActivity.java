package com.mriveros.disco.disco;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.mriveros.disco.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProductosActivity extends Activity {


	private ListView listaa;
	String URL = "http://104.236.113.194/disco/RestServices/ProductosJson.php";
	
	Activity a;
	Context context;
	static ArrayList<Productos> lista;
	JSONArray pers;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        lista = new ArrayList<Productos>();
        a=this;
        context=getApplicationContext();
        listaa = (ListView) findViewById(R.id.listViewListaProducto);
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
	            pDialog = new ProgressDialog(ProductosActivity.this);
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
                        
                        String nombre = c.getString("pro_nom");
                        String descripcion = c.getString("pro_des");
                        String precio = c.getString("pro_precio");
                        String imagen = c.getString("pro_imagen");


 
                        Productos e=new Productos();
                        e.setURLimagen(imagen);
						e.setNombre(nombre);
						e.setDescripicion(descripcion);
						e.setPrecio(precio);
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
class CargarListTask extends AsyncTask<Void,String,AdapterProducto>{
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	    }
	  
	    protected AdapterProducto doInBackground(Void... arg0) {
	        // TODO Auto-generated method stub
	  
	        try{

	        }catch(Exception ex){
	            ex.printStackTrace();
	        }

			AdapterProducto adaptador = new AdapterProducto(a,lista);
	        return adaptador;
	    }
	  
	    @Override
	    protected void onPostExecute(AdapterProducto result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        listaa.setAdapter(result);

	    }
	}
	}
}