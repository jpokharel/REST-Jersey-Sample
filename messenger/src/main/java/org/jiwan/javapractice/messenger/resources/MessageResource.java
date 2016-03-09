package org.jiwan.javapractice.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jiwan.javapractice.messenger.model.Message;
import org.jiwan.javapractice.messenger.resources.beans.MessageFilterBean;
import org.jiwan.javapractice.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
// This is one type: Produces(MediaType.APPLICATION_JSON)
//This allows both JSON and XML output to client
@Produces(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})

public class MessageResource {

	MessageService messageService=new MessageService();
	/*
	@GET
	public List<Message> getMessages(@QueryParam("year") int year,
	 								   @QueryParam("start") int start,
	 								   @QueryParam("size") int size){
		if(year > 0){ 
			return messageService.getAllMessagesByYear(year);
		}
		if(start >= 0 && size >= 0){ 
			return messageService.getAllMessagesPaginated(start, size);
		}
		return  messageService.getAllMessages(); 
	}
	*/
	
	//Using Bean Parameter for the above method such as to make parameters cleaner.
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean){
		if(filterBean.getYear() > 0){ 
			return messageService.getAllMessagesByYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0){ 
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return  messageService.getAllMessages(); 
	}
	
	@POST
	public Response addMessage(Message message,@Context UriInfo uriInfo){ //This will return message with status code
		Message newMessage=messageService.addMessage(message); //Create new message
		String newId=String.valueOf(newMessage.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();//Appends the URI
		return Response.created(uri)
				       //.status(Status.CREATED) //Returns response code for creation
					   .entity(newMessage)
					   .build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId,Message message){
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void removeMessage(@PathParam("messageId") long messageId){
		messageService.removeMessage(messageId);
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId, /* This is to get URI for Links*/ @Context UriInfo uriInfo){
		Message message=messageService.getMessage(messageId);
		message.addLink(getUriForSelf(uriInfo, message),"Self");
		message.addLink(getUriForProfile(uriInfo, message),"Profile");
		message.addLink(getUriForComments(uriInfo, message),"Comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				   .path(MessageResource.class)
				   .path(MessageResource.class,"getCommentResource")
				   .path(CommentResource.class)
				   .resolveTemplate("messageId", message.getId())
				   .build();
			return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				   .path(ProfileResource.class)
				   .path(message.getAuthor())
				   .build();
			return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri=uriInfo.getBaseUriBuilder()
			   .path(MessageResource.class)
			   .path(Long.toString(message.getId()))
			   .build()
			   .toString();
		return uri;
	}
	
	//Calling the Comment SubResource
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
}
