package sweeper;

class Map//карта боксов
{
    private Box[] [] map;

    Map(Box defaultBox)
    {
        map = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for(Coord coord : Ranges.getAllCoords())
            map [coord.x] [coord.y] = defaultBox;
    }

    Box get (Coord coord)
    {
        if(Ranges.inRange (coord)) //проверяем влезают ли введённые координаты в наши настройки
        return map [coord.x] [coord.y];
        return null;
    }

    void set (Coord coord, Box box)
    {
        map [coord.x] [coord.y] = box;
    }
}
