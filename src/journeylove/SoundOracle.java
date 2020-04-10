/*Editor: Johnson Gao

 * Date This Project Created:May 2019
 * Description Of This Class:This class is going to the constants of sounds in this package.
 */
package journeylove;

/**
 * This stores the constants of sounds.
 * @author Johnson Gao & Beautiful Jenny
 */
public interface SoundOracle
{
    /**
     * Some sad sounds.
     */
    final String[] SAD_SOUND =
    {
        "Buzz",
        "Cat Scream",
        "Error Alert",
        "HUA-HUA-HUA-HUA",
        "Sad Male",
        "Computer Error2",
        "Crowd Boo 3",
        "haofanni",
        "beishangliugeiziji",
        "LiangLiangcli"
    };
    
    
    /**
     * A sound similar to "tika", which comes softly and calmly, stored in the package.
     */
    
    final public static String TINY_BUTTON_SOUND = "Tiny Button Push.wav";
    /**
     * Produce a sound like "cla-clock" used when a major button is pressed.
     */
    public static final String BUTTON_CLICKED_SOUND = "Click Button 2.wav";
    /**
     * Produce a sound like a door is opening.
     * Ususlly used when user are opening another window.
     */
    public final static String DOOR_UNLOCKED_SOUND = "Door Unlock.wav";
    /**
     * Produce a sound like "deng".
     */
    static final public String DENG_SOUND_SOUND = "deng.wav";
    /**
     * Produce a sound like phone typing. Usually used when user enter a 
     * text in a textfield.
     */
    final static String PHONE_TYPING_SOUND = "phoneTyping.wav";
    /**
     * Produce a water press sound.
     */
    final String WATER_PRESS_1 = "waterPress1.wav";
    final String WATER_PRESS_2 = "waterPress2.wav";
    final String WATER_PRESS_3 = "waterPress3.wav";
    String WATER_PRESS_4 = "waterPress4.wav";
    /**
     * A sound like something is done, to give user respond for their successful
     * operations, invoke this sound in <code>clickSound</code> method that can
     * be found in almost every JFrame classes.
     */
    String UI_DINGDONG = "UI-dingdong.wav";
    /**
     * ERROR sounds.
     */
    String BUZZ_SOUND = "Buzz.wav",
            CAT_SCREAM_SOUND="Cat Scream.wav",
            ERROR_ALERT_SOUND = "Error Alert.wav",
            HUA_HUA_HUA_HUA_SOUND = "HUA-HUA-HUA-HUA.wav";
    
}
