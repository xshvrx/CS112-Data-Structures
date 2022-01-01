/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author:
 *
 *************************************************************************/

import java.awt.Color;

public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) {
       
        // WRITE YOUR CODE HERE
        this(filename, 100, 4);
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) {
        collageDimension = cd;
        tileDimension = td;
        original=new Picture(filename);
        collage= new Picture((collageDimension*tileDimension),(tileDimension*collageDimension));
        int w= tileDimension*collageDimension;
        int h= tileDimension*collageDimension;
        for (int tcol=0; tcol<w; tcol++){
            for (int trow=0; trow<h; trow++){
                int scol= tcol * original.width()/w;
                int srow= trow*original.height()/h;
                Color color= original.get(scol, srow);
                collage.set(tcol, trow, color);
            }
        }
       
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {

        // WRITE YOUR CODE HERE
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {

        // WRITE YOUR CODE HERE
        return tileDimension;
    }
    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() {

        // WRITE YOUR CODE HERE
        return original;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() {

        // WRITE YOUR CODE HERE
        return collage;
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {

        // WRITE YOUR CODE HERE
        original.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {

        // WRITE YOUR CODE HERE
        collage.show();
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        Picture p = new Picture(filename);
        double xScale = tileDimension * 1.0 / p.width();
        double yScale = tileDimension * 1.0 / p.height();

        for (int x=0; x < tileDimension ; x++){
            for (int y=0; y< tileDimension ; y++){
                int scol = x + (collageCol * tileDimension);
                int srow= y + (collageRow * tileDimension);
                
                int scaledX = (int)(x / xScale);
                int scaledY = (int)(y / yScale);
                
                Color color= p.get(scaledX, scaledY);
                
                collage.set(scol, srow, color);
            }
        }
    }
    
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () {
        // WRITE YOUR CODE HERE
        double xScale = tileDimension * 1.0 / original.width();
        double yScale = tileDimension * 1.0 / original.height();
        
        for (int tileX = 0 ; tileX < collageDimension; tileX++){
            for(int tileY = 0 ; tileY < collageDimension ; tileY++) {
                for (int y = 0 ; y < tileDimension ; y++){ 
                    for(int x = 0 ; x < tileDimension ; x++) {
                        int scol= tileX * tileDimension + x;
                        int srow= tileY * tileDimension + y;
                        int scaledX = (int)(x / xScale);
                        int scaledY = (int)(y / yScale);
                        
                        Color color= original.get(scaledX, scaledY);
                        collage.set(scol, srow, color);
                    }
                }
            }
        }
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see CS111 Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        
        for(int x = 0 ; x < tileDimension ; x++) {
            for(int y = 0 ; y < tileDimension ; y++) {
                int scol = collageCol * tileDimension + x;
                int srow = collageRow * tileDimension + y;
                int rgb = collage.getRGB(scol, srow);
                if(component.equals("red")) {
                    collage.setRGB(scol, srow, rgb & 0xFF0000);
                }
                if(component.equals("green")) {
                    collage.setRGB(scol, srow, rgb & 0x00FF00);
                }
                if(component.equals("blue")) {
                    collage.setRGB(scol, srow, rgb & 0x0000FF);
                }
            }
        }
        
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     * (see CS111 Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */

    public void grayscaleTile (int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        for(int x = 0 ; x < tileDimension ; x++) {
            for(int y = 0 ; y < tileDimension ; y++) {
                int scol = collageCol * tileDimension + x;
                int srow = collageRow * tileDimension + y;
                Color color = collage.get(scol, srow);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int avg = (red + green + blue) / 3;
                color = new Color(avg, avg, avg);
                collage.set(scol, srow, color);
            }
        }
        
    }


    /*
     *
     *  Test client: use the examples given on the assignment description to test your ArtCollage
     */
    public static void main (String[] args) {
        ArtCollage art = 
        new ArtCollage(args[0], 200, 3);
        art.makeCollage();
        // Replace tile at col 1, row 1 with 
        // args[1] image
        art.replaceTile(args[1],1,1);
        art.showCollagePicture();
    }
}
