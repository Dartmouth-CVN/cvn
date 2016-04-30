package model;

import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

/**
 * Created by mrampiah on 4/30/16.
 */
public class CheckBoxCellFactory implements Callback {
    @Override
    public TableCell call(Object param) {
        CheckBoxTableCell<AbsRelationWrapper, Boolean> checkBoxCell = new CheckBoxTableCell();
        return checkBoxCell;
    }
}