package sweeper;

public class Game//основной класс с игровыми функциями
{
    private Mine mine;//объект мина
    private Flag flag;//объект влаг
    private GameState state;//состояние игры
    int lives;//реализация варианта с жизнями

    public void setLives(int lives) { //устанавливаем кол-во жизней
        this.lives = lives;
    }

    public GameState getState()//получаем состояние
    {
        return state;
    }

    public Game (int cols, int rows, int mines)//инициализация основных функций
    {
        Ranges.setSize(new Coord(cols, rows));
        mine = new Mine(mines);
        flag = new Flag();
    }

    public void start ()//запуск процесса
    {
        mine.start();
        flag.start();
        state = GameState.played;
    }

    public Box getBox (Coord coord)//содержимое ящика
    {
        if (flag.get(coord) == Box.opened)
            return mine.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton (Coord coord)//работа левой кнопка мыши
    {
        if (gameOver ()) return;
        openBox (coord);
        checkWinner();
    }

    private void checkWinner ()//флаги=мины, победа
    {
        if (state == GameState.played)
        {
            if (flag.getCountOfClosedBoxes() == mine.getTotalMines())
                state = GameState.winner;
        }
    }

    private void openBox(Coord coord)//вскрываем бокс, варианты событий
    {
        switch (flag.get(coord))
        {
            case opened : setOpenedToClosedBoxesAroundNumber(coord);return;
            case flaged : return;
            case closed :
                switch (mine.get(coord))
                {
                    case zero: openBoxesAround (coord); return;
                    case mine: if(lives==0){
                        openMines (coord);
                     return;
                    }
                    else lives--;
                    default: flag.setOpenedToBox(coord); return;
                }
        }
    }

    private void openMines(Coord exploded)//попадание на мину
    {
        state = GameState.exploded;
        flag.setExplodedToBox(exploded);
        for (Coord coord : Ranges.getAllCoords() )
            if(mine.get(coord) == Box.mine)
                flag.setOpenedToClosedMineBox (coord);
            else
                flag.setNoMineToFlagedSafeBox (coord);

    }

    private void openBoxesAround(Coord coord)//вскрываем соседние боксы
    {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord)//правая кнопка - устанавливаем/снимаем флажки
    {
        if (gameOver ()) return;
        flag.toggleFlagedToBox (coord);
    }

    private boolean gameOver()//останавливаем игру
    {
        if (state == GameState.played)
            return false;
        //start();
        return true;
    }

    private void setOpenedToClosedBoxesAroundNumber (Coord coord)//открывваем соседние боксы, если они безопасны
    {
        if (mine.get(coord) != Box.mine)
        {
            if (flag.getCountOfFlagedBoxesAround(coord) == mine.get(coord).getNumber())//открываем все клетки где точно нет мин
            {
                for (Coord around : Ranges.getCoordsAround(coord))
                {
                    if (flag.get(around) == Box.closed)
                        openBox(around);
                }
            }
        }
    }

}