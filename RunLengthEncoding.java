/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Arrays;
import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */

    private Run head;
    private int size = 0;

    private int width;
    private int height;


  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
    // Your solution here.
      this.width = width;
      this.height = height;
      head = new Run(0, 0, 0, 0); //sentinel run
      head.next = new Run((width*height), 0, 0, 0, head, head);
      head.prev = head.next;
      size++;
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
    // Your solution here.
      this.width = width;
      this.height = height;
      head = new Run(0, 0, 0, 0); //sentinel run
      head.next = new Run(runLengths[0], red[0], green[0], blue[0], head, head);
      head.prev = head.next;
      for(int i = 1; i < width * height; i++) {
          head.prev = new Run(runLengths[i], red[i], green[i], blue[i], head, head.prev);
          head.prev.prev.next = head.prev;
      }
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    // Replace the following line with your solution.
    return new RunIterator(head.next);
    // You'll want to construct a new RunIterator, but first you'll need to
    // write a constructor in the RunIterator class.
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  public PixImage toPixImage() {
      // Replace the following line with your solution.
      PixImage image = new PixImage(width, height);
      RunIterator i = iterator();
      int piximage_pointer_x = 0;
      int piximage_pointer_y = 0;
      int[] current_pixel = {0, 0, 0, 0};
      int new_x;
      int new_y;
      while (i.hasNext()) {
          int[] attributes = i.next();
          for (int j = 0; j < attributes[0]; j++) {
              image.setPixel(piximage_pointer_x, piximage_pointer_y, (short) attributes[1], (short) attributes[2],
                      (short) attributes[3]);
              new_x = image.nextPixelX(piximage_pointer_x, piximage_pointer_y);
              new_y = image.nextPixelY(piximage_pointer_x, piximage_pointer_y);
              piximage_pointer_x = new_x;
              piximage_pointer_y = new_y;
          }
      }
      current_pixel = i.next();
      image.setPixel(piximage_pointer_x, piximage_pointer_y, (short) current_pixel[1], (short) current_pixel[2],
              (short) current_pixel[3]);
      return image;
  }

  /**
   *  toString() returns a String representation of this RunLengthEncoding.
   *
   *  This method isn't required, but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return a String representation of this RunLengthEncoding.
   */
  public String toString() {
      // Replace the following line with your solution.
      // It works!!
      Run current_run = head.next;
      String result = "[";
      while (current_run.run_length != 0) {
          if (current_run.next.run_length == 0) {
              result += "[" + Integer.toString(current_run.run_length) + ", " + Integer.toString(current_run.red) + ", " +
                      Integer.toString(current_run.green) + ", " + Integer.toString(current_run.blue) + "]";
          } else {
              result += "[" + Integer.toString(current_run.run_length) + ", " + Integer.toString(current_run.red) + ", " +
                      Integer.toString(current_run.green) + ", " + Integer.toString(current_run.blue) + "], ";
          }
          current_run = current_run.next;
      }

      result += "]";
      return result;
  }


  /**
   *  The following methods are required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   * 
   *  Note that you must encode the image in row-major format, i.e., the second
   *  pixel should be (1, 0) and not (0, 1).
   *
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {
      // Your solution here, but you should probably leave the following line
      // at the end.
      // It works!!
      head = new Run(0, 0, 0, 0);
      head.next = head;
      head.prev = head;
      width = image.getWidth();
      height = image.getHeight();
      int i = 0;
      int j = 0;
      while (j < image.getHeight() && i < image.getWidth()) {
          int run_length = 1;
          int red = image.getRed(i, j);
          int green = image.getGreen(i, j);
          int blue = image.getBlue(i, j);
          int next_i = i;
          int next_j = j;

          if (i == image.getWidth()-1 && j != image.getHeight()-1) {
              next_i = 0;
              next_j++;
          } else if (i == image.getWidth()-1 && j == image.getHeight()-1) {
              addToEnd(run_length, red, green, blue);
              break;
          } else {
              next_i++;
          }

          while (red == image.getRed(next_i, next_j) && green == image.getGreen(next_i, next_j) &&
                  blue == image.getBlue(next_i, next_j)) {

              run_length++;

              if (next_i == image.getWidth()-1 && next_j != image.getHeight()-1) {
                  next_i = 0;
                  next_j++;
              } else if (next_i == image.getWidth()-1 && next_j == image.getHeight()-1) {
                  break;
              } else {
                  next_i++;
              }
          }

          addToEnd(run_length, red, green, blue);

          i = next_i;
          j = next_j;
      }
  check();
  }

   public void addToEnd(int run_length, int red, int green, int blue) {
       if (size == 0) {
           head.next = new Run(run_length, red, green, blue, head, head);
           head.prev = head.next;
           size++;
       } else {
           head.prev.next = new Run(run_length, red, green, blue, head, head.prev);
           head.prev = head.prev.next;
           size++;
       }
   }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
      // Your solution here.
      Run current_run = head.next;
      int total_run_lengths = current_run.run_length;
      while (current_run.next.run_length != 0) {
          total_run_lengths += current_run.next.run_length;
          if (current_run.red == current_run.next.red && current_run.green == current_run.next.green &&
                  current_run.blue == current_run.next.blue) {
              System.out.println("ERROR!!! There is a mistake in your RunLengthEncoding constructor. Two " +
                      "consecutive runs have the same RGB intensities!!");
          }
          current_run = current_run.next;
      }
      if (total_run_lengths != width * height) {
          System.out.println("ERROR!!! The total run_length does not equal the number of pixels in this image!!");
      }
  }


  /**
   *  The following method is required for Part IV.
   */

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
      // Your solution here, but you should probably leave the following line
      //   at the end.
      // First find the Run that corresponds to this particular coordinate.
      // 1) If the pixel is different than the RGB of the run it is stored in:
      //    1) If the coordinate is the first pixel in the Run:
      //            1) Check if the pixel matches the pixel on the run before. If it does:
      //                    1) Increase the run length of the previous run
      //                    2) Decrease the run length of this run
      //                    3) Break
      //            2) Add New Run
      //    2) If the pixel is the last pixel in the Run:
      //            1) Check if the pixel matches the pixel on the next run. If it does:
      //                    1) Increase the run length of the next run
      //                    2) Decrease the run length of this run
      //                    3) Break
      //            2) Add New Run
      //    3) If the pixel is anywhere in the run AND it is different than pixels on either side of it:
      //            1) Create a Run2 with run_length = run_length - this pixels place in the run_length and Run1
      //                    with run_length = (this pixel's place in the Run) - 1 and Run3 with run_length = 1 with
      //                    Run3 having a different RGB as the other two Runs. This new RGB should be the input RGB.
      //            2) Link original_run.prev to Run1 to Run2 to Run3 to original_run.next effectively cutting out the
      //                    old run.
      // 2) If the pixel is the same RGB as the run it is stored in:
      //    1) Don't do anything, end program.

      int pixel_number;
      pixel_number = (width * y) + (x + 1);
      RunIterator i = iterator();
      int counter = 0;
      Run current_run = head;
      System.out.println("The original RunLengthEncoding is: " + toString());
      System.out.println("The pixel being added is: " + Integer.toString(x) + ", " + Integer.toString(y) + ", " +
              Integer.toString(red) + ", " + Integer.toString(green) + ", " + Integer.toString(blue));
      while (pixel_number > counter) {
          if (i.hasNext()) {
              counter += i.next()[0];
          } else {
              counter += i.next()[0];
          }
          current_run = current_run.next;
      }
      // Now, current_run should be pointing to the run which contains the pixel we need to change
      if (current_run.red == red && current_run.green == green && current_run.blue == blue) {

      } else if (current_run.run_length == 1) {
          if (current_run.prev.red == red && current_run.prev.green == green && current_run.prev.blue == blue) {
              current_run.prev.run_length++;
              current_run.prev.next = current_run.next;
              current_run.next.prev = current_run.prev;
          } else if (current_run.next.red == red && current_run.next.green == green && current_run.next.blue == blue) {
              current_run.next.run_length++;
              current_run.prev.next = current_run.next;
              current_run.next.prev = current_run.prev;
          } else {
              current_run.red = red;
              current_run.green = green;
              current_run.blue = blue;
          }
      } else if (counter - current_run.run_length + 1 == pixel_number) { // If pixel is first pixel in current_run
          if (current_run.prev.run_length != 0 && current_run.prev.red == red && current_run.prev.green == green &&
                  current_run.prev.blue == blue) {
              current_run.prev.run_length += 1;
              current_run.run_length -= 1;
          } else {
              current_run.prev.next = new Run(1, red, green, blue, current_run, current_run.prev);
              current_run.prev = current_run.prev.next;
              size++;
              current_run.run_length -= 1;
          }
      } else if (counter == pixel_number) {  // If pixel is last pixel in current_run
          if (current_run.next.red == red && current_run.next.green == green && current_run.next.blue == blue) {
              current_run.next.run_length += 1;
              current_run.run_length -= 1;
          } else {
              current_run.next.prev = new Run(1, red, green, blue, current_run.next, current_run);
              current_run.next = current_run.next.prev;
              size++;
              current_run.run_length -= 1;
          }
      } else {
          current_run.prev.next = new Run(current_run.run_length - (counter - pixel_number) - 1, current_run.red,
                  current_run.green, current_run.blue);
          current_run.prev.next.prev = current_run.prev;
          current_run.prev.next.next = new Run(1, red, green, blue, null, current_run.prev.next);
          current_run.prev.next.next.next = new Run (counter - pixel_number, current_run.red, current_run.green,
                  current_run.blue, current_run.next, current_run.prev.next.next);
          size += 2;
      }
      System.out.println("The result is: " + toString());
      check();
  }


  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");


    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}
