/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.tim.savefiles.resources;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import nu.tim.savefiles.beans.FileBean;

/**
 *
 * @author Tim
 */
@Path("image/")
public class FileResource {
    @EJB
    FileBean filebean;
    
    @Path("comp")
    @POST
    public Response saveImageComp(String b64String){
        if(filebean.saveOnComp(filebean.createBytes(b64String))){
            return Response.accepted().entity("Image Saved Successfully").build();
        }
        else{
            return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity("Could not be saved").build(); 
        }
        
    }
    
    @Path("text")
    @POST
    public Response saveImageText(String b64String){
        if(filebean.saveAsText(b64String, "test")){
            return Response.accepted().entity("Image Saved Successfully").build();
        }
        else{
            return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity("Could not be saved").build(); 
        }
        
    }
    
    @Path("blob")
    @POST
    public Response saveImageBlob(String b64String){
        if(filebean.saveAsBlob(filebean.createBytes(b64String), "test")){
            return Response.accepted().entity("Image Saved Successfully").build();
        }
        else{
            return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity("Could not be saved").build(); 
        }
        
    }
}
