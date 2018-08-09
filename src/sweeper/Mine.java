package sweeper;

public class Mine
{
    private Map mineMap; //карта мин
    private int totalMines;

    Mine(int totalMines)//фиксируем кол-во мин
    {
        this.totalMines = totalMines;
        fixMineCount();
    }

    void start()//выставляем мины
    {
        mineMap = new Map(Box.zero);
        for(int i = 0; i < totalMines; i ++)
        placeMine();
    }

    Box get (Coord coord)
    {
        return mineMap.get(coord);
    }

    private void fixMineCount ()//ограничение на кол-во мин в настройках
    {
        int maxMines = Ranges.getSize().x * Ranges.getSize().y / 2;
        if(totalMines > maxMines)
            totalMines = maxMines;
    }

    private void placeMine()//расставляем мины
    {
        while (true)
        {
            Coord coord = Ranges.getRandomCoord();
            if(Box.mine == mineMap.get(coord))
                continue;
            mineMap.set(coord, Box.mine);
            incNumberAroundMine(coord);
            break;
        }
    }

    private void incNumberAroundMine (Coord coord)//увеличение цифры в зависимости от кол-ва мин
    {
        for(Coord around : Ranges.getCoordsAround(coord))
            if (Box.mine != mineMap.get(around))
            mineMap.set(around, mineMap.get(around).getNextNumberBox());
    }

    int getTotalMines()//получаем кол-во мин
    {
        return totalMines;
    }
}
