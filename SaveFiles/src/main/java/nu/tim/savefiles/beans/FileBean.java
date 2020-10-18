/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.tim.savefiles.beans;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.sql.rowset.serial.SerialBlob;
import nu.tim.savefiles.ConnectionFactory;

/**
 *
 * @author Tim
 */
@Stateless
public class FileBean {
    
    public byte[] createBytes(String b64String){
        return Base64.getDecoder().decode(b64String);
    }
    
    public boolean saveOnComp(byte[] data){
        String fileName = "test";
            String fileType = "png";
            int count = 1;
        try{
            
            while(Files.exists(Paths.get("./Images/"+fileName+"."+fileType).toAbsolutePath().normalize())){
                fileName+= count;
                count++;
            }
            Files.write(Paths.get("./Images/"+fileName+"."+fileType).toAbsolutePath().normalize(), data);
        }catch(IOException e){
            System.out.println("Error FileBean.saveOnComp: "+ e.getMessage());
            return false;
        }
        
        String url = "C:\\Users\\Tim\\Payara_Server\\glassfish\\domains\\domain1\\config\\Images\\"+fileName+"."+fileType;
        
        try(Connection con = ConnectionFactory.getConnection()){
                //String sql = String.format("INSERT INTO img_ref(name,path) VALUES('%s',QUOTE('%s'))",fileName,url);
                String sql = "INSERT INTO img_ref(name,path) VALUES(?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, fileName);
                stmt.setString(2, url);
                stmt.executeUpdate();                
            }catch(Exception e){
                System.out.println("Error FileBean.saveOnComp: "+ e.getMessage());
                return false;
            }
        return true;
    
    }
    
    public boolean saveAsText(String data, String name){
        try(Connection con = ConnectionFactory.getConnection()){
                //String sql = String.format("INSERT INTO img_ref(name,path) VALUES('%s',QUOTE('%s'))",fileName,url);
                String sql = "INSERT INTO img_text(name,data) VALUES(?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, data);
                stmt.executeUpdate();                
            }catch(Exception e){
                System.out.println("Error FileBean.saveAsText: "+ e.getMessage());
                return false;
            }
        
        return true;
    }
    
    public boolean saveAsBlob(byte[] data, String name){
        try(Connection con = ConnectionFactory.getConnection()){
                //String sql = String.format("INSERT INTO img_ref(name,path) VALUES('%s',QUOTE('%s'))",fileName,url);
                String sql = "INSERT INTO img_blob(name,data) VALUES(?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setBlob(2, new SerialBlob(data));
                stmt.executeUpdate();                
            }catch(Exception e){
                System.out.println("Error FileBean.saveAsText: "+ e.getMessage());
                return false;
            }
        
        return true;
    }
}
