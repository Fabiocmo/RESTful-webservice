package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mukeshmaurya.Student;
import mukeshmaurya.StudentResult;
import service.MyConnection;

@Path("/rest")
public class StudentResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
	    return "Service is running!";
	}
	
	@GET
	@Path("/roll={roll}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudentDetails(@PathParam("roll") String roll){
		Student student = new Student();
		student.setRoll(roll);
		MyConnection conn= new MyConnection();
		String status=conn.connect(student);
		if(status.equals("SUCCESS"))
		return student;
		else
		return new Student();
	}
	@GET
	@Path("/roll={roll}&pass={pass}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudentDetails(@PathParam("roll") String roll,@PathParam("pass") String pass){
		Student student = new Student();
		student.setRoll(roll);
		student.setPass(pass);
		MyConnection conn= new MyConnection();
		String status=conn.connectAs(student);
		if(status.equals("SUCCESS"))
		return student;
		else
		return new Student();
	}
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response setStudentDetail(@FormParam("name") String name,@FormParam("roll") String roll,@FormParam("email") String email,@FormParam("mobile") String mobile,@FormParam("pass") String pass){
		Student student = new Student();
		student.setRoll(roll);
		student.setPass(pass);
		student.setName(name);
		student.setEmail(email);
		student.setMobile(mobile);
		MyConnection conn= new MyConnection();
		String status=conn.connectWithDetail(student);
		System.out.println(status);
		if(status.equals("SUCCESS"))
		return Response.status(200).entity(status).build();
		else
		return Response.status(400).entity(status).build();
	}
	@GET
	@Path("/result/roll={roll}")
	@Produces(MediaType.APPLICATION_JSON)
	public StudentResult getStudentResult(@PathParam("roll") String roll){
		StudentResult studentResult=new StudentResult();
		MyConnection conn= new MyConnection();
		String status=conn.connectForResult(studentResult,roll);
		if(status.equals("SUCCESS"))
		return studentResult;
		else
		return new StudentResult();
	}
}
