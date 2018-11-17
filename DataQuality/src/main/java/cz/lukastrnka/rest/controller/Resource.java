package cz.lukastrnka.rest.controller;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class Resource {

	
	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCountries() {
		// test
		return Response.status(Response.Status.OK).entity("HelloWorld").build();
	}
	
	
	@GET
	@Path("rules")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRules() {
		// test
		return Response.status(Response.Status.OK).entity("getAllRules").build();
	}
	
	@GET
	@Path("rules/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRuleById(@PathParam("id") int id) {
		
		return Response.status(Response.Status.OK).entity("getRuleById "+id).build();
	}
	
	@POST
	@Path("rules")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRule(String rule) {
		
		return Response.status(Response.Status.CREATED).entity("addRule "+rule).build();
	}
	
	@PUT
	@Path("rules/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRuleById(@PathParam("id") int id, String rule) {

		return Response.status(Response.Status.OK).entity("updateRuleById "+id + "/n" + rule).build();
	}

	@DELETE
	@Path("rules/{id}")
	public Response deleteRuleById(@PathParam("id") int id) {

		return Response.status(Response.Status.NO_CONTENT).entity("deleteRuleById "+id).build();
	}
	
	// ----------------

	// Single Rule Evaluation
	@POST
	@Path("rules/{id}/eval")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response evalRuleById(@PathParam("id") int id, String data) {
		
		return Response.status(Response.Status.OK).entity("evalRuleById "+id).build();
	}
	

	// Full Evaluation
	@POST
	@Path("rules/eval")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response evalAllRules(String data) {
		
		return Response.status(Response.Status.OK).entity("evalAllRules ").build();
	}
	

	
}
