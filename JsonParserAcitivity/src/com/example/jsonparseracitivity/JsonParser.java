package com.example.jsonparseracitivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
	
	final String SPLIT_MARK = ":";
	/**
	* Parser Json Data
	* 
	* @param ArrayList<String> Strings fetched from URL
	* @return
	*/
    public ArrayList<String> parserJson(String strResult) {
    	ArrayList<String> jsonlist = new ArrayList<String>();
    	jsonlist.clear();
        try {  
            JSONArray jsonObjs = new JSONObject(strResult).getJSONArray("anagrams");  
            String s = ""; 
            for(int i = 0; i < jsonObjs.length() ; i++){
            	s = jsonObjs.getString(i);
            	if (s != null)
            		jsonlist.add(s);
            }  
        } catch (JSONException e) {  
            System.out.println("Jsons parse error !");  
            e.printStackTrace();  
        }
        return jsonlist;
    } 

    /**
	* Sort Json Data
	* 
	* @param ArrayList<String> Strings need to be sorted
	* @return ArrayList<String> String sorted
	*/
    public ArrayList<String> sortJsonList(ArrayList<String> strArray){
    	
    	ArrayList<String> strObject = new ArrayList<String>();    	
    	ArrayList<String> strDesString = new ArrayList<String>();
    	
    	HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
    	int index, j = 0;
    	String temp;
    	for(String s :strArray){    		
    		temp = sortSingleString(s);
    		if(!hashMap.containsKey(temp)){
    			hashMap.put(temp, j++);
    			strDesString.add(s); 
    		}else{
    			index = hashMap.get(temp);
    			String strR = strDesString.get(index);
    			strR = strR + SPLIT_MARK + s;
    			strDesString.set(index, strR);
    		}
    	}
   	
    	for(String s :strDesString){
    		ArrayList<String> strSpt = new ArrayList<String>();
    		strSpt = strSplit(s);
    		for(String l:strSpt)
    			strObject.add(l);
    	}
    	return strObject;

    }
    
    /**
   	* Split anagrams
   	* 
   	* @param ArrayList<String> Strings need to be splited
   	* @return ArrayList<String> String splited
   	*/
    public ArrayList<String> strSplit(String str){
    	
    	String temp; 
    	ArrayList<String> list=new ArrayList<String>(); 
    	
    	while(str != null){
    		int index=str.indexOf(SPLIT_MARK);
    		if(index > 0){
    			temp=str.substring(0,index); 
    		}else{
    			temp = str;
    			if(!temp.equals("")) 
        			list.add(temp);
    			break;
    		}
    		if(!temp.equals("")) 
    			list.add(temp);
    		str = str.substring(index + SPLIT_MARK.length());
    	};
 
    	return list;
    }
    
    /**
   	* Finding anagrams
   	* 
   	* @param Strings need to be sort by char array
   	* @return String
   	*/
    public String sortSingleString(String strInput){
    	char []array = strInput.toCharArray();
    	Arrays.sort(array);
    	String strRes = new String(array);
    	return strRes;
    }

}
