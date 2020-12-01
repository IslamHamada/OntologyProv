import java.util.HashSet;
import java.util.Set;

public class DisjunctionOfConjunctions {
    Set<Conjunction> set;

    public DisjunctionOfConjunctions(){
        this.set = new HashSet<>();
    }

    public void add_conjunction(Conjunction c){
        this.set.add(c);
    }
}
