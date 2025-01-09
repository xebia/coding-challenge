import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class PascalsTriangle {

    List<Integer> triangleRow(int n) {
        if (n == 0) {
            return asList(1);
        }

        var previousRow = triangleRow(n - 1);

        var row = new ArrayList<Integer>();
        row.add(1);
        for (int col = 1; col < n; col++) {
            row.add(previousRow.get(col - 1) + previousRow.get(col) + 1);
        }
        row.add(1);

        return row;
    }
}
