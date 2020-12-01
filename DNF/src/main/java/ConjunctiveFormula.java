/**
 * The type Conjunctive formula. It represents conjunctive formulas
 */
public class ConjunctiveFormula extends NonAtomicFormula{

    /**
     * Instantiates a new Conjunctive formula.
     *
     * @param left  the left formula
     * @param right the right formula
     */
    public ConjunctiveFormula(Formula left, Formula right) {
        super(left, right);
    }

    /**
     * Instantiates a new Conjunctive formula.
     *
     * @param left  the left formula as string
     * @param right the right formula as string
     */
    public ConjunctiveFormula(String left, String right) {
        super(left, right);
    }

    @Override
    public String toString(){
        return "(" + this.left + "∧" + this.right + ")";
    }

    @Override
    public void create_dnf_as_sets(Formula f) {
        f.dnf_as_sets.add(as_a_set(this));
    }
}
