package com.eggsnham.Debug;

import com.eggsnham.Debug.Level;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DebugLogger {

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    String time = formatter.format(date).split("\s")[1];

    private File file;

    public DebugLogger(File file)
    {
        this.file = file;
    }

    public void appendToFile(String text)
    {
        try
        {
            FileWriter writer = new FileWriter(file, true);
            writer.write(text);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToFile(String text)
    {
        try
        {
            FileWriter writer = new FileWriter(file, false);
            writer.write(text);
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void clearDebugLog()
    {
        writeToFile("");
    }

    public void log(String text)
    {
        appendToFile("[" + time + " INFO]: " + text + "\n");
    }

    public void log(String text, Level lvl)
    {
        String level = "[" + time + " INFO]: ";

        if(lvl == Level.INFO) level = "[" + time + " INFO]: ";
        if(lvl == Level.WARNING) level = "[" + time + " WARNING]: ";
        if(lvl == Level.ERROR) level = "[" + time + " ERROR]: ";

        appendToFile(level + text + "\n");
    }
}
