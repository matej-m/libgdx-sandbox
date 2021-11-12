package si.um.feri.libgdxsandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import si.um.feri.libgdxsandbox.tictactoe.TicTacToeGame;
import si.um.feri.libgdxsandbox.tictactoe.config.GameConfig;

public class DesktopLauncherTicTacToe {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Tic Tac Toe";
        config.width = (int) GameConfig.WIDTH;
        config.height = (int) GameConfig.HEIGHT;
        config.forceExit = false;
        new LwjglApplication(new TicTacToeGame(), config);
    }
}
