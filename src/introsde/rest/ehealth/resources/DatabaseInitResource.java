package introsde.rest.ehealth.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import introsde.rest.ehealth.model.Activity;
import introsde.rest.ehealth.model.ActivityType;
import introsde.rest.ehealth.model.Person;

@Stateless
@LocalBean//Will map the resource to the URL /ehealth/v2
@Path("/init")
public class DatabaseInitResource {
	@GET
	@Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
	public List<Person> getPersonsBrowser() {
		if(ActivityType.getAll().size()>0) {
			for(ActivityType at:ActivityType.getAll())
				ActivityType.removePerson(at);
		}
		if(Activity.getAll().size()>0) {
			for(Activity a:Activity.getAll())
				Activity.removeLifeStatus(a);
		}
		if(Person.getAll().size()>0) {
			for(Person p:Person.getAll())
				Person.removePerson(p);
		}
		
		ActivityType at;
    	at=new ActivityType();
    	at.setAtName("Videogaming");
    	ActivityType.savePerson(at);
    	at=new ActivityType();
    	at.setAtName("Sport");
    	ActivityType.savePerson(at);
    	at=new ActivityType();
    	at.setAtName("Studying");
    	ActivityType.savePerson(at);
    	
    	Person p;
    	List<Activity> activitylist=new ArrayList<Activity>();
    	p=new Person();
    	p.setIdPerson(1);
    	p.setFirstname("Mattia");
    	p.setLastname("Buffa");
    	p.setBirthdate("1992-02-15");
    	Activity a;
    	a=new Activity();
    	a.setIdActivity(101);
    	a.setName("Playing PUBG");
    	a.setDescription("Playing the PUBG videogame on PC");
    	a.setPlace("Home");
    	a.setStartdate("2017-11-24-15:00");
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Videogaming"));
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	//ActivityDAO.saveActivity(a);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(2);
    	p.setFirstname("Denis");
    	p.setLastname("Gallo");
    	p.setBirthdate("1993-04-15");
    	a.setIdActivity(102);
    	a.setName("Playing LOL");
    	a.setDescription("Playing League Of Legends on PC");
    	a.setPlace("Home");
    	a.setStartdate("2017-11-25-18:25");
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Videogaming"));
    	//ActivityDAO.saveActivity(a);
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(3);
    	p.setFirstname("Linda");
    	p.setLastname("Bertolli");
    	p.setBirthdate("1994-05-24");
    	a.setIdActivity(103);
    	a.setName("Studying SDE");
    	a.setDescription("Studying the Theory ot SDE Course");
    	a.setPlace("Library");
    	a.setStartdate("2017-12-01-08:22");
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Studying"));
    	//ActivityDAO.saveActivity(a);
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(4);
    	p.setFirstname("Enrico");
    	p.setLastname("Tomiello");
    	p.setBirthdate("1994-11-24");
    	a.setIdActivity(104);
    	a.setName("Jogging");
    	a.setDescription("Running in the morning");
    	a.setPlace("Trento");
    	a.setStartdate("2017-12-12-08:44");
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Sport"));
    	//ActivityDAO.saveActivity(a);
      	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	a=new Activity();
    	p=new Person();
    	activitylist=new ArrayList<Activity>();
    	p.setIdPerson(5);
    	p.setFirstname("Christian");
    	p.setLastname("Corso");
    	p.setBirthdate("1995-08-22");
    	a.setIdActivity(105);
    	a.setName("Tennis");
    	a.setDescription("Playing Tennis");
    	a.setPlace("Trento");
    	a.setStartdate("2017-11-30-17:33");
    	a.setPerson(p);
    	a.setType(ActivityType.getPersonById("Sport"));
    	//ActivityDAO.saveActivity(a);
    	activitylist.add(a);
    	p.setActivities(activitylist);
    	Person.savePerson(p);
    	
    	System.out.println("Persondb size is "+Person.getAll().size()+" and activitydb size is "+Activity.getAll().size()+" and activitytypesdb size is "+ActivityType.getAll().size());   	
        
		System.out.println("Getting list of people...");
	    List<Person> people = Person.getAll();
		return people;
	}
}

