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
import javax.ws.rs.core.Response.Status;

import cz.lukastrnka.rest.service.NonUniqueNameException;
import cz.lukastrnka.rest.service.RuleService;
import cz.lukastrnka.rest.service.UnknownArgumentException;

@Path("/")
public class Resource {

	RuleService ruleService = new RuleService();

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
	public Response getRuleById(@PathParam("id") long id) {

		if (ruleService.getRule(id) == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(ruleService.getRule(id)).build();
		}
		return Response.status(Response.Status.OK).entity(ruleService.getRule(id)).build();
	}

	@POST
	@Path("rules")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRule(String rule) {

		try {
			return Response.status(Response.Status.CREATED).entity(ruleService.addRule(rule)).build();
		} catch (UnknownArgumentException uae) {
			// System.out.println("catch unknownArg exc!!");
			// return Response.status(Response.Status.BAD_REQUEST).entity("{\"unknown
			// argument\":"+e.getErrNode() + "}").build();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"code\": \"400\",\"unknown argument\" : [" + uae.getErrNode() + "]}").build();
		} catch (NonUniqueNameException nune) {
			return Response.status(Response.Status.BAD_REQUEST).entity("not unique name :" + nune.getName()).build();
		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"code\": \"400\",\"Illegal Argument\" : \"" + iae.getMessage() + "\"}").build();
		}

	}

	@PUT
	@Path("rules/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRuleById(@PathParam("id") long id, String rule) {

		return Response.status(Response.Status.OK).entity("updateRuleById " + id + "/n" + rule).build();
	}

	@DELETE
	@Path("rules/{id}")
	public Response deleteRuleById(@PathParam("id") long id) {
		if (ruleService.getRule(id) == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(ruleService.getRule(id)).build();
		}
		ruleService.deleteRule(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	// ====================================

	// Single Rule Evaluation
	@POST
	@Path("rules/{id}/eval")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response evalRuleById(@PathParam("id") long id, String data) {

		return Response.status(Response.Status.OK).entity("evalRuleById " + id).build();
	}

	// Full Evaluation
	@POST
	@Path("rules/eval")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response evalAllRules(String data) {
		System.out.println("evalAllRules - data: " + data);
		return Response.status(Response.Status.OK).entity("evalAllRules ").build();
	}

}
