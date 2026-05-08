module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires transitive javafx.graphics;
    requires javafx.base;

    opens app to javafx.fxml;
    opens app.controller.card to javafx.fxml;
    opens app.controller.modal to javafx.fxml;
    opens app.controller.page to javafx.fxml;

    exports app;
    exports app.controller.card;
    exports app.controller.modal;
    exports app.controller.page;
    exports app.io;
    exports app.model;
    exports app.util;
}