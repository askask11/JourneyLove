/*   !!PLEASE DO NOT STAY UP DOING THIS PROGRAM!! 
 * Editor: Johnson Gao
 * Date This Project Created:wo aini
 * Description Of This Class: Connected to database : secretgarden.
 * 为了偶的小甜心~加油↖(^ω^)↗
 
I LOOOOOVE YOU JENNY!!!!!!!!!!
 */
package journeylove;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javazoom.jl.decoder.JavaLayerException;

/**
 * This project will only have one database: SecretGarden. This database will
 * have multiple table that stores diffrernt infos.
 * <p>
 * Do not</p> manually erase data in ‘Table’table.
 *
 * @author Johnson Gao For his cute & beautiful girlfriend Jenny. >v<
 */
public class SecretGardenConnection implements AutoCloseable
{

    Connection dbConn = null;
    String dbName = "SecretGarden";
    public final static String[] COLLECTORHEADER =
    {
        "Name", "Columns_Detail", "Descriptions"
    };

    public SecretGardenConnection()
    {
        this.setDbConn();
    }

    static final String EXPORT_SUBSPLIT = ">";

    static final String EXPORT_SPLIT = "||";

    static final String ALL_IMAGES_TABLE_NAME = "ImagePlayList";

    static final String IMAGES_SUBLIST_PREFIX = "ImageSubList_";

    static final String IMAGETABLES_COLUMNS = "Name varchar(90), UrlAddress varchar(400), type int, ID int ,Description varchar(600)";
//Collctors already created, code not yet updated

    final String PREF_KEY_SAVINGPATH = "Default Saving Path";

    final String PERF_KEY_MAILBOX = "MailBoxAddress";
    final String PERF_KEY_MUSIC_PLAYMODE = "MusicPlayMode";

    /**
     * This returns an arraylist contains the data from the table.
     *
     * @param tableName The name of table which are being read.
     * @param tableHeader The columns of the table.
     * @return The data inside the table.
     */
    public ArrayList<ArrayList<String>> getTablesData(String tableName, String[] tableHeader)
    {
        return getTablesData(tableName, tableHeader, ALL_IMAGES_TABLE_NAME);
    }

    /**
     * This returns an arraylist contains the data from the table.
     *
     * @param tableName The name of table which are being read.
     * @param tableHeader The columns of the table.
     * @param listName
     * @return The data inside the table.
     */
    public ArrayList<ArrayList<String>> getTablesData(String tableName, String[] tableHeader, String listName)
    {
        System.out.println("Received reading request:"
                + "\n Table name: " + tableName + "\n columns :" + tableHeader);
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        int columnCount = tableHeader.length;
        Statement s = null;
        ResultSet rs = null;
        String dbQuery = "SELECT * FROM " + listName;
        try
        {
            s = this.dbConn.createStatement();
            rs = s.executeQuery(dbQuery);
            while (rs.next())
            {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 0; i < columnCount; i++)
                {
                    row.add(rs.getString(tableHeader[i]));
                }
                data.add(row);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("journeylove.SecretGardenConnection.getData()");
        } catch (NullPointerException npe)
        {
            npe.printStackTrace();
            System.out.println("From: journeylove.SecretGardenConnection.getData()");
            System.out.println("This is what data returned: " + data);
        }
        //System.out.println("");
        return data;
    }

    /*
    *****> For class: DisplayImage Table name: ImagePlayList Values name(varchar 50), UrlAddress(varchar 300), type(int),ID (int) ,Description (varchar(500))<*****
     Also table name: Name: ImageSubLists  Column： Name varchar(50) contains all subtables of imagelist.
     */
    //About displayimage
    /**
     * Get the display image of default list.
     *
     * @return The display images of the default list.
     * @throws SQLException
     */
    public ArrayList<DisplayImage> getDisplayImage() throws SQLException
    {
        return getDisplayImage(ALL_IMAGES_TABLE_NAME);
    }

    public void checkConnection()throws SQLException
    {
        if(this.dbConn.isClosed())
        {
            setDbConn();
        }
    }
    /*
     *****> For class: DisplayImage Table name: ImagePlayList Values name(varchar 50), UrlAddress(varchar 300), type(int),ID (int) ,Description (varchar(500))<*****
    Also table name: Name: ImageSubLists  Column： Name varchar(50) contains all subtables of imagelist.
     */
    //About displayimage
    /**
     * Get the list of display images from an specified list.
     *
     * @param listName The name of the list.
     * @return Displayimage list.
     * @throws SQLException
     */
    public ArrayList<DisplayImage> getDisplayImage(String listName) throws SQLException
    {
        checkConnection();
        
        ArrayList<DisplayImage> data = new ArrayList<>();
        //String tableName = "ImagePlayList";
        //String[] tableHeader = {"Name", "UrlAddress", "Type"};
        ResultSet rs = null;
        //int columns = 3;

        Statement s = dbConn.createStatement();
        System.out.println("journeylove.SecretGardenConnection.getDisplayImage(): Statement Created!");
        rs = s.executeQuery("SELECT * FROM " + listName);
        while (rs.next())
        {
            DisplayImage tempDis = new DisplayImage();
            tempDis.setName(rs.getString("Name"));
            tempDis.setUrlString(rs.getString("UrlAddress"));
            tempDis.setType(rs.getInt("Type"));
            tempDis.setId(rs.getInt("ID"));
            tempDis.setDescription(rs.getString("Description"));
            //tempDis.readIcon();
            data.add(tempDis);
        }

        System.out.print("journeylove.SecretGardenConnection.getDisplayImage() :  ");
        //System.out.println(data);
        return data;
    }

    /**
     * Insert a display image into the default list.
     *
     * @param dm The new display image.
     * @throws SQLException
     */
    public void insertImageIntoList(DisplayImage dm) throws SQLException
    {
        insertImageIntoList(dm, SecretGardenConnection.ALL_IMAGES_TABLE_NAME);
    }

    /**
     * Insert a new image into a specified list.
     *
     * @param dm The new display image.
     * @param listName Name of the list to be edited.
     * @throws SQLException
     */
    public void insertImageIntoList(DisplayImage dm, String listName) throws SQLException
    {
        checkConnection();
        String name = dm.getName();
        String url = dm.getUrlAsString();
        int type = dm.getType();
        int id = dm.getId();
        String description = dm.getDescription();
        String dbQuary = "INSERT INTO " + listName + " VALUES (?,?,?,?,?)";
        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        ps.setString(1, name);
        ps.setString(2, url);
        ps.setInt(3, type);
        ps.setInt(4, id);
        ps.setString(5, description);
        ps.executeUpdate();
        System.out.println("journeylove.SecretGardenConnection.insertImageIntoList()");
        System.out.println("Inserted successfully!!!");
    }

