package com.ventasimple.domain.i18n;

/** Sujeto observable del patrón Observer. */
public interface IObservable {
    void addObserver(IObserver o);
    void removeObserver(IObserver o);
    void notificar();
}
