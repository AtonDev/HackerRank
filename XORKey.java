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
    		String[] params = reader.readLine().split("[0-9]?");
    		n = Integer.parseInt(params[0]);
    		q = Integer.parseInt(params[1]);
    		ArrayList<Integer> keys = new ArrayList<Integer>(n);
    		String[] k = reader.readLine().split("[0-9]?");
    		for (String i : k) {
    			keys.add(new Integer(i));
    		}
    		for (int query = 0; query < q; query++) {
    			String[] q_params = reader.readLine().split("[0-9]?");
    			System.out.println(max(Integer.parseInt(q_params[0]), 
    				Integer.parseInt(q_params[1]), Integer.parseInt(q_params[2]), 
    				keys));
    		}
    	}
    }

    static int max(int a, int low, int high, ArrayList<Integer> keys) {
    	int result = 0;
    	for (int i = low; i <= high; i++) {
    		result = Math.max(result, a ^ keys.get(i).intValue());
    	}
    	return result;
    }
}