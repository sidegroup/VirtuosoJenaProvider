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
//		String port = "1111"; // 1111 32769 31007
//		String port = "31007";
		String port = "32771";
//		String address = "200.129.79.59"; // 192.168.99.100 10.0.24.146 200.129.79.59 localhost
//		String address = "10.0.24.146";
		String address = "192.168.99.100";
		String username = "dba";
		String password = "dba";
		VirtGraph set = new VirtGraph("jdbc:virtuoso://"+address+":"+port, username, password);
		
		System.out.println("\nexecute: CLEAR GRAPH <nodeID://test1>");
        String str = "CLEAR GRAPH <nodeID://test1>";
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();  
        
        System.out.println("\nexecute: INSERT INTO GRAPH <nodeID://test1> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }");
        str = "INSERT INTO GRAPH <http://nodeID> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }";
        vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();
        
        System.out.println("\nexecute: SELECT * FROM <nodeID://test1> WHERE { ?s ?p ?o }");
		Query sparql = QueryFactory.create("SELECT * FROM <nodeID://test1> WHERE { ?s ?p ?o }");
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
