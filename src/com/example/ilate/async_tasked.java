package com.example.ilate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;



import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class async_tasked extends AsyncTask<Object,Integer,String>{
	
	HttpEntity entity;
	HttpResponse result;
	String respons;//hasil kembalian
	
	
	
	static String id ;
	static String pass;
	
	static String path;//URI alamat gambar
	String kode;//post / login
	private ProgressDialog mProgressDialog;
	private Context context;
	GetArrayList sendAll = null;
	
	
	String idusers;
	String idfood;
	String[] datacoment;
	String kirim;
	
	public static List<Map<String,Object>> data;
	public static List<Map<String,Object>> isiTL;
	public static List<Map<String,Object>> isiFav;
	public static List<Map<String,Object>> isiComm;
	public static List<Map<String,Object>> profileSet;
	public static List<Map<String,Object>> galerySet;
	public static List<Map<String,Object>> latlngSet;
	public static List<Map<String,Object>> status;
	public static List<Map<String,Object>> coment;
	public static List<Map<String,Object>> albumfix;
	String[] info;
	
	private String id_foods;
	private String id_user;
	private String id_kategoriFoto;
	//constructor
	public async_tasked(Context context,String test)
	{
		this.context = context;
	
	}
	
	public async_tasked(Context context,GetArrayList ambil)
	{
		this.context= context;
		this.sendAll = ambil;
		
		
	}
	
	
	protected String doInBackground(Object... params) {
		 kode = (String)params[2];
		 System.out.print(kode);
		if(kode.equals("login"))
		{
			id = (String)params[0];
			pass = (String)params[1];
			System.out.println(id+" "+pass);
			return onLogin(id,pass);
		}
		else if(kode.equals("like"))
		{
			idusers = (String)params[0];
			idfood = (String)params[1];
			return onLike();
			
		}
		else if(kode.equals("signup"))
		{
			info = (String[]) params[1];
			return onsignup();
		}
		else if(kode.equals("post"))
		{
			path = (String)params[0];
			info = (String[]) params[1];
			System.out.println(info[0]+" "+info[1]);
			try {
				return onPost(path);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(kode.equals("timeline"))
		{
			
			return onTimeline();
		}
		else if(kode.equals("favorite"))
		{
			
			return onFavorite();
		}
		else if(kode.equals("profile"))
		{
			id_user = (String) params[1];
			return onProfile();
		}
		else if(kode.equals("galery"))
		{
			id_user = (String) params[1];
			id_kategoriFoto = (String) params[0];
			return onGalery();
		}
		else if(kode.equals("mapAll"))
		{
			return mapAll();
		}
		else if(kode.equals("addcoment"))
		{
			id_foods =(String) params[1];
			System.out.println("testates");
			return onComent();
		}
		else if(kode.equals("sendcoment"))
		{
			datacoment =(String[])params[0];
			kirim = (String) params[1];
			return onSendComent();
		}
		else if(kode.equals("albumfix"))
		{
			id_user=(String) params[1];
			System.out.println("wjadjwajdawwd");
			return onAlbumFix();
		}
		else if(kode.equals("snackmap"))
		{
			return snackmap();
		}
		else if(kode.equals("maincourse"))
		{
			return mainmap();
		}
		
			 
		
		return kode;
	}
	

	@Override
	protected void onPreExecute()
	{
			super.onPreExecute();
			/*if(kode.equals("upload"))
			{
				 mProgressDialog = new ProgressDialog(context);
				 mProgressDialog.setMessage("Downloading file..");
			     mProgressDialog.setIndeterminate(false);
			     mProgressDialog.setMax(100);
			     mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			     mProgressDialog.setCancelable(true);
			     mProgressDialog.show();
			}*/
			 
		
	}
	
	protected void onPostExecute(String hasil)
	{
		if(kode.equals("login")){
			try
			{
				JSONArray arr = new JSONArray(respons);
				data = new ArrayList<Map<String,Object>>();
				
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("username", arr.getJSONObject(k).get("namauser"));
					map.put("iduser", arr.getJSONObject(k).get("iduser"));
					map.put("fotouser", arr.getJSONObject(k).get("fotouser"));
					
					data.add(map);
				}
			}
			catch(JSONException e){}
			sendAll.getArray(data);
		}
		
		else if(kode.equals("addcoment"))
		{
			Map<String,Object> map;
			coment =  new ArrayList<Map<String,Object>>();
			try
			{
				JSONArray arr = new JSONArray(respons);
				
				
				
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("user", arr.getJSONObject(k).get("user"));
					map.put("isikomen", arr.getJSONObject(k).get("isikomen"));
					
					coment.add(map);
				}
			}
			catch(JSONException e)
			{
				
			}
			if(coment.size()==0)
			{
				map = new HashMap<String,Object>();
				map.put("user", "no coment");
				map.put("isikomen", "empty");
				coment.add(map);
				System.out.println("gagalzzzzzzzzzz "+coment.size()+" "+ coment.get(0).get("user").toString());
				sendAll.getArray(coment);
			}
			else
			{
				sendAll.getArray(coment);
			}
			
			
		}
		else if(kode.equals("signup"))
		{
			System.out.println(respons);
			status = new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			if(respons.length()>0)
			{
				map =new HashMap<String,Object>();
				map.put("hasil", "sukses");
				System.out.println("dikembalikan sukses ");
			}
			else
			{
				map =new HashMap<String,Object>();
				map.put("hasil", "gagal");
				System.out.println("dikembalikan gagal ");
			}
			
			
			map.put("iduser", respons.toString());
			
			status.add(map);
			sendAll.getArray(status);
		}
		else if(kode.equals("upload")){
			//mProgressDialog.dismiss();
		}
		else if(kode.equals("albumfix"))
		{

			try
			{
			JSONArray arr = new JSONArray(respons);
			//System.out.println(respons);
			albumfix = new ArrayList<Map<String,Object>>();//membuat arraylist yang berisikan map
			
			Map<String,Object> map;//membuat map
			
			for(int k=0;k<arr.length();k++)
			{
				map = new HashMap<String,Object>();
				map.put("idjenis",arr.getJSONObject(k).getString("idjns"));
				map.put("jmlhfotoperjenis",arr.getJSONObject(k).getString("jmlhfotoperjenis") );
				albumfix.add(map);
			}
			}
			catch(JSONException e){}
			
			MyProfileAdapter pa = new MyProfileAdapter(this.context,albumfix);
			MyProfile.gv.setAdapter(pa);
			MyProfile.gv.setDrawSelectorOnTop(true);
		}
		else if(kode.equals("timeline")){
			
			try
			{
			JSONArray arr = new JSONArray(respons);
			//System.out.println(respons);
			isiTL = new ArrayList<Map<String,Object>>();//membuat arraylist yang berisikan map
			
			Map<String,Object> map;//membuat map
			
			for(int k=0;k<arr.length();k++)
			{
				map = new HashMap<String,Object>();
				map.put("user",arr.getJSONObject(k).getString("user"));
				map.put("fotouser",arr.getJSONObject(k).getString("fotouser") );
				map.put("fotomakanan",arr.getJSONObject(k).getString("foto") );
				map.put("nmplace",arr.getJSONObject(k).getString("place") );
				map.put("namamakanan",arr.getJSONObject(k).getString("nmfood"));
				map.put("like",arr.getJSONObject(k).getString("jmlhlike"));
				map.put("idfood",arr.getJSONObject(k).getString("idfood"));
				map.put("id_user",arr.getJSONObject(k).getString("iduser"));
				map.put("lat", arr.getJSONObject(k).getString("lat"));
				map.put("long", arr.getJSONObject(k).getString("long"));
				map.put("alamat", arr.getJSONObject(k).getString("alamat"));
				map.put("desc", arr.getJSONObject(k).getString("desc"));
				isiTL.add(map);
			}
			}
			catch(JSONException e){}
			sendAll.getArray(isiTL);
			
		}
		else if(kode.equals("favorite"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				
				isiFav = new ArrayList<Map<String,Object>>();
				
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("user",arr.getJSONObject(k).getString("user"));
					map.put("fotouser",arr.getJSONObject(k).getString("fotouser") );
					map.put("fotomakanan",arr.getJSONObject(k).getString("foto") );
					map.put("nmplace",arr.getJSONObject(k).getString("place") );
					map.put("namamakanan",arr.getJSONObject(k).getString("nmfood"));
					map.put("like",arr.getJSONObject(k).getString("jmlhlike"));
					map.put("idfood",arr.getJSONObject(k).getString("idfood"));
					map.put("id_user",arr.getJSONObject(k).getString("iduser"));
					map.put("lat", arr.getJSONObject(k).getString("lat"));
					map.put("long", arr.getJSONObject(k).getString("long"));
					map.put("alamat", arr.getJSONObject(k).getString("alamat"));
					map.put("desc", arr.getJSONObject(k).getString("desc"));
					isiFav.add(map);
					
				}
				
				
				
			}
			catch(JSONException e)
			{
				
			}
			sendAll.getArray(isiFav);
			
		}
		else if(kode.equals("comment"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				
				isiComm = new ArrayList<Map<String,Object>>();
				
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("foto",arr.getJSONObject(k).getString("foto"));
					isiComm.add(map);
				}
				
			}
			catch(JSONException e)
			{
				
			}
			
			sendAll.getArray(isiFav);
			
		}
		else if(kode.equals("addcomment"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				
				isiComm = new ArrayList<Map<String,Object>>();
				
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("user",arr.getJSONObject(k).getString("user"));
					map.put("food",arr.getJSONObject(k).getString("food"));
					map.put("isi",arr.getJSONObject(k).getString("isi"));
					isiComm.add(map);
				}
				
			}
			catch(JSONException e)
			{
				
			}
			sendAll.getArray(isiFav);
			
		}
		else if(kode.equals("profile"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				profileSet = new ArrayList<Map<String,Object>>();
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("user",arr.getJSONObject(k).getString("nmuser"));
					map.put("fotouser",arr.getJSONObject(k).getString("fotouser").toString());
					map.put("jmlhfoto",arr.getJSONObject(k).getString("jmlhfoto"));
					map.put("status",arr.getJSONObject(k).getString("status"));
					profileSet.add(map);
				}
			}
			catch(JSONException e)
			{
				
			}
			sendAll.getArray(profileSet);
		}
		
		else if(kode.equals("galery"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				galerySet = new ArrayList<Map<String,Object>>();
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("foodname",arr.getJSONObject(k).getString("namamkn").toString());
					map.put("foodfoto",arr.getJSONObject(k).getString("fotomkn").toString());
					
					galerySet.add(map);
				}
				
			}
			catch(JSONException e)
			{
				
			}
			sendAll.getArray(galerySet);
		}
		else if(kode.equals("mapAll"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				latlngSet = new ArrayList<Map<String,Object>>();
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("nmplace", arr.getJSONObject(k).getString("nmplace").toString());
					map.put("alamat", arr.getJSONObject(k).getString("alamat").toString());
					map.put("latitude", arr.getJSONObject(k).getString("latitude"));
					map.put("longitude", arr.getJSONObject(k).getString("longitude"));
					
					latlngSet.add(map);
				}
			}
			catch(JSONException e)
			{
				
			}
			sendAll.getArray(latlngSet);
		}
		else if(kode.equals("snackmap"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				latlngSet = new ArrayList<Map<String,Object>>();
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("nmplace", arr.getJSONObject(k).getString("nmplace").toString());
					map.put("nmfood", arr.getJSONObject(k).getString("nmfood").toString());
					map.put("alamat", arr.getJSONObject(k).getString("alamat").toString());
					map.put("latitude", arr.getJSONObject(k).getString("lat"));
					map.put("longitude", arr.getJSONObject(k).getString("long"));
					map.put("foto", arr.getJSONObject(k).getString("foto"));
					latlngSet.add(map);
				}
			}
			catch(JSONException e)
			{
				
			}
			sendAll.getArray(latlngSet);
		}
		else if(kode.equals("maincourse"))
		{
			try
			{
				JSONArray arr = new JSONArray(respons);
				latlngSet = new ArrayList<Map<String,Object>>();
				Map<String,Object> map;
				for(int k=0;k<arr.length();k++)
				{
					map = new HashMap<String,Object>();
					map.put("nmplace", arr.getJSONObject(k).getString("nmplace").toString());
					map.put("nmfood", arr.getJSONObject(k).getString("nmfood").toString());
					map.put("alamat", arr.getJSONObject(k).getString("alamat").toString());
					map.put("latitude", arr.getJSONObject(k).getString("lat"));
					map.put("longitude", arr.getJSONObject(k).getString("long"));
					map.put("foto", arr.getJSONObject(k).getString("foto"));
					
					latlngSet.add(map);
				}
			}
			catch(JSONException e)
			{
				
			}
			sendAll.getArray(latlngSet);
		}
		
	}
	
	
	
	
	protected String onPost(String path) throws ClientProtocolException, IOException
	{	
		String responses ="xx";
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		DefaultHttpClient mHttpClient = new DefaultHttpClient(params);
		HttpPost httppost = new HttpPost("http://www.technubi.com/ilatte/upload.php");
		try
		{
			
			
			
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
	        //multipartEntity.addPart("filename",new StringBody("wola"));
			multipartEntity.addPart("kategori",new StringBody(info[0]));//oke
			multipartEntity.addPart("nmmakan",new StringBody(info[1]));//oke
			multipartEntity.addPart("nmtmpt",new StringBody(info[2]));
			multipartEntity.addPart("descfoto",new StringBody(info[3]));//oke
			multipartEntity.addPart("long",new StringBody(info[4]));//oke
			multipartEntity.addPart("lati",new StringBody(info[5]));//oke
			
			
			multipartEntity.addPart("user",new StringBody(info[6]));
			multipartEntity.addPart("nmfoto",new StringBody(info[8]));
			
			multipartEntity.addPart("alamat",new StringBody(info[7]));//oke
	        /*File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/ilate");
	        final String uploadFilePath = mediaStorageDir.toString();
	        final String uploadFileName = "/IMG_20140505_090841.jpg";*/
	        
	        
	        BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inSampleSize = 16;
			final Bitmap bitmap = BitmapFactory.decodeFile(path,opt);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte[] data = bao.toByteArray();
			ByteArrayBody bab2 = new ByteArrayBody(data, "image/jpeg", info[1]+".jpeg");
			
	        
	        
	        //resizeIcon(path,640,480);
	        File sourceFile = new File(path); 
	        //multipartEntity.addPart("uploaded_file", new FileBody(sourceFile));
	        multipartEntity.addPart("file",bab2);
	        httppost.setEntity(multipartEntity);

	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        responses = mHttpClient.execute(httppost, responseHandler);
		}
		catch (ClientProtocolException e) {
            System.out.println("gagal1");
        } 
		catch (IOException e) {
			System.out.println("gagal2");
        }
		catch (Exception e)
		{
			System.out.println("gagal3");
		}
		return responses;
	}
	protected String onComent()
	{
		
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/isikomen.php");
		try
		{
			System.out.println(id_foods);
			List<NameValuePair> infoz = new ArrayList<NameValuePair>();
			infoz.add(new BasicNameValuePair("idfood",id_foods));
				
			
			hp.setEntity(new UrlEncodedFormEntity(infoz));
			HttpResponse response = hc.execute(hp);
			respons = inputStreamToString(response.getEntity().getContent()).toString();
			
		}
		catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } 
		catch (IOException e) {
            // TODO Auto-generated catch block
        }
		catch (Exception e)
		{
			
		}
		return respons;
	}
	protected String onSendComent()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/addkomen.php");
		try
		{
			List<NameValuePair> infoz = new ArrayList<NameValuePair>();
			infoz.add(new BasicNameValuePair("iduser",datacoment[1]));
			infoz.add(new BasicNameValuePair("idfood",datacoment[0]));
			infoz.add(new BasicNameValuePair("isi",kirim));	
			
			hp.setEntity(new UrlEncodedFormEntity(infoz));
			HttpResponse response = hc.execute(hp);
			respons = inputStreamToString(response.getEntity().getContent()).toString();
			
		}
		catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } 
		catch (IOException e) {
            // TODO Auto-generated catch block
        }
		catch (Exception e)
		{
			
		}
		return respons;
	}
	protected String onsignup()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/signup.php");
		try
		{
			List<NameValuePair> infoz = new ArrayList<NameValuePair>();
			infoz.add(new BasicNameValuePair("user",info[0]));
			infoz.add(new BasicNameValuePair("pass",info[2]));
			infoz.add(new BasicNameValuePair("email",info[1]));	
			
			hp.setEntity(new UrlEncodedFormEntity(infoz));
			HttpResponse response = hc.execute(hp);
			respons = inputStreamToString(response.getEntity().getContent()).toString();
			
		}
		catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } 
		catch (IOException e) {
            // TODO Auto-generated catch block
        }
		catch (Exception e)
		{
			
		}
		
		return respons;
	}
	protected String onLogin(String username,String password)
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/login.php");
		//bisa pake HttpGet
		try
		{
			//bisa pake StatusLine statusLine = httpResponse.getStatusLine();
			//digunakan untuk mendapat hasil angka ex : 404
			//ifstatusline.getStatusCode() == 200
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username",username));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            hp.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = hc.execute(hp);
            response = hc.execute(hp);
            respons = inputStreamToString(response.getEntity().getContent()).toString();
            System.out.println(respons);
        }
		catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } 
		catch (IOException e) {
            // TODO Auto-generated catch block
        }
		catch (Exception e)
		{
			
		}
		return "sukses";
	}
	protected String onTimeline()
	{
		
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/home.php");
		try
		{
		//SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(main.getApplicationContext());
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("perintah","timeline")); 
		hp.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
		result = hc.execute(hp);
		
		respons= inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		return respons;
		
	}
	protected String onFavorite()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/fav.php");
		try
		{
		//SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(main.getApplicationContext());
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("perintah","favorite")); 
		hp.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
		result = hc.execute(hp);
		
		respons= inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		return respons;
		
	}
	protected String onProfile()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/goProfile.php");
		try
		{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("iduser",id_user));
			hp.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		
		return respons;
		
	}
	protected String onLike()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/likekok.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("iuser",idusers));
			parameter.add(new BasicNameValuePair("ifood",idfood));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		System.out.println(respons);
		return respons;
	}
	protected String onAlbumFix()
	{
		
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/countalbum.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("id",id_user));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		System.out.println(respons);
		return respons;
	}
	protected String onGalery()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/galery.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("iduser",id_user));
			parameter.add(new BasicNameValuePair("jenis",id_kategoriFoto));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		return respons;
	}
	protected String mapAll()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/latlngall.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("k","x"));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		return respons;
		
	}
	protected String snackmap()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/snackmap.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("id","2"));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		System.out.println(respons);
		return respons;
	}
	protected String mainmap()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/snackmap.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("id","2"));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		System.out.println(respons);
		return respons;
	}
	protected String beveragesmap()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/snackmap.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("id","3"));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		System.out.println(respons);
		return respons;
	}
	protected String desertmap()
	{
		HttpClient hc = HttpClientFactory.getThreadSafeClient();
		HttpPost hp = new HttpPost("http://www.technubi.com/ilatte/snackmap.php");
		try
		{
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			parameter.add(new BasicNameValuePair("id","4"));
			hp.setEntity(new UrlEncodedFormEntity(parameter));
			result = hc.execute(hp);
			respons = inputStreamToString(result.getEntity().getContent()).toString();
		}
		catch(ClientProtocolException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		System.out.println(respons);
		return respons;
	}
private StringBuilder inputStreamToString(InputStream is)
		{	
			String line = "";
			StringBuilder total = new StringBuilder();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			try{
				while((line = rd.readLine()) != null)
				{
					total.append(line);
				}
			}catch (IOException e){
				e.printStackTrace();
			}
			return total;
		}

	 

	

	

}