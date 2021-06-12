package cn.xmut.edu;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;


 
public class MyGameFrame extends JFrame {
	Date startTime = new Date();    //游戏起始时刻
	Date endTime;  //游戏结束时刻
	 Image bgImg = GameUtil.getImage("image/pg.png");
     Image planeImg = GameUtil.getImage("image/plane.png");
    
     Plane plane = new Plane(planeImg,300,300,3);
 
    ArrayList<Shell>  shellList = new ArrayList<Shell>();    
     
     
     //paint方法作用是：画出整个窗口及内部内容。被系统自动调用。
     @Override
     public void paint(Graphics g) {  
         g.drawImage(bgImg, 0, 0, null);
         plane.drawMySelf(g);//  画飞机
       //画出容器中所有的子弹
         for(int i=0;i<shellList.size();i++){
             Shell b =  shellList.get(i);
             b.draw(g);
             //飞机和所有炮弹对象进行矩形检测
             boolean peng = b.getRect().intersects(plane.getRect());
             if(peng){
                 plane.live = false;   //飞机死掉,画面不显示
           
             }
         }
         
         if(!plane.live){
             if(endTime==null){
                 endTime = new Date();
             }
             int period = (int)((endTime.getTime()-startTime.getTime())/1000);
             printInfo(g, "时间："+period+"秒", 50, 120, 260, Color.white);
         }
         
       
     }
     
     public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
         Color c = g.getColor();
         g.setColor(color);
         Font f = new Font("宋体",Font.BOLD,size);
         g.setFont(f);
         g.drawString(str,x,y);
         g.setColor(c);
     }  
     //刷新窗口
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
     //键盘监听类
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
        //在游戏窗口打印标题
        setTitle("czxde期末");
        //窗口默认不可见，设为可见
        setVisible(true);
        //窗口大小：宽度500，高度500
        setSize(500, 500);
        //窗口左上角顶点的坐标位置
        setLocation(300, 300);
        
        for(int i=0;i<50;i++){
            Shell b = new Shell();
            shellList.add(b);
        }
        addKeyListener(new KeyMonitor());//增加键盘的监听
//        //增加关闭窗口监听，这样用户点击右上角关闭图标，可以关闭游戏程序
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