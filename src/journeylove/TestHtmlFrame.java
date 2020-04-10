/*Editor: Johnson Gao
 * Date This Project Created:
 * Description Of This Class:
 */
package journeylove;

import java.awt.Color;
import java.awt.FlowLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Jianqing Gao
 */
public class TestHtmlFrame extends JFrame
{
    
   private  JLabel titleLabel;
   private JFXPanel fXPanel;
   Scene scene;
   WebView webView;
   StackPane root;
   
    
   public TestHtmlFrame()
   {
       super("HTML");
       setBackground(new Color(255, 153, 153));
       setLayout(new FlowLayout());
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       setVisible(true);
       
       
       Platform.runLater(()->{
       //Construct and format JFXStackPane
        root = new StackPane();
         webView = new WebView();
         webView.getEngine().load("http://www.google.com");
      //  webView.getEngine().load("https://reader-content.vhlcentral.com/vhl1-prod/products/es21_l1-na-vtext/3/assets/images/thumbnail/ES21_L1_SE_U1_030-085_0_hi53.jpg");
       // webView.getEngine().loadContent(" <iframe width=\"560\" height=\"315\" src=\"https://www.baidu.com/"+"</iframe>");
       // webView.getEngine().loadContent(" <iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/q6wqfxYILMM\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
//       JSObject js = (JSObject) webView.getEngine().executeScript("dd.doTrack()");
        
//       js.setMember("num", 123456789);
       
        //add button inside pane.
        //root.getChildren().add(button);
        root.getChildren().add(webView);
        //Construct a new JFX scene.
        scene = new Scene(root,600,400);
        
        fXPanel = new JFXPanel();
        fXPanel.setScene(scene);
        this.add(fXPanel);
        //this.ssetVisiable(true);
       });
      // setVisiable(true);
   }
   
   
   
    public static void main(String[] args)
    {
       // Application.launch(args);
        new TestHtmlFrame();
    }
}


