module edu.ai.aifinalprojectcheckers {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    exports edu.ai.tests.anygame;
    exports edu.ai.tests.checkers;
    exports edu.ai.tests.checkers.moves;
    exports edu.ai.tests.players;
    //opens edu.ai.mainproj.main to javafx.fxml;
    opens edu.ai.mainproj.main to javafx.graphics;
    opens edu.ai.mainproj.gui to javafx.graphics;
}