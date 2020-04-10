/*Editor: Johnson Gao

 * Date This Project Created: Aug 2019
 * Description Of This Class: This is responsible for computating.
 */
package journeylove;

/**
 * This class is responsible for computate ciphers.
 * @author Johnson Gao
 */
public class CipherFactory
{

    /**
     * The maximun letter point of uppercase letter.
     * 
     */
    final static int MAX_UPPERCASE_LETTER_POINT = 90,
            MIN_UPPERCASE_LETTER_POINT = 65;
    final static int MIN_LOWERCASE_LETTER_POINT = 97;
    static final char[] ALL_ALPHABETS_UPPERCASE={'A',
    'B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    /**
     * Encrypt a code by caeasr cipher.
     * @param code The String to be encryped.
     * @param offset The offset used to encrypt the code.
     * @return The code encryped.
     */
    public static String encryptCaesarsCode(String code, int offset)
    {
        char[] codes = code.trim().toCharArray();
        return encryptCaesarsCode(codes, offset);
    }

    /**
     * Encrypt a code by caeasr cipher.
     * @param codes The set of characters to be encryped.
     * @param offset The offset used to encrypt the code.
     * @return The code encryped.
     */
    public static String encryptCaesarsCode(char[] codes, int offset)
    {
       
        String decoded = "";
        //int shift;//A=0
        for (int i = 0; i < codes.length; i++)
        {
            char charpoint = codes[i];
            if (Character.isAlphabetic(charpoint))
            {//See if the this character is an alphabit char.
                if (Character.isUpperCase(charpoint))
                {
                    
                    int letterPoint = Character.codePointAt(codes, i) - MIN_UPPERCASE_LETTER_POINT + offset;
                    int shift = roundLetterPointInBetween(letterPoint, 26, 0);
                    decoded = decoded.concat(Character.toString(Character.toChars(shift + MIN_UPPERCASE_LETTER_POINT)[0]));
                } else
                {
                    int letterPoint = Character.codePointAt(codes, i) - MIN_LOWERCASE_LETTER_POINT + offset;
                    int shift = roundLetterPointInBetween(letterPoint, 26, 0);
                    decoded = decoded.concat(Character.toString(Character.toChars(shift + MIN_LOWERCASE_LETTER_POINT)[0]));
                }

            } else
            {
                decoded = decoded.concat(Character.toString(charpoint));
            }
        }
        return decoded;
    }

    /**
     * Decrypt a caeasrs code.
     * @param code
     * @param offset
     * @return 
     */
    public static String decryptCaesarsCode(String code, int offset)
    {
        return encryptCaesarsCode(code, offset * -1);
    }

    public static char encryptCaesarsChar(char code, int offset)
    {
        char decoded;
        if (Character.isAlphabetic(code))
        {
            if (Character.isUpperCase(code))
            {
                int letterPoint = Character.codePointAt(new char[]
                {
                    code
                }, 0) - MIN_UPPERCASE_LETTER_POINT + offset;
                int shift = roundLetterPointInBetween(letterPoint, 26, 0);
                decoded = Character.toChars(shift + MIN_UPPERCASE_LETTER_POINT)[0];
            } else
            {//Create a char array in order to get the numberic letter point.
                int letterPoint = Character.codePointAt(new char[]
                {
                    code
                }, 0) - MIN_LOWERCASE_LETTER_POINT + offset;//Get the sequence number and apply the offset.
                int shift = roundLetterPointInBetween(letterPoint, 26, 0);//As parsed the offset, parse it back to alphabet.
                decoded = Character.toChars(shift + MIN_LOWERCASE_LETTER_POINT)[0];//Return the chatacter decoded.
            }
        } else
        {
            decoded = code;//If the user entered a non-alphabetic, return itself.
        }
        return decoded;
    }

    /**
     * Get the numberic value which the offset actually applied to the letter.
     * 
     * @param point The number to be put in.
     * @param upperbound The upperbound of the number circle.
     * @param lowerbound The minimun of the number cycle.
     * @return The effective snumberic value between upperbound and lowerbound.
     */
    public static int roundLetterPointInBetween(int point, int upperbound, int lowerbound)
    {
        int shift;//The range of which the 'offset' actually applied to the letter.
        
        if (point >= upperbound)
        {
            shift = point - upperbound;
            while (shift > upperbound)
            {
                shift -= upperbound;
            }
        } else if (point < lowerbound)
        {
            shift = upperbound + point;
            while (shift < lowerbound)
            {
                shift += upperbound;
            }
        } else
        {
            shift = point;
        }
        return shift;
    }
    
    /**
     * Parse a key to the format for vigenere code.
     * @param key The key to parse.
     * @param length The target length.
     * @return The vigenere length.
     */
    public static String parseVigenereCode(String key, int length)
    {
        String code="";
        int keyLength = key.length();
        while(code.length() != length)
        {
            int lg=(length-code.length())/keyLength;
           // System.out.println(lg);
            if(lg == 0)
            {
                int differ = length-code.length();
                char[] chars= key.toCharArray();
                for(int i=0; i<differ ; i++)
                {
                    code += chars[i];
                }
            }else
            {
                code+=key;
            }
        }
        return code;
    }
    
    /**
     * Encrypt a vigenere code.
     * @param codes The set of characters to be encrypted.
     * @param passwords The passwords used in encryption.
     * @return Encrypted code.
     */
    public static String encryptVigenereCipher(char[] codes, char[] passwords)
    {
        int length = codes.length;
        String decoded="";
        if(codes.length != passwords.length)
        {
            throw new IllegalArgumentException("For vigenia password encryption/decryption, the password length must equal to codes length."+
                    "\n In this case, codes length=" + codes.length + "passwordLength="+passwords.length);
        }
        for (int i = 0; i < length; i++)
        {
            int offset = Character.getNumericValue(passwords[i])-10;
            decoded = decoded.concat(Character.toString(encryptCaesarsChar(codes[i], offset)));
        }
        return decoded;
    }

    /**
     * Encrypt a code into vigenere cipher with password.
     * @param code The code for encryption.
     * @param password The password to decrypt the code.
     * @return The encrypted code.
     */
    public static String encryptVigenereCipher(String code, String password)
    {
        return encryptVigenereCipher(code.trim().toCharArray(), password.trim().toCharArray());
    }
    
    public static String decryptVigenereCipher(char[] codes, char[] passwords)
    {
        int length = codes.length;
        String decoded="";
        if(codes.length != passwords.length)
        {
            throw new IllegalArgumentException("For vigenia password decryption, the password length must equal to codes length."+
                    "\n In this case, codes length=" + codes.length + "passwordLength="+passwords.length);
        }
        for (int i = 0; i < length; i++)
        {
            int offset = (Character.getNumericValue(passwords[i])-10) * -1;
            decoded = decoded.concat(Character.toString(encryptCaesarsChar(codes[i], offset)));
        }
        return decoded;
    }
    
    /**
     * Decrypt a Vigenere cipher with password given.
     * @param code The code for decrypt,
     * @param password The password for decryption.
     * @return The decrypted code.
     */
    
    public static String decryptVigenereCipher(String code,String password)
    {
        return decryptVigenereCipher(code.trim().toCharArray(), password.trim().toCharArray());
    }
    
    //<editor-fold>
//    public static String encryptVernamCipher(char[] codes, char[] passwords)
//    {
//        int length = codes.length;
//        String decoded="";
//        if(codes.length != passwords.length)
//        {
//            throw new IllegalArgumentException("No, the codes length "  +codes.length +" must equal to the length="+passwords.length+"  of the password.");
//        }
//        
//        for (int i = 0; i < length; i++)
//        {
//            int cnv=Character.getNumericValue(codes[i])-10;//NumericValue of code.
//            int pnv=Character.getNumericValue(passwords[i])-10;//NumericValue Of password.
//        }
//        return decoded;
//    }</editor-fold>
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //<editor-fold>
        // TODO code application logic here
       // System.out.println(Character.getNumericValue('A'));
//        for(int i=0;i<7;i++)
//        {
//            System.out.println(Character.codePointAt(new char[]{'a','b','c','d','e','f','g'}, i));
//        }
        //System.out.println(Character.toChars(97)[0]);
        //System.out.println(Integer.compareUnsigned(1, 25));
//        System.out.println(CipherFactory.encryptCaesarsChar('A', 1));
//        System.out.println(CipherFactory.encryptVigenereCipher("This is an example".replace(" ", "").toCharArray(), "MASKLNSFLDFKJPQ".toCharArray()));
//        System.out.println(CipherFactory.decryptVigenereCipher("fhactfsspafwyau", "MASKLNSFLDFKJPQ"));
        //</editor-fold>
       System.out.println(CipherFactory.parseVigenereCode("Kelly", 9));
    }

}
