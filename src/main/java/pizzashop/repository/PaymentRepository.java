package pizzashop.repository;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private static String filename = "data/payments.txt";
    private List<Payment> paymentList;

    public PaymentRepository(){
        this.paymentList = new ArrayList<>();
        readPayments();
    }

    private void readPayments(){
        File file = new File(filename);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while((line=br.readLine())!=null){
                Payment payment=getPayment(line);
                paymentList.add(payment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Payment getPayment(String line){
        Payment item=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
       Integer tableNumber= Integer.parseInt(st.nextToken());
        String type= st.nextToken();
        Double amount = Double.parseDouble(st.nextToken());
        item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
        return item;
    }

    public void add(Payment payment){
        paymentList.add(payment);
        writeNewPayment(payment);
    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeNewPayment(Payment payment){
        File file = new File(filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                System.out.println(payment.toString());
                bw.write(payment.toString());
                bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
