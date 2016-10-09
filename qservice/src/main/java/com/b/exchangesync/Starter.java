package main.java.com.b.exchangesync;


import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry file that create worker thread.
 */
public class Starter {

    private static final Logger LOG = Logger.getLogger(Starter.class);

    public static void main(String[] args) throws InterruptedException {


        try {
            System.out.println(args[0]);
            if(args.length<1){
                System.out.println("no params were supplied.");
                System.exit(0);
            }else{
                new ClassPathXmlApplicationContext(args[0]);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);

        }




    }

}