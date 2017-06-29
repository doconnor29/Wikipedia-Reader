/*This program uses the Jsoup and freetts  libraries to save randomly generated 
Wikipedia articles to an audio(WAVE) file. Parts of the code for this project were obtained from the website Stack Overflow.
This project will be used for educational purposes only.*/

package Wikipedia_Project;

import com.sun.speech.engine.BaseEngineProperties;
import com.sun.speech.engine.synthesis.BaseSynthesizerProperties;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import static java.lang.System.out;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import static java.lang.System.out;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.sun.speech.freetts.FreeTTS;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import javax.sound.sampled.AudioFileFormat.Type;


public class Wikipedia_Project
{     
    public static void main(String[] args) throws IOException 
    {   //moreTrax keeps track of how many WAVE files have been created throughout the program
        int moreTrax = 0;
 
        //numTrax sets the total amount of WAVE files that the program will create
        final int numTrax = 5;
        for(int i = 1; i <= numTrax; i++)
        {
            
            //The web site to be saved    
            try
            {
                Document readDoc = (Document) Jsoup.connect("http://en.wikipedia.org/wiki/Special:Random").get(); 
            
           
            Elements el;
            
            //This specifies that the paragraph elements will be saved off of the wikipedia page
            el = readDoc.select("p");
            
            
            
            //create a  file and direct where to save it       
            //java.io.File inFile = new java.io.File("C:\\Users\\david\\Documents\\NetBeansProjects\\Wikipedia_Project\\ArticleText.txt");
            java.io.File inFile = new java.io.File("C:\\Users\\docon\\Documents\\NetBeansProjects\\Wikipedia_Project\\ArticleText.txt");
 
            //create a PrintWriter and save the p elements from the web page to the ArticleText file 
            java.io.PrintWriter output = new java.io.PrintWriter(inFile);                
            output.print(el.text());
            output.close(); 

            //reads in the saved file and creates String variables to store the contents of the file
            //java.io.File outFile = new java.io.File("C:\\Users\\david\\Documents\\NetBeansProjects\\Wikipedia_Project\\ArticleText.txt");
            java.io.File outFile = new java.io.File("C:\\Users\\docon\\Documents\\NetBeansProjects\\Wikipedia_Project\\ArticleText.txt");
 
            //word saves one word at a time
            String word = "";

            //allWords concatenates all of the different word values into one String
            String allWords = "";
                                
            try
            {
                Scanner input = new Scanner(outFile);
                //read in the file and save the text in the file as string allWords
                while(input.hasNext())
                {
                    word = input.next();
                    allWords = allWords + "          "  +  word;              
                }    
                input.close();
            }

            catch(java.io.IOException ex)
            {
                System.out.println("Error creating the article file");
            }
            
            //this code implements freetts to save the allWords string to a WAVE file    
            FreeTTS speak;              
            AudioPlayer audioPlayer = null;
            String voiceName = "kevin16";

            System.out.println();
            System.out.println("Using voice: " + voiceName);

            VoiceManager sound = VoiceManager.getInstance();
            Voice kevVoice = sound.getVoice(voiceName);

            if (kevVoice == null)
            {
                System.err.println(
                    "Cannot find a voice named "
                    + voiceName + ".  Please specify a different voice.");
                System.exit(1);
            }

            //Allocates resources for the voice
            kevVoice.allocate();                                

            //creates files to dump the audio into                       
            //String fname = "C:\\Users\\david\\Documents\\NetBeansProjects\\Wikipedia_Project//Track" + Integer.toString(i) ;
            String fname = "C:\\Users\\docon\\Documents\\NetBeansProjects\\Wikipedia_Project//Track" + Integer.toString(i) ;
            audioPlayer = new SingleFileAudioPlayer(fname,Type.WAVE);

            //attach the audioplayer 
            kevVoice.setAudioPlayer(audioPlayer); 
            
            //set the rate of speech
            kevVoice.setRate(127f);
            
            //saves the string allWords tp a WAVE file
            kevVoice.speak(allWords);                
            kevVoice.deallocate();
            audioPlayer.close();     
            moreTrax++;  
            }
            catch (java.io.IOException ex)
            {
                System.out.println("Error accessing URL. Please Try again.");
            }
        }                    
        System.exit(0);               
    }
}

