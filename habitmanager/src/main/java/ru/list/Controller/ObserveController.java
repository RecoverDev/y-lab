package ru.list.Controller;

import ru.list.Observe;

public interface ObserveController extends Observe{

    void addListener(Observe observe);

    void removeListener(Observe observe);

}
