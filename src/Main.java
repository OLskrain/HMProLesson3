import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        readFileToByteArray();
        mergeFiles();
        readFileByPage();
    }
    //Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
    public static void readFileToByteArray(){
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try{
            in = new BufferedInputStream(new FileInputStream("1.txt"));
            out = new ByteArrayOutputStream();
            int x;
            while((x = in.read()) != -1) out.write(x); //пока не дойдем до конца. читаем файл
            byte[] bytes = out.toByteArray(); //байтовый массив, куда мы записываем то что считали
            System.out.println(new String(bytes)); //строка может создаваться из byArray
        }catch(IOException e){
            e.printStackTrace();
        }finally{ //закрытие потоков всегда нужно закрытвать в файноли!
            try{
                in.close();
                out.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    //Последовательно сшить 5 файлов в один (файлы также ~100 байт).
    public static void mergeFiles() throws IOException{
        ArrayList<InputStream> arrayList = new ArrayList<>();
        arrayList.add(new FileInputStream("2.txt"));
        arrayList.add(new FileInputStream("3.txt"));
        arrayList.add(new FileInputStream("4.txt"));
        BufferedInputStream in = new BufferedInputStream(new SequenceInputStream(Collections.enumeration(arrayList)));
        int x;
        while((x = in.read()) != -1) System.out.print((char) x);
        in.close();
    }
    //Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb), вводим страницу,
    // программа выводит ее в консоль (за страницу можно принять 1800 символов). Время чтения файла должно находится в разумных пределах
    // (программа не должна загружаться дольше 10 секунд), ну и чтение тоже не должно занимать >5 секунд.

    public static void readFileByPage() throws IOException{
        final int PAGE_SIZE = 1800;
        RandomAccessFile randomAccessFile = new RandomAccessFile("5.txt", "rw");//RandomAccessFile, потому что у него есть удлбный метод,
        //который позволяет перепрыгивать на нужное число байт
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter page: "); //номер введенной страницы
        int page = scanner.nextInt() - 1; //-1 потому что пользов. введет №1 а система читает с 0.
        randomAccessFile.seek(page*PAGE_SIZE); //говорим читать
        byte[] bytes = new byte[PAGE_SIZE];
        randomAccessFile.read(bytes);
        System.out.println(new String(bytes));
        randomAccessFile.close();
    }

}
