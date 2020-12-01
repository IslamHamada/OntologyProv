import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Conjunction {
    Set<Atom> set;

    public Conjunction(){
        this.set = new HashSet<>();
    }

    public void add_atom(Atom a){
        this.set.add(a);
    }

    public String toString(){
       return Arrays.toString(this.set.toArray());
    }
}
