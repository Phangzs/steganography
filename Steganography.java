import java.awt.Color;
import java.util.*;
import java.awt.*;

/**
 * Write a description of class Steganography here.
 *
 * @author Erik Feng
 * @version 2/21/2023
 */

public class Steganography
{
    // instance variables - replace the example below with your own
    /**
    * Clear the lower (rightmost) two bits in a pixel.
    */
   public static void clearLow(Pixel p) {
       System.out.println(p);
       p.setRed(p.getRed()/4*4);
       p.setBlue(p.getBlue()/4*4);
       p.setGreen(p.getGreen()/4*4);
   }
   public static Picture testClearLow(Picture picture) {
       for (int i = 0; i < picture.getHeight(); i++) for (int j = 0; j < picture.getWidth(); j++) clearLow(picture.getPixel(j,i));
       return picture;
   }
   /**
    * Set the lower 2 bits in a pixel to the highest 2 bits in c
    */
   public static void setLow(Pixel p, Color c) {
       p.setRed(p.getRed()/4*4+c.getRed()/64);
       p.setBlue(p.getBlue()/4*4+c.getBlue()/64);
       p.setGreen(p.getGreen()/4*4+c.getGreen()/64);
   }
   public static Picture testSetLow(Picture picture) {
       for (int i = 0; i < picture.getHeight(); i++) for (int j = 0; j < picture.getWidth(); j++) setLow(picture.getPixel(j,i),picture.getPixel(j,i).getColor());
       return picture;
   }
   /**
    * Sets the highest two bits of each pixel's colors
    * to the lowest two bits of each pixel's colors
    */
   public static Picture revealPicture(Picture hidden) {
       Picture copy = new Picture(hidden);
       Pixel[][] pixels = copy.getPixels2D();
       Pixel[][] source = hidden.getPixels2D();
       for (int r = 0; r < pixels.length; r++) {
           for (int c = 0; c < pixels[0].length; c++) {
               Color col = source[r][c].getColor();
               //pixels[r][c].setRed((col.getRed()%4)*64+col.getRed()-(col.getRed()/64)*64);
               //pixels[r][c].setBlue((col.getBlue()%4)*64+col.getBlue()-(col.getBlue()/64)*64);
               //pixels[r][c].setGreen((col.getGreen()%4)*64+col.getGreen()-(col.getGreen()/64)*64);
               pixels[r][c].setRed((col.getRed()%4)*64);
               pixels[r][c].setBlue((col.getBlue()%4)*64);
               pixels[r][c].setGreen((col.getGreen()%4)*64);
               //System.out.println("Red: " + (col.getRed()%4)*64 + "+" + col.getRed() + "+" + (col.getRed()/64)*64);
           }
       }
       return copy;
   }
   /**
        * Determines whether secret can be hidden in source, which is
        * true if source and secret are the same dimensions.
        * @param source is not null
        * @param secret is not null
        * @return combined Picture with secret hidden in source
        * precondition: source is same width and height as secret
        */
   public static boolean canHide(Picture source, Picture secret) {
           if (source.getWidth() < secret.getWidth()) return false;
           if (source.getHeight() < secret.getHeight()) return false;
           return true;
       }
   public static Picture hidePicture(Picture source, Picture secret, int startRow, int startCol) {
       for (int i = startCol; i < source.getHeight(); i++) for (int j = startRow; j < source.getWidth(); j++) setLow(source.getPixel(j,i), secret.getPixel(j,i).getColor());
       return source;
   }
   public static boolean isSame(Picture picture1, Picture picture2) {
       if (picture1.getWidth()!=picture2.getWidth() || picture1.getHeight()!=picture2.getHeight()) return false;
       for (int i = 0; i < picture1.getHeight(); i++) for (int j = 0; j < picture1.getWidth(); j++) {
           if ((picture1.getPixel(j,i).getRed()!=picture2.getPixel(j,i).getRed()) || (picture1.getPixel(j,i).getGreen()!=picture2.getPixel(j,i).getGreen()) || (picture1.getPixel(j,i).getBlue()!=picture2.getPixel(j,i).getBlue())) return false;
           System.out.println("["+j+","+i+"]" + picture1.getPixel(j,i).getRed() + " " + picture2.getPixel(j,i).getRed() + " | " + picture1.getPixel(j,i).getGreen() + " " + picture2.getPixel(j,i).getGreen() + " | " + picture1.getPixel(j,i).getBlue() + " " + picture2.getPixel(j,i).getBlue() + "|||" + ((picture1.getPixel(j,i).getRed()!=picture2.getPixel(j,i).getRed()) || (picture1.getPixel(j,i).getGreen()!=picture2.getPixel(j,i).getGreen()) || (picture1.getPixel(j,i).getBlue()!=picture2.getPixel(j,i).getBlue())));
       }
       return true;
   }
   public static ArrayList findDifferences(Picture picture1, Picture picture2) {
       ArrayList<Point> points = new ArrayList<Point>();
       for (int i = 0; i < picture1.getHeight(); i++) for (int j = 0; j < picture1.getWidth(); j++) {
            if ((picture1.getPixel(j,i).getRed()!=picture2.getPixel(j,i).getRed()) || (picture1.getPixel(j,i).getGreen()!=picture2.getPixel(j,i).getGreen()) || (picture1.getPixel(j,i).getBlue()!=picture2.getPixel(j,i).getBlue())) points.add(new Point(j,i));   
        }
        return points;
   }
   public static Picture showDifferentArea(Picture picture, ArrayList<Point> differenceList) {
       for (Point point: differenceList) {
               picture.getPixel(point.x, point.y).setRed(77);
               picture.getPixel(point.x, point.y).setGreen(26);
               picture.getPixel(point.x, point.y).setBlue(127);
           }
           return picture;
   }
   /**
* Takes a string consisting of letters and spaces and
* encodes the string into an arraylist of integers.
* The integers are 1-26 for A-Z, 27 for space, and 0 for end of
* string. The arraylist of integers is returned.
* 
* @param s string consisting of letters and spaces
* @return ArrayList containing integer encoding of uppercase
* version of s
*/
public static ArrayList<Integer> encodeString(String s) {
    s = s.toUpperCase();
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    ArrayList<Integer> result = new ArrayList<Integer>(); 
    for (int i = 0; i < s.length(); i++) {
        if (s.substring(i,i+1).equals(" ")) result.add(27);
        else result.add(alpha.indexOf(s.substring(i,i+1))+1);
    }
    result.add(0); 
    return result;
}
/**
* Returns the string represented by the codes arraylist. * 1-26 = A-Z, 27 = space
* @param codes encoded string
* @return decoded string
*/
public static String decodeString(ArrayList<Integer> codes) {
    String result="";
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    for (int i=0; i < codes.size(); i++) {
        System.out.println("Arraylist Size: " + codes.size() + ", current: " + i + "(" + codes.get(i)+ ")");
        if (codes.get(i) == 27) result += " ";
        else if (codes.get(i) == 0) break;
        else result += alpha.substring(codes.get(i)-1,codes.get(i));
    }
    return result; 
}
/**
 * Given a number from 0 to 63, creates and returns a 3-element
 * int array consisting of the integers representing the 
 * pairs of bits in the number from right to left.
 * @param num nnumber to be broken up
 * @return bit pairs in number
 */
public static int[] getBitPairs(int num) {
    int[] bits = new int[3];
    int code = num;
    for (int i = 0; i < 3; i++) {
        bits[i] = code%4;
        code /= 4;
    }
    return bits;
}
/** 
 * Hide a string (must be only capital letters and spaces) in a 
 * picture.
 * The stirng always starts in the upper left corner.
 * @param source picture to hide string in 
 * @param s string to hide
 * @return picture with hidden string
 */
public static void hideText(Picture source, String s) {
    int stringCounter = 0;
    ArrayList<Integer> encoded = encodeString(s);
    for (int i = 0; i < source.getWidth(); i++) for (int j = 0; j < source.getHeight(); j++) {
        if (stringCounter > s.length()) break;
        Pixel pixel = source.getPixel(i,j);
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        
        System.out.println(stringCounter);
        
        int code = encoded.get(stringCounter);
        stringCounter++;
        
        int[] splitCode = getBitPairs(code);
        
        pixel.setRed(red/4*4+splitCode[0]);
        pixel.setGreen(green/4*4+splitCode[1]);
        pixel.setBlue(blue/4*4+splitCode[2]);
    }
}
/**
 * Returns a string hidden in the picture
 * @param source picture with hidden string
 * @return revealed string
 */
public static String revealText(Picture source) {
    ArrayList<Integer> encoded = new ArrayList<Integer>();
    boolean isBreak = false;
    for (int i = 0; i < source.getWidth(); i++) {
        if (isBreak) break;
        for (int j = 0; j < source.getHeight(); j++) {
            Pixel pixel = source.getPixel(i,j);
            int code = pixel.getRed()%4+(pixel.getGreen()%4)*4+pixel.getBlue()%4*16;
            System.out.print("Code: " + (code==0));
            if (code == 0) {
                isBreak = true;
                break;
            }
            encoded.add(code);
        }
    }
    for (int i = 0; i < 20; i++) System.out.println(encoded.get(i));
    String decoded = decodeString(encoded);
    return decoded;
}
   public static void main(String[] args) {
       
       /*
       Picture beach = new Picture ("beach.jpg");
       Picture mark_original = new Picture ("blue-mark.jpg");
       Picture mark_showChange = new Picture ("blue-mark.jpg");
       Picture mark = new Picture ("blue-mark.jpg");
       if (canHide(mark, beach)) {
           mark.explore();
           Picture combined = hidePicture(mark, beach,0,0);
           combined.explore();
           revealPicture(combined).explore();
           System.out.println("Combined vs Mark: " + isSame(combined, mark_original) + ", " + "Mark vs Mark: " +isSame(mark, mark));
           ArrayList<Point> differences = findDifferences(combined, mark_original);
           if (differences.size() > 0) System.out.println(differences.get(0));
           System.out.println(differences.size() + " differences between Combined and Mark out of " + combined.getWidth()*combined.getHeight());
           /*
           /*
           int maxXPoint = Integer.MIN_VALUE;
           int maxYPoint = Integer.MIN_VALUE;
           int minXPoint = Integer.MAX_VALUE;
           int minYPoint = Integer.MAX_VALUE;
           for (int i = 0; i < differences.size(); i++) maxXPoint = Math.max(differences.get(i).x, maxXPoint);
           for (int i = 0; i < differences.size(); i++) maxYPoint = Math.max(differences.get(i).y, maxYPoint);
           for (int i = 0; i < differences.size(); i++) minXPoint = Math.min(differences.get(i).x, minXPoint);
           for (int i = 0; i < differences.size(); i++) minYPoint = Math.min(differences.get(i).y, minYPoint);
           
           
           showDifferentArea(mark_showChange, differences);
           mark_showChange.explore();
           */
        Picture regular = new Picture ("beach.jpg");
        Picture encoded = new Picture ("beach.jpg");
        hideText(encoded, "HOW IS YOUR DAY GOING?");
        regular.explore();
        encoded.explore();
        System.out.println(revealText(encoded));
           
    }
       
       
       //hidePicture(beach,beach);
       //beach.explore();
}
