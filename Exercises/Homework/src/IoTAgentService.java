//code by Mik-el
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@WebService
public interface IoTAgentService {
	
	// @GET /nodes /* invoked by consumers */
	public Response getNodesList();
	
	// @GET /nodes/{nodeId} /* invoked by consumers */
	public Response getNodeById(@PathParam("nodeId") int nodeId);
	
	// @GET /nodes/node?{latitude}&{longitude}&{radius} /* invoked by consumers */
	public Response getNodeByPosition(@QueryParam("latitude") double latitude, @QueryParam("longitude") double longitude, @QueryParam("radius") double radius);
	
	// @GET /nodes/{nodeId}/sensors/{attribute} /* invoked by consumers */
	public Response getSensor(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute);
	
	// @GET /nodes/{nodeId}/sensors/{attribute}/samples/{sampleId} /* invoked by consumers */
	public Response getSample(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, @PathParam("sampleId") int sampleId );
	
	// @GET /nodes/{nodeId}/sensors/{attribute}/samples_average /* invoked by consumers */
	public Response getSampleAverage(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute);
	
	// @POST /nodes /* invoked by nodes */
	public Response addNode(Node node);
	
	// @POST /nodes/{nodeId}/sensors /* invoked by nodes */
	public Response addSensor(@PathParam("nodeId") int nodeId, Sensor sensor);
	
	// @POST /nodes/{nodeId}/sensors/{attribute}/samples /* invoked by nodes */
	public Response addSample(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, Sample sample);
	
	// @PUT /nodes/{nodeId} /* invoked by nodes */
	public void setNodeProperties(@PathParam("nodeId") int nodeId, Node node);
	
	// @PUT /nodes/{nodeId}/sensors/{attribute} /* invoked by nodes */
	public void setSensorProperties(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, Sensor sensor);
	
	// @DELETE /nodes/{nodeId} /* invoked by nodes or infrastructure */
	public void deleteNode(@PathParam("nodeId") int nodeId);
	
	// @DELETE /nodes/{nodeId}/sensors/{attribute} /* invoked by nodes */
	public void deleteSensor(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute);
	
	// @DELETE /nodes/{nodeId}/sensors/{attribute}/samples/{sampleId} /* invoked by nodes or consumers */
	public void deleteSample(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, @PathParam("sampleId") int sampleId);
	
}
