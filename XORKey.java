import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.lang.Math;

public class XORKey {


    public static void main(String[] args) throws IOException {
        int t = 0;
        int n = 0;
        int q = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        t = Integer.parseInt(reader.readLine());
        for (int test = 0; test < t; test++) {
            String[] params = reader.readLine().split("\\s");
            n = Integer.parseInt(params[0]);
            q = Integer.parseInt(params[1]);
            BitTrie keysTrie = new InnerTrie();
            String[] k = reader.readLine().split("\\s");
            int index = 0;
            for (String i : k) {
                try {
                    keysTrie.insert(new Integer(i), index);
                    index += 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (int query = 0; query < q; query++) {
                String[] q_params = reader.readLine().split("\\s");
                try {
                    System.out.println(keysTrie.xorKey(Integer.parseInt(q_params[0]), 
                    Integer.parseInt(q_params[1]), Integer.parseInt(q_params[2])));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                
            }
        }
    }

}





























abstract class BitTrie {

    /** An empty trie. */
    static protected final BitTrie EMPTY = new EmptyTrie();

    /** Number of spaces in one indentation unit. */
    static final int INDENTATION = 3;

    /** The size in bits of an int. */
    static protected int INT_SIZE = 15;

    /** The label at this node. Defined only on leaves. */
    abstract public int label() throws Exception;

    /** True if this Trie is a leaf (containing a single String). */
    abstract public boolean isLeaf();

    /** True if this Trie is empty */
    abstract public boolean isEmpty();

    /** returns child at zero for bit==0 and
     *  and child at one for bit==1. Works just for innerTrie. */
    abstract protected BitTrie child(int bit) throws Exception;

    /** Returns NEWCHILD at BIT. Requires this to be inner tree. */
    abstract protected void setChild(int bit, BitTrie newChild) throws Exception;

    /** Return true iff the trie contains the value at index INDEX. */
    abstract protected boolean validIndex(int index);

    /** Returns a TreeSet contaning the valid indexes. */
    abstract protected TreeSet<Integer> getIndexes();


    /** The result of inserting X into this Trie, if it is not
     *  already there, and returning this. This trie is
     *  unchanged if X is in it already. */
    public BitTrie insert(int n, int index) throws Exception {
        return insert(n, index, 0);
    }

    /** Assumes that we are at level LEVEL in the trie
     *  and constructs recursively the trie. It returns
     *  this. */
    private BitTrie insert(int n, int index, int level) throws Exception {
        int bit = getBit(n, level);
        if (isLeaf() && label() == n) {
            return this;
        } else if (level == INT_SIZE) {
            return new LeafTrie(n, index);
        } else if (isEmpty()) {
            InnerTrie newNode = new InnerTrie(bit, insert(n, level + 1));
            return newNode;
        } else {
            setChild(bit, child(bit).insert(n, level + 1));
            return this;
        }
    }




    /** Returns the Nth bit of an int NUM beginning
     *  the indexing from the left. Throws index out of bounds. */
    protected int getBit(int num, int n) {
        if (n < 0 || n > INT_SIZE - 1)
            return -1;
        int mask = 1 << (INT_SIZE - (n + 1));
        if ((num & mask) == 0)
            return 0;
        return 1;
    }



    /** Returns the XOR-Key max value. */
    public int xorKey(int a, int low, int high) throws Exception {
        return xorKey(a, low, high, 0);
    }

    public int xorKey(int a, int low, int high, int level) throws Exception {
        if (isEmpty())
            return 0;
        else if (isLeaf()) 
            return a ^ label();
        else {
            int bit = getBit(a, level);
            if (bit == 1 && !(high <= child(1).getIndexes().first() 
                || low > child(1).getIndexes().last())) {
                if (child(0).isEmpty())
                    return child(1).xorKey(a, low, high, level + 1);
                return child(0).xorKey(a, low, high, level + 1);
            } else {
                if (child(1).isEmpty())
                    return child(0).xorKey(a, low, high, level + 1);
                return child(1).xorKey(a, low, high, level + 1);
            }

        }
    }
}




/** Representation of an empty trie. */
class EmptyTrie extends BitTrie {
    @Override
    public int label() throws Exception {throw new Exception();}
    @Override
    public boolean isLeaf() {return false;}
    @Override
    public boolean isEmpty() {return true;}
    @Override
    protected BitTrie child(int bit) throws Exception {throw new Exception();}
    @Override
    protected void setChild(int bit, BitTrie newChild) throws Exception {throw new Exception();}
    @Override
    protected boolean validIndex(int index) {return false;}
    @Override
    protected TreeSet<Integer> getIndexes() {return new TreeSet<Integer>(); }
}

/** Representation of a leaf trie. */
class LeafTrie extends BitTrie {
    private int _index;
    private int _label;
    /** Constructor that sets this.lable to LABEL. */
    public LeafTrie(int label, int index) {
        _label = label;
        _index = index;
    }
    @Override
    public int label() throws Exception {return _label;}
    @Override
    public boolean isLeaf() {return true;}
    @Override
    public boolean isEmpty() {return false;}
    @Override
    protected BitTrie child(int bit) throws Exception {throw new Exception();}
    @Override
    protected void setChild(int bit, BitTrie newChild) throws Exception {throw new Exception();}
    @Override
    protected boolean validIndex(int index) {return index == _index;}
    @Override
    protected TreeSet<Integer> getIndexes() {
        TreeSet<Integer> res = new TreeSet<Integer>();
        res.add(_index);
        return res;
    }
}

/** Representation of an inner trie. */
class InnerTrie extends BitTrie {
    /** Stores the indexes of values contained by this trie. */
    private TreeSet<Integer> _indexes;
    /** Stores the child at bit==0. */
    private BitTrie _zero;
    /** Stores the child at bit==1. */
    private BitTrie _one;
    /** Constructor. Initializes trie with no children. */
    protected InnerTrie() {
        _zero = EMPTY;
        _one = EMPTY;
        _indexes = new TreeSet<Integer>();
    }
    /** Constructor. Initializes trie with children NEWCHILD at zero or one
     *  according to BIT.  */
    protected InnerTrie(int bit, BitTrie newChild) {
        _zero = EMPTY;
        _one = EMPTY;
        _indexes = new TreeSet<Integer>();
        setChild(bit, newChild);
    }
    @Override
    public int label() throws Exception {throw new Exception();}
    @Override
    public boolean isLeaf() {return false;}
    @Override
    public boolean isEmpty() {return false;}
    @Override
    protected BitTrie child(int bit) throws Exception {
        if (bit == 0)
            return _zero;
        return _one;
    }
    @Override
    protected void setChild(int bit, BitTrie newChild) {
        if (bit == 0)
            _zero = newChild;
        else
            _one = newChild;
        for (Integer n : newChild.getIndexes())
            _indexes.add(n);
    }
    @Override
    protected boolean validIndex(int index) {
        return _indexes.contains(index);
    }
    @Override
    protected TreeSet<Integer> getIndexes() {
        return _indexes;
    }

}






























