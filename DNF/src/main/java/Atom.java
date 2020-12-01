/**
 * The type Atom. It represents atomic formulas
 */
public class Atom extends Formula{

    private String name;

    /**
     * Instantiates a new Atom.
     *
     * @param name the atom name
     */
    public Atom(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public void create_dnf_as_sets(Formula f) {
        f.dnf_as_sets.add(as_a_set(this));
    }
}
