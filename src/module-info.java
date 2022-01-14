module main
{
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires java.sql;

    opens dbviewer;
    opens dbviewer.form;
}