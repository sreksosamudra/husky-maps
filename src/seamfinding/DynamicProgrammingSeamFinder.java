package seamfinding;

import kotlin.collections.ArrayDeque;
import seamfinding.energy.EnergyFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {

        double[][] energyCost = new double[picture.width()][picture.height()];
        List<Integer> result = new ArrayList<>();

        //Fill out the energy pixels in leftmost column
        for (int y = 0; y < picture.height(); y++) {
            energyCost[0][y] = f.apply(picture, 0, y);
        }

        //For each pixel in each of the remaining columns,...
        for (int x = 1; x < picture.width(); x++) {
            for(int y = 0; y < picture.height(); y++) {
                double up = Double.POSITIVE_INFINITY;
                double down = Double.POSITIVE_INFINITY;

                // Find the energy of neighbor pixels: left-down, left-mid, and left-down...
                if (y > 0) {
                    down = energyCost[x-1][y-1];
                }
                if (y < picture.height()-1) {
                    up = energyCost[x-1][y+1];
                }
                double mid = energyCost[x-1][y];

                // Compute the total energy cost to the current pixel by adding its energy to the
                energyCost[x][y] = f.apply(picture, x, y) + Math.min((Math.min(up, down)), mid);
            }
        }

        //Add the y (index) value of pixel with smallest energy
        int index = 0;
        for (int y = 0; y < picture.height(); y++) {
            if (energyCost[picture.width()-1][y] < energyCost[picture.width()-1][index]) {
                index = y;
            }
        }
        result.add(index);

        //Follow the path back to left
        for (int x = picture.width()-1; x > 0; x--) {
            double up = Double.POSITIVE_INFINITY;
            double down = Double.POSITIVE_INFINITY;

            // Find the neighbors of the predecessor pixel
            if (index > 0) {
                down = energyCost[x-1][index-1];
            }
            if (index < picture.height()-1) {
                up = energyCost[x-1][index+1];
            }
            double mid = energyCost[x-1][index];

            //Compare which neighbor pixel has lowest energy
            if (up <= Math.min(mid, down)) {
                index++;
            } else if (down < mid) {
                index--;
            }
            result.add(index);
        }
        //Reverse 'result' to get the coordinates from left to right
        Collections.reverse(result);

        return result;
    }
}
