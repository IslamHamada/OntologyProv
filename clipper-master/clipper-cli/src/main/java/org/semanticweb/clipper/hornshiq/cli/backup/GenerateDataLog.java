package org.semanticweb.clipper.hornshiq.cli.backup;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.IOException;


public class GenerateDataLog {

	public static String ontologyFile;
	public static String sparqlFile;
//	public static String dlvPath;
	public static String purpose;

	/**
	 * @param args
	 * @throws RecognitionException
	 * @throws OWLOntologyCreationException
	 * @throws IOException
	 */
	public static void main(String[] args) throws RecognitionException, OWLOntologyCreationException, IOException {
		if (!parseArgs(args)) {
			printUsage();
			System.exit(1);
		}

		System.setProperty("entityExpansionLimit", "512000");
		CQ cq = parseCQ();
		//cq.getHead().getPredicate().getEncoding();
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		//note that naming strategy shoud be set after create new QAHornSHIQ
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
		qaHornSHIQ.setOntologyName(ontologyFile);
		if (purpose.equals("-a")){
		qaHornSHIQ.setDatalogFileName("ABox."+ontologyFile + ".dl");
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.generateABoxDatalog();
		} else if (purpose.equals("-t")){
			qaHornSHIQ.setDatalogFileName(sparqlFile + "QueryAndRules-" + ontologyFile + "-" + ".dl");
			qaHornSHIQ.setQuery(cq);
			qaHornSHIQ.generateQueriesAndCompletionRulesDatalog();
		} else if (purpose.equals("-p")){
			qaHornSHIQ.setDatalogFileName(sparqlFile + "Program-" + ontologyFile + "-" + ".dl");
			qaHornSHIQ.setQuery(cq);

			qaHornSHIQ.generateDatalog();
		}
//		System.out.println("Ontology parsing and normalization time:                      " + qaHornSHIQ.getNormalizationTime() + "  milliseconds");
//		System.out.println("Reasoning time:                                               " + qaHornSHIQ.getReasoningTime()
//				+ "  milliseconds");
//		System.out.println("Query rewriting time:                                         "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  milliseconds");
		long totalTime= qaHornSHIQ.getClipperReport().getReasoningTime() 	+ qaHornSHIQ.getClipperReport().getQueryRewritingTime();
		System.out.println(qaHornSHIQ.getClipperReport().getNumberOfRewrittenQueries()+ " " + qaHornSHIQ.getClipperReport().getNumberOfRewrittenQueriesAndRules() + " " + totalTime);
//		System.out.println("Total time for query rewriting (reasoning + rewriting time):  "
//				+ totalTime + "  milliseconds");
//		System.out.println("Time of running datalog program:                              " + qaHornSHIQ.getDatalogRunTime() + "  milliseconds");
//		System.out.println("Time for output answer  :                                     " + qaHornSHIQ.getOutputAnswerTime() + "  milliseconds");
//		System.out.println("Time for counting queries realted rules (just for benchmark): " + qaHornSHIQ.getCoutingRealtedRulesTime() + "  milliseconds");
//		long runningTime= endTime -startTime - qaHornSHIQ.getCoutingRealtedRulesTime();
//		System.out.println("Total running time of the whole system:                       " + runningTime + "  milliseconds");
	}

	protected static CQ parseCQ() throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(sparqlFile);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();
		return cq;
	}

	public static boolean parseArgs(String[] args) {
		int i = 0;
		while (i < args.length) {
			if (args[i].equals("-ontology")) {
				ontologyFile = args[i + 1];
				i += 2;
			} else if (args[i].equals("-sparql")) {
				sparqlFile = args[i + 1];
				i += 2;
			} else if (args[i].equals("-p") || args[i].equals("-t") || args[i].equals("-a")) {
				purpose = args[i];
				i += 1;
			} else if (args[i].equals("-verbose")) {
				ClipperManager.getInstance().setVerboseLevel(Integer.parseInt(args[i + 1]));
				i += 2;
			} else {
				return false;
			}
		}

		if (ontologyFile != null && sparqlFile != null && purpose != null) {
			return true;
		}

		return false;
	}

	private static void printUsage() {

		String usage = //
		"Usage: kaos -ontology <ontology_file> -sparql <sparql_file> <purpose> [-verbose <verbose_level>]\n" + //
				"  <ontology_file>\n" + //
				"    the ontology file to be read, which has to be in Horn-SHIQ fragment \n" + //
				"  <sparql_file>\n" + //
				"    the sparql file to be query, which has to be a Conjunctive Query. \n" + //
				"  <purpose>\n" + //
				"    -p Completed program, -t queries and completion rules, -a Abox \n" + //
				"  <verbose_level>\n" + //
				"    Specify verbose category (default: 0)\n" + "\n" + //
				"Example: java -jar kaos.jar -ontology university.owl -sparql q1.sparql -dlv /usr/bin/dlv " //
		;

		System.out.println(usage);

	}

}