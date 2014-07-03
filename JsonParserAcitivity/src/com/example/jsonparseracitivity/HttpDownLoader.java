package com.example.jsonparseracitivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownLoader {
	
	private URL url;
	/**
	* Fetching Json data from remote website
	* 
	* @param urlStr HTTP URL
	* @return
	*/
	public String download(String urlStr){
		StringBuffer strBuffer = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try{
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while((line = buffer.readLine()) != null){
				strBuffer.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if (buffer != null){
					buffer.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return strBuffer.toString();	
	}		

}
