package com.DzinVision.preprsoteSuplence.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Functions {
    public static void setPath() {
        //Get username and OS
        String os=System.getProperty("os.name");
        String user=System.getProperty("user.name");

        //Set path depending on OS
        if (os.contains("Mac")) Main.path="/Users/"+user+"/Library/Application Support/Preproste Suplence/";
        else if(os.contains("Windows")) Main.path="/C:/Users/"+user+"/AppData/Roaming/Preproste Suplence/";
        else if(os.contains("Linux")) Main.path="/home/"+user+"/.Preproste Suplence/";
    }


    public static void install() {
        Functions.setPath();

        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(Main.path+"path.txt")));
            String line; String out="";

            while ((line=in.readLine())!=null) out+=line;
            Main.mainPath=out;
        } catch (Exception e) {}


        File oldFile;
        File newFile;


        oldFile=new File(Main.mainPath);
        newFile=new File(Main.path+"Preproste Suplence.jar");

        newFile.renameTo(oldFile);
    }
}