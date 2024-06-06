module com.example.iss {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.xerial.sqlitejdbc;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens com.example.iss to javafx.fxml;
    exports com.example.iss;
    exports domain;
    opens domain;
    exports repo;
    opens repo to javafx.fxml;

}