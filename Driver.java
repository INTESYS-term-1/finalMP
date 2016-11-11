import javax.swing.UIManager;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try 
		{ 
		    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
		} 
		catch(Exception e){ 
		}
		Gui gui = new Gui();

	}

}
