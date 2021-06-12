package cn.xmut.edu;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;


 
public class MyGameFrame extends JFrame {
	Date startTime = new Date();    //��Ϸ��ʼʱ��
	Date endTime;  //��Ϸ����ʱ��
	 Image bgImg = GameUtil.getImage("image/pg.png");
     Image planeImg = GameUtil.getImage("image/plane.png");
    
     Plane plane = new Plane(planeImg,300,300,3);
 
    ArrayList<Shell>  shellList = new ArrayList<Shell>();    
     
     
     //paint���������ǣ������������ڼ��ڲ����ݡ���ϵͳ�Զ����á�
     @Override
     public void paint(Graphics g) {  
         g.drawImage(bgImg, 0, 0, null);
         plane.drawMySelf(g);//  ���ɻ�
       //�������������е��ӵ�
         for(int i=0;i<shellList.size();i++){
             Shell b =  shellList.get(i);
             b.draw(g);
             //�ɻ��������ڵ�������о��μ��
             boolean peng = b.getRect().intersects(plane.getRect());
             if(peng){
                 plane.live = false;   //�ɻ�����,���治��ʾ
           
             }
         }
         
         if(!plane.live){
             if(endTime==null){
                 endTime = new Date();
             }
             int period = (int)((endTime.getTime()-startTime.getTime())/1000);
             printInfo(g, "ʱ�䣺"+period+"��", 50, 120, 260, Color.white);
         }
         
       
     }
     
     public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
         Color c = g.getColor();
         g.setColor(color);
         Font f = new Font("����",Font.BOLD,size);
         g.setFont(f);
         g.drawString(str,x,y);
         g.setColor(c);
     }  
     //ˢ�´���
     class PaintThread extends Thread {
         public void run(){
             while(true){
                 repaint();
                 try {
                     Thread.sleep(40); //1s = 1000ms
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }   
             }
         }
     } 
     //���̼�����
     class KeyMonitor extends KeyAdapter {
         @Override
         public void keyPressed(KeyEvent e) {

			plane.addDirection(e);
         }
  
         @Override
         public void keyReleased(KeyEvent e) {
             plane.minusDirection(e);
         }
     }
     
     
     
   //  
    public void launchFrame(){
        //����Ϸ���ڴ�ӡ����
        setTitle("czxde��ĩ");
        //����Ĭ�ϲ��ɼ�����Ϊ�ɼ�
        setVisible(true);
        //���ڴ�С�����500���߶�500
        setSize(500, 500);
        //�������ϽǶ��������λ��
        setLocation(300, 300);
        
        for(int i=0;i<50;i++){
            Shell b = new Shell();
            shellList.add(b);
        }
        addKeyListener(new KeyMonitor());//���Ӽ��̵ļ���
//        //���ӹرմ��ڼ����������û�������Ͻǹر�ͼ�꣬���Թر���Ϸ����
//        addComponentListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
        
        new PaintThread().start();
    }    
    public static void main(String[] args) {
        MyGameFrame f = new MyGameFrame();
        f.launchFrame();
        

      
    }
}