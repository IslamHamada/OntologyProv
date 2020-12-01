/**
 * The type Existential. Represents the datalog rules, where the body has an existential rule, produced by clipper
 */
public class Existential extends DatalogRule{
    /**
     * The Unary clause in the body(the concept name).
     */
    String unaryClause;
    /**
     * The Binary clause in the body(the rule name).
     */
    String binaryClause;

    /**
     * Instantiates a new Existential.
     *
     * @param rule the datalog rule in String
     */
    public Existential(String rule) {
        String[] splitted_rule = rule.split(" :- ");
        head = splitted_rule[0].substring(0, splitted_rule[0].length() - 3);
        String[] body = splitted_rule[1].split(", ");
        String last_clause = body[body.length - 1];
        body[body.length - 1] = last_clause.substring(0, last_clause.length() - 1);

        String[] test = body[0].split(",");

        //check whether the unary clause is the first clause
        if(test.length == 1) {
            unaryClause = body[0].substring(0, body[0].length() - 3);
            binaryClause = body[1].substring(0, body[1].length() - 5);
        } else {
            unaryClause = body[1].substring(0, body[1].length() - 3);
            binaryClause = body[0].substring(0, body[0].length() - 5);
        }
        this.body = new String[]{unaryClause, binaryClause};
    }

    @Override
    public String selectQuery() {
//        return "select " + binaryClause + ".v1, " + unaryClause + ".provsql" + " from " + binaryClause + " join " + unaryClause + " on " + binaryClause + ".v2 = " + unaryClause + ".v1";
        return "select " + binaryClause + ".v1 from " + binaryClause + " join " + unaryClause + " on " + binaryClause + ".v2 = " + unaryClause + ".v1";
    }

    @Override
    public String toString(){
        return "exis[" + unaryClause + "(1), " + binaryClause + "(2)" + "]";
    }
}
