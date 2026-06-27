package com.ventasimple.domain.i18n;

/**
 * Observador. Se usa actualizar() (y no notify()) porque Object.notify() es
 * final en Java y no puede sobrescribirse.
 */
public interface IObserver {
    void actualizar();
}
