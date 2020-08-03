

import java.util.Scanner;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class Solution {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    int numberOfSkyscrapers = scanner.nextInt();
    int[] height = new int[numberOfSkyscrapers];
    for (int i = 0; i < numberOfSkyscrapers; i++) {
      height[i] = scanner.nextInt();
    }
    scanner.close();

    long result = calculate_numberOfValidRoutes(height);
    System.out.println(result);
  }

  private static long calculate_numberOfValidRoutes(int[] height) {
    Stack<Integer> previousHeights = new Stack<Integer>();
    Map<Integer, Integer> frequencyPerHeight = new HashMap<Integer, Integer>();
    long numberOfValidRoutes = 0;

    for (int i = 0; i < height.length; i++) {

      // Consequtive decreasing heights: record frequency and put them in stack.
      if (previousHeights.isEmpty() || previousHeights.peek() > height[i]) {
        previousHeights.push(height[i]);
        fillMap_frequencyPerHeight(height[i], frequencyPerHeight);
      }
      // Equal heights: only record their frequency, no need to be more than once in the stack.
      else if (previousHeights.peek() == height[i]) {
        fillMap_frequencyPerHeight(height[i], frequencyPerHeight);
      }
      // Previous smaller heights: calculate valid routes, remove them from stack and map.
      else if (previousHeights.peek() < height[i]) {
        while (!previousHeights.isEmpty() && previousHeights.peek() < height[i]) {
          int h = previousHeights.pop();
          if (frequencyPerHeight.containsKey(h)) {
            numberOfValidRoutes =
                numberOfValidRoutes
                    + (long) frequencyPerHeight.get(h) * (long) (frequencyPerHeight.remove(h) - 1);
          }
        }

        fillMap_frequencyPerHeight(height[i], frequencyPerHeight);
        previousHeights.push(height[i]);
      }
    }

    // Calculate valid routes for any heights not removed during the iteration.
    for (int h : frequencyPerHeight.keySet()) {
      numberOfValidRoutes =
          numberOfValidRoutes
              + (long) (frequencyPerHeight.get(h) * (long) (frequencyPerHeight.get(h) - 1));
    }
    return numberOfValidRoutes;
  }

  private static void fillMap_frequencyPerHeight(
      int height, Map<Integer, Integer> frequencyPerHeight) {
    if (!frequencyPerHeight.containsKey(height)) {
      frequencyPerHeight.put(height, 1);
    } else {
      frequencyPerHeight.put(height, frequencyPerHeight.get(height) + 1);
    }
  }
}