    public void insertImagesInBatch(DisplayImage[] images, String targetListName) throws SQLException
    {
        checkConnection();
        String dbQuary = "INSERT INTO " + targetListName + " VALUES (?,?,?,?,?)";
        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        for (DisplayImage image : images)
        {
            ps.setString(1, image.getName());
            ps.setString(2, image.getUrlAsString());
            ps.setInt(3, image.getType());
            ps.setInt(4, image.getId());
            ps.setString(5, image.getDescription());
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println("journeylove.SecretGardenConnection.insertImagesInBatch()"
                + " Batch executed successfully!");

    }

    /**
     * Remove a member from the default image list using the id.
     *
     * @param id The ID of the image to be removed.
     * @throws SQLException
     */
    public void removeFromImageDisplayList(int id) throws SQLException
    {
        removeFromImageDisplayList(id, SecretGardenConnection.ALL_IMAGES_TABLE_NAME);//delete from mainlist
    }

    /**
     * Remove a specific member from an list using ID of the image.
     *
     * @param id The id of the image.
     * @param listName The name of the list to be edited.
     * @throws SQLException
     */
    public void removeFromImageDisplayList(int id, String listName) throws SQLException
    {
        checkConnection();
        String dbQuary = "DELETE FROM " + listName + " WHERE ID=?";
        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("removed successfully!");
    }

    /**
     * Update an row in the table ImageDisplayList; this will NOT give the
     * column a new ID.Neither will read ID from the object given.
     *
     * @param oldId The id which will be updated.
     * @param alternativeImage The new DisplayImage object.
     * @throws SQLException When failed to update.
     */
    public void updateImageDisplayList(int oldId, DisplayImage alternativeImage) throws SQLException
    {
        updateImageDisplayList(oldId, alternativeImage, SecretGardenConnection.ALL_IMAGES_TABLE_NAME);

    }

    /**
     * Update an row in the table ImageDisplayList; this will NOT give the
     * column a new ID.Neither will read ID from the object given.
     *
     * @param oldId The id which will be updated.
     * @param alternativeImage The new DisplayImage object.
     * @param listName The list to be updated
     * @throws SQLException When failed to update.
     */
    public void updateImageDisplayList(int oldId, DisplayImage alternativeImage, String listName) throws SQLException
    {
        checkConnection();
        String sql = "UPDATE " + listName + " SET Name=?, UrlAddress=? ,Type=?, Description=? WHERE ID = ?";
        PreparedStatement ps = dbConn.prepareStatement(sql);
        ps.setString(1, alternativeImage.getName());
        ps.setString(2, alternativeImage.getUrlAsString());
        ps.setInt(3, alternativeImage.getType());
        ps.setString(4, alternativeImage.getDescription());
        ps.setInt(5, oldId);
        ps.executeUpdate();
        System.out.println("Yeah, updated successfully!");
    }

    /**
     * Switch two members in a list.
     *
     * @param id1 The ID of one of the musics.
     * @param id2 Another music's row ID.
     * @throws SQLException
     */
    public void swapImageDisplayList(int id1, int id2) throws SQLException
    {
        swapImageDisplayList(id1, id2, SecretGardenConnection.ALL_IMAGES_TABLE_NAME);
    }

    /**
     * Switch two members' position in a list.
     *
     * @param id1 The id of a member to be exchanged.
     * @param id2 The id of another member to be exchanged.
     * @param listName The sublist to be exchanged.
     * @throws SQLException
     */
    public void swapImageDisplayList(int id1, int id2, String listName) throws SQLException
    {
        checkConnection();
        String dbQuary = "SELECT * FROM " + listName + " WHERE ID=?";
        DisplayImage img1 = new DisplayImage();
        DisplayImage img2 = new DisplayImage();
        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        ps.setInt(1, id1);
        ResultSet rs = ps.executeQuery();
        rs.next();
        img1.setName(rs.getString("Name"));
        img1.setUrlString(rs.getString("UrlAddress"));
        img1.setType(rs.getInt("Type"));
        img1.setDescription(rs.getString("Description"));
        ps = dbConn.prepareStatement(dbQuary);
        ps.setInt(1, id2);
        rs = ps.executeQuery();
        rs.next();
        img2.setName(rs.getString("Name"));
        img2.setUrlString(rs.getString("UrlAddress"));
        img2.setType(rs.getInt("Type"));
        img2.setDescription(rs.getString("Description"));
        updateImageDisplayList(id2, img1, listName);
        updateImageDisplayList(id1, img2, listName);
        System.out.println("Swamped Successfully @ Imagelist!!!(＾－＾)V");
    }

    /**
     * Export user's list to a .journey file.
     *
     * @param out
     * @throws IOException
     */
    public void exportImageList(File out) throws IOException, SQLException
    {
        exportImageList(out, SecretGardenConnection.ALL_IMAGES_TABLE_NAME);
    }

    /**
     * Export user's list to a .journey file.
     *
     * @param out
     * @param listName
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public void exportImageList(File out, String listName) throws IOException, SQLException
    {
        checkConnection();
        if (!out.exists())
        {
            out.mkdirs();
        }
        File export = new File(out, "ExportImageList" + new java.util.Date().getTime() + ".journey");
        export.createNewFile();
        try (FileWriter writer = new FileWriter(export))
        {

            ArrayList<DisplayImage> list;
            list = getDisplayImage(listName);
            writer.write("{export:db=SecretGarden,name=ImageList}\n");
            for (int i = 0; i < list.size(); i++)
            {//following name>address>type>description
                DisplayImage dp;
                dp = list.get(i);
                writer.write("||" + dp.getName() + EXPORT_SUBSPLIT + dp.getUrlAsString() + EXPORT_SUBSPLIT + dp.getType() + EXPORT_SUBSPLIT + dp.getDescription() + "\n");
            }
            writer.flush();
            System.out.println("EXPORTED!");
        }
    }

    public void importIntoImageList(File in) throws IOException, FileNotFoundException, SQLException
    {
        importIntoImageList(in, ALL_IMAGES_TABLE_NAME);
    }

    /**
     * Import a .journey file into image list.
     *
     * @param in In file.
     * @param tableName Name of the dest table.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public void importIntoImageList(File in, String tableName) throws FileNotFoundException, IOException, SQLException
    {
        checkConnection();
        try (FileReader reader = new FileReader(in))
        {
            BufferedReader br;
            br = new BufferedReader(reader);
            if (br.readLine().equals("{export:db=SecretGarden,name=ImageList}"))//To See if it is a currect file
            {
                String line;
                String[] rs;
                while ((line = br.readLine()) != null)
                {
                    if (line.startsWith("||"))
                    {
                        line = line.replace("||", "");
                        //line=line.concat(" ");
                        rs = line.split(EXPORT_SUBSPLIT);
                        if (rs.length == 3)
                        {
                            insertImageIntoList(new DisplayImage(rs[0], rs[1], Integer.parseInt(rs[2]), Randomizer.randomInt(0, 9999)), tableName);
                        } else if (rs.length == 4)
                        {
                            insertImageIntoList(new DisplayImage(rs[0], rs[1], Integer.parseInt(rs[2]), Randomizer.randomInt(0, 9999), rs[3]), tableName);
                        }
                        //System.out.println(rs.length);

                        System.out.println(Arrays.toString(rs));
                        ;//insert read information
                    }
                }
            } else
            {
                Warning warning = new Warning("Incorrect file! for image list importing");
            }
        }

    }

    public void testImportIntoImageList(File in, String name) throws IOException
    {
        
        java.util.List<String> list = Files.readAllLines(in.toPath());
        for (String img : list)
        {
            System.out.println(img);
        }
    }

    /*
    Image sub list management
     */
    /**
     * Get the sublist information of users. The first member is the system
     * default list.
     *
     * @return The nameList of user-created sublists.
     * @throws SQLException
     */
    public String[] getImageSublists() throws SQLException
    {
        checkConnection();
        String dbQuary = "SELECT * FROM ImageSubLists";
        Statement s = dbConn.createStatement();
        ResultSet rs = s.executeQuery(dbQuary);
        ArrayList<String> lists = new ArrayList<>();
        while (rs.next())
        {
            lists.add(rs.getString(1));
        }
        String[] list = new String[lists.size()];
        for (int i = 0; i < lists.size(); i++)
        {
            list[i] = lists.get(i).replace(IMAGES_SUBLIST_PREFIX, "");
        }
        return list;
    }

    /**
     * Insert a new sublist into the sublist recorder.
     *
     * @param listName The name of the list.
     * @throws SQLException
     */
    public void recordImageSublist(String listName) throws SQLException
    {
        checkConnection();
        String dbQuary = "INSERT INTO ImageSubLists VALUES (?)";
        String name = IMAGES_SUBLIST_PREFIX + listName;
        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        ps.setString(1, name);
        ps.executeUpdate();
        System.out.println("A image sublist has successfully been recorded.  Name: " + name);

    }

    /**
     * Record the action of dropping a list.
     *
     * @param listName Name of the list to be dropped.
     * @return
     * @throws SQLException
     */
    public int recordDropImageList(String listName) throws SQLException
    {
        checkConnection();
        String dbQuary = "DELETE FROM ImageSubLists WHERE Name=?";
        int status;
        String name = IMAGES_SUBLIST_PREFIX + listName;
        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        ps.setString(1, name);
        status = ps.executeUpdate();
        System.out.println("A image sublist has successfully been deleted.  Name: " + name);
        return status;
    }

    /**
     * Create a image sublist.
     *
     * @param listName The name of the new sublist.
     * @throws SQLException
     */
    public void createImageSublist(String listName) throws SQLException
    {
        //String sql = "CREATE TABLE " + IMAGES_SUBLIST_PREFIX+listName;
        //Statement s = dbConn.createStatement();
        checkConnection();
        createTable(IMAGES_SUBLIST_PREFIX + listName, IMAGETABLES_COLUMNS, "A subulist of imagelist.");
        recordImageSublist(listName);//Sublist is under main lists supervision.

    }

    /**
     * Drop an image sublist.
     *
     * @param listName The name of the list to be dropped.
     * @throws SQLException
     */
    public void dropImageSublist(String listName) throws SQLException
    {
        checkConnection();
        dropTable(IMAGES_SUBLIST_PREFIX + listName);
        recordDropImageList(listName);
        System.out.println("A image sublist has beed dropped.");
    }

    /**
     * Insert into a user-defined sublist.
     *
     * @param dm new display image.
     * @param listName Name of the list to be inserted, which does not include
     * perfix.
     * @throws SQLException
     */
    public void insertIntoImageSublist(DisplayImage dm, String listName) throws SQLException
    {
        //insertImageIntoList(dm);//This will insert the display image into the main list to be monitored.
        insertImageIntoList(dm, IMAGES_SUBLIST_PREFIX + listName);//This will be recorded to the sublist.
    }

//    public void deleteFromImageSublist(int id, String listName) throws SQLException
//    {
//        //removeFromImageDisplayList(id);
//        removeFromImageDisplayList(id, listName);
//    }
    /**
     * Update an member in the image sublist using ID.
     *
     * @param id id of the image to be updated.
     * @param dm new display image.
     * @param listName Name of the sublist to be updated, whoch does not include
     * prefix.
     * @throws SQLException
     */
    public void updateImageSublist(int id, DisplayImage dm, String listName) throws SQLException
    {
        //updateImageDisplayList(id, dm);
        updateImageDisplayList(id, dm, IMAGES_SUBLIST_PREFIX + listName);
    }

    //Untested
    /**
     * Rename a user-created sublist.
     *
     * @param oldName
     * @param newName
     * @throws SQLException
     */
    public void renameImageSublist(String oldName, String newName) throws SQLException
    {
        checkConnection();
        if (oldName.startsWith(IMAGES_SUBLIST_PREFIX) && newName.startsWith(IMAGES_SUBLIST_PREFIX))
        {
            String sql = "ALTER TABLE " + oldName + "RENAME TO " + newName;
            String dbQuary = "UPDATE ImageSubLists SET Name=? WHERE Name=?";
            Statement s = dbConn.createStatement();
            PreparedStatement ps = dbConn.prepareStatement(dbQuary);
            ps.setString(1, newName);
            ps.setString(2, oldName);
            ps.executeUpdate();
            s.execute(sql);
            System.out.println("Table " + oldName + " are renamed to " + newName);
        } else
        {
            System.out.println("journeylove.SecretGardenConnection.renameImageSublist()");
            System.err.println("get: Illegal name for" + newName + " or " + oldName);
        }

    }


    /*
    MusicList
    为了小甜心，加油！！！↖(^ω^)↗
        *****>For Class:MyMusic Contains Columns:  Name varchar(150), URL varchar(400), Type int, ID int, Time varchar(15), LyricAddress varchar300<*****
     */
    /**
     * Get the arraylist contains the data of my musiclist.
     *
     * @return Data in musiclist table.
     * @throws SQLException Failed to connect.
     */
    public ArrayList<MyMusic> getMyMusics() throws SQLException
    {
        checkConnection();
        ArrayList<MyMusic> musics = new ArrayList<>();
        String dbQuary = "SELECT * FROM MusicList";
        System.out.println("MusicList: ");
        Statement s = dbConn.createStatement();
        ResultSet rs;
        rs = s.executeQuery(dbQuary);
        while (rs.next())
        {
            //System.out.println(rs.getString(2));
            MyMusic tempMusic = new MyMusic(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
            musics.add(tempMusic);
        }
        return musics;
    }

    /**
     * Remove a specific member by ID from the music list.
     *
     * @param id The id of the member.
     * @throws SQLException when failed.
     */
    public void removeFromMusicList(int id) throws SQLException
    {
        checkConnection();
        String sql = "DELETE FROM MusicList WHERE ID=?";
        PreparedStatement ps = dbConn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("removed from music list id= " + id + " successfully!");
    }

    /**
     * Add a music into music list. If the music don't have a specific time,
     * method <code> MyMusic.calculateTimeLength </code> will be invoked to get
     * the time length of the music. It will read the file one more time.
     *
     * @param myMusic The object of music that is going to be added.
     * @return either row count or 0 for SQL statments that returns nothing.
     * @throws SQLException Connection error.
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws javax.sound.sampled.LineUnavailableException
     * @throws java.io.IOException
     */
    public int addIntoMusicList(MyMusic myMusic) throws SQLException, UnsupportedAudioFileException, JavaLayerException, LineUnavailableException, IOException
    {
        checkConnection();
        String sql = "INSERT INTO MusicList VALUES (?,?,?,?,?,?)";
        int executed;
        //sql= "INSERT INTO MusicList VALUES Name=?, Url=?, Type=?,ID=?,Time=?,LyricAddress=?";
        PreparedStatement ps = dbConn.prepareStatement(sql);
        ps.setString(1, myMusic.getName());
        ps.setString(2, myMusic.getUrl());
        ps.setInt(3, myMusic.getType());
        ps.setInt(4, myMusic.getId());
        ps.setString(5, myMusic.getTime());
        ps.setString(6, myMusic.getLyricAddress());
        //ps.setString(7, myMusic.getLyricAddress());
        //System.out.println(ps.toString());
        //ps.setInt(6, myMusic.getParam2());
        executed = ps.executeUpdate();
        System.out.println("Congrats!! for :" + myMusic);
        System.out.println("journeylove.SecretGardenConnection.addIntoMusicList():  Inserted succssfully!!");
        //need to parpare for ps and dispatch it, need test.
        return executed;
    }

    public int[] addIntoMusicListInBatch(MyMusic[] musicList) throws SQLException, UnsupportedAudioFileException, JavaLayerException, LineUnavailableException, IOException
    {
        checkConnection();
        String sql = "INSERT INTO MusicList VALUES (?,?,?,?,?,?)";
        //sql= "INSERT INTO MusicList VALUES Name=?, Url=?, Type=?,ID=?,Time=?,LyricAddress=?";
        int[] executeBatch;
        PreparedStatement ps = dbConn.prepareStatement(sql);

        for (MyMusic myMusic : musicList)
        {
            ps.setString(1, myMusic.getName());
            ps.setString(2, myMusic.getUrl());
            ps.setInt(3, myMusic.getType());
            ps.setInt(4, myMusic.getId());
            ps.setString(5, myMusic.getTime());
            ps.setString(6, myMusic.getLyricAddress());
            ps.addBatch();
        }
        executeBatch = ps.executeBatch();
        System.out.println("journeylove.SecretGardenConnection.addIntoMusicListInBatch(): added successfully.");
        return executeBatch;
    }

    /**
     * Update a specific ID of music in a row, it will not change the ID of the
     * row.
     *
     * @param id The ID needs to be changed.
     * @param newMusic The new music to put into table.
     * @throws java.sql.SQLException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public void updateMusicList(int id, MyMusic newMusic) throws SQLException, UnsupportedAudioFileException, JavaLayerException, LineUnavailableException, IOException
    {
        checkConnection();
        String sql = "UPDATE MusicList SET Name=?, URL=?, Type=?, Time=?, LyricAddress=? WHERE ID=?";
        PreparedStatement ps = dbConn.prepareStatement(sql);
        ps.setString(1, newMusic.getName());
        ps.setString(2, newMusic.getUrl());
        ps.setInt(3, newMusic.getType());
        ps.setString(4, newMusic.getTime());
        ps.setString(5, newMusic.getLyricAddress());
        ps.setInt(6, id);
        ps.executeUpdate();
        System.out.println("From: journeylove.SecretGardenConnection.updateMusicList()");
        System.out.println("Congrats!! music list updated successfully!");
    }

    /**
     * Swap(exchange) two members in the music list. ID of two musics will be
     * changed.
     *
     * @param id1 First music to be changed.
     * @param id2 Another music need to be changed.
     * @throws SQLException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws javax.sound.sampled.LineUnavailableException
     * @throws java.io.IOException
     */
    public void swapMusicList(int id1, int id2) throws SQLException, UnsupportedAudioFileException, JavaLayerException, LineUnavailableException, IOException
    {
        checkConnection();
        MyMusic music1 = new MyMusic();
        MyMusic music2 = new MyMusic();
        String dbQuary = "SELECT * FROM MusicList Where ID=?";
        //String sql = "UPDATE MusicList SET Name=?, URL=?, Type=?, Time=?, ID=? WHERE ID=?";
        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        ResultSet rs1, rs2;
        ps.setInt(1, id1);
        rs1 = ps.executeQuery();
        rs1.next();
        music1.setName(rs1.getString(1));
        music1.setUrl(rs1.getString(2));
        music1.setType(rs1.getInt(3));
        music1.setId(rs1.getInt(4));
        music1.setTime(rs1.getString(5));
        music1.setLyricAddress(rs1.getString(6));

        //music1.setId(rs1.getInt(6));
        System.out.println("Music one result: " + music1);

        ps = dbConn.prepareStatement(dbQuary);
        ps.setInt(1, id2);
        rs2 = ps.executeQuery();
        rs2.next();
        music2.setName(rs2.getString(1));
        music2.setUrl(rs2.getString(2));
        music2.setType(rs2.getInt(3));
        music2.setId(rs2.getInt(4));
        music2.setTime(rs2.getString(5));
        music2.setLyricAddress(rs2.getString(6));
        //music2.setId(rs2.getInt(6));
        System.out.println("Music 2 result: " + music2);

        updateMusicList(id1, music2);
        updateMusicList(id2, music1);

        System.out.println("Two members are swamped successfully!");

    }

    /**
     * This will export user's musiclist information into an txt file.
     *
     * @param out
     * @throws SQLException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public void exportMusicList(File out) throws SQLException, UnsupportedAudioFileException, JavaLayerException, LineUnavailableException, IOException
    {
        //create a new file, if the directory does not exist, create one.
        //String outpath = out.getAbsolutePath()+"\\Export";
        //File pathFile = new File(outpath);
        checkConnection();
        if (!out.exists())
        {
            out.mkdirs();
        }
        //create output file using default saving address ending with time.
        File outFile = new File(out, "MusicListExport" + new java.util.Date().getTime() + ".journey");
        outFile.createNewFile();
        System.out.println("New file " + outFile.getAbsolutePath() + " is created!");

        try (FileWriter writer = new FileWriter(outFile))
        {//write in file

            writer.write("{export:db=SecretGarden,name=MusicList}\n");
            ArrayList<MyMusic> musicList;
            musicList = getMyMusics();
            for (int i = 0; i < musicList.size() - 1; i++)
            {
                MyMusic music = musicList.get(i);
                //following name>address>type>id>time
                writer.write(EXPORT_SPLIT + music.getName().replace(EXPORT_SUBSPLIT, "<") + EXPORT_SUBSPLIT + music.getUrl() + EXPORT_SUBSPLIT + music.getType() + EXPORT_SUBSPLIT + music.getId() + EXPORT_SUBSPLIT + music.getTime() + EXPORT_SUBSPLIT + music.getLyricAddress() + "\n");
            }
            writer.flush();
            System.out.println("Successfully written into file");
        }
        System.out.println("Balaboom!");

    }

    /**
     * Import from exported file into the app.
     *
     * @param in Import file.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public void importIntoMusicList(File in) throws FileNotFoundException, IOException, SQLException, UnsupportedAudioFileException, JavaLayerException, LineUnavailableException
    {
        //Create a file reader with java try-with-resources.
        checkConnection();
        try (FileReader reader = new FileReader(in))
        {

            //transfer reader into buffered reader, which java can understand.
            BufferedReader br = new BufferedReader(reader);

            //Files.readAllLines(in.toPath());
            String line;
            String[] rs;
            //br.
            if (br.readLine().equals("{export:db=SecretGarden,name=MusicList}"))
            {
                while ((line = br.readLine()) != null)
                {
                    if (line.startsWith(EXPORT_SPLIT))
                    {
                        line = line.replace("||", "");

                        rs = line.split(EXPORT_SUBSPLIT);
//                     
                        addIntoMusicList(new MyMusic(rs[0], rs[1], Integer.parseInt(rs[2]), Integer.parseInt(rs[3]), rs[4], rs[5]));
                    }
                }

            } else
            {
                Warning warning = new Warning("Incorrect file for music.");
            }
        }
    }

    /**
     * ****************************************
     * Name: Preferences Column： Name varchar(30), Information varchar(300)
     * description: This is the perferences of the user. Now have 1.Name: 1.
     * Default Saving Path 2. MailBoxAddress , 3.MusicPlayMode (default =
     * Sequence)
     */
    /**
     * Insert a new setting into the setting field.
     *
     * @param name Name of the setting.
     * @param defaultVar The default given.
     * @throws SQLException
     */
    public void insertIntoPreferencesList(String name, String defaultVar) throws SQLException
    {
        checkConnection();
        System.out.println("journeylove.SecretGardenConnection.insertIntoPreferencesList()");
        String sql = "INSERT INTO Preferences VALUES (?,?)";
        PreparedStatement ps = dbConn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, defaultVar);
        ps.executeUpdate();
        System.out.println("Name " + name + "default " + defaultVar + " are inserted successfully.");
    }

    public String getInfoFromPerferList(String key) throws SQLException
    {
        checkConnection();
        String dbQuary = "SELECT * FROM Preferences Where Name = ?";
        String info;
        try (PreparedStatement ps = dbConn.prepareStatement(dbQuary))
        {
            ResultSet rs = null;
            ps.setString(1, key);
            rs = ps.executeQuery();
            if (rs.next())
            {
                info = rs.getString(2);
            } else
            {
                info = null;
            }
            System.out.println("journeylove.SecretGardenConnection.getInfoFromPerferList() : " + key);
            rs.close();
        }
        return info;
    }

    public void updatePreferenceList(String key, String newVar) throws SQLException
    {
        checkConnection();
        String sql = "UPDATE Preferences SET Information=? WHERE Name=?";
        try (PreparedStatement ps = dbConn.prepareStatement(sql))
        {
            ps.setString(1, newVar);
            ps.setString(2, key);
            ps.executeUpdate();
            System.out.println("journeylove.SecretGardenConnection.updatePreferenceList() key = " + key + " newVar = " + newVar);
        }
    }

    /**
     * Get user-defined saving path in system.
     *
     * @return The saveing path.
     * @throws java.sql.SQLException
     */
    public String getDefaultSavingPath() throws SQLException
    {
        checkConnection();
        String path;
        String dbQuary = "SELECT * FROM Preferences Where Name = 'Default Saving Path'";
        Statement s = dbConn.createStatement();
        ResultSet rs = s.executeQuery(dbQuary);
        
        if(rs.next())
        {
            path = rs.getString(2);
        }else
        {
            path = null;
        }
        
        return path;
    }

    /**
     * Update the default saving path.
     *
     * @param path The new saving path.
     * @throws SQLException
     */
    public void updateDefaultSavingPath(String path) throws SQLException
    {
        checkConnection();
        String sql = "UPDATE Preferences SET Information=? WHERE Name=?";
        PreparedStatement ps = dbConn.prepareStatement(sql);
        ps.setString(1, path);
        ps.setString(2, "Default Saving Path");
        ps.executeUpdate();
        System.out.println("---Preferences updated---");
    }

    public String getMailBoxAddress() throws SQLException
    {
        checkConnection();
        Statement s = dbConn.createStatement();
        ResultSet rs = s.executeQuery("SELECT Information FROM Preferences WHERE Name='MailBoxAddress'");
        rs.next();
        return rs.getString("Information");
    }

    public void updateMailBoxAddress(String path) throws SQLException
    {
        checkConnection();
        String sql = "UPDATE Preferences SET Information=? WHERE Name=?";
        PreparedStatement ps = dbConn.prepareStatement(sql);
        ps.setString(1, path);
        ps.setString(2, "MailBoxAddress");
        ps.executeUpdate();
        System.out.println("---Preferences updated---");
    }

    public String getMusicPlayMode() throws SQLException
    {
        return getInfoFromPerferList(PERF_KEY_MUSIC_PLAYMODE);
    }

    public void setMusicPlayMode(String mode) throws SQLException
    {
        updatePreferenceList(PREF_KEY_SAVINGPATH, mode);
        System.out.println("---Preferences updated---");
    }

    /**
     * Close the database connection with the exception handling.
     */
    public void closeDb()
    {
        try
        {
            dbConn.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Test main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        int decision;
        int repeat = 0;
        Scanner keyboard = new Scanner(System.in);
        SecretGardenConnection secretGardenConnection = new SecretGardenConnection();
        //secretGardenConnection.createTable("Tables", "Name varchar(15), Columns_Detail varchar(70), descriptions varchar(60)");
        // TODO code application logic here
        Connection dbConn = secretGardenConnection.getDbConn();
        String url;
        String name;
        int type;
        int id;
        System.out.println("Welcome to Secret Garden DB manager!");
        do
        {
            System.out.println("Welcome to SG manager "
                    + "\n Please enter operation you want to do:"
                    + "\n 1. See what tables you have now."
                    + "\n 2. Clear ImageList"
                    + "\n 3. Insert into Image playlist;"
                    + "\n 4. Check Playlist."
                    + "\n 5. remove from imagelist"
                    + "\n 6: swap two musics in musiclist;"
                    + "\n 7. add into music list"
                    + "\n 8. see music list"
                    + "\n 9 update music list"
                    + "\n 10.CLEAR musiclist"
                    + "\n 11.open Background Music Manager"
                    + "\n 12.export music list"
                    + "\n 13.import into musiclist"
                    + "\n 14. Export image list"
                    + "\n 15. import image list from a directory"
                    + "\n 16. do statement.");
            try
            {
                decision = keyboard.nextInt();
                switch (decision)
                {
                    case 1:
                        System.out.println(secretGardenConnection.getTablesData("Tables", SecretGardenConnection.COLLECTORHEADER));
                        break;
                    case 2:
                        // secretGardenConnection.insertIntoTables("Tables", "Name varchar(15), Columns_Detail varchar(70), descriptions varchar(60)", "Keep tracks of tables in database");
                        //secretGardenConnection.createTable("MusicList","Name varchar(15), URL varchar(400), Type int, ID int","The list of background music");
                        System.out.println("Are you sure to clear your imagelist? say\"yes\" to continue.");
                        if (keyboard.nextLine().equalsIgnoreCase("Yes"))
                        {
                            secretGardenConnection.doStatement("DELETE FROM ImagePlayList");
                            System.out.println("All cleared");
                        }
                        break;
                    case 3:
                        DisplayImage dm = new DisplayImage("TestImage", "testUrl", 0, 0);
                        secretGardenConnection.insertImageIntoList(dm);
                        break;
                    case 4:
                        System.out.println(secretGardenConnection.getDisplayImage());
                        break;
                    case 5:
                        System.out.println("please enter id to remove:");
                        keyboard.nextLine();
                        id = keyboard.nextInt();
                        System.out.println("To remove id = " + id);
                        secretGardenConnection.removeFromImageDisplayList(id);
                        break;
                    case 6:
                        System.out.println("Enter id1");
                        id = keyboard.nextInt();

                        System.out.println("id2");
                        type = keyboard.nextInt();
                        type = keyboard.nextInt();

                        secretGardenConnection.swapMusicList(id, type);
                        //secretGardenConnection.updateImageDisplayList(0, new DisplayImage("First meet", "www.mylove.com", 2, 77077));
                        break;
                    case 7:
                        System.out.println("enter name");
                        name = keyboard.nextLine();
                        name = keyboard.nextLine();
                        System.out.println("enter url:");
                        url = keyboard.nextLine();
                        //url=keyboard.nextLine();
                        System.out.println("enter type: ");
                        type = keyboard.nextInt();
                        System.out.println("Assign ID:");
                        id = keyboard.nextInt();
                        //id = keyboard.nextInt();
                        secretGardenConnection.addIntoMusicList(new MyMusic(name, url, type, id));
                        //System.out.println("YEAH! Now table heve:");
                        break;
                    case 8:
                        System.out.println(secretGardenConnection.getMyMusics());
                        break;
                    case 9:
                        System.out.println(secretGardenConnection.getMyMusics());
                        System.out.println("enter new name");
                        name = keyboard.nextLine();
                        name = keyboard.nextLine();
                        System.out.println("enter new url:");
                        url = keyboard.nextLine();
                        System.out.println("enter new type");
                        type = keyboard.nextInt();
                        System.out.println("enter id to edit:");
                        id = keyboard.nextInt();

                        secretGardenConnection.updateMusicList(id, new MyMusic(name, url, type, id));

                        System.out.println(secretGardenConnection.getMyMusics());
                        break;
                    case 10:
                        System.out.println("ARE YOU SURE TO CLEAR YOUR MUSIC LIST say\"Yes\" to continue");
                        keyboard.nextLine();
                        if (keyboard.nextLine().equalsIgnoreCase("yes"))
                        {
                            secretGardenConnection.doStatement("DELETE FROM MusicList");
                            System.out.println("ALL musics are removed.");
                        } else
                        {
                            repeat = 1;
                        }
                        break;
                    case 11:
                        BackgroundMusic backgroundMusic = new BackgroundMusic();

                        break;
                    case 12:
//                        secretGardenConnection.createTable("Preferences", "Name varchar(30), Information varchar(300)", ""
//                                + "This is the perferences of the user.");
                        //secretGardenConnection.insertIntoPreferencesList("Default Saving Path", "");
                        //secretGardenConnection.doStatement("CREATE TABLE Preferences");
                        //secretGardenConnection.doStatement("ALTER TABLE MusicList ALTER COLUMN Name SET DATA TYPE varchar(150)");
                        // secretGardenConnection.insertIntoPreferencesList("Version", "1.3");
                        //secretGardenConnection.updateDefaultSavingPath("");
                        // System.out.println("now it has "+ secretGardenConnection.getDefaultSavingPath());
                        secretGardenConnection.exportMusicList(new File(secretGardenConnection.getDefaultSavingPath() + "\\Export"));
                        break;

                    case 13:
                        //secretGardenConnection.importIntoMusicList(new File("C:\\Users\\app\\Desktop\\Java Project\\Music src\\Export\\MusicListExport15 60008077281.txt\""));
                        JFileChooser chooser = new JFileChooser(secretGardenConnection.getDefaultSavingPath() + "\\Export");
                        int confirmed = chooser.showOpenDialog(null);
                        if (confirmed == JFileChooser.APPROVE_OPTION)
                        {
                            secretGardenConnection.importIntoMusicList(chooser.getSelectedFile());
                        }

                        break;
                    case 14:
//                        System.out.println("enter st");
//                        keyboard.nextLine();
//                        secretGardenConnection.doStatement("DELETE FROM MusicList");
                        secretGardenConnection.exportImageList(new File(secretGardenConnection.getDefaultSavingPath() + "\\Export"));
                        break;
                    case 15:
//Testing 
                        secretGardenConnection.importIntoImageList(new File("C:\\Users\\app\\Desktop\\Java Project\\Music src\\Export\\ExportImageList1560052024669.journey"), SecretGardenConnection.ALL_IMAGES_TABLE_NAME);

                        break;
                    case 16:
                        //secretGardenConnection.doStatement("ALTER TABLE ImagePlayList ADD COLUMN Description varchar(500)");
                        secretGardenConnection.recordImageSublist("Main Imagelist");
                        secretGardenConnection.recordDropImageList("Main Imagelist");

//secretGardenConnection.createTable("ImageSubLists", "Name varchar(50)", "This stores all sub-list of images, including the main.");
                        break;
                    case 17:
//                        secretGardenConnection.doStatement("ALTER TABLE ImageSubLists ALTER COLUMN Name SET DATA TYPE varchar(90)");
//                        secretGardenConnection.doStatement("ALTER TABLE Tables ALTER COLUMN Name SET DATA TYPE varchar(90)");
                        System.out.println(Arrays.toString(secretGardenConnection.getImageSublists()));
                        break;
                    case 18:
                        keyboard.nextLine();
                        secretGardenConnection.dropImageSublist(keyboard.nextLine());
                        break;

                    //[Main Imagelist, Test_List, Test_listsssssssss,  ,  do ers`~]
                    case 19:
                        secretGardenConnection.doStatement("SHOW TABLES");
//                            secretGardenConnection.recordDropImageList("doers`~");
//                            secretGardenConnection.recordDropImageList("do ers`~");
//                            secretGardenConnection.doStatement("DELETE FROM ImageSubLists WHERE NAME= 'doers`~'");
//                            secretGardenConnection.doStatement("DELETE FROM ImageSubLists WHERE NAME= 'do ers`~'");
//                            secretGardenConnection.recordImageSublist("Main Imagelist");
//                            secretGardenConnection.dropTable("ImageSubList_Test_list");
//                            Connection dbconn = secretGardenConnection.getDbConn();
//                            Statement s = dbConn.createStatement();
//                            s.executeUpdate("DELETE FROM ImageSubLists");
//                            s.executeUpdate("INSERT INTO ImageSubLists VALUES Name=''");
                        /* 1.23 update
                            secretGardenConnection.doStatement("ALTER TABLE MusicList ADD COLUMN LyricAddress varchar(300)");
                         */
                        secretGardenConnection.doStatement("ALTER TABLE MusicList ALTER COLUMN LyricAddress SET DATA TYPE varchar(300)");

                        break;
                    case 20:
                    // secretGardenConnection.recordImageSublist("Main Imagelist");

                    case 21:
                    //secretGardenConnection.testImportIntoImageList(new File("C:\\Users\\app\\Desktop\\Java Project\\Music src\\Export\\ExportImageList1561196805376.journey"), ALL_IMAGES_TABLE_NAME);
                    default:
                        break;

                }
                System.out.println("repeat? 1 for yes");
                repeat = keyboard.nextInt();
            } catch (SQLException e)
            {
                repeat = 0;
                Logger.getLogger(SecretGardenConnection.class.getName()).log(Level.SEVERE, e.toString(), e);
            } catch (InputMismatchException ime)
            {
                System.out.println("ime, bye bye!");
                repeat = 0;
            } catch (IOException | UnsupportedAudioFileException | JavaLayerException | LineUnavailableException ex)
            {
                Logger.getLogger(SecretGardenConnection.class.getName()).log(Level.SEVERE, ex.toString(), ex);
            }

        } while (repeat == 1);

    }

    /**
     * Execute a SQL statement.
     *
     * @param statement
     * @throws SQLException
     */
    public void doStatement(String statement) throws SQLException
    {
        Statement s = this.dbConn.createStatement();
        s.execute(statement);
        System.out.println("Yeahhh statement : " + statement + " has successfully executed");
    }

    /**
     * Log the detail of an exception to a log file.
     *
     * @param ex The exception to log.
     * @return Either <code>true</code>for logs stored in the default saving
     * path or<code>false</code>for no default saving path or meet an exception.
     */
    public boolean createErrorLog(Throwable ex)
    {
        boolean successIn;
        try
        {
            String defDir = getDefaultSavingPath();
            File dir;

            if (defDir == null)
            {
                dir = new File("C:\\Programlog\\Journey's secret garden");
                successIn = true;
            } else
            {
                dir = new File(defDir + "\\logs");
                successIn = false;
            }

            File out = new File(dir, "errorlog " + new SimpleDateFormat("yyyyMMdd hh:mm:ss").format(new Date()) + ".log");

            if (!dir.exists())
            {
                dir.mkdirs();
            }
            out.createNewFile();
            try (PrintWriter writer = new PrintWriter(out))
            {
                ex.printStackTrace(writer);
                writer.flush();
                System.out.println("journeylove.SecretGardenConnection.createErrorLog() success flush \n," + out.getPath());
            }
        } catch (SQLException | IOException ex1)
        {
            successIn = false;
            // Logger.getLogger(SecretGardenConnection.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return successIn;
    }

    //Main working sys
    /**
     * Create a new table within a specific database.
     *
     * @param tableName The name of the table that is going to be created.
     * @param headers column name and datatype of the new table.
     * @param dbName The name of the db that will be storing the table.
     * @param description brief description of the new table.
     */
    private void createTable(String tableName, String headers, String dbName, String description) throws SQLException
    {
        checkConnection();
        System.out.println(tableName);
        String dbQuary = "CREATE TABLE " + tableName + " (" + headers + ")";
        setDbName(dbName);
        Statement s;

        s = this.dbConn.createStatement();
        s.execute(dbQuary);
        System.out.println("Table successfully created!!!");
        System.out.println("Table name: " + tableName);
        System.out.println("Statement used: " + headers);
        System.out.println("Within database: " + dbName);
//            this.insertIntoTables(tableName, headers, description);

    }

    /**
     * Create a new table with specified table name and columns but use default
     * dbname.
     *
     * @param tableName The name of new table.
     * @param headers Columns and datatypes.
     * @param description brief description about the new table.
     * @throws java.sql.SQLException
     */
    public void createTable(String tableName, String headers, String description) throws SQLException
    {
        this.createTable(tableName, headers, this.dbName, description);
    }

    /**
     * Creates a new table use default dbName without description.
     *
     * @param tableName Name of the new table.
     * @param headers statements of columns of the new table.
     */
    public void createTable(String tableName, String headers) throws SQLException
    {
        this.createTable(tableName, headers, dbName, null);
    }

    /**
     * Drop the table within the database.
     *@deprecated This table is no longer used.
     * @param tableName
     */
    public void dropTable(String tableName)
    {
        if (!tableName.equals("Tables"))
        {
            String dropQuary = "DROP TABLE " + tableName;
            System.out.println(dropQuary);
            Statement s;
            try
            {
                s = dbConn.createStatement();
                s.execute(dropQuary);
                this.recordDropTable(tableName);
                System.out.println("Table dropped: " + tableName);
            } catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("journeylove.SecretGardenConnection.dropTable()");
            }
        } else
        {
            throw new IllegalArgumentException("You cannot drop recorder!!!");

        }
    }

    public Connection getDbConn()
    {
        return dbConn;
    }

    /**
     * Get tables in this database.
     *
     * @param data The list of tabls in this database.
     * @return The objcase of data.
     * @deprecated This table is no longer used.
     */
    public Object[][] getTablesInfo(ArrayList<ArrayList<String>> data)
    {
        System.out.println("data: " + data);
        int columnCount = data.get(0).size();

        Object[][] toBeDisplayed = new Object[data.size()][columnCount];
        for (int i = 0; i < data.size(); i++)//see how may columns there
        {
            ArrayList<String> row = data.get(i);
            for (int j = 0; j < columnCount; j++)
            {
                toBeDisplayed[i][j] = row.get(j);
            }
            //Assume that we know there are 3 columns.
            /*toBeDisplayed[i][0] = row.get(0);
                toBeDisplayed[i][1] = row.get(1);
                toBeDisplayed[i][2] = row.get(2);*/
        }
        //System.out.println(1toBeDisplayed);
        return toBeDisplayed;
    }

    /**
     *
     * @param name
     * @param statement
     * @param description
     * @deprecated This table is no longer used.
     * @throws SQLException
     * 
     */
    public void insertIntoTables(String name, String statement, String description) throws SQLException
    {
        String dbQuary = "INSERT INTO Tables VALUES (?,?,?)";

        PreparedStatement ps = dbConn.prepareStatement(dbQuary);
        ps.setString(1, name);
        ps.setString(2, statement);
        ps.setString(3, description);
        ps.executeUpdate();
        System.out.println("Another table has been under monitor successfully!");
        System.out.println(" Name: " + name + " Column： " + statement + " description: " + description);

    }

    /**
     * Record when the a table has been dropped, remove it from the list.
     *@deprecated 
     * @param tableName The name of the table being dropped.
     */
    private void recordDropTable(String tableName) throws SQLException
    {
        String dropStatement = "DELETE FROM Tables WHERE Name=?";
        PreparedStatement ps;

        ps = dbConn.prepareStatement(dropStatement);
        ps.setString(1, tableName);
        ps.executeUpdate();

//            e.printStackTrace();
//            System.out.println(" Error at: journeylove.SecretGardenConnection.recordDropTable()");
//            System.out.println("data failed removing.");
    }
    //昨天晚上还得写essay 只不过下午开始我不能睡了。。

    /**
     * This estbalished a connection, for an already existing one!.
     */
    public void setDbConn(/*No Input variable, running by itself.*/)
    {

        //NO this.dbConn = dbConn;
        String connectionURL = "jdbc:mysql://db.jianqinggao.com:3306/" + this.dbName;
        this.dbConn = null;
        //Find the driver and make connection;

        try
        {
            System.out.println("The URL LOADED: " + connectionURL);
            System.out.println("ATTENTION: This application has already connected to database: " + dbName
                    + "\n please do not double connect otherwise it will fail");
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//!!!!!!!!!!!!!Typo?
//            
//            this.dbConn = DriverManager.getConnection(connectionURL);
            Class.forName("com.mysql.cj.jdbc.Driver"); //URL for new version jdbc connector.
            Properties properties = new Properties(); //connection system property
            properties.setProperty("user", "Jenny");
            properties.setProperty("password", "3266933");
            properties.setProperty("useSSL", "true");//set this true if domain suppotes SSL
            //"-u root -p mysql1 -useSSL false"
            this.dbConn = DriverManager.getConnection(connectionURL, properties);
            System.out.println("Database connection to : " + dbName + " are established successfully. ");
        } catch (ClassNotFoundException cnfe)
        {
//            System.out.println("class for name not found!");
//            cnfe.printStackTrace(System.err);
            Warning warning = new Warning("A serious error has occuring while accessing library, please make sure this application comes with .lib directory", "Close this application and contact author.", cnfe, false);
            warning.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } catch (SQLException sqle)
        {
//            System.out.println("SQL CONNECTION ERROR");
//            sqle.printStackTrace(System.err);
            Warning warning = new Warning("Failed to connect to database.", "Please close another running application and make sure this jar comes with \"SecretGarden\"and that directory is never touched.", sqle, false,
                    () ->
            {
                int conf = JOptionPane.showConfirmDialog(null, "Do you want to close this application", "ERROR-CLOSE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (conf == JOptionPane.OK_OPTION)
                {
                    System.exit(1);

                }

            });

        }
    }

    public void setDbName(String newDBname)
    {
        this.dbName = newDBname;
    }

    /**
     * This method creates a new database inside the package.
     *
     * @deprecated A new database(secret garden) has already created for Johnson
     * and Jenny's application.
     * @param dbName Name of new database.
     */
    public void createDb(String dbName)
    {
        //setDbName(dbName);
        //NO this.dbConn = dbConn;
        String connectionURL = "jdbc:derby:" + dbName + ";create=true"/*CREATE THIS!*/;
        //this.dbConn = null;
        //Find the driver and make connection;

        try
        {
            System.out.println("The URL LOADED:" + connectionURL);
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//!!!!!!!!!!!!!Typo?
            dbConn = DriverManager.getConnection(connectionURL);
            System.out.println("New database " + dbName + " created!");
        } catch (ClassNotFoundException cnfe)
        {
            System.out.println("class for name not found!");
            cnfe.printStackTrace(System.err);
        } catch (SQLException sqle)
        {
            System.out.println("SQL CONNECTION ERROR");
            sqle.printStackTrace(System.err);
        }
    }

    public static SecretGardenConnection getDefaultInstance() throws ClassNotFoundException, SQLException
    {
        return new SecretGardenConnection();
    }
    
    @Override
    public void close() throws SQLException
    {
        this.dbConn.close();
    }

}
