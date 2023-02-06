package org.fit.hiai.hadoop;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;
import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.hadoop.FullFileInputFormat;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NeedlemanWunshHadoop {


    //<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
    public static class NWAMapper extends Mapper<NullWritable, Text, Text, Text> {
        private Logger mapperLogger = Logger.getLogger(NWAMapper.class);

        private String filenameKey, sqlSinkStr, sqlTaintStr;
        private Path filePath;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            InputSplit split = context.getInputSplit();
            filePath = ((FileSplit) split).getPath();
            filenameKey = filePath.getName();

            sqlSinkStr = context.getConfiguration().get("sqlSinks");
            sqlTaintStr = context.getConfiguration().get("sqlTaints");


            //example filenamekey: golaberto.googlecode.com~WorkPanelList.java
//            int tildeIndex = filenameKey.indexOf("~");
//            if(tildeIndex > 0)
//                filenameKey = filenameKey.substring(0, tildeIndex);

        }

        //<KEYIN, VALUEIN, Context>
        protected void map(NullWritable key, Text value, Context context) throws IOException, InterruptedException {
            boolean sqlCommandsFound = false;
            String inputValue = value.toString();
            Set<String> sqlSinks = new HashSet<>(Arrays.asList(sqlSinkStr.split(",")));
            Set<String> sqlTaintedSrcs = new HashSet<>(Arrays.asList(sqlTaintStr.split(",")));

            //make sure there is text in the incoming file
            if (!inputValue.isEmpty() && inputValue.length() >= ProjectConstants.MIN_CODE_LENGTH) {
                context.write(new Text(filenameKey), new Text()); //enclose output as text
                }

            }
        }


    //<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
    public static class NWAReducer extends Reducer<Text, Text, Text, Text> {
        private Logger reducerLogger = Logger.getLogger(NWAReducer.class);

        @Override
        public void run(Context context) throws IOException, InterruptedException {
            this.setup(context);

            //add header labels to the output file
            Text column = new Text("FileID");
            String headerLabels = "";
            context.write(column, new Text(headerLabels));

            try {
                while (context.nextKey()) {
                    this.reduce(context.getCurrentKey(), context.getValues(), context);
                }
            } finally {
                this.cleanup(context);
            }
        }

        public void reduce(Text key, Text value, Context context) throws IOException, InterruptedException {
            //reducerLogger.info("Generated Featureset: " + sqlFESet.toString());

            context.write(key, value);

        }
    }


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapreduce.output.textoutputformat.separator", ProjectConstants.FEATURE_SEPARATOR + "");

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 4) {
            System.err.println("Usage: Driver <in> <out> <sinksfile>");
            System.exit(2);
        }


        FileSystem fs = FileSystem.get(conf);
//        //get the FileStatus list from given dir
//        FileStatus[] status_list = fs.listStatus(new Path(otherArgs[0]));
//        if (status_list != null) {
//
//            for (FileStatus status : status_list) {
//                //add each file to the list of inputs for the map-reduce job
//                FileInputFormat.addInputPath(job, status.getPath());
//            }
//        }

        Path sinksFilePath = new Path(otherArgs[2]);
        Path taintedSrcsFilePath = new Path(otherArgs[3]);

        FSDataInputStream sinksInputStream = fs.open(sinksFilePath);
        FSDataInputStream taintedSrcsInputStream = fs.open(taintedSrcsFilePath);

//        String sinksText = IOUtils.toString(inputStream, "UTF-8");
        LineIterator sinkIterator = IOUtils.lineIterator(sinksInputStream, "UTF-8");
        LineIterator taintIterator = IOUtils.lineIterator(taintedSrcsInputStream, "UTF-8");

        Set<String> sqlSinks = new HashSet<>();
        Set<String> sqlTaintedSrcs = new HashSet<>();

        while (sinkIterator.hasNext()) {
            sqlSinks.add(sinkIterator.nextLine());
        }

        while (taintIterator.hasNext()) {
            sqlTaintedSrcs.add(taintIterator.nextLine());
        }



        conf.set("sqlSinks", String.join(",", sqlSinks));
        conf.set("sqlTaints", String.join(",", sqlTaintedSrcs));
        conf.set("mapreduce.job.split.metainfo.maxsize", "-1");

        Job job = Job.getInstance(conf, "SQLIMRFeatureExtractor");

        //process multiple paths
        String [] pathStr = otherArgs[0].split(";");

        for(String filePath : pathStr) {

            FileStatus[] status_list = fs.listStatus(new Path(filePath));

            if (status_list != null) {

                for (FileStatus status : status_list) {
                    //add each file to the list of inputs for the map-reduce job
                    FileInputFormat.addInputPath(job, status.getPath());
                }
            }
        }

        System.out.println("Processed " + pathStr.length +" input paths");

        //use this if there is only one path
//        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        job.setJarByClass(NeedlemanWunshHadoop.class);


        job.setInputFormatClass(FullFileInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);


        job.setMapperClass(NWAMapper.class);
        job.setReducerClass(NWAReducer.class);
//        job.setNumReduceTasks(0); //turn off reducer

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        /*====Expected usage
        org.fit.hiai.hadoop jar /path/CodeSearcher.jar classpath.CodeSearcher
        /path/to/input/dataset 0
        /path/to/output/folder 1
        /path/to/sql/sinks/file 2
        /path/to/sql/taints/file 3
        */




        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
