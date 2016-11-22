package example;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import virtuoso.jena.driver.*;

public class LoadDataSPARQL {

	public static void main(String[] args) {
		VirtGraph graph = new VirtGraph("load:test", "jdbc:virtuoso://192.168.99.100:32769", "dba", "dba");
		
		graph.clear();
		
		System.out.print ("Begin read from 'http://www.w3.org/People/Berners-Lee/card#i'  ");
		graph.read("http://www.w3.org/People/Berners-Lee/card#i", "RDF/XML");
		System.out.println ("\t\t\t Done.");
		
		System.out.print ("Begin read from 'http://demo.openlinksw.com/dataspace/person/demo#this'  ");
		graph.read("http://demo.openlinksw.com/dataspace/person/demo#this", "RDF/XML");
		System.out.println ("\t Done.");

		System.out.print ("Begin read from 'http://kidehen.idehen.net/dataspace/person/kidehen#this'  ");
		graph.read("http://kidehen.idehen.net/dataspace/person/kidehen#this", "RDF/XML");
		System.out.println ("\t Done.");
		
		
		Query sparql = QueryFactory.create("SELECT ?s ?p ?o WHERE { ?s ?p ?o }");
		
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, graph);
		
		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
		    RDFNode graph_name = result.get("graph");
		    RDFNode s = result.get("s");
		    RDFNode p = result.get("p");
		    RDFNode o = result.get("o");
		    System.out.println(graph_name + " { " + s + " " + p + " " + o + " . }");
		}

		System.out.println("graph.getCount() = " + graph.getCount());

	}

}
