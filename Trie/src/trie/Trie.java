package trie;

import java.util.ArrayList;
/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
    // prevent instantiation
    private Trie() { }
    /**
     * Builds a trie by inserting all words in the input array, one at a time,
     * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
     * The words in the input array are all lower case.
     * 
     * @param allWords Input array of words (lowercase) to be inserted.
     * @return Root of trie with all words inserted from the input array
     */
    public static TrieNode buildTrie(String[] allWords) {
        /* COMPLETE THIS METHOD */
        TrieNode root = new TrieNode(null, null, null);
        // FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
        // MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
        for(int i=0;i<allWords.length;i++) {
            root.firstChild = add(allWords, root.firstChild, i, (short) 0, (short)(allWords[i].length()-1));
        }
        return root;
    }
private static TrieNode add(String[] allWords, TrieNode root, int wordIndex, short startIndex, short endIndex) {
    if(root == null) {
        return new TrieNode( new Indexes(wordIndex, startIndex, endIndex),
                null, null);
        } else {    
            TrieNode node = root;
            String current_word = allWords[wordIndex];
              TrieNode leftNode = null;
            while(node!=null) {    
                int index = node.substr.wordIndex;
                short start = node.substr.startIndex;
                short end = node.substr.endIndex;
                String word = allWords[index];
                int count = 0;
                for(int i=start+1;i<=end+1;i++) {
                    if(word.substring(start, i).equals(current_word.substring(startIndex, (short)(startIndex + count + 1)))) {
                        count++;
                    }
                    else break;
                }
        //System.out.println(count);
                //
                if(count == end - start + 1) {
                    node.firstChild = 
                            add(allWords, node.firstChild, wordIndex, (short)(startIndex + count), endIndex);
                    return root;
                }
                else if(count != 0){
                      System.out.println(word +" "+current_word);
                    System.out.println(count);
                    TrieNode sibling = node.sibling;
                    short newEndIndex = (short)(startIndex + count - 1);
                    TrieNode newNode = new TrieNode(
                            new Indexes(index, startIndex, newEndIndex), null, null);
                    newNode.sibling = node.sibling;
                    node.sibling = null;
                    node.substr.startIndex = (short)(newEndIndex + 1);
                    newNode.firstChild = add(allWords, node, wordIndex, (short)(startIndex+ count), endIndex);
         if(leftNode!=null)
            leftNode.sibling = newNode;
          else return newNode;
                    return root;
                }
                leftNode = node;
                if(node.sibling!=null)
                    node = node.sibling;
                else {
                    // if the nodes dont have common string, add a sibling
                    node.sibling = new TrieNode(new Indexes(wordIndex, startIndex, endIndex), 
                            null, null);
                    break;
                }
            }
            return root;
        }
    }
    
public static void addAll(ArrayList<TrieNode>ans,  TrieNode root) {
    if(root==null) {
        return;
    }
    if(root.firstChild==null) {
        ans.add(root);
    } else {
        addAll(ans, root.firstChild);
    }
    addAll(ans, root.sibling);
}
    
public static void getList(ArrayList<TrieNode>ans,  TrieNode root, String allWords[], String prefix) {
    if(root==null){
      return;
    }
    if(prefix.length()==0) {
        addAll(ans, root);
        return;
    } else {
        TrieNode node = root;
        while(node!=null) {
            int index = node.substr.wordIndex;
            short start = node.substr.startIndex;
            short end = node.substr.endIndex;
            String word = allWords[index];                
            int count = 0;    
            for(int i = start+1; i <= end+1 && count < prefix.length(); i++) {
                if(word.substring(start, i).equals(prefix.substring(0, count + 1))) {
                    count++;
                }
                else break;
            }    
        if(count>=prefix.length()){
            if(node.firstChild != null)
                addAll(ans, node.firstChild);
              else ans.add(node);
        }
        else if(count == end - start + 1){
            getList(ans, node.firstChild, allWords, 
            prefix.substring(count));
        }
        node = node.sibling;
        }    
    }
}

    /**
     * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
     * trie whose words start with this prefix. 
     * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
     * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
     * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
     * and for prefix "bell", completion would be the leaf node that holds "bell". 
     * (The last example shows that an input prefix can be an entire word.) 
     * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
     * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
     *
     * @param root Root of Trie that stores all words to search on for completion lists
     * @param allWords Array of words that have been inserted into the trie
     * @param prefix Prefix to be completed with words in trie
     * @return List of all leaf nodes in trie that hold words that start with the prefix, 
     *             order of leaf nodes does not matter.
     *         If there is no word in the tree that has this prefix, null is returned.
     */
    public static ArrayList<TrieNode> completionList(TrieNode root,
                                        String[] allWords, String prefix) {
        /* COMPLETE THIS METHOD */
        // FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
        // MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
    ArrayList<TrieNode> ans = new ArrayList<TrieNode>();
        getList(ans, root.firstChild, allWords, prefix);
        if(ans.size()==0)
            return null;
            return ans;
    }
    
    public static void print(TrieNode root, String[] allWords) {
        System.out.println("\nTRIE\n");
        print(root, 1, allWords);
    }
    
    private static void print(TrieNode root, int indent, String[] words) {
        if (root == null) {
            return;
        }
        for (int i=0; i < indent-1; i++) {
            System.out.print("    ");
        }
        
        if (root.substr != null) {
            String pre = words[root.substr.wordIndex]
                            .substring(0, root.substr.endIndex+1);
            System.out.println("      " + pre);
        }
        for (int i=0; i < indent-1; i++) {
            System.out.print("    ");
        }
        System.out.print(" ---");
        if (root.substr == null) {
            System.out.println("root");
        } else {
            System.out.println(root.substr);
        }
        
        for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
            for (int i=0; i < indent-1; i++) {
                System.out.print("    ");
            }
            System.out.println("     |");
            print(ptr, indent+1, words);
        }
    }
 }