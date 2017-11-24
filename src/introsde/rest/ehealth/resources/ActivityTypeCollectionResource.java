package introsde.rest.ehealth.resources;
import introsde.rest.ehealth.model.*;

import java.io.IOException;
import java.util.List;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/activity_types")
public class ActivityTypeCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="assignment2")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "assignment2",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;

    // Return the list of people to the user in the browser
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    //@Produces({  MediaType.APPLICATION_XML })
    public List<ActivityType> getActivityTypeBrowser() {
        System.out.println("Getting list of activitytypes...");
        List<ActivityType> at = ActivityType.getAll();
        /*List<String> activityList=new ArrayList<String>();
        for (int i=0;i<at.size();i++) {
        	activityList.add(at.get(i).getAtName());
        }*/

        /*String[] activityTypes = new String[activityList.size()];
        activityTypes = activityList.toArray(activityTypes);
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("activityTypes", activityTypes);
        return map;*/
        return at;
        //return Response.status(Status.ACCEPTED).entity(at).build();
    }

    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        System.out.println("Getting count...");
        List<ActivityType> at = ActivityType.getAll();
        int count = at.size();
        return String.valueOf(count);
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public ActivityType newActivityType(ActivityType at) throws IOException {
        System.out.println("Creating new activityType..."); 
        ActivityType.savePerson(at);
        return at;
    }

    // Defines that the next path parameter after the base url is
    // treated as a parameter and passed to the PersonResources
    // Allows to type http://localhost:599/base_url/1
    // 1 will be treaded as parameter todo and passed to PersonResource
    @Path("{name}")
    public ActivityTypeResource getActivityType(@PathParam("name") String id) {
        return new ActivityTypeResource(uriInfo, request, id);
    }
}