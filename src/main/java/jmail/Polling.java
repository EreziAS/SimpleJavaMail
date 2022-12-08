package jmail;

public class Polling extends Thread{
    private RetrieveMail rm;

    public Polling(RetrieveMail rm){

        this.rm = rm;
    }

    public void run(){
        while(true){
            try {
                System.out.println("Polling");
                Thread.sleep(120000);
                System.out.println("CheckMail");
                rm.Retrieve();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
