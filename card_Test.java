
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

import java.io.File;
import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.imageio.*;


import java.io.BufferedReader;
import java.io.FileReader;

public class card_Test{
    static StringBuilder sb = new StringBuilder();
    static public void getDeckData(String isr){
        String line;
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader(isr);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
/*
//卡片數值產生
        rand_Card_Value rcv;
        rcv = new rand_Card_Value();
        int num1;
        System.out.println("How many set do you want to generate ?"); 
        Scanner scanner = new Scanner(System.in);
        num1 = scanner.nextInt();
        rcv.generate_Card_Value(num1);
        scanner.close();
*/
        /*
       // 卡片物件生成
        Card cardA;
        cardA = new Card(args[0]);
        short a = cardA.get_card_Score();
        System.out.println(a);
        */

        picture_Generator picGen;
        picGen = new picture_Generator();

        getDeckData("./cardInfo.txt");
        String[] tokens = (sb.toString()).split(" ");
        for (int i = 0 ; i<tokens.length ; i++) {
            String token = tokens[i];
            picGen.generate(token,i);
        }

        System.out.print("Picture is generated.");

    }
}
class rand_Card_Value{
    int random,value_Limit;
    Integer []value;
    String []pabcd = {"1000","0100","0010","0001"};


    public void generate_Card_Value (int num){

        value = new Integer [4];
        List<Integer> intList;

        try{
            FileWriter fw = new FileWriter("./carddata.txt");

            for(int i = 0 ; i < num ; i++){
                random = (int)(Math.random()*(2-0+1)) + 0;//score
                System.out.print(random);   
                fw.write(String.valueOf(random));//w
                         
                random = (int)(Math.random()*(3-0+1)) + 0;//permanent
                System.out.print(pabcd[random]);
                fw.write(pabcd[random]);//w  

                value_Limit = (int)(Math.random()*(5-3+1)) + 3;
                
                int j = 0;
                while(j<4){
                    random = (int)(Math.random()*(3-0+1)) + 0; 

                    if(value_Limit-random>=0){
                        value_Limit-=random;
                        value[j]=random; 
                        j++;
                    }           
                }

                intList = Arrays.asList(value);

                Collections.shuffle(intList);

                intList.toArray(value);
                for(int a:value){
                    System.out.print(a);     //need
                    fw.write(String.valueOf(a)); //w              
                }

                System.out.println();   
                fw.write("\r\n"); 
            }    
            fw.flush();
            fw.close();    
        }
        catch (IOException e) {
            e.printStackTrace();
        }




    }
}
class Card {

    private short card_Score;
    private short permanent_PointA;
    private short permanent_PointB;
    private short permanent_PointC;
    private short permanent_PointD;
    private short need_PointA;
    private short need_PointB;
    private short need_PointC;
    private short need_PointD;
    private String card_Picture;
    public Card(String card_Info,String card_Picture){
        card_Score =  (short)(card_Info.charAt(0)-'0');
        permanent_PointA = (short)(card_Info.charAt(1)-'0');
        permanent_PointB = (short)(card_Info.charAt(2)-'0');
        permanent_PointC = (short)(card_Info.charAt(3)-'0');
        permanent_PointD = (short)(card_Info.charAt(4)-'0');
        need_PointA = (short)(card_Info.charAt(5)-'0');
        need_PointB = (short)(card_Info.charAt(6)-'0');
        need_PointC = (short)(card_Info.charAt(7)-'0');
        need_PointD = (short)(card_Info.charAt(8)-'0');
        this.card_Picture = card_Picture;
    }
    public short getCard_Score() {
        return card_Score;
    }

    public short getPermanent_PointA() {
        return permanent_PointA;
    }

    public short getPermanent_PointB() {
        return permanent_PointB;
    }

    public short getPermanent_PointC() {
        return permanent_PointC;
    }

    public short getPermanent_PointD() {
        return permanent_PointD;
    }

    public short getNeed_PointA() {
        return need_PointA;
    }

    public short getNeed_PointB() {
        return need_PointB;
    }

    public short getNeed_PointC() {
        return need_PointC;
    }

    public short getNeed_PointD() {
        return need_PointD;
    }
}

