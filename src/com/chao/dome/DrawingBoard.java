package com.chao.dome;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawingBoard {
    //创建Frame对象
    private Frame frame=new Frame();
    //创建PopupMenu菜单
    private PopupMenu colorMenu=new PopupMenu();
    //创建颜色单项组件
    private MenuItem redMenu=new MenuItem("红色");
    private MenuItem greenMenu=new MenuItem("绿色");
    private MenuItem blunMenu=new MenuItem("蓝色");

    //定义改色
    private Color varColor=Color.BLACK;

    //设置面板宽高
    private final int WIDTH=400;
    private final int Hight=300;

    //创建Image子类对象
    private BufferedImage image=new BufferedImage(WIDTH,Hight,BufferedImage.TYPE_3BYTE_BGR);

    //创建画笔
    private Graphics g=image.getGraphics();

    //创建画布
    private class Mycanavs extends Canvas{
        @Override
        public void paint(Graphics g) {
            g.drawImage(image,0,0,null);
        }
    }

    //设置鼠标起始位置
    private int preX=-1;
    private int preY=-1;

    //创建画布
    Mycanavs mycanavs=new Mycanavs();
    //组装界面
    public void init(){
        //组装菜单
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String actionCommand = actionEvent.getActionCommand();
                switch (actionCommand){
                    case "红色":
                        varColor=Color.red;
                        break;
                    case "蓝色":
                        varColor=Color.blue;
                        break;
                    case "绿色":
                        varColor=Color.green;
                        break;
                }
            }
        };
        //注册监听
        redMenu.addActionListener(actionListener);
        greenMenu.addActionListener(actionListener);
        blunMenu.addActionListener(actionListener);
        //将这些放入菜单组件中
        colorMenu.add(redMenu);
        colorMenu.add(greenMenu);
        colorMenu.add(blunMenu);
        //将菜单放入画布中
        mycanavs.add(colorMenu);
        //当右键按下抬起时显示菜单
        mycanavs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                boolean popupTrigger = e.isPopupTrigger();
                if (popupTrigger) {
                    colorMenu.show(mycanavs,e.getX(), e.getY());
                }
                preX=-1;
                preY=-1;
            }
        });


        //将画布设置为白色背景
        g.fillRect(0,0,WIDTH,Hight);

        mycanavs.setPreferredSize(new Dimension(WIDTH,Hight));

        //设置鼠标移动监听
        mycanavs.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (preX>0&&preY>0){
                    g.setColor(varColor);
                    g.drawLine(preX,preY,e.getX(),e.getY());
                }
                //更新起始位置
                preX=e.getX();
                preY=e.getY();
                mycanavs.repaint();
            }
        });

        //将画布放入容器中
        frame.add(mycanavs);

        //适配
        frame.pack();
        frame.setVisible(true);

        //设置窗口关闭
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        new DrawingBoard().init();
    }
}
