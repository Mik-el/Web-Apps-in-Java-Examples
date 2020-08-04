//code by Mik-el
package math;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
/*
 * Per accedere alla risorsa dal client devo utilizzare la seguente URI:
 * http://localhost:8080/esercizio14-1RestfulProdotto/rest/products?op1=10&op2=3
 */
@Path("/products")
@Produces("text/plain")
public class MultiplierService {
	@GET//il metodo verra eseguito con una richiesta di tipo GET
	public Response multiply (@QueryParam("op1") double a, @QueryParam("op2") double b){
		Double p = a*b;
		return Response.ok(p.toString()).build();//il punto build crea l'oggetto di tipo response (cioè costruisce la risposta)
	}
	
}
