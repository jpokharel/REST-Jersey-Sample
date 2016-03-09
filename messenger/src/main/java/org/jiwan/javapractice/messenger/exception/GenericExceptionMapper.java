package org.jiwan.javapractice.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;

import org.jiwan.javapractice.messenger.model.ErrorMessage;

//Commented Provider Annotation
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage=new ErrorMessage(ex.getMessage(),500,"https://google.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
	}
}
