package example;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

public class InsertDataSPARQL {

	public static void main(String[] args) {
		VirtGraph set = new VirtGraph("jdbc:virtuoso://192.168.99.100:32769", "dba", "dba");
		
		System.out.println("\nexecute: CLEAR GRAPH <http://test1>");
        String str = "CLEAR GRAPH <http://test1>";
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();  
        
        System.out.println("\nexecute: INSERT INTO GRAPH <http://test1> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }");
        str = "INSERT INTO GRAPH <http://test1> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }";
        vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();
        
        System.out.println("\nexecute: SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
		Query sparql = QueryFactory.create("SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);
		
		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
		    RDFNode s = rs.get("s");
		    RDFNode p = rs.get("p");
		    RDFNode o = rs.get("o");
		    System.out.println(" { " + s + " " + p + " " + o + " . }");
		}

	}

}
