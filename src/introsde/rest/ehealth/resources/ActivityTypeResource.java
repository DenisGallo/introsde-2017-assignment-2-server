package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.*;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class ActivityTypeResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public ActivityTypeResource(UriInfo uriInfo, Request request,String id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public ActivityTypeResource(UriInfo uriInfo, Request request,String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ActivityType getActivityType() {
    	ActivityType at = this.getActivityTypeById(id);
        if (at == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        return at;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public ActivityType getActivityTypeHTML() {
    	ActivityType at = this.getActivityTypeById(id);
        if (at == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        System.out.println("Returning person... " + at.getAtName());
        return at;
    }


    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putActivityType(ActivityType at) {
        System.out.println("--> Updating Person... " +this.id);
        System.out.println("--> "+at.toString());
        ActivityType.updatePerson(at);
        ActivityType existing = getActivityTypeById(this.id);

        if (existing == null) {
            return Response.noContent().build();
        } else {
            at.setAtName(this.id);
            ActivityType.updatePerson(at);
            return Response.created(uriInfo.getAbsolutePath()).entity(at).build();
        }
        
    }

    @DELETE
    public void deleteActivityType() {
    	ActivityType c = getActivityTypeById(id);
        if (c == null)
            throw new RuntimeException("Delete: Person with " + id
                    + " not found");
        ActivityType.removePerson(c);
    }

    public ActivityType getActivityTypeById(String name) {
        System.out.println("Reading ActivityType from DB with id: "+name);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        ActivityType at = ActivityType.getPersonById(name);
        System.out.println("Person: "+at.toString());
        return at;
    }
}