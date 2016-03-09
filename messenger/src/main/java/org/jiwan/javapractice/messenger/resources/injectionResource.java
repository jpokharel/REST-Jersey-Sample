package org.jiwan.javapractice.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectionResource")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class injectionResource {

	@GET
	@Path("annotations")
	public String getAnnotations(@MatrixParam("matrixValue") String matrixValue,
								 @HeaderParam("authorizationToken") String authorizationToken,
								 @CookieParam("name") String cookieValue){
		return "Matrix Parameter separated by ; is "+matrixValue+" \nHeader Value with Authorization Token is "+
								 authorizationToken+"\nCookie Parameter is "+cookieValue;
	}
	
	@GET
	@Path("context")
	public String getContext(@Context UriInfo uriinfo,@Context HttpHeaders headers){
		String path= uriinfo.getAbsolutePath().toString();
		String cookies=headers.getCookies().toString();
		return "Path "+path+" Cookies "+cookies;
	}
}
