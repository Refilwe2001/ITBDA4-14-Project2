package za.eduvos.itbda;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

// Q1.4 - Hadoop MapReduce configuration (driver)
public class KeywordSearchDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: KeywordSearchDriver <input> <output> <keyword>");
            return 1;
        }
        Configuration conf = getConf();
        conf.set("search.keyword", args[2]);
        conf.set("mapreduce.output.textoutputformat.separator", "\t");

        Job job = Job.getInstance(conf, "Keyword Search: " + args[2]);
        job.setJarByClass(KeywordSearchDriver.class);
        job.setInputFormatClass(NonSplittableTextInputFormat.class);
        job.setMapperClass(KeywordMapper.class);
        job.setReducerClass(KeywordReducer.class);
        job.setNumReduceTasks(1);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        FileSystem fs = out.getFileSystem(conf);
        if (fs.exists(out)) fs.delete(out, true);
        FileInputFormat.addInputPath(job, in);
        FileOutputFormat.setOutputPath(job, out);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new Configuration(), new KeywordSearchDriver(), args));
    }
}
