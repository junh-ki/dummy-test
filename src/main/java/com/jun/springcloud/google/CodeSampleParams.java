package com.jun.springcloud.google;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.PrintStream;

/**
 * A base class for all code examples to handle command line argument parsing and usage printing.
 */
public abstract class CodeSampleParams {

    @Parameter(names = "--help", help = true)
    protected boolean help = false;

    public boolean parseArguments(String[] args) {
        return parseArguments(args, Runtime.getRuntime(), System.err);
    }

    boolean parseArguments(String[] args, Runtime runtime, PrintStream usageStream) {
        JCommander jc = new JCommander(this);

        if (args.length == 0) {
            return false;
        }

        jc.parse(args);

        if (help) {
            StringBuilder usageOut = new StringBuilder();
            jc.usage(usageOut);
            usageStream.println(usageOut.toString());
            runtime.exit(0);
        }

        return true;
    }
}
