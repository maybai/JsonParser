package com.example.jsonparseracitivity;

import java.util.ArrayList;
import java.util.HashMap;


//import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class  MainActivity extends Activity {

	final String strUrl = "http://www.soti.net/android/anagram.json";
    String strJson = "";
    protected BaseAdapter adapter;
	ProgressDialog mypDialog;
	ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String,String>>();
	
	Button btnJson; 
	Button btnClear;
	Button btnExit;   			
	final String JSON = "JSON";
	final String SORT = "SORT";
	final String SPLIT_MARK = ":";
	//ListView listView;


	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJson = (Button) this.findViewById(R.id.js_parser); 
        btnClear = (Button) this.findViewById(R.id.js_clear);
        btnExit = (Button) this.findViewById(R.id.js_exit);
        
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);        

        //Bind arraylist with SimpleAdapte
        adapter = new SimpleAdapter(this, arraylist, R.layout.list_view, new String[] {JSON,SORT}, new int[] {R.id.tvJson,R.id.tvSort});
        listView.setAdapter(adapter);
  	  	adapter.notifyDataSetChanged();
  	  	
        btnJson.setOnClickListener(new View.OnClickListener() {
           	@Override  
           	public void onClick(View v) {
           		DownLoadThread thread = new DownLoadThread();
           		thread.start(); 
                }  
        	});  

        btnClear.setOnClickListener(new View.OnClickListener() {  
                @Override  
                public void onClick(View v) {  
                	arraylist.clear();
                	adapter.notifyDataSetChanged();
                }  
            });
        btnExit.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {
            	System.exit(0);
            	
            }  
        });
        
        mypDialog = new ProgressDialog(this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setTitle(R.string.progTitle);
        mypDialog.setMessage(this.getString(R.string.progStatus));
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(true);
        
    } 
	    
	public  Handler mHandler = new Handler() {
	    // Overload handleMessage()
	    
		@Override 
	    public void handleMessage(Message msg) {        	
	        switch (msg.what) { 
	        // If get data from HTTP server, retrieve and sort data.
	        case 2:
	        	  mypDialog.dismiss();
	        	  resProcess(strJson);	        	  
	        	  adapter.notifyDataSetChanged();
	              break;
	        // show dialog while retrieving and sorting
	        case 1:	        	  
	        	  mypDialog.show();
	        	  break; 
	        case 0: 
	        	  mypDialog.dismiss();
	              Toast.makeText(getApplication(),  
	            		  "Http Issue", Toast.LENGTH_SHORT) 
	                      .show(); 
	              break;
	          
	          
	          } 
	        super.handleMessage(msg);
	      } 
		
	};
     

	 /**
 	* DownLoad/Parser/Sort Thread
 	* 
 	*/
	private class DownLoadThread extends Thread { 

           @Override 
           public void run() {
        	   Message msg = new Message();
        	   msg.what = 1;
        	   mHandler.sendMessage(msg);
        	   HttpDownLoader http = new HttpDownLoader();
        	   strJson = http.download(strUrl);
        	   if (strJson != null && strJson.length() != 0) {
        		   msg = new Message();
	        	   msg.what = 2;
	        	   mHandler.sendMessage(msg);
        	   }
        	   else{
        		   	msg.what = 0;
        		   	mHandler.sendMessage(msg);
        	   }
        	   
           } 
	};
      
      /**
  	* Parser Json from String
  	* 
  	* @param Par
     * @return 
     * @return 
  	* @return
  	*/      
	public void resProcess(String strResult) {    	  
		JsonParser parJson = new JsonParser();
    	ArrayList<String> strJsonList = new ArrayList<String>();
    	ArrayList<String> strSortList = new ArrayList<String>();
    	strJsonList = parJson.parserJson(strResult);
    	strSortList = parJson.sortJsonList(strJsonList);

    	for(int i=0; i<strJsonList.size(); i++){
    		HashMap<String, String> map = new HashMap<String, String>();
    		map.put(JSON, (strJsonList.get(i)).toString());
    		map.put(SORT, (strSortList.get(i)).toString());
    		arraylist.add(map);  		
    	}
     } 

}