class picture_Generator{  
    public void generate(String card_Parameter,int card_Number){

        int card_Score =  (int)(card_Parameter.charAt(0)-'0');
        int permanent_PointA = (int)(card_Parameter.charAt(1)-'0');
        int permanent_PointB = (int)(card_Parameter.charAt(2)-'0');
        int permanent_PointC = (int)(card_Parameter.charAt(3)-'0');
        int permanent_PointD = (int)(card_Parameter.charAt(4)-'0');
        int need_PointA = (int)(card_Parameter.charAt(5)-'0');
        int need_PointB = (int)(card_Parameter.charAt(6)-'0');
        int need_PointC = (int)(card_Parameter.charAt(7)-'0');
        int need_PointD = (int)(card_Parameter.charAt(8)-'0');

        int width = 250;
        int height = 400;
        File folder_Create = new File("./card_Picture/generated/");
        folder_Create.mkdir();
        File file_PartA = new File("./card_Picture/coin_a.png");
        File file_PartB = new File("./card_Picture/coin_b.png");
        File file_PartC = new File("./card_Picture/coin_c.png");
        File file_PartD = new File("./card_Picture/coin_d.png");

        File file_Part_Border = new File("./card_Picture/image_border.png");

        File file = new File("./card_Picture/generated/c"+String.valueOf(card_Number)+".png");
        Font font = new Font("Times New Roman", Font.BOLD, 70);
        Font font2 = new Font("Times New Roman", Font.BOLD, 50);
        //建立一個畫布
        try {         
            BufferedImage part_TokenA = ImageIO.read(file_PartA);
            BufferedImage part_TokenB = ImageIO.read(file_PartB);
            BufferedImage part_TokenC = ImageIO.read(file_PartC);
            BufferedImage part_TokenD = ImageIO.read(file_PartD);
            BufferedImage part_Border = ImageIO.read(file_Part_Border);

            int part_Token_Width = part_TokenA.getWidth();
            int part_Token_Height = part_TokenA.getHeight();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //獲取畫布的畫筆
            Graphics2D g2 = (Graphics2D)bi.getGraphics();

            //開始繪圖

            g2.setPaint(Color.black);



            g2.setFont(font2);
            g2.drawImage(part_Border, 0, 0, width,height,null);// permanent token 

            if(permanent_PointA>0){
                g2.drawImage(part_TokenA, 11, 11, part_Token_Width,part_Token_Height,null);// permanent token A
            }
            else if(permanent_PointB>0){
                g2.drawImage(part_TokenB, 11, 11, part_Token_Width,part_Token_Height,null);// permanent token B
            }
            else if(permanent_PointC>0){
                g2.drawImage(part_TokenC, 11, 11, part_Token_Width,part_Token_Height,null);// permanent token C
            }
            else{
                g2.drawImage(part_TokenD, 11, 11, part_Token_Width,part_Token_Height,null);// permanent token D
            }


            if(need_PointA>0){
                g2.drawImage(part_TokenA, 11, 100, part_Token_Width,part_Token_Height,null);// need token A
                g2.drawString("x"+String.valueOf(need_PointA), 11+part_Token_Width+5, 100+part_Token_Height); //繪製字串  

            }
            if(need_PointB>0){
                g2.drawImage(part_TokenB, 11, 100+part_Token_Height+10, part_Token_Width,part_Token_Height,null);// need token B
                g2.drawString("x"+String.valueOf(need_PointB), 11+part_Token_Width+5, 100+part_Token_Height+part_Token_Height+10); //繪製字串  
            }
            if(need_PointC>0){        
                g2.drawImage(part_TokenC, 11, 100+(part_Token_Height+10)*2, part_Token_Width,part_Token_Height,null);// need token C
                g2.drawString("x"+String.valueOf(need_PointC), 11+part_Token_Width+5, 100+part_Token_Height+(part_Token_Height+10)*2); //繪製字串  
            }
            if(need_PointD>0){
                g2.drawImage(part_TokenD, 11, 100+(part_Token_Height+10)*3, part_Token_Width,part_Token_Height,null);// need token D
                g2.drawString("x"+String.valueOf(need_PointD), 11+part_Token_Width+5, 100+part_Token_Height+(part_Token_Height+10)*3); //繪製字串  
            }
            g2.setFont(font);
            g2.drawString(String.valueOf(card_Score), 200, 65); //繪製字串 
  

            //將生成的圖片儲存為jpg格式的檔案。ImageIO支援jpg、png、gif等格式
            ImageIO.write(bi, "png", file);  
        }
        catch (IOException e) {  
            System.out.println("Generate error.");  
            e.printStackTrace();  
        }  

               
    }
     
}  
/*
001000320
101000300
201002010
010000300
000103100
101001003
200012001
100101130
100010030
110003000
200010102
001000102
101002000
201000030
101001121
000011010
200010120
100012001
101000111
201000010
110000310
010000032
000010211
010001011
001001011
200011300
101000110
201003002
001000103
001000300
110000101
100101030
100010003
001000210
210001022
000101130
200101300
210000310
000013100
101002010
*/