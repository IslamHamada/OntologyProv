/**
 * The type Datalog rule. Represents the datalog rules produced by Clipper
 */
public abstract class DatalogRule {
    /**
     * The Head of a rule.
     */
    String head;
    /**
     * The Body of a rule.
     */
    String[] body;

    /**
     * Instantiates a new Datalog rule.
     */
    public DatalogRule(){

    }

    /**
     * Instantiates a new Datalog rule.
     *
     * @param head the head of a rule
     */
    public DatalogRule(String head){
        this.head = head;
    }

    /**
     * @return a select query for the datalog rule
     */
    abstract public String selectQuery();
}
