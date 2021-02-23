package com.beta1.mikrotik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class conexion {
   String cmd [] = {"/bin/bash" , "-c"};
        Process pb = Runtime.getRuntime().exec(cmd);

    public conexion() throws IOException {
       try{
           String line;
           BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
           while ((line = input.readLine()) != null){
               System.out.println(line);
           }
           input.close();
       }catch (Exception e){
           System.out.println(e);
       }

    }
}
