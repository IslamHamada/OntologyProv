import java.util.Arrays;

/**
 * The type Conjunction. Represents the datalog rules, where the body is a conjunction, produced by clipper
 */
public class Conjunction extends DatalogRule{
    /**
     * The Unary clauses in the body of the conjunction.
     */
    String[] unaryClauses;

    /**
     * Instantiates a new Conjunction.
     *
     * @param rule the datalog conjunction rule in String
     */
    public Conjunction(String rule){
        String[] splitted_rule = rule.split(" :- ");
        head = splitted_rule[0].substring(0, splitted_rule[0].length() - 3);
        unaryClauses = splitted_rule[1].split(", ");
        String last_rule = unaryClauses[unaryClauses.length - 1];

        //remove the full-stop at the end
        unaryClauses[unaryClauses.length - 1] = last_rule.substring(0, last_rule.length() - 1);
        for(int i = 0; i < unaryClauses.length; i++){
            String current_rule = unaryClauses[i];
            unaryClauses[i] = current_rule.substring(0, current_rule.length() - 3);
        }
        body = unaryClauses;
    }

    @Override
    public String selectQuery() {
        String result = "select " + body[0] + ".v1 from " + body[0];
        for(int i = 1; i < body.length; i++){
            result += " join " + body[i] + " on " + body[i - 1] + ".v1" + " = " + body[i] + ".v1";
        }

        return result;
    }

    @Override
    public String toString(){
        return "con"+ Arrays.toString(unaryClauses);
    }
}
