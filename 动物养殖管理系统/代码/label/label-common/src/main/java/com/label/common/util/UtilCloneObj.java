package com.label.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class UtilCloneObj {
	@SuppressWarnings("unchecked")
	    public static <T extends Serializable> T clone(T obj){
	        T clonedObj = null;
	        try {
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ObjectOutputStream oos = new ObjectOutputStream(baos);
	            oos.writeObject(obj);
	            oos.close();
	            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	            ObjectInputStream ois = new ObjectInputStream(bais);
	            clonedObj = (T) ois.readObject();
	            ois.close();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return clonedObj;
	    }
	
	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(src);  

	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    @SuppressWarnings("unchecked")  
	    List<T> dest = (List<T>) in.readObject();  
	    return dest;  
	}

}
