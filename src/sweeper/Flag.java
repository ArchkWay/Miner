package sweeper;

class Flag // верхний слой c флагами
{
    private Map flagMap;
    private int countOfclosedBoxes;//считаем закрытые боксы

    void start ()
    {
        flagMap = new Map(Box.closed);
        countOfclosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get (Coord coord)//получаем бокс
    {
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord)//открываем бокс
    {
        flagMap.set(coord, Box.opened);
        countOfclosedBoxes--;
    }

    void toggleFlagedToBox (Coord coord)//помечаем клетку
    {
        switch (flagMap.get(coord))
        {
            case flaged : setClosedToBox (coord); break;//снять флаг
            case closed : setFlagedToMine(coord); break;//поставить флаг
        }
    }

    private void setClosedToBox(Coord coord)//снять флаг
    {
        flagMap.set(coord, Box.closed);
    }

    public void setFlagedToMine(Coord coord)//поставить флаг
    {
        flagMap.set(coord, Box.flaged);
    }

    int getCountOfClosedBoxes()//количество не тронутых боксов
    {
        return countOfclosedBoxes;
    }

    void setExplodedToBox(Coord coord)//картинка мины меняется на взрыв
    {
        flagMap.set(coord, Box.exploded);
    }

    void setOpenedToClosedMineBox(Coord coord)//картинка с открытым боксом
    {
        if (flagMap.get(coord) == Box.closed)
            flagMap.set(coord, Box.opened);
    }

    void setNoMineToFlagedSafeBox(Coord coord)//если не угадали с флажком - ставим nomine
    {
        if (flagMap.get(coord) == Box.flaged)
            flagMap.set(coord, Box.nomine);
    }

    int getCountOfFlagedBoxesAround (Coord coord)//сравниваем количество флагов с количеством мин, считаем количество клеток свободных от мин
    {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == Box.flaged)
                count++;
        return count;
    }
}