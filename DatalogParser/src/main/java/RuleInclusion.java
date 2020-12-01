/**
 * The type Rule inclusion. Represents the datalog rules that represents rule inclusion produced by clipper
 */
public class RuleInclusion extends DatalogRule{
    /**
     * The Binary clause of the body.
     */
    String binaryClause;

    /**
     * Instantiates a new Rule inclusion.
     *
     * @param rule the datalog rule in String
     */
    public RuleInclusion(String rule){
        String[] splitted_rule = rule.split(" :- ");
        head = splitted_rule[0].substring(0, splitted_rule[0].length() - 5);
        binaryClause = splitted_rule[1].substring(0, splitted_rule[1].length() - 6);
        body = new String[]{binaryClause};

    }

    @Override
    public String selectQuery() {
        return "select * from " + binaryClause;
    }

    @Override
    public String toString(){
        return "inc[" + head + ", " + binaryClause + "]";
    }
}
