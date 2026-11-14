package za.eduvos.itbda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// Q1.3 - Reducer
public class KeywordReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text file, Iterable<Text> positions, Context context)
            throws IOException, InterruptedException {
        List<int[]> list = new ArrayList<>();
        for (Text p : positions) {
            String[] rc = p.toString().replaceAll("[()]", "").split(",");
            list.add(new int[]{Integer.parseInt(rc[0]), Integer.parseInt(rc[1])});
        }
        list.sort((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        StringBuilder sb = new StringBuilder("count=" + list.size() + " |");
        for (int[] rc : list) {
            sb.append(" (").append(rc[0]).append(",").append(rc[1]).append(")");
        }
        context.write(file, new Text(sb.toString()));
    }
}
