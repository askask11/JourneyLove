/*Editor: Johnson Gao
* Date This Project Created: March.30 2019
* Introduce of this class.
* This class defiend more preferred colors based on java.awt.color class.
 */
package journeylove;

import java.awt.Color;

/**
 * Initial idea from Johnson Gao.<br>This class defiend more preferred colors
 * based on java.awt.color class.
 *
 * @author Johnson Gao
 */
public class LovelyColors
{
    //My pre-defined color for my classes to use:)

    /**
     * <body style="background-color:#FE6673;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color MERRY_CRANESBILL = Color.decode("#FE6673");
    /**
     * <body style="background-color:#FBB8AC;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color TRUE_BLUSH = Color.decode("#FBB8AC");
    /**
     * <body style="background-color:#FAD8BE;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color MAGIC_POWDER = Color.decode("#FAD8BE");
    /**
     * <body style="background-color:#E3C887;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color MUSTARD_ADDICTED = Color.decode("#E3C887");
    /**
     * <body style="background-color:#E6E2C3;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color BRAIN_SAND = Color.decode("#E6E2C3");
    /**
     * <body style="background-color:#BDF3D4;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color SILLY_FIZZ = Color.decode("#BDF3D4");
    /**
     * <body style="background-color:#CBF5FB;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color GLASS_GALL = Color.decode("#CBF5FB");
    /**
     * <body style="background-color:#ACF6EF;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color LIGHT_HEART_BLUE = Color.decode("#ACF6EF");
    /**
     * <body style="background-color:#A2E1D4;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color MYSTICAL_GREEN = Color.decode("#A2E1D4");
    /**
     * <body style="background-color:#2AE0C8;">
     * returns a color like:.
     * <p>
     * this</p> </body>
     */
    public static final Color BEWITCHED_TREE = Color.decode("#2AE0C8");
    // Refer to color chart.
    /**
     * <body style="background-color:#00FF7F;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color SPRING_GREEN = Color.decode("#00FF7F");
    /**
     * <body style="background-color:#6B4226;">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color HALF_N_HALF_CHOCOLATE = Color.decode("#6B4226");
    //Japanese Traditional groups
    /**
     * <body style="background-color:rgb(254, 223, 225);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color SAKURA_PINK = new Color(254, 223, 225);
    /**
     * <body style="background-color:rgb(160, 137, 145);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public final Color SAKURA_FONTCOLOR = new Color(160, 137, 145);
    /**
     * <body style="background-color:rgb(235, 122, 119);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color JINZAMOMI_PINK = new Color(235, 122, 119);
    /**
     * <body style="background-color:rgb(164, 74, 73);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color JINZAMOMI_FONTCOLOR = new Color(164, 74, 73);
    /**
     * <body style="background-color:rgb(245, 150, 170);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color MOMO_PINK = new Color(245, 150, 170);
    /**
     * <body style="background-color:rgb(157, 82, 102);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color MOMO_FONTCOLOR = new Color(157, 82, 102);
    /**
     * <body style="background-color:rgb(225, 107, 140);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color KOHBAI_PINK = new Color(225, 107, 140);
    /**
     * <body style="background-color:rgb(148, 59, 81);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color KOHBAI_FONTCOLOR = new Color(148, 59, 81);
    //Green

    /**
     * <body style="background-color:rgb(134, 193, 102);">
     * returns a color like
     * <p>
     * green</p> </body>
     */
    public static final Color NAE_GREEN = new Color(134, 193, 102);
    /**
     * <body style="background-color:rgb(60, 98, 38);">
     * returns a color like
     * <p>
     * this</p> </body>
     */
    public static final Color NAE_FONTCOLOR = new Color(60, 98, 38);

    public static final int CUTE_BLUE_GROUP = 0, SIDE_WOODEN_GROUP = 1;

    /**
     * Since this class is an abstruction and no input needed, it have an empty
     * constructor with nothing.
     */
    public LovelyColors()
    {

    }

    /**
     *
     * @param groupStyle Please go to
     * https://docs.google.com/document/d/1nJrnyI1GmtNWJd9fyhStBSmnY1FSqy5Fw2wFomOkSnY/edit?usp=sharing
     * for reference of group colors.
     * @param colorSequnce Enter the sequence number in the group.
     * @return A color produced by this method. if in-vaild input, return black;
     *
     */
    public Color perferredColorGroup(int groupStyle, int colorSequnce)
    {

        Color groupedColor;
        //as a failed, this method will return a black;
        int red = 0, green = 0, blue = 0;
        //accepted color number and sequence, find color.
        switch (groupStyle)
        {
            case CUTE_BLUE_GROUP:
                switch (colorSequnce)
                {
                    case 1:
                        red = 199;
                        green = 237;
                        blue = 233;
                        break;
                    case 2:
                        red = 175;
                        green = 215;
                        blue = 237;
                        break;
                    case 3:
                        red = 92;
                        green = 167;
                        blue = 186;
                        break;
                    case 4:
                        red = 255;
                        green = 66;
                        blue = 93;
                        break;
                    case 5:
                        red = 147;
                        green = 224;
                        blue = 255;
                        break;
                    default:
                       // Warning noWarning = new Warning("Unexpected error for color.@Group 0");
                        break;
                }
                break;
            //group one colors
            case 1:
                switch (colorSequnce)
                {
                    case 1:
                        red = 254;
                        green = 67;
                        blue = 101;
                        break;
                    case 2:
                        red = 252;
                        green = 157;
                        blue = 154;
                        break;
                    case 3:
                        red = 249;
                        green = 205;
                        blue = 173;
                        break;
                    case 4:
                        red = 200;
                        green = 200;
                        blue = 169;
                        break;
                    case 5:
                        red = 131;
                        green = 175;
                        blue = 155;
                        break;
                    default:
                        throw new Error("Expected integer @ preferredColorGroup does not exist in this method. @Group 1");

                }
                break;
            //Green group
            case 2:
                switch (colorSequnce)
                {
                    case 1:
                        red = 255;
                        green = 255;
                        blue = 255;
                        break;
                    case 2:
                        red = 160;
                        green = 191;
                        blue = 124;
                        break;
                    case 3:
                        red = 101;
                        green = 147;
                        blue = 74;
                        break;
                    case 4:
                        red = 64;
                        green = 116;
                        blue = 52;
                        break;
                    case 5:
                        red = 3;
                        green = 35;
                        blue = 14;
                        break;
                    default:
                        throw new Error("this seq# does not exist @ Group two");

                }
                break;
            default:
                // Warning numbernoExist = new Warning("INNER ERROR: @LovelyColor.java, colorgroup, index out of bounce");
                throw new Error("groupStyle int @ preferredColorGroup does not exist in this method. ");

        }
        groupedColor = new Color(red, green, blue);
        return groupedColor;
    }

    /**
     * TEST MAIN METHOD
     *
     * @param args Line commands argument
     */
    public static void main(String[] args)
    {
        //new LovelyColorsFun();
        
    }
   

    /**
     * To string override.
     *
     * @return String = "Color Class";
     */
    @Override
    public String toString()
    {
        return "LovelyColors{" + "Colors Class" + '}';
    }

}

