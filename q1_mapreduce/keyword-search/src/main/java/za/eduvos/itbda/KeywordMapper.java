package za.eduvos.itbda;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

// Q1.2 - Mapper
public class KeywordMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Pattern pattern;
    private String fileName;
    private long row = 0;
    private final Text outKey = new Text();
    private final Text outVal = new Text();

    @Override
    protected void setup(Context context) {
        String keyword = context.getConfiguration().get("search.keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("search.keyword not supplied");
        }
        pattern = Pattern.compile("\\b" + Pattern.quote(keyword.trim()) + "\\b",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
        row = 0;
    }

    @Override
    protected void map(LongWritable offset, Text line, Context context)
            throws IOException, InterruptedException {
        row++;
        Matcher m = pattern.matcher(line.toString());
        while (m.find()) {
            int col = m.start() + 1;
            outKey.set(fileName);
            outVal.set("(" + row + "," + col + ")");
            context.write(outKey, outVal);
            context.getCounter("KeywordSearch", "MATCHES").increment(1);
        }
    }
}
