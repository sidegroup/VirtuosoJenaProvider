package example;

import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

//import virtuoso.jena.driver.*;

public class SelectSPARQL {

	public static void main(String[] args) {
		VirtGraph set = new VirtGraph ("jdbc:virtuoso://192.168.99.100:32769", "dba", "dba");
		org.apache.jena.query.Query sparql = QueryFactory.create("SELECT * WHERE { GRAPH ?graph { ?s ?p ?o } } limit 100");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set);

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
		    RDFNode graph = result.get("graph");
		    RDFNode s = result.get("s");
		    RDFNode p = result.get("p");
		    RDFNode o = result.get("o");
		    System.out.println(graph + " { " + s + " " + p + " " + o + " . }");
		}
				
	}

}
