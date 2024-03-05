package edu.school21.printer.logic;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = {"--white"})
    private String param1;

    @Parameter(names = {"--black"})
    private String param2;

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }
}
