package org.fit.hiai.hadoop;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

public class FullFileInputFormat extends FileInputFormat<NullWritable, Text> {
    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }
    @Override
    public RecordReader<NullWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        CustomRepoFileRecordReader reader = new CustomRepoFileRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}
