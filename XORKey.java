import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.lang.Math;

public class XORKey {


    public static void main(String[] args) throws IOException {

        BitTrie bt = new InnerTrie();
        try {
            bt.insert(14457);
        } catch (Exception e) {
            System.exit(1);
        }

        System.out.println("something happened");
        

        int t = 0;
        int n = 0;
        int q = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        t = Integer.parseInt(reader.readLine());
        for (int test = 0; test < t; test++) {
            String[] params = reader.readLine().split("\\s");
            n = Integer.parseInt(params[0]);
            q = Integer.parseInt(params[1]);
            ArrayList<Integer> keys = new ArrayList<Integer>(n);
            String[] k = reader.readLine().split("\\s");
            for (String i : k) {
                keys.add(new Integer(i));
            }
            for (int query = 0; query < q; query++) {
                String[] q_params = reader.readLine().split("\\s");
                System.out.println(max(Integer.parseInt(q_params[0]), 
                    Integer.parseInt(q_params[1]), Integer.parseInt(q_params[2]), 
                    keys));
            }
        }
    }

    static int max(int a, int low, int high, ArrayList<Integer> keys) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = low - 1; i < high; i++) {
            list.add(keys.get(i));
        }
        for (int bitIndex = 14; bitIndex >= 0; bitIndex -= 1) {
            LinkedList<Integer> newlist = new LinkedList<Integer>();
            for (int num : list) {
                boolean isOne = false;
                if ((get_bit(a, bitIndex) ^ get_bit(num, bitIndex)) == 1) {
                    newlist.add(num);
                    isOne = true;
                }
                if (isOne) {
                    list = newlist;
                }
            }
            if (list.size() == 1) {
                return a ^ list.element().intValue();
            }
        }
        return 0;
    }

    static byte get_bit(int number, int index) {
        int constant = 1 << index;
        if ((number & constant) == constant) {
            return 1;
        } else {
            return 0;
        }
    }
}






































