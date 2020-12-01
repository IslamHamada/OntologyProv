/**
 * The type Disjunctive formula.
 */
public class DisjunctiveFormula extends NonAtomicFormula{

    /**
     * Instantiates a new Disjunctive formula.
     *
     * @param left  the left formula
     * @param right the right formula
     */
    public DisjunctiveFormula(Formula left, Formula right) {
        super(left, right);
    }

    /**
     * Instantiates a new Disjunctive formula.
     *
     * @param left  the left formula as string
     * @param right the right formula as string
     */
    public DisjunctiveFormula(String left, String right) {
        super(left, right);
    }

    @Override
    public String toString(){
        return "(" + this.left + "∨" + this.right + ")";
    }

    @Override
    public void create_dnf_as_sets(Formula f) {
        //add the right and left disjuncts to the DNF set of f
        this.right.create_dnf_as_sets(f);
        this.left.create_dnf_as_sets(f);
    }
}
