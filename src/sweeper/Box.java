package sweeper;

public enum Box//задаём перечислением варианты содержимого боксов(картинками из res/img)
{
    zero,
    num1,
    num2,
    num3,
    num4,
    num5,
    num6,
    num7,
    num8,
    mine,
    opened,
    closed,
    flaged,
    exploded,
    nomine;

    public Object image;

    Box getNextNumberBox ()
    {
        return Box.values() [this.ordinal() + 1];
    }

    int getNumber ()
    {
        return this.ordinal();
    }


}