/* BitTrie extension to work with this problem. */




    abstract class BitTrie {

        /** The empty Trie. */
        public static final BitTrie EMPTY = new EmptyTrie();
        
        /** Returns the number of children of the trie. */
        abstract protected int children();

        /** Decrements and returns the number of children of the trie. */
        abstract protected int decrementChildren();

        /** Increments and returns the number of children of the trie. */
        abstract protected int incrementChildren();

        /** The label at this node. Defined only on leaves. */
        abstract public int label() throws Exception;
        
        /** True if this Trie is a leaf (containing a single String). */
        abstract public boolean isLeaf();
        
        /** True if this Trie is empty */
        abstract public boolean isEmpty();
        
        /** The child numbered with character K. Requires that this node
        * not be empty. Child 0 corresponds to ✷. */
        abstract public BitTrie child(int k) throws Exception;
        
        /** Set the child numbered with character K to CHILD. Requires that
        * this node not be empty. (Intended only for internal use. */
        abstract protected void setChild(int k, BitTrie child) throws Exception;

        /** True if X is in this Trie. */
        public boolean isIn(int x) throws Exception {
            BitTrie lp = longestPrefix(x, 0);
            return lp.isLeaf() && lp.label() == x;
        }

        /** The node repsenting the longest prefix of X that
         *  matches a int in this trie. */
        private BitTrie longestPrefix(int x, int startLevel) throws Exception {
            if (isEmpty() || isLeaf()) {
                return this;
            } 
            int bit = getBit(x, startLevel);
            if (child(bit).isEmpty()) {
                return this;
            } else {
                return child(bit).longestPrefix(x, startLevel + 1);
            }
        }

        /** The result of inserting X into this Trie, if it is not
        * already there, and returning this. This trie is
        * unchanged if X is in it already. */
        public BitTrie insert(int x) throws Exception {
            return insert(x, 0);
        }
        
        /** Assumes this is the node at LEVEL in some trie.
         *  Returns this after the NUM was inserted.
         *  No effect if NUM is already in the trie. */
        private BitTrie insert(int num, int level) throws Exception {
            if (isEmpty()) {
                return new LeafTrie(num);
            }
            int bit = getBit(num, level);
            if (isLeaf()) {
                if (num == label()) {
                    return this;
                } else if (bit == getBit(label(), level)) {
                    return new InnerTrie(bit, insert(num, level + 1));
                } else {
                    BitTrie newNode = new InnerTrie(bit, new LeafTrie(num));
                    newNode.setChild(getBit(label(), level), this);
                    return newNode;
                }
            } else {
                setChild(bit, child(bit).insert(num, level + 1));
                incrementChildren();
                return this;
            }
        }

        /** The result of removing X from this Trie, if it is present.
         *  The trie is unchanged if X is not present. */
        public BitTrie remove(int x) throws Exception {
            return remove(x, 0);
        }


        /** Removes NUM from this trie, which is assumed to be at 
         *  LEVEL, and returns the resulting trie. */
        private BitTrie remove(int num, int level) throws Exception {
            if (isEmpty()) 
                return this;
            if (isLeaf()) {
                if (num == label()) {
                    return EMPTY;
                } else {
                    return this;
                }
            }
            int bit = getBit(num, level);
            setChild(bit, child(bit).remove(num, level + 1));
            decrementChildren();
            int d = onlyMember();
            if (d >= 0)
                return child(d);
            return this;
        }

        /** Returns 1 or 0 if there a single int in the trie starting
         *  at child 1 or 0 respectively. returns -1 otherwise. **/
        private int onlyMember() {
            if (children() == 1) {
                return 1;
            } else {
                return -1;
            }
        }

        /* some utility methods declaration. */

        /** Return the bit at INDEX of NUM beginning from the left most. */
        protected int getBit(int num, int index) {
            int constant = 1 << (32 - index);
            if ((num & constant) == constant) {
                return 1;
            } else {
                return 0;
            }
        }

        /** Returns the XOR-Key max value. */
        public int xorKey(int a) throws Exception {
            return xorKey(a, 0);
        }

        public int xorKey(int a, int level) throws Exception {
            if (isEmpty()) {
                return 0;
            } else if (isLeaf()) {
                return a ^ label();
            } else {
                int bit = getBit(a, level);
                if (bit == 1) {
                    if (child(0).isEmpty())
                        return child(1).xorKey(a, level + 1);
                    return child(0).xorKey(a, level + 1);
                } else {
                    if (child(1).isEmpty())
                        return child(0).xorKey(a, level + 1);
                    return child(1).xorKey(a, level + 1);
                }
            }
        }
    }



    /** Representation of an empty trie. */
    class EmptyTrie extends BitTrie {
        public boolean isEmpty() {return true;}
        public boolean isLeaf() {return false;}
        public int label() throws Exception {throw new Exception();}
        public BitTrie child(int k) throws Exception {throw new Exception();}
        public void setChild(int k, BitTrie child) throws Exception {throw new Exception();}
        protected int children() {return 0;}
        protected int incrementChildren() {return 0;}
        protected int decrementChildren() {return 0;}
    }


    /** Representation of a leaf trie. */
    class LeafTrie extends BitTrie {
        private int label;

        /** constructor. a leaf trie containing label l. */
        LeafTrie(int l) {label = l;}

        public boolean isEmpty() {return false;}
        public boolean isLeaf() {return true;}
        public int label() throws Exception {return label;}
        public BitTrie child(int k) throws Exception {return EMPTY;}
        public void setChild(int k, BitTrie child) throws Exception {throw new Exception();}
        protected int children() {return 1;}
        protected int incrementChildren() {return 1;}
        protected int decrementChildren() {return 1;}
    }

    /** Representation of an inner trie. */
    class InnerTrie extends BitTrie {
        int cNum = 0;
        int val;
        BitTrie zero;
        BitTrie one;

        /** Constructor. A trie with val VALUE and child at 
         *  indexValue equals to CHILD. */
        InnerTrie(int childValue, BitTrie child) {
            cNum = 1;
            if (childValue == 0) {
                zero = child;
            } else {
                one = child;
            }
        }

        InnerTrie() {
            cNum = 0;
            zero = new EmptyTrie();
            one = new EmptyTrie();
        }

        public boolean isEmpty() {return false;}
        public boolean isLeaf() {return false;}
        public int label() throws Exception {throw new Exception();}
        public BitTrie child(int childValue) throws Exception {
            if (childValue == 0) {
                return zero;
            } else {
                return one;
            }
        }
        public void setChild(int childValue, BitTrie child) throws Exception {
            if (childValue == 0) {
                zero = child;
            } else {
                one = child;
            }
        }
        protected int children() {
            return cNum;
        }
        protected int incrementChildren() {
            cNum += 1;
            return cNum;
        }
        protected int decrementChildren() {
            cNum -= 1;
            return cNum;
        }
    }

