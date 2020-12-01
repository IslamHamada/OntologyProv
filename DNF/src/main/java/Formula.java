import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * The type Formula. It's tailored for the formulas produced by the tool provsql, i.e., Only binary operators can be used
 */
public abstract class Formula {
    /**
     * The DNF as sets.
     */
    Set<Set<String>> dnf_as_sets;


    /**
     * Instantiates a new Formula.
     */
    public Formula(){
        dnf_as_sets = new HashSet<>();
    }


    /**
     * check if a character is an operator
     *
     * @param c : a character
     * @return whether the character is an operator or not
     */
    static boolean is_operator(char c){
        return c == '⊗' || c == '⊕';
    }

    /**
     * If the formula doesn't contain operators then it's atomic
     *
     * @param formula : a formula in a string form
     * @return return whether the formula is atomic
     */
    static boolean is_atomic(String formula){
        return !formula.contains("⊕") && !formula.contains("⊗");
    }


    /**
     * if the formula is atomic, return the input formula
     * if the formula is a disjunction, call the function to the right and the left part
     * if the formula is a conjunction apply one of the following transformations accordingly:
     * A ∧ (B ∨ C) is equivalent to (A ∧ B) ∨ (A ∧ C)
     * (A ∨ B) ∧ C is equivalent to (A ∧ C) ∨ (B ∧ C)
     *
     * @param f an input formula
     * @return return a new formula in Disjunctive normal form
     */
    public static Formula toDNF(Formula f){
        if(f instanceof DisjunctiveFormula) {
            DisjunctiveFormula df = (DisjunctiveFormula) f;
            Formula new_left = toDNF(df.left);
            Formula new_right = toDNF(df.right);
            return new DisjunctiveFormula(new_left, new_right);
        } else if (f instanceof ConjunctiveFormula){
            ConjunctiveFormula cf = (ConjunctiveFormula) f;
            if(cf.left instanceof DisjunctiveFormula){
                DisjunctiveFormula left_df = (DisjunctiveFormula) cf.left;
                Formula new_left = toDNF(new ConjunctiveFormula(left_df.left, cf.right));
                Formula new_right = toDNF(new ConjunctiveFormula(left_df.right, cf.right));
                return new DisjunctiveFormula(new_left, new_right);
            } if(cf.right instanceof DisjunctiveFormula) {
                DisjunctiveFormula right_df = (DisjunctiveFormula) cf.right;
                Formula new_left = toDNF(new ConjunctiveFormula(toDNF(cf.left), toDNF(right_df.left)));
                Formula new_right = toDNF(new ConjunctiveFormula(toDNF(cf.left), toDNF(right_df.right)));
                return new DisjunctiveFormula(new_left, new_right);
            }
        }
        return f;
    }

    /**
     * Compute the attribute dnf_as_sets.
     */
    public void create_dnf_as_sets(){
        create_dnf_as_sets(this);
    }

    /**
     * compute the attribute dnf_as_sets for the formula f using this instance (the instance is simply a subformula of f).
     *
     * @param f a formula in DNF
     */
    public abstract void create_dnf_as_sets(Formula f);

    /**
     * computes the set representation of a formula
     *
     * @param formula a formula in DNF
     * @return a set represenation of the formula
     */
    public static Set<String> as_a_set(Formula formula) {
        Set<String> conjunct = new HashSet<>();
        add_to_set(formula, conjunct);
        return conjunct;
    }

    /**
     * Add an atom or a conjunctive formula to a conjunct
     *
     * @param formula a formula in DNF
     * @param conjunct     a conjunct
     */
    public static void add_to_set(Formula formula, Set<String> conjunct) {
        if(formula instanceof Atom)
            conjunct.add(formula.toString());
        else if(formula instanceof ConjunctiveFormula) {
            ConjunctiveFormula cf = (ConjunctiveFormula)formula;
            add_to_set(cf.left, conjunct);
            add_to_set(cf.right, conjunct);
        }
    }

    /**
     * Parse formula. Make sure the propositional variables don't contain these characters: space, (, ), ⊗, ⊕, ∧, ∨
     *
     * @param formula_as_string the formula as a string
     * @return a Formula Object
     */
    public static Formula parse(String formula_as_string){
        //remove white spaces
        formula_as_string = formula_as_string.replaceAll("\\s+","");

        //remove the big brackets if they exist. They are added by the provenance if the formula isn't atomic
        if(formula_as_string.charAt(0)=='(')
            formula_as_string = formula_as_string.substring(1,formula_as_string.length()-1);

        Formula f = null;
        Stack<Character> stack = new Stack<Character>();
        boolean atomic = Formula.is_atomic(formula_as_string);

        if(atomic){
            if(formula_as_string.charAt(0) == '(')
                formula_as_string = formula_as_string.substring(1,formula_as_string.length()-1);
            return new Atom(formula_as_string);
        } else {
            // for-loop to parse formula_as_string
            for (int i = 0; i < formula_as_string.length(); i++) {
                char c = formula_as_string.charAt(i);

                if (c == '(')
                    stack.push(c);
                else if (c == ')')
                    stack.pop();

                if(stack.empty() && Formula.is_operator(c)){
                    String left = formula_as_string.substring(0, i);
                    String right = formula_as_string.substring(i+1);
                    if(c == '⊕')
                        return new DisjunctiveFormula(left, right);
                    else if (c == '⊗')
                        return new ConjunctiveFormula(left, right);
                    break;
                }
            }
        }
        return null;
    }
}
