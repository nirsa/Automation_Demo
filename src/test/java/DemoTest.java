import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemoTest {


    @Test
    public void test1() throws IOException {

        //String cmdrun = "cmd /c start cmd.exe /K \"C:\\Jenkins_Deploy\\Test1_hello.exe";
        final List<String> commands = new ArrayList<String>();

        commands.add("cmd.exe");
        commands.add("/C");
        commands.add("start");
        commands.add("cmd.exe");
        commands.add("/K");
        commands.add("C:\\Jenkins\\workspace\\GreetingApp\\Test1_hello.exe");
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.start();
        wait(5000);
        System.out.println( "I am running Autoit" );
        boolean flag=false;
        String expected = "C:\\Jenkins\\workspace\\GreetingApp\\GOOD.txt";
        String results = "C:\\Jenkins\\workspace\\GreetingApp\\Results.txt";
        java.io.File needed = new java.io.File("C:\\Jenkins\\workspace\\GreetingApp\\Results.txt");
        while( !needed.exists() ) {
            try {
                Thread.sleep( 10000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
          flag =  compareFiles(expected,results);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(flag);

    }

//    public void runCmdCommand(String command){
//        try {
//            Runtime.getRuntime().exec(command);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                wait(2000);
//                Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private void wait(int miliSeconds){
        try {
            Thread.sleep(miliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean compareFiles(String file1, String file2) throws IOException{
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));

        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        String line1 = reader1.readLine();

        String line2 = reader2.readLine();

        boolean areEqual = true;

        int lineNum = 1;

        while (line1 != null || line2 != null)
        {
            if(line1 == null || line2 == null)
            {
                areEqual = false;

                break;
            }
            else if(! line1.equalsIgnoreCase(line2))
            {
                areEqual = false;

                break;
            }

            line1 = reader1.readLine();

            line2 = reader2.readLine();

            lineNum++;
        }

        if(areEqual)
        {
            System.out.println("Two files have same content.");
            return true;
        }
        else
        {
            System.out.println("Two files have different content. They differ at line "+lineNum);

            System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
            return false;
        }


    }

}








