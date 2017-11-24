package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Activity;
import introsde.rest.ehealth.model.ActivityType;
import introsde.rest.ehealth.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;


@Stateless
@LocalBean
public class PersonResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	EntityManager entityManager;
	
	int id;

	public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.entityManager = em;
	}
	
	public PersonResource(UriInfo uriInfo, Request request,int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	
	// Application integration
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPerson() {
		Person person = Person.getPersonById(id);System.out.println("Ho una persona null credo");
		if (person == null) {
			return Response.status(Status.NOT_FOUND).build();
			///throw new RuntimeException("Get: Person with " + id + " not found");
		}
		return Response.ok().entity(person).build();
		
	}

	/*
	// for the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Response getPersonHTML() {
		Person person = Person.getPersonById(id);
		System.out.println(person.toString());
		if (person == null) {
			System.out.println("ciaone");
			return Response.status(Status.NOT_FOUND).build();
			//throw new RuntimeException("Get: Person with " + id + " not found");
		}
		System.out.println("Returning person... " + person.getIdPerson());
		return null;
	}*/
	
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{activity_type}")
    public List<Activity> getPersonActivityTypeFiltered(@PathParam("activity_type") String activityType, @DefaultValue("N/A") @QueryParam("before") String beforeDate, @DefaultValue("N/A") @QueryParam("after") String afterDate){
    	System.out.println(beforeDate);
    	System.out.println(afterDate);
    	if(!beforeDate.equals("N/A")&&!afterDate.equals("N/A")) {
	    	System.out.println("Im here");
	    	List<Activity> returnlist=new ArrayList<Activity>();
	    	if(ActivityType.getPersonById(activityType).getAtName().equals(activityType)) {
	    		Person p=Person.getPersonById(id);
	    		List<Activity> mylist=p.getActivities();
	    		for(int i=0;i<mylist.size();i++) {
	    			if(mylist.get(i).getType().getAtName().equals(activityType)&&betweenDate(afterDate,beforeDate,mylist.get(i).getStartdate())){
	    				returnlist.add(mylist.get(i));
	    			}
	    		}
	    	}
	    	return returnlist;
    	}
    	System.out.println("Sadly im here");
    	Person p=Person.getPersonById(id);
    	List<Activity> filteredActivities=new ArrayList<Activity>();
    	for (int i=0;i<p.getActivities().size();i++) {
    		if(p.getActivities().get(i).getType().getAtName().equals(activityType)) {
    			filteredActivities.add(p.getActivities().get(i));
    		}
    	}
    	return filteredActivities;
    }
    
    public boolean betweenDate(String a, String b, String testedString) {
    	testedString=testedString.replace("-", "");
    	testedString=testedString.replace(":", "");
    	a=a.replace("-", "");
    	a=a.replace(":", "");
    	b=b.replace("-", "");
    	b=b.replace(":", "");
    	System.out.println(a);
    	System.out.println(b);
    	System.out.println(testedString);
    	if(a.compareTo(testedString)<0 && b.compareTo(testedString)>0)
    		return true;
    	return false;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{activity_type}/{activity_id}")
    public Activity getActivity(@PathParam("activity_type") String activityType,@PathParam("activity_id")int activityId) {
    	Person p=Person.getPersonById(id);
    	List<Activity> filteredActivities=new ArrayList<Activity>();
    	for (int i=0;i<p.getActivities().size();i++) {
    		if(p.getActivities().get(i).getType().getAtName().equals(activityType)) {
    			if(p.getActivities().get(i).getIdActivity()==activityId)
    				filteredActivities.add(p.getActivities().get(i));
    		}
    	}
    	if(filteredActivities.size()>0)
    		return filteredActivities.get(0);
    	return null;
    }
    
    @POST
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Path("{activity_type}")
    public Response changeActivityType(@PathParam("activity_type") String activity_type, Activity a) throws IOException {
        Person p=Person.getPersonById(id);
        Activity old=p.getActivities().get(0);
        Activity.removeLifeStatus(old);
        a.setIdActivity(old.getIdActivity());
        ActivityType at=ActivityType.getPersonById(activity_type);
        if (at==null) {
        	at=new ActivityType();
        	at.setAtName(activity_type);
        	ActivityType.savePerson(at);
        }
        a.setType(at);
        old.setIdActivity(Activity.getAll().size()+102);
        p.getActivities().add(old);
        p.getActivities().add(a);
        Person.updatePerson(p);
        System.out.println("Updated a");
        //List<Activity> mylist=Activity.getAll();
        return Response.status(Status.CREATED).entity(a).build();
    }

	@PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        System.out.println("--> Updating Person... " +this.id);
        System.out.println("--> "+person.toString());
        System.out.println(person.getFirstname()+" "+person.getLastname()+" "+person.getBirthdate()+" "+person.getIdPerson()+" "+person.getActivities());
        Person existing = Person.getPersonById(this.id);

        if (existing == null) {
            return Response.noContent().build();
        } else {
            person.setIdPerson(this.id);
            if(person.getFirstname()==null)
            	person.setFirstname(existing.getFirstname());
            if(person.getLastname()==null)
            	person.setLastname(existing.getLastname());
            if(person.getBirthdate()==null)
            	person.setBirthdate(existing.getBirthdate());
            person.setActivities(existing.getActivities());
            Person.updatePerson(person);
            return Response.created(uriInfo.getAbsolutePath()).entity(person).build();
        }
    }
	
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{activity_type}/{activity_id}")
    public Response putActivityTypeInActivity(@PathParam("activity_type") String activityType,@PathParam("activity_id")int activityId, Activity newA) {
    	Person p=Person.getPersonById(id);
    	List<Activity> activitylist=p.getActivities();
    	ActivityType at=null;
    	if(newA.getType()!=null) {
    		at=ActivityType.getPersonById(newA.getType().getAtName());
    		if (at==null) {
    			at=new ActivityType();
    			at.setAtName(newA.getType().getAtName());
    			ActivityType.savePerson(at);
    			System.out.println("I created a new activitytype");
    		}
    	}
    	for (int i=0;i<activitylist.size();i++) {
    		if(activitylist.get(i).getIdActivity()==activityId) {
    			Activity returnActivity=activitylist.get(i);
    			if(newA.getName()!=null)
    				returnActivity.setName(newA.getName());
    			if(newA.getDescription()!=null)
    				returnActivity.setDescription(newA.getDescription());
    			if(newA.getPlace()!=null)
    				returnActivity.setPlace(newA.getPlace());
    			if(newA.getStartdate()!=null)
    				returnActivity.setStartdate(newA.getStartdate());
    			if(newA.getDescription()!=null)
    				returnActivity.setDescription(newA.getDescription());
    			if(at!=null)
    				activitylist.get(i).setType(at);
    			Person.updatePerson(p);
    			return Response.ok().entity(activitylist.get(i)).build();
    		}
    	}
    	return Response.notModified().build();
    }

	@DELETE
	public void deletePerson() {
		Person c = Person.getPersonById(id);
		if (c == null)
			throw new RuntimeException("Delete: Person with " + id
					+ " not found");

		Person.removePerson(c);
	}

	
	/*public Person getPersonById(int personId) {
		System.out.println("Reading person from DB with id: "+personId);
		//Person person = entityManager.find(Person.class, personId);
		
		Person person = Person.getPersonById(personId);
		System.out.println("Person: "+person.toString());
		return person;
	}*/
}
