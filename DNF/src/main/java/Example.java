import java.util.Arrays;

public class Example {
    //uncomment the formula you want to test
    public static void main(String[] args) {
//        String formula = "(((s1 ⊗ s4) ⊕ (s2 ⊗ s2) ⊕ (s5 ⊗ s2)) ⊗ ((s4 ⊗ s4) ⊕ (s3 ⊗ s3)))";
//        String formula = "(((s4 ⊗ s1) ⊕ (s7 ⊗ s1) ⊕ (s6 ⊗ s3) ⊕ (s3 ⊗ s3)) ⊗ ((s2 ⊗ s2) ⊕ (s1 ⊗ s1)))";
//        String formula = " ((clip11.mp4_low_low ⊗ clip11.mp4_fast_fast) ⊕ clip11.mp4_indoor_indoor)";
//        String formula = "(s1)";
//        String formula = "s1";
//        String formula = "((s1 ⊕ s2) ⊗ (s3 ⊕ s4))";
//        String formula = "((s1 ⊗ s2) ⊕ (s3 ⊗ s4))";
        String formula = "((clip11.mp4_low ⊗ clip11.mp4_fast) ⊕ clip11.mp4_indoor)";

        Formula f = Formula.parse(formula);

        Formula dnf_formula = Formula.toDNF(f);
        System.out.println(dnf_formula);
        dnf_formula.create_dnf_as_sets();
        System.out.println(Arrays.toString(dnf_formula.dnf_as_sets.toArray()));
    }
}
