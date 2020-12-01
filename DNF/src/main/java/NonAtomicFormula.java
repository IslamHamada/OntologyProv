import java.util.Stack;

/**
 * The type Non atomic formula. It represents non-atomic formulas, in this case only conjunctive or disjunctive ones.
 */
public abstract class NonAtomicFormula extends Formula{
    /**
     * The Left formula
     */
    Formula left;
    /**
     * The Right formula
     */
    Formula right;

    /**
     * Instantiates a new Non atomic formula using two subformulas
     *
     * @param left  the left formula
     * @param right the right formula
     */
    public NonAtomicFormula(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Instantiates a new Non atomic formula.
     *
     * @param left  the left formula as string
     * @param right the right formula as string
     */
    public NonAtomicFormula(String left, String right){

        if(is_atomic(left)) {
            if(left.charAt(0) == '(')
                left = left.substring(1,left.length()-1);
            this.left = new Atom(left);
        }
        else{
            Stack<Character> stack = new Stack<Character>();
            for (int i = 0; i < left.length(); i++) {
                char c = left.charAt(i);

                if (c == '(')
                    stack.push(c);
                else if (c == ')')
                    stack.pop();

                if(left.charAt(0) == '(' && stack.empty() && i == left.length() - 1){
                    i = -1;
                    left = left.substring(1,left.length()-1);
                    continue;
                }

                if(stack.empty() && is_operator(c)){
                    String left_left = left.substring(0, i);
                    String left_right = left.substring(i+1);
                    if(c == '⊕')
                        this.left = new DisjunctiveFormula(left_left, left_right);
                    else if (c == '⊗')
                        this.left = new ConjunctiveFormula(left_left, left_right);
                    break;
                }
            }
        }

        if(is_atomic(right)) {
            if (right.charAt(0) == '(')
                right = right.substring(1, right.length() - 1);
            this.right = new Atom(right);
        }
        else{
            Stack<Character> stack = new Stack<Character>();
            for (int i = 0; i < right.length(); i++) {
                char c = right.charAt(i);

                if (c == '(')
                    stack.push(c);
                else if (c == ')')
                    stack.pop();

                if(right.charAt(0) == '(' && stack.empty() && i == right.length() - 1){
                    i = -1;
                    right = right.substring(1,right.length()-1);
                    continue;
                }

                if(stack.empty() && is_operator(c)){
                    String right_left = right.substring(0, i);
                    String right_right = right.substring(i+1);
                    if(c == '⊕')
                        this.right = new DisjunctiveFormula(right_left, right_right);
                    else if (c == '⊗')
                        this.right = new ConjunctiveFormula(right_left, right_right);
                    break;
                }
            }
        }
    }
}
