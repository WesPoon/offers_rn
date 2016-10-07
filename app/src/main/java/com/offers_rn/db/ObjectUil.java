package com.offers_rn.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

import com.offers_rn.*;


public class ObjectUil {

   // static final Base64 base64 = new Base64();

    @SuppressLint("NewApi")
	public static String serializeObjectToString(Object object) throws IOException {
       
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream); 
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            gzipOutputStream.flush();
            gzipOutputStream.close();
            return new String(Base64.encode(arrayOutputStream.toByteArray(),0));
              
        
    }

    public static Object deserializeObjectFromString(String objectString) throws IOException, ClassNotFoundException {
       
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(Base64.decode(objectString,0));
                GZIPInputStream gzipInputStream = new GZIPInputStream(arrayInputStream);
                Log.d("test","test1");
                ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);
      //          ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);
                Log.d("test","test2");
                return objectInputStream.readObject();
            
    }
}
