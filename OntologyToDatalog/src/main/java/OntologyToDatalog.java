import com.mainproject.commons.GlobalVaribales;
import org.antlr.runtime.*;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;

import com.mainproject.commons.GlobalVaribales;

public class OntologyToDatalog {

    //to generate the datalog, at least one query needs to be executed. There might be other ways but I couldn't find any yet
    public static void main(String args[]) throws OWLOntologyCreationException, RecognitionException {
        String tmpDatalogFile = GlobalVaribales.datalog_path;
        String ontologyFile = GlobalVaribales.ontology_path;

        String sparqlString = "PREFIX : <http://www.semanticweb.org/islam/ontologies/2020/9/untitled-ontology-48#>\n"+
                "SELECT ?x {\n" +
                "?x a :Object .\n" +
                "}\n";
        System.out.println(sparqlString);
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(1);
        qaHornSHIQ.setDatalogFileName(tmpDatalogFile);
        qaHornSHIQ.setOntologyName(ontologyFile);
        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);
        CharStream stream = new ANTLRStringStream(sparqlString);
        SparqlLexer lexer = new SparqlLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        SparqlParser parser = new SparqlParser(tokenStream);
        CQ cq = parser.query();

        String queryString = cq.toString();
        qaHornSHIQ.setQuery(cq);
        qaHornSHIQ.execQuery();

        //uncomment if you are interested in seeing the outcome of the query
//        System.out.println(qaHornSHIQ.getAnswers());
    }
}
