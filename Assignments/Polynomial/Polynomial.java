package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
    
    /**
     * Reads a polynomial from an input stream (file or keyboard). The storage format
     * of the polynomial is:
     * <pre>
     *     <coeff> <degree>
     *     <coeff> <degree>
     *     ...
     *     <coeff> <degree>
     * </pre>
     * with the guarantee that degrees will be in descending order. For example:
     * <pre>
     *      4 5
     *     -2 3
     *      2 1
     *      3 0
     * </pre>
     * which represents the polynomial:
     * <pre>
     *      4*x^5 - 2*x^3 + 2*x + 3 
     * </pre>
     * 
     * @param sc Scanner from which a polynomial is to be read
     * @throws IOException If there is any input error in reading the polynomial
     * @return The polynomial linked list (front node) constructed from coefficients and
     *         degrees read from scanner
     */
    public static Node read(Scanner sc) 
    throws IOException {
        Node poly = null;
        while (sc.hasNextLine()) {
            Scanner scLine = new Scanner(sc.nextLine());
            poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
            scLine.close();
        }
        return poly;
    }
    
    /**
     * inserts the node at the right position.
     * @param head
     * @param node
     * @return
     */
    private static Node insertNode(Node head, Node node) {
        Node copy = new Node(node.term.coeff, node.term.degree, null);
        if(node.term.coeff == 0) {
            return head;
        }
        
        if(head == null) {
            return copy;
        }
        else {
            Node current = head;
            while(current != null) {
                if(current.term.degree == node.term.degree) {
                    current.term.coeff += node.term.coeff;
                    return head;
                }
                if(current.term.degree < node.term.degree) {
                    if(current.next != null) {
                        if(current.next.term.degree > node.term.degree) {
                            // has to come in between
                            copy.next = current.next;
                            current.next = copy;
                            return head;
                        }
                    }
                    else {
                        current.next = copy;
                        return head;
                    }
                }
                current = current.next;
            }
            return head;
        }
    }
    
    private static Node cleanUp(Node node) {
        Node head = null;
        Node tail = null;
        Node current = node;
        while(current != null) {
            if(current.term.coeff != 0) {
                if(tail != null) {
                    tail.next = current;
                    tail = current;
                }
                else {
                    head = tail = current;
                }
            }
            current = current.next;
        }
        
        return head;
    }
    /**
     * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
     * The returned polynomial MUST have all new nodes. In other words, none of the nodes
     * of the input polynomials can be in the result.
     * 
     * @param poly1 First input polynomial (front of polynomial linked list)
     * @param poly2 Second input polynomial (front of polynomial linked list
     * @return A new polynomial which is the sum of the input polynomials - the returned node
     *         is the front of the result polynomial
     */
    public static Node add(Node poly1, Node poly2) {
        /** COMPLETE THIS METHOD **/
        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
        // CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
        Node head = null;

        
        while(poly1 != null) {
            head = insertNode(head, poly1);
            poly1 = poly1.next;
        }
        
        while(poly2 != null) {
            head = insertNode(head, poly2);
            poly2 = poly2.next;
        }
        
        return cleanUp(head);
    }
    
    /**
     * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
     * The returned polynomial MUST have all new nodes. In other words, none of the nodes
     * of the input polynomials can be in the result.
     * 
     * @param poly1 First input polynomial (front of polynomial linked list)
     * @param poly2 Second input polynomial (front of polynomial linked list)
     * @return A new polynomial which is the product of the input polynomials - the returned node
     *         is the front of the result polynomial
     */
    public static Node multiply(Node poly1, Node poly2) {
        /** COMPLETE THIS METHOD **/
        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
        // CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
        Node head = null;
        
        while(poly1 != null) {
            Node current = poly2;
            Node iterHead = null;
            Node iterTail = null;
            while(current != null) {
                Node node = new Node(current.term.coeff, current.term.degree, null);
                node.term.degree += poly1.term.degree;
                node.term.coeff *= poly1.term.coeff;
                
                if(iterHead == null) {
                    iterHead = node;
                    iterTail = node;
                }
                else {
                    iterTail.next= node;
                    iterTail = iterTail.next;
                }
                current = current.next; 
            }
            // we have multiplied a single term in the first poly with all the
            // terms in the second poly. Now we need to add it to the result.
            head = Polynomial.add(head, iterHead);
            poly1 = poly1.next;
        }
        
        return head;
    }
        
    /**
     * Evaluates a polynomial at a given value.
     * 
     * @param poly Polynomial (front of linked list) to be evaluated
     * @param x Value at which evaluation is to be done
     * @return Value of polynomial p at x
     */
    public static float evaluate(Node poly, float x) {
        /** COMPLETE THIS METHOD **/
        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
        // CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
        
        float result = 0;
        Node current = poly;
        while(current != null) {
            Term term = current.term;
            result += term.coeff *  Math.pow(x, term.degree); 
            current = current.next;
        }
        return result;
    }
    
    /**
     * Returns string representation of a polynomial
     * 
     * @param poly Polynomial (front of linked list)
     * @return String representation, in descending order of degrees
     */
    public static String toString(Node poly) {
        if (poly == null) {
            return "0";
        } 
        
        String retval = poly.term.toString();
        for (Node current = poly.next ; current != null ;
        current = current.next) {
            retval = current.term.toString() + " + " + retval;
        }
        return retval;
    }    
}
