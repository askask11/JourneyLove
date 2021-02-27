/*
 * Author: jianqing
 * Date: Feb 28, 2021
 * Description: This document is created for
 */
package journeylove;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * User Preference.
 *
 * @author Jianqing gao
 */
public class Preference implements AutoCloseable
{

    private String savingPath;
    private String rotateMethod;
    public static final String SEQUENCE_ROTATE = "Sequence";
    public final static String SINGLE_ROTATE = "Single";
    static public final String RANDOM_ROTATE = "Random";

    public Preference()
    {
        savingPath = "";
        rotateMethod = SEQUENCE_ROTATE;
    }

    public Preference(String savingPath, String rotateMethod)
    {
        this.savingPath = savingPath;
        this.rotateMethod = rotateMethod;
    }

    public void setRotateMethod(String rotateMethod)
    {
        this.rotateMethod = rotateMethod;
    }

    public void setSavingPath(String savingPath)
    {
        this.savingPath = savingPath;
    }

    public String getRotateMethod()
    {
        return rotateMethod;
    }

    public String getSavingPath()
    {
        return savingPath;
    }

    public static Preference getInstance() throws IOException
    {
        File dir = new File("./Program Files");
        if (!dir.exists())
        {
            dir.mkdir();
        }

        File settingFile = new File(dir, "preferences.json");
        if (!settingFile.exists())
        {
            settingFile.createNewFile();
            Preference modelClass = new Preference();
            writeIntoFile(modelClass, settingFile);
            return modelClass;
        } else
        {
            try
            {
                JSONObject json = new JSONObject(FileUtil.readString(settingFile, "UTF-8"));
                return new Preference(json.getStr("savingPath"), json.getStr("rotateMethod"));
            } catch (Exception ex)
            {
                settingFile.createNewFile();
                Preference modelClass = new Preference();
                writeIntoFile(modelClass, settingFile);
                return modelClass;
            }
        }

    }

    public void save() throws IOException
    {
        //see if the mother dir exists. if not, create.
        File dir = new File("./Program Files");
        if (!dir.exists())
        {
            dir.mkdir();
        }
        //read the file
        File settingFile = new File(dir, "preferences.json");
        if (!settingFile.exists())
        {
            //if does not exist, create a new one.
            settingFile.createNewFile();
        }
        writeIntoFile(this, settingFile);
    }

    private static void writeIntoFile(Preference pf, File target) throws IOException
    {
        try (BufferedWriter writer = FileUtil.getWriter(target, Charset.forName("UTF-8"), false))
        {
            JSONObject json = JSONUtil.parseObj(pf);
            json.write(writer);
            writer.flush();
        }
    }

    public static void main(String[] args) throws IOException
    {
        Preference p = Preference.getInstance();
        p.setSavingPath("/Users/jianqing/Desktop");
        p.save();
    }
    
    @Override
    public void close() throws IOException
    {
        save();
    }
}
