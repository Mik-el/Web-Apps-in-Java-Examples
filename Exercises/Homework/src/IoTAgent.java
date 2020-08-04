//code by Mik-el
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Produces ("application/json")
@Consumes ("application/json")
@Path("/nodes")
public class IoTAgent implements IoTAgentService {
	private HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();

	//@Override
	@GET
	public Response getNodesList() {
		return Response
				.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.entity(new Nodes(new ArrayList<Node>(nodes.values())))
				.build();
	}

	@Override
	@GET
	@Path("/{nodeId}")
	public Response getNodeById(@PathParam("nodeId") int nodeId) {
		return Response
				.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.entity(nodes.get(nodeId))
				.build();
	}

	@Override
	@GET
	@Path("/node")
	public Response getNodeByPosition(@QueryParam("latitude") double latitude, @QueryParam("longitude") double longitude, @QueryParam("radius") double radius) {
		for(HashMap.Entry<Integer, Node> entry : nodes.entrySet()) {
			Node node = entry.getValue();
			if(node.getLatitude() == latitude && node.getLongitude() == longitude && node.getRadius() == radius)
				return Response
						.status(200)
						.header("Access-Control-Allow-Origin", "*")
						.entity(node)
						.build();
		}
		
		return Response.status(404).build();
	}

	@Override
	@GET
	@Path("/{nodeId}/sensors/{attribute}")
	public Response getSensor(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		for(Sensor sensor : sensors)
			if(sensor.getAttribute().equalsIgnoreCase(attribute))
				return Response
						.status(200)
						.header("Access-Control-Allow-Origin", "*")
						.entity(sensor)
						.build();
		return null;
	}

	@Override
	@GET
	@Path("/{nodeId}/sensors/{attribute}/samples/{sampleId}")
	public Response getSample(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, @PathParam("sampleId") int sampleId) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		for(Sensor sensor : sensors)
			if(sensor.getAttribute().equalsIgnoreCase(attribute))
				for(Sample sample : sensor.getSamplesList())
					if(sample.getId() == sampleId) {
						System.out.println(sample.toString());
						return Response
								.status(200)
								.header("Access-Control-Allow-Origin", "*")
								.entity(sample)
								.build();
					}
		
		return Response.status(404).build();
	}

	@Override
	@GET
	@Path("/{nodeId}/sensors/{attribute}/samples_average")
	public Response getSampleAverage(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		for(Sensor sensor : sensors)
			if(sensor.getAttribute().equalsIgnoreCase(attribute))
				return Response
						.status(200)
						.header("Access-Control-Allow-Origin", "*")
						.entity(sensor.calculateSamplesAverage())
						.build();
		return Response.status(404).build();
	}

	@Override
	@POST
	public Response addNode(Node node) {
		int position = nodes.size();
		
		if(node.getSensors() == null) node.setSensors(new ArrayList<Sensor>());
		
		nodes.put(position, node);
		
		URI uri = null;
		
		try {
			uri = new URI("/nodes/" + position);
		}catch(URISyntaxException e) {
			e.printStackTrace();
		}
		
		return Response.created(uri).build();
	}

	@Override
	@POST
	@Path("/{nodeId}/sensors")
	public Response addSensor(@PathParam("nodeId") int nodeId, Sensor sensor) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		
		if(sensor.getSamplesList() == null) sensor.setSamplesList(new ArrayList<Sample>());
		
		sensors.add(sensor);
		
		URI uri = null;
		try {
			uri = new URI("/nodes/" + nodeId + "/sensors/" + sensor.getAttribute());
		}catch(URISyntaxException e) {
			e.printStackTrace();
		}
		
		return Response.created(uri).build();
	}

	@Override
	@POST
	@Path("/{nodeId}/sensors/{attribute}/samples")
	public Response addSample(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, Sample sample) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		
		for(Sensor sensor: sensors) {
			if(sensor.getAttribute().equalsIgnoreCase(attribute)) {
				List<Sample> samples = sensor.getSamplesList();
				samples.add(sample);
				
				nodes.get(nodeId).setLastSignal(sample.getDate());
			}
		}
					
		URI uri = null;
		try {
			uri = new URI("/nodes/" + nodeId + "/sensors/" + attribute + "/samples/" + sample.getId());
		}catch(URISyntaxException e) {
			e.printStackTrace();
		}
		
		return Response.created(uri).build();
	}

	@Override
	@PUT
	@Path("/{nodeId}")
	public void setNodeProperties(@PathParam("nodeId") int nodeId, Node node) {
		nodes.put(nodeId, node);
	}

	@Override
	@PUT
	@Path("/{nodeId}/sensors/{attribute}")
	public void setSensorProperties(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, Sensor sensor) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		
		for(Sensor s : sensors)
			if(s.getAttribute().equalsIgnoreCase(attribute)) {
				sensors.set(sensors.indexOf(s), sensor);
				
				if(nodes.get(nodeId).getWorkingStatus() != sensor.getWorkingStatus())
					nodes.get(nodeId).setWorkingStatus(sensor.getWorkingStatus());
				return;
			}
	}

	@Override
	@DELETE
	@Path("/{nodeId}")
	public void deleteNode(@PathParam("nodeId") int nodeId) {
		nodes.remove(nodeId);
	}

	@Override
	@DELETE
	@Path("/{nodeId}/sensors/{attribute}")
	public void deleteSensor(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		for(Sensor sensor : sensors)
			if(sensor.getAttribute().equalsIgnoreCase(attribute)) {
				nodes.get(nodeId).getSensors().remove(sensor);
				
				return;
			}
	}

	@Override
	@DELETE
	@Path("/{nodeId}/sensors/{attribute}/samples/{sampleId}")
	public void deleteSample(@PathParam("nodeId") int nodeId, @PathParam("attribute") String attribute, @PathParam("sampleId") int sampleId) {
		List<Sensor> sensors = nodes.get(nodeId).getSensors();
		
		int sensorIndex = 0;
		
		for(Sensor sensor : sensors)
			if(sensor.getAttribute().equalsIgnoreCase(attribute)) {
				sensorIndex = sensors.indexOf(sensor);
				for(Sample sample : sensor.getSamplesList())
					if(sample.getId() == sampleId) {
						nodes.get(nodeId).getSensors().get(sensorIndex).getSamplesList().remove(sample);
						
						return;
					}
			}
	}
}

