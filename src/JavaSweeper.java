import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaSweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;
//Настройки игры
    private final int COLS = 9; // столбцы
    private final int ROWS = 9; // строки
    private final int MINES = 10; // количество бомб
    private final int IMAGE_SIZE = 40; // размер картинки одинаковый по x и по y




    public static void main(String[] args)
    {
        new JavaSweeper();
    }

    private JavaSweeper ()
    {
        game = new Game(COLS, ROWS, MINES);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
        game.setLives(0);//можем поставить количество жизней, по умолчанию 0
    }

    private void initLabel ()// стартовая надпись
    {
        label = new JLabel("Мины! Они повсюду...");
        add (label, BorderLayout.SOUTH);
    }

    private void initPanel ()// иницируем панель с игрой
    {
        panel = new JPanel() // при инициализации выводим картинки
        {
            @Override
            protected void paintComponent(Graphics g) //прорисовка
            {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                {
                    //g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this); //приведение типа к Image
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, this); //приведение типа к Image
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() { //подключаем мышку
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) // левая кнопка мыши
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3) // правая кнока мыши
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2) // средняя кнопка мыши
                    game.start (); // перезапускаем игру
                label.setText(getMessage ());
                panel.repaint(); // после каждого действия мыши перерисовываем панель игры
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getMessage()//сообщения во время игры
    {
        switch (game.getState())
        {
            case played: return "Thiiink";
            case exploded: return "BOOOOM!!11 Now you dead, silly! Hehe..I mean - sad face:( ";
            case winner: return "Congratulations! You are ALIVE! Mr.Smarty-Pants:)!";
            default: return "Wellcome, I Wanna Play a Game";
        }
    }

    private void initFrame ()//создаём рамку
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Miner Sweeper");
        setResizable(false);
        setVisible(true);
        pack(); //метод из класса JFrame устанавливает размер окна достаточный для отображения
        setIconImage(getImage("icon"));
        setLocationRelativeTo(null);

    }

    private void setImages ()//ставим картинки
    {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name)//получаем картинки
    {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename)); //подключаем папку с ресурсами(картинками)
        return icon.getImage();
    }
}