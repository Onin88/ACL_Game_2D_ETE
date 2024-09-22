module ete {
    requires javafx.controls;
    requires javafx.fxml;
    requires libtiled;
    requires java.desktop;
    requires java.xml.bind;

    opens ete to javafx.fxml;
    exports ete;
}