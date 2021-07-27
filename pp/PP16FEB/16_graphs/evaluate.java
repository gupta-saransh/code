import java.util.*;

public class evaluate {
    
    public class Edge {
        String nbr;
        double wt;

        public Edge(String nbr, double wt) {
            this.nbr = nbr;
            this.wt = wt;
        }
    }

    public void display(HashMap<String, ArrayList<Edge>> graph) {
        for(String key : graph.keySet()) {
            System.out.print("[" + key + "] -> ");
            for(Edge e : graph.get(key)) {
                System.out.print("(" + e.nbr + "_" + e.wt + ")");
            }
            System.out.println();
        }
    }

    public boolean findAndFill(HashMap<String, ArrayList<Edge>> graph, String src, String dst,
            HashSet<String> vis, double[] res, int qi, double asf) {
        if(src.equals(dst) == true) {
            res[qi] = asf;
            return true;
        }

        vis.add(src);
        for(Edge e : graph.get(src)) {
            if(vis.contains(e.nbr) == false) {
                boolean rres = findAndFill(graph, e.nbr, dst, vis, res, qi, asf * e.wt);
                if(rres == true) return true;
            }
        }
        return false;
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {

        HashMap<String, ArrayList<Edge>> graph = new HashMap<>();
        int n = values.length; // no of edges

        for(int i = 0; i < n; i++) {
            String v1 = equations.get(i).get(0);
            String v2 = equations.get(i).get(1);
            double wt = values[i];

            // a--> b
            if(graph.containsKey(v1) == false) {
                graph.put(v1, new ArrayList<>());
            }
            graph.get(v1).add(new Edge(v2, wt));

            // b--> a
            if(graph.containsKey(v2) == false) {
                graph.put(v2, new ArrayList<>());
            }
            graph.get(v2).add(new Edge(v1, 1.0 / wt));
            
        }

        display(graph);
        double[] res = new double[queries.size()];
        for(int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            String src = query.get(0);
            String dst = query.get(1);

            if(graph.containsKey(src) == false || graph.containsKey(dst) == false) {
                res[i] = -1.0;
            } else if(src.equals(dst) == true) {
                res[i] = 1.0;
            } else {
                HashSet<String> vis = new HashSet<>();
                boolean rres = findAndFill(graph, src, dst, vis, res, i, 1.0);
                if(rres == false) {
                    res[i] = -1.0;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {


        // [["a","b"],["b","c"],["bc","cd"]]
        List<List<String>> equations = new ArrayList<>();
        List<String> l1 = new ArrayList<>();
        l1.add("a");
        l1.add("b");

        List<String> l2 = new ArrayList<>();
        l2.add("b");
        l2.add("c");

        List<String> l3 = new ArrayList<>();
        l3.add("bc");
        l3.add("cd");
        
        equations.add(l1);
        equations.add(l2);
        equations.add(l3);

        // ["a","c"],["c","b"],["bc","cd"],["cd","bc"]
        List<List<String>> queries = new ArrayList<>();
        
        List<String> q1 = new ArrayList<>();
        q1.add("a");
        q1.add("c");
        
        List<String> q2 = new ArrayList<>();
        q2.add("c");
        q2.add("b");
        
        List<String> q3 = new ArrayList<>();
        q3.add("bc");
        q3.add("cd");
        
        List<String> q4 = new ArrayList<>();
        q4.add("cd");
        q4.add("bc");

        queries.add(q1);
        queries.add(q2);
        queries.add(q3);
        queries.add(q4);

        double[] values = {1.5, 2.5, 5.0};

        evaluate e = new evaluate();
        
        double[] res = e.calcEquation(equations, values, queries);
        System.out.println(Arrays.toString(res));
    }
}
