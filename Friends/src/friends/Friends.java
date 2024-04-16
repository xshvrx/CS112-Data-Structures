package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

    /**
     * Finds the shortest chain of people from p1 to p2.
     * Chain is returned as a sequence of names starting with p1,
     * and ending with p2. Each pair (n1,n2) of consecutive names in
     * the returned chain is an edge in the graph.
     * 
     * @param g Graph for which shortest chain is to be found.
     * @param p1 Person with whom the chain originates
     * @param p2 Person at whom the chain terminates
     * @return The shortest chain from p1 to p2. Null or empty array list if there is no
     *         path from p1 to p2
     */
    public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
        
        /** COMPLETE THIS METHOD **/
        ArrayList<String> chain = new ArrayList<String>();
        Queue<Person> next = new Queue<Person>();
        String[] last = new String [g.members.length];
        boolean[] same = new boolean[g.members.length];
        next.enqueue(g.members[g.map.get(p1)]);
        same[g.map.get(p1)] = true;
        while(!next.isEmpty()) {
            Person current = next.dequeue(); 
            Friend m = current.first;
            while(m != null) {
                if(!same[m.fnum]) {
                    last[m.fnum] = current.name;
                    same[m.fnum] = true;
                    next.enqueue(g.members[m.fnum]);
                    if(g.members[m.fnum].name.equals(p2)) {
                        next.clear();
                        break;
                    }
                }
                m = m.next;
            }
        }
        if(last[g.map.get(p2)] == null) {
            return null;
        }
        String pointer = p2;
        while(pointer != null) {
            chain.add(0,pointer);
            pointer = last[g.map.get(pointer)]; 
        }
        return chain;
        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
        // CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION

    }
    
    /**
     * Finds all cliques of students in a given school.
     * 
     * Returns an array list of array lists - each constituent array list contains
     * the names of all students in a clique.
     * 
     * @param g Graph for which cliques are to be found.
     * @param school Name of school
     * @return Array list of clique array lists. Null or empty array list if there is no student in the
     *         given school
     */
    public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
        
        /** COMPLETE THIS METHOD **/
        ArrayList<ArrayList<String>> every = new ArrayList<ArrayList<String>>();
        ArrayList<String> c = new ArrayList<String>();
        boolean[] bool = new boolean[g.members.length];
        Queue<Person> given = new Queue<Person>();
        for(int i = 0; i < g.members.length;i++) {
            if(bool[i]) {
                continue;
            }
            bool[i] = true;
            if(g.members[i].student && g.members[i].school.equals(school)) {
                c = new ArrayList<String>();
                c.add(g.members[i].name);
                given = new Queue<Person>();
                given.enqueue(g.members[i]);
                while(!given.isEmpty()) {
                    Person current = given.dequeue();
                    Friend n = current.first;
                    while(n != null) {
                        if(!bool[n.fnum] && g.members[n.fnum].student && g.members[n.fnum].school.equals(school)) {
                            c.add(g.members[n.fnum].name);
                            bool[n.fnum] = true;
                            given.enqueue(g.members[n.fnum]);
                        }
                        n = n.next;
                    }
                }
                every.add(c);
            }
        }
        return every;
        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
        // CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
        
    }
    
    /**
     * Finds and returns all connectors in the graph.
     * 
     * @param g Graph for which connectors needs to be found.
     * @return Names of all connectors. Null or empty array list if there are no connectors.
     */
    public static ArrayList<String> connectors(Graph g) {
        
        /** COMPLETE THIS METHOD **/
        ArrayList<String> connect = new ArrayList<String>();
        boolean[] bool = new boolean[g.members.length];
        int[] dfsNum = new int[g.members.length],back = new int[g.members.length];
        for(int i = 0; i < bool.length; i++) {
            if(!bool[i]) {
                dfs(g,i,bool,1,dfsNum,back, connect);
            }
        }
        return connect;
        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
        // CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
        
    }
    private static void dfs(Graph g, int v,boolean [] visited,int count,int[] dfsNum,int[]back,  ArrayList<String> c) {
        visited[v] = true;
        dfsNum[v] = back[v] = count;
        for(Friend f = g.members[v].first; f != null ; f = f.next) {
            if(!visited[f.fnum]) {
                count++;
                dfs(g,f.fnum,visited,count,dfsNum,back,c);
                if(dfsNum[v] <=back[f.fnum] && !c.contains(g.members[v].name) && g.members[v].first.next != null) {
                    c.add(g.members[v].name);
                }
                if(dfsNum[v]>back[f.fnum]){
                    back[v] = Math.min(back[f.fnum],back[v]);
                }
            }
            else {
                back[v] = Math.min(back[v], dfsNum[f.fnum]);
            }
        }
    }
